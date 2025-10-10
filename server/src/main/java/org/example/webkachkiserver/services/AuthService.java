package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashService hashService;

    public ResponseEntity<?> login(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email address already in use");
        }
        user.setPassword(hashService.hash(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
