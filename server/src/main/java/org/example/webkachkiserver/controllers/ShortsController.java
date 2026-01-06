package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.shorts.Shorts;
import org.example.webkachkiserver.services.ShortsService;
import org.example.webkachkiserver.services.VideoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shorts")
public class ShortsController {
    @Autowired
    private ShortsService shortsService;
    @Autowired
    private VideoStorageService videoStorageService;
    @PostMapping("/{userId}/{shortsId}/upload")
    public ResponseEntity<?> upload(@PathVariable("shortsId") int shortsId,
                                    @PathVariable("userId") long userId,
                                    @RequestBody Shorts shorts) {
        return shortsService.upload(shortsId, userId, shorts);
    }
    @PostMapping("/{shortsId}/generateUrl")
    public ResponseEntity<?> generateUrl(@PathVariable int shortsId, @RequestBody FileData data) {
        String gcsPath = videoStorageService.generateUrl(data.type, shortsId);
        return ResponseEntity.ok(gcsPath);
    }
    @DeleteMapping("/{shortsId}/delete")
    public ResponseEntity<?> delete(@PathVariable("shortsId") int shortsId) {
        return shortsService.delete(shortsId);
    }
    @GetMapping("load")
    public ResponseEntity<?> load(@RequestParam int amount) {
        return shortsService.load(amount);
    }
}
