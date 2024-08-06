package org.example.hexlet.dto.courses;

import io.javalin.validation.ValidationError;

import java.util.List;
import java.util.Map;

public class EditCoursePage {
    private Long id;
    private String title;
    private String description;
    private Map<String, List<ValidationError<Object>>> errors;

    public EditCoursePage() {
    }

    public EditCoursePage(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public EditCoursePage(String title, String description, Map<String, List<ValidationError<Object>>> errors) {
        this.title = title;
        this.description = description;
        this.errors = errors;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, List<ValidationError<Object>>> getErrors() {
        return errors;
    }
}
