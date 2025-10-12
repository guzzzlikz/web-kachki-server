package org.example.webkachkiserver.components;

import org.springframework.stereotype.Component;
import java.security.MessageDigest;
import java.util.HexFormat;

@Component
public class HashComponent {
    public String hash(String input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(input.getBytes());
        return  HexFormat.of().formatHex(hash);
    }
}
