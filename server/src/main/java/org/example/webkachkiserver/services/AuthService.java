package org.example.webkachkiserver.services;

import org.example.webkachkiserver.components.HashComponent;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashComponent hashComponent;

    public ResponseEntity<?> register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email address already in use");
        }
        user.setPassword(hashComponent.hash(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> login(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail());
        if(dbUser != null && hashComponent.hash(user.getPassword()).equals(dbUser.getPassword())) {
            // TODO(add token)

            Map<String, Object> response = new HashMap<>();

            response.put("email", dbUser.getEmail());
            response.put("name", dbUser.getName());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
}
