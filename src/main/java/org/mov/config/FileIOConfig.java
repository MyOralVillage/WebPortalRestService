package org.mov.config;

import org.mov.model.file.FileIO;
import org.mov.model.file.dropbox.DropboxFileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:spring/file-io.properties")
public class FileIOConfig {
    private final Environment env;

    @Autowired
    public FileIOConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public FileIO fileIoHandler() {
        return new DropboxFileIO(env.getProperty("dropboxClientId"),
                env.getProperty("dropboxToken"),
                Long.parseLong(env.getProperty("dropboxChunckedUploadSize")),
                Integer.parseInt(env.getProperty("dropboxChunckedUploadMaxAttempts")));
    }
}
