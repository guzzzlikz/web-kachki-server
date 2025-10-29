package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<?> addCourse(Course course, long courseId) {
        if (courseRepository.existsById(courseId)) {
            return ResponseEntity.badRequest().body("Course already exists");
        }
        course.setCourseId(courseId);
        courseRepository.save(course);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<?> removeCourse(Course course) {
        courseRepository.delete(course);
        return ResponseEntity.ok().build();
    }
}
