package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.example.webkachkiserver.services.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/photo")
public class PhotoController {

    @Autowired
    private PhotoStorageService photoStorageService;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}/uploadUser")
    public ResponseEntity<?> uploadUserProfilePhoto(@PathVariable long userId,
                                         @RequestParam("file") MultipartFile file) {
        try {
            String gcsPath = photoStorageService.uploadUserProfilePhoto(file, userId);
            User user = userRepository.findById(userId);
            user.setPathToPhoto(gcsPath);
            if (!userRepository.existsById(userId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}/user")
    public User getUser(@PathVariable long userId) {
        return photoStorageService.getUser(userId);
    }
    @GetMapping("/{userId}/photo")
    public ResponseEntity<?> getUrl(@PathVariable long userId) {
        return photoStorageService.getUrl(userId);
    }
    @GetMapping("/index")
    public ResponseEntity<?> getPhotosForIndex() {
        return photoStorageService.getPhotosForIndex();
    }
    @GetMapping("/misc")
    public ResponseEntity<?> getMiscPhotos() {
        return photoStorageService.getMiscPhotos();
    }
    @PostMapping("/{courseId}/uploadCoursePhoto")
    public ResponseEntity<?> uploadCoursePhoto(@RequestParam("file") MultipartFile file, @PathVariable long courseId) {
        try {
            String gcsPath = photoStorageService.uploadCoursePhoto(file, courseId);
            Course course = courseRepository.findByCourseId(courseId);
            course.setPathToPreviewPhoto(gcsPath);
            if (!courseRepository.existsById(courseId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            courseRepository.save(course);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

}
