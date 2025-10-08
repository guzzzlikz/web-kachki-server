package org.example.webkachkiserver.services;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.HexFormat;

@Service
public class HashService {
    public static String hash(String input) {
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
