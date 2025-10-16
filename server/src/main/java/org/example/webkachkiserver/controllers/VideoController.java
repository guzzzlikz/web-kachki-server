package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.example.webkachkiserver.services.VideoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class VideoController {

    @Autowired
    private VideoStorageService videoStorageService;

    @Autowired
    private LessonRepository lessonRepository;

    @PostMapping("/{courseId}/lessons/upload")
    public ResponseEntity<?> uploadVideo(@PathVariable String courseId,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestParam("title") String title) {
        try {
            String gcsPath = videoStorageService.uploadVideo(file, Integer.valueOf(courseId));

            Lesson lesson = new Lesson();
            lesson.setTitle(title);
            lesson.setCourseId(Integer.valueOf(courseId));
            lesson.setFideoFileName(gcsPath);
            lessonRepository.save(lesson);

            return ResponseEntity.ok(lesson);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{courseId}/lessons")
    public List<Lesson> getLessons(@PathVariable Integer courseId) {
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        return lessons.stream().map(lesson -> {
            String signedUrl = videoStorageService.getSignedUrl(lesson.getFideoFileName());
            return new Lesson(lesson.getId(), lesson.getTitle(), lesson.getDescription(),
                    lesson.getTeacherId(), lesson.getCourseId(), signedUrl);
        }).toList();
    }
}
