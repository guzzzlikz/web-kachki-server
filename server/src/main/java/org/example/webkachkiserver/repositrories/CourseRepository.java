package org.example.webkachkiserver.repositrories;

import org.example.webkachkiserver.models.course.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, Long> {
    Course findByCourseId(long id);
    List<Course> findByUserId(long id);
    List<Course> findByCourseIdIn(List<Long> ids);
}
