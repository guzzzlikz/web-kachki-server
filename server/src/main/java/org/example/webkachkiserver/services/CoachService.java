package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.user.TYPE;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> getCoachesForIndex() {
        List<User> coaches = userRepository.findAllByType(TYPE.COACH);
        coaches.sort((c1, c2) -> Double.compare(c2.getRates(), c1.getRates()));
        coaches.stream()
                .limit(3)
                .toList();
        return ResponseEntity.ok(coaches);
    }
}
