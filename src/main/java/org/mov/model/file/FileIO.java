package org.mov.model.file;

import java.io.File;

public interface FileIO {
    void uploadFile(File fileToUpload, String path) throws Exception;
}
