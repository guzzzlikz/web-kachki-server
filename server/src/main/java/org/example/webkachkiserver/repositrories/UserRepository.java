package org.example.webkachkiserver.repositrories;

import org.example.webkachkiserver.models.user.TYPE;
import org.example.webkachkiserver.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findById(long id);
    List<User> findAllByType(TYPE type);
}
