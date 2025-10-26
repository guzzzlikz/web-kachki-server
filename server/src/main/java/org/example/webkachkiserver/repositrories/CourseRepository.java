package org.example.webkachkiserver.repositrories;

import org.example.webkachkiserver.models.course.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
    Course findById(long id);
}
