package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.example.webkachkiserver.services.VideoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/video")
public class VideoController {

    @Autowired
    private VideoStorageService videoStorageService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/{courseId}/{lessonId}/generateUrl")
    public ResponseEntity<?> generateUrl(@PathVariable long courseId,
                                         @PathVariable long lessonId,
                                         @RequestBody FileData data) {
        String gcsPath = videoStorageService.generateUrl(data.type, courseId, lessonId);
        return ResponseEntity.ok(gcsPath);
    }
    @PostMapping("/{courseId}/generateUrl")
    public ResponseEntity<?> generateUrl(@PathVariable long courseId, @RequestBody FileData data) {
        String gcsPath = videoStorageService.generateUrl(data.type, courseId);
        return ResponseEntity.ok(gcsPath);
    }

    @PostMapping("/{courseId}/{lessonId}/finish")
    public ResponseEntity<?> finishVideo(@PathVariable long courseId, @PathVariable long lessonId, @RequestBody FileData data) {
        Lesson lesson = lessonRepository.findById(lessonId);
        lesson.setVideoFileName("videos/course" + courseId + "/" + "lesson" + lessonId);
        lessonRepository.save(lesson);
        return ResponseEntity.ok("Good!");
    }
    @PostMapping("/{courseId}/finish")
    public ResponseEntity<?> finishVideo(@PathVariable long courseId, @RequestBody FileData data) {
        Course course = courseRepository.findByCourseId(courseId);
        course.setPathToPreviewVideo("videos/course" + courseId + "/" + data.title);
        courseRepository.save(course);
        return ResponseEntity.ok("Good!");
    }
}

class FileData {
    public String title;
    public String type;
}
