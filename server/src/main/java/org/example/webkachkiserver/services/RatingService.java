package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> updateRatingForCourses(int rate, long courseId) {
        Course course = courseRepository.findByCourseId(courseId);
        course.setRates(course.getRates() + 1);
        course.setRating(Math.round((
                (course.getRating() + rate) / course.getRates()) * 10) / 10);
        courseRepository.save(course);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> updateRatingForCoaches(int rate, long userId) {
        User coach = userRepository.findById(userId);
        coach.setRates(coach.getRates() + 1);
        coach.setRating(Math.round((
                (coach.getRating() + rate) / coach.getRates()) * 10) / 10);
        userRepository.save(coach);
        return ResponseEntity.ok().build();
    }
}
