package org.example.webkachkiserver.models.course;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="course")
@Data
@Builder
public class Course {
    private String title;
    private String creatorName;
    private String description;
    private List<TAGS> tagsList;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TAGS> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<TAGS> tagsList) {
        this.tagsList = tagsList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
