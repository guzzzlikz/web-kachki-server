package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.models.shorts.Shorts;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.example.webkachkiserver.repositrories.ShortsRepository;
import org.example.webkachkiserver.services.VideoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/video")
public class VideoController {

    @Autowired
    private VideoStorageService videoStorageService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ShortsRepository shortsRepository;

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
    public ResponseEntity<?> finishVideo(@PathVariable long courseId, @PathVariable long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId);
        lesson.setVideoFileName("videos/course" + courseId + "/" + "lesson" + lessonId);
        lessonRepository.save(lesson);
        return ResponseEntity.ok("Good!");
    }
    @PostMapping("/{courseId}/finish")
    public ResponseEntity<?> finishVideo(@PathVariable long courseId, @RequestBody FileData data) {
        Course course = courseRepository.findByCourseId(courseId);
        course.setPathToPreviewVideo("videos/course" + courseId + "/preview_" + courseId + data.type);
        courseRepository.save(course);
        return ResponseEntity.ok("Good!");
    }
    @PostMapping("/{shortsId}/finish")
    public ResponseEntity<?> finishVideo(@PathVariable int shortsId) {
        Optional<Shorts> shorts = shortsRepository.findById(shortsId);
        shorts.get().setPathToVideo("videos/shorts" + shortsId + ".mp4");
        shortsRepository.save(shorts.get());
        return ResponseEntity.ok("Good!");
    }
}

class FileData {
    public String title;
    public String type;
}
