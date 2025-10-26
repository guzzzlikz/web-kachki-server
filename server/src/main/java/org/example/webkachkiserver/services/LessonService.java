package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    public ResponseEntity<?> createLesson(Lesson lesson, String lessonId, String courseId) {
        lesson.setId(lessonId);
        lesson.setCourseId(courseId);
        lessonRepository.save(lesson);
        return ResponseEntity.ok(lesson);
    }
}
