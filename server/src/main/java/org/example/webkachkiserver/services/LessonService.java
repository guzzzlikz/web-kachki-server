package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private VideoStorageService videoStorageService;

    public ResponseEntity<?> createLesson(Lesson lesson, long courseId, long lessonId) {
        lesson.setId(lessonId);
        lesson.setCourseId(courseId);
        lessonRepository.save(lesson);
        return ResponseEntity.ok(lesson);
    }

    public ResponseEntity<?> removeLesson(long lessonId) {
        lessonRepository.deleteById(lessonId);
        return ResponseEntity.ok().body("Removed");
    }

    public List<Lesson> getLessons(long courseId) {
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        lessons.stream().forEach(lesson -> {
            lesson.setVideoFileName(videoStorageService.getSignedUrl(lesson.getVideoFileName()));
        });
        return lessons;
    }
}
