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

    @PostMapping("/{courseId}/add")
    public ResponseEntity<?> addCourse(@RequestBody Course course, @PathVariable String courseId) {
        return courseService.addCourse(course, courseId);
    }
    @PostMapping("/{courseId}/remove")
    public ResponseEntity<?> removeCourse(@RequestBody Course course) {
        return courseService.removeCourse(course);
    }

}
