package org.example.webkachkiserver.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Configuration
public class GcsConfig {
    @Value("${gcs.credentials.json}")
    private String credentialsJson;

    @Bean
    public Storage storage() throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(credentialsJson);
        System.out.println(decodedBytes);
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                        new ByteArrayInputStream(decodedBytes))
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
