package org.mov.model.file;

import java.io.File;
import java.io.OutputStream;

public interface FileIO {
    void uploadFile(File fileToUpload, String path) throws Exception;

    void downloadFile(OutputStream outputStream, String path) throws Exception;

    String getSharedLink(String filePath) throws Exception;
}
