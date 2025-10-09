package org.example.webkachkiserver.models.course;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="course")
@Data
@Builder
public class Course {
}
