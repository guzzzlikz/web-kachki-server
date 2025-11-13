package org.example.webkachkiserver.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
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
    @Autowired
    private JWTService jwtService;

    public ResponseEntity<?> register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email address already in use");
        }
        if (userRepository.existsByName(user.getName())) {
            return ResponseEntity.badRequest().body("Name already in use");
        }
        user.setPassword(hashComponent.hashToString(user.getPassword()));
        userRepository.save(user);
        Map<String, String> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("type", user.getType().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String s;
        try {
            s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(jwtService.generateToken(s));
    }

    public ResponseEntity<?> login(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail());
        if(dbUser != null && hashComponent.hashToString(user.getPassword()).equals(dbUser.getPassword())) {
            return ResponseEntity.ok().body(jwtService.generateToken(user.getEmail()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    public ResponseEntity<?> getId(String token) {
        String email = jwtService.getDataFromToken(token);
        long id = userRepository.findByEmail(email).getId();
        return ResponseEntity.ok(id);
    }
}
