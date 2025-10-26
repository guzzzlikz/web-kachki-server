package org.example.webkachkiserver.models.lesson;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lessons")
@Data
@Builder
public class Lesson {
    @Id
    private String id;
    private String title;
    private String description;
    private String teacherId;
    private String courseId;
    private String fideoFileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getFideoFileName() {
        return fideoFileName;
    }

    public void setFideoFileName(String fideoFileName) {
        this.fideoFileName = fideoFileName;
    }
}
