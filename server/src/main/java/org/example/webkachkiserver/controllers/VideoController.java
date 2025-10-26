package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.example.webkachkiserver.services.VideoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class VideoController {

    @Autowired
    private VideoStorageService videoStorageService;

    @Autowired
    private LessonRepository lessonRepository;

    @PostMapping("/{courseId}/{lessonId}/upload")
    public ResponseEntity<?> uploadVideo(@PathVariable String courseId,
                                         @PathVariable String lessonId,
                                         @RequestParam("file") MultipartFile file) {
        try {
            String gcsPath = videoStorageService.uploadVideo(file, courseId);
            Lesson lesson = lessonRepository.findById(lessonId);
            lesson.setFideoFileName(gcsPath);
            if (!lessonRepository.existsById(lessonId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            lessonRepository.save(lesson);
            return ResponseEntity.ok(lesson);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{courseId}/lessons")
    public List<Lesson> getLessons(@PathVariable Integer courseId) {
        return videoStorageService.getLessons(courseId);
    }
}
