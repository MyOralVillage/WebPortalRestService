package org.mov.model.file.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.NetworkIOException;
import com.dropbox.core.RetryException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import org.mov.model.file.FileIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

public class DropboxFileIO implements FileIO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DropboxFileIO.class);

    private long chunckedUploadSize;
    private int chunckedUploadMaxAttempts;

    private DbxClientV2 client;

    public DropboxFileIO(String clientIdentifier,
                         String token,
                         long chunckedUploadSize,
                         int chunckedUploadMaxAttempts) {
        this.chunckedUploadSize = chunckedUploadSize;
        this.chunckedUploadMaxAttempts = chunckedUploadMaxAttempts;

        this.client = new DbxClientV2(new DbxRequestConfig(clientIdentifier), token);
    }

    @Override
    public void uploadFile(File fileToUpload, String path) throws Exception {
        if (fileToUpload.length() >= chunckedUploadSize) chunckedFileUpload(fileToUpload, path);
        else singleFileUpload(fileToUpload, path);
    }

    @Override
    public void downloadFile(OutputStream outputStream, String path) throws Exception {
        this.client.files().downloadBuilder(path).download(outputStream);
    }

    private void singleFileUpload(File fileToUpload, String dropboxPath) throws Exception {
        try (InputStream in = new FileInputStream(fileToUpload)) {
            LOGGER.info("Single file uploading started.");
            client.files().uploadBuilder(dropboxPath)
                    .withMode(WriteMode.OVERWRITE)
                    .withClientModified(new Date(fileToUpload.lastModified()))
                    .uploadAndFinish(in);
            LOGGER.info("Single file uploading finished.");
        } catch (DbxException e) {
            throw new DbxException("Error uploading to Dropbox: " + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Error reading from file \"" + fileToUpload + "\": " + e.getMessage());
        }
    }

    private void chunckedFileUpload(File fileToUpload, String dropboxPath) throws Exception {
        UploadSessionCursor cursor;
        DbxException thrown = null;
        String sessionId = null;
        long uploaded = 0L;
        long size = fileToUpload.length();

        LOGGER.info("Chuncked file uploading started.");
        for (int i = 0; i < chunckedUploadMaxAttempts; i++) {
            if (i > 0) {
                LOGGER.info("Retrying chunked upload (%d / %d attempts)\n", i + 1, chunckedUploadMaxAttempts);
            }

            try (InputStream in = new FileInputStream(fileToUpload)) {
                in.skip(uploaded);

                if (sessionId == null) {
                    sessionId = client.files().uploadSessionStart()
                            .uploadAndFinish(in, chunckedUploadSize)
                            .getSessionId();
                    uploaded += chunckedUploadSize;
                    printProgress(uploaded, size);
                }

                cursor = new UploadSessionCursor(sessionId, uploaded);
                while ((size - uploaded) > chunckedUploadSize) {
                    client.files().uploadSessionAppendV2(cursor).uploadAndFinish(in, chunckedUploadSize);
                    uploaded += chunckedUploadSize;
                    printProgress(uploaded, size);
                    cursor = new UploadSessionCursor(sessionId, uploaded);
                }

                CommitInfo commitInfo = CommitInfo.newBuilder(dropboxPath)
                        .withMode(WriteMode.OVERWRITE)
                        .withClientModified(new Date(fileToUpload.lastModified()))
                        .build();
                client.files().uploadSessionFinish(cursor, commitInfo)
                        .uploadAndFinish(in, size - uploaded);
                LOGGER.info("Chuncked file uploading finished.");
                return;
            } catch (RetryException ex) {
                thrown = ex;
                sleepQuietly(ex.getBackoffMillis());
            } catch (NetworkIOException ex) {
                thrown = ex;
            } catch (UploadSessionLookupErrorException ex) {
                if (ex.errorValue.isIncorrectOffset()) {
                    thrown = ex;
                    uploaded = ex.errorValue.getIncorrectOffsetValue().getCorrectOffset();
                } else {
                    thrown = new DbxException("Error uploading to Dropbox: " + ex.getMessage());
                    break;
                }
            } catch (UploadSessionFinishErrorException ex) {
                if (ex.errorValue.isLookupFailed() && ex.errorValue.getLookupFailedValue().isIncorrectOffset()) {
                    thrown = ex;
                    uploaded = ex.errorValue.getLookupFailedValue().getIncorrectOffsetValue().getCorrectOffset();
                } else {
                    thrown = new DbxException("Error uploading to Dropbox: " + ex.getMessage());
                    break;
                }
            } catch (DbxException e) {
                throw new DbxException("Error uploading to Dropbox: " + e.getMessage());
            } catch (IOException e) {
                throw new IOException("Error reading from file \"" + fileToUpload + "\": " + e.getMessage());
            }
        }

        throw new DbxException("Maxed out upload attempts to Dropbox. Most recent error: " + thrown.getMessage());
    }

    private void printProgress(long uploaded, long size) {
        LOGGER.info("Uploaded %12d / %12d bytes (%5.2f%%)\n", uploaded, size, 100 * (uploaded / (double) size));
    }

    private void sleepQuietly(long millis) throws InterruptedException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            throw new InterruptedException("Error uploading to Dropbox: interrupted during backoff.");
        }
    }
}
