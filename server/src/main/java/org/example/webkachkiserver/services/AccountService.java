package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<?> getUser(long userId) {
        return ResponseEntity.ok(userRepository.findById(userId));
    }
    public ResponseEntity<?> changeDescription(long userId, String description) {
        User mongoUser = userRepository.findById(userId);
        mongoUser.setDescription(description);
        userRepository.save(mongoUser);
        return ResponseEntity.ok(mongoUser.getDescription());
    }
    public ResponseEntity<?> changeName(long userId, String name) {
        User mongoUser = userRepository.findById(userId);
        mongoUser.setName(name);
        userRepository.save(mongoUser);
        return ResponseEntity.ok(mongoUser.getName());
    }
    public ResponseEntity<?> getCourses(long userId) {
        List<Course> courses = courseRepository.findByUserId(userId);
        return ResponseEntity.ok(courses);
    }
    public ResponseEntity<?> changeContactInformation(long userId, List<String> contactInformation) {
        User user = userRepository.findById(userId);
        user.setContacts(contactInformation);
        userRepository.save(user);
        return ResponseEntity.ok(user.getContacts());
    }
}
