package org.example.webkachkiserver.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@Configuration
public class GcsConfig {
    @Value("${gcp.credentials.location}")
    private String credentialsPath;

    @Bean
    public Storage storage() throws IOException {
        if (credentialsPath == null || credentialsPath.isEmpty()) {
            throw new IllegalStateException("GOOGLE_APPLICATION_CREDENTIALS environment variable not set");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(
                        new FileInputStream(credentialsPath))
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
