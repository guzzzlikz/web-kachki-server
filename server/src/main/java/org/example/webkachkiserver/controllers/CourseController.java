package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /*Виникає питання, що таке courseId
    /*При натисканні на умовне (Створити курс)
    /*Відкривається окрема сторінка по типу
    /*https://бімбімбамбам/courseId/...
    /*Тобто сторінка одразу йде з випадково генерованим
    /*courseId і відповідно мені потім прилтає запрос
    /*по цьому courseId*/

    @PostMapping("/{userId}/{courseId}/add")
    public ResponseEntity<?> addCourse(@RequestBody Course course, @PathVariable long courseId) {
        return courseService.addCourse(course, courseId);
    }
    @PostMapping("/{userId}/{courseId}/remove")
    public ResponseEntity<?> removeCourse(@PathVariable long courseId) {
        return courseService.removeCourse(courseId);
    }
    @GetMapping("/{userId}/{courseId}")
    public ResponseEntity<?> checkCourse(@PathVariable long userId, @PathVariable long courseId) {
        return courseService.checkCourse(userId, courseId);
    }
    @PostMapping("/{userId}/{courseId}/buy")
    public ResponseEntity<?> buyCourse(@PathVariable long userId, @PathVariable long courseId) {
        return courseService.buyCourse(userId, courseId);
    }
    @GetMapping("/{userId}/get")
    public ResponseEntity<?> getBoughtCourses(@PathVariable long userId) {
        return courseService.getBoughtCourses(userId);
    }
    @GetMapping()
    public ResponseEntity<?> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "rating") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return courseService.getCoursesPreview(page, size, sortBy, direction);
    }

}
