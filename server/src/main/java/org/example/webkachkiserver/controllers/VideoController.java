package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.example.webkachkiserver.services.VideoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/courses")
public class VideoController {

    @Autowired
    private VideoStorageService videoStorageService;

    @Autowired
    private LessonRepository lessonRepository;

    @PostMapping("/{courseId}/{lessonId}/generateUrl")
    public ResponseEntity<?> generateUrl(@PathVariable long courseId,
                                         @PathVariable long lessonId,
                                         @RequestBody FileData data) {
        String gcsPath = videoStorageService.generateUrl(data.title, data.type, courseId);
        return ResponseEntity.ok(gcsPath);
    }

    @PostMapping("/{courseId}/{lessonId}/finish")
    public ResponseEntity<?> finishVideo(@PathVariable long courseId, @PathVariable long lessonId, @RequestBody FileData data) {
        Lesson lesson = lessonRepository.findById(lessonId);
        lesson.setVideoFileName("videos/course" + courseId + "/" + data.title);
        lessonRepository.save(lesson);
        return ResponseEntity.ok("Good!");
    }

    @GetMapping("/{courseId}/lessons")
    public List<Lesson> getLessons(@PathVariable long courseId) {
        return videoStorageService.getLessons(courseId);
    }

    @GetMapping("/{courseId}/{lessonId}/video")
    public ResponseEntity<?> getUrl(@PathVariable long lessonId) {
        return videoStorageService.getUrl(lessonId);
    }
}

class FileData {
    public String title;
    public String type;
}
