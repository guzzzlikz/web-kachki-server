package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.example.webkachkiserver.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return authService.login(user);
    }
}
