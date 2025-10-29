package org.example.webkachkiserver.repositrories;

import org.example.webkachkiserver.models.course.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, Long> {
    Course findByCourseId(long id);
    boolean existsByName(String name);
    Course findByName(String name);
    List<Course> findByUserId(long id);
}
