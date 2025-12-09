package org.example.webkachkiserver.repositrories;

import org.example.webkachkiserver.models.shorts.Shorts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortsRepository extends MongoRepository<Shorts, Integer>, ShortsRepositoryCustom {
}
