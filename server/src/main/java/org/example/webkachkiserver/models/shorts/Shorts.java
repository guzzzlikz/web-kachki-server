package org.example.webkachkiserver.models.shorts;

import lombok.Builder;
import lombok.Data;
import org.example.webkachkiserver.models.course.TAGS;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection="shorts")
public class Shorts {
    private int id;
    private String title;
    private String description;
    private TAGS tags;
    private String pathToVideo;
    private int views;
    private long userId;
}
