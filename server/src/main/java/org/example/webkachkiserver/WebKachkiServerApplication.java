package org.example.webkachkiserver;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebKachkiServerApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("server")
                .load();
        System.setProperty("GCS.CREDENTIALS.BASE64", dotenv.get("GCS.CREDENTIALS.BASE64"));
        SpringApplication.run(WebKachkiServerApplication.class, args);
    }
}
