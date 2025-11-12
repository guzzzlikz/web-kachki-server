package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rating")
public class RatingController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private RatingService ratingService;
    @PostMapping("/{courseId}/rating")
    public ResponseEntity<?> updateRatingForCourses(@RequestParam int rate, @PathVariable long courseId) {
        return ratingService.updateRatingForCourses(rate, courseId);
    }
    @PostMapping("/{userId}/rating")
    public ResponseEntity<?> updateRatingForCoaches(@RequestParam int rate, @PathVariable long userId) {
        return ratingService.updateRatingForCoaches(rate, userId);
    }
}
