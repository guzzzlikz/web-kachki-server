package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.shorts.Shorts;
import org.example.webkachkiserver.repositrories.ShortsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortsService {
    @Autowired
    private ShortsRepository shortsRepository;
    @Autowired
    private VideoStorageService videoStorageService;

    public ResponseEntity<?> upload(int shortsId, long userId, Shorts shorts) {
        shorts.setId(shortsId);
        shorts.setUserId(userId);
        shortsRepository.save(shorts);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<?> delete(int shortsId) {
        shortsRepository.deleteById(shortsId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> load(int amount) {
        List<Shorts> list = shortsRepository.findRandom(amount);
        list.stream().forEach(shorts -> {
            shorts.setPathToVideo(videoStorageService.getSignedUrl(shorts.getPathToVideo()));
        });
        return ResponseEntity.ok().body(list);
    }
}
