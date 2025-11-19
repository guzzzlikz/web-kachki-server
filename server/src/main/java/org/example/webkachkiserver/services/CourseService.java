package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.models.user.TYPE;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhotoStorageService photoStorageService;
    @Autowired
    private VideoStorageService videoStorageService;

    public ResponseEntity<?> addCourse(Course course, long courseId, long userId) {
        if (courseRepository.existsById(courseId)) {
            return ResponseEntity.badRequest().body("Course already exists");
        }
        course.setCourseId(courseId);
        course.setUserId(userId);
        courseRepository.save(course);
        return ResponseEntity.ok("Added!");
    }
    public ResponseEntity<?> removeCourse(long courseId) {
        courseRepository.deleteById(courseId);
        return ResponseEntity.ok("Removed!");
    }
    public ResponseEntity<?> buyCourse(long userId, long courseId) {
        User mongoUser = userRepository.findById(userId);
        if (mongoUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (mongoUser.getBoughtCoursesId() == null) {
            mongoUser.setBoughtCoursesId(new ArrayList<>());
        }
        mongoUser.getBoughtCoursesId().add(courseId);
        userRepository.save(mongoUser);
        return ResponseEntity.ok().body("Course bought!");
    }
    public ResponseEntity<?> checkCourse(long userId, long courseId) {
        User mongoUser = userRepository.findById(userId);
        if (!mongoUser.getBoughtCoursesId().contains(courseId)) {
            return ResponseEntity.badRequest().body("Course not avaliable");
        } else {
            return ResponseEntity.ok().build();
        }
    }
    public ResponseEntity<?> getBoughtCourses(long userId) {
        User mongoUser = userRepository.findById(userId);
        List<Course> boughtCourses = courseRepository.findByCourseIdIn(mongoUser.getBoughtCoursesId());
        return ResponseEntity.ok().body(boughtCourses);
    }

    public ResponseEntity<?> getCoursesPreview(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        List<Course> courses = courseRepository.findAll(pageable).stream().toList();
        courses.forEach(course -> {
            if (course.getPathToPreviewVideo() != null) {
                course.setPathToPreviewVideo(videoStorageService.getSignedUrl(course.getPathToPreviewVideo()));
            }
            if (course.getPathToPreviewPhoto() != null) {
                course.setPathToPreviewPhoto(photoStorageService.getSignedUrl(course.getPathToPreviewPhoto()));
            }
        });
        return ResponseEntity.ok(courses);
    }

    public ResponseEntity<?> getCoursesForIndex() {
        List<Course> courses = courseRepository.findAll();
        courses.sort((c1, c2) -> Double.compare(c2.getRates(), c1.getRates()));
        courses.stream()
                .limit(3)
                .toList();
        courses.stream().forEach(course -> {
            if (course.getPathToPreviewVideo() != null) {
                course.setPathToPreviewVideo(videoStorageService.getSignedUrl(course.getPathToPreviewVideo()));
            }
            if (course.getPathToPreviewPhoto() != null) {
                course.setPathToPreviewPhoto(photoStorageService.getSignedUrl(course.getPathToPreviewPhoto()));
            }
        });
        return ResponseEntity.ok(courses);
    }
}
