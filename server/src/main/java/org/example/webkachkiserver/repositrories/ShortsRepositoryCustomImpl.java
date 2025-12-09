package org.example.webkachkiserver.repositrories;

import org.example.webkachkiserver.models.shorts.Shorts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShortsRepositoryCustomImpl implements ShortsRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ShortsRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Shorts> findRandom(int size) {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.sample(size));
        return mongoTemplate.aggregate(aggregation, "shorts", Shorts.class).getMappedResults();
    }
}
