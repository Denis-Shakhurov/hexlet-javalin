package org.example.hexlet.dto.courses;

import io.javalin.validation.ValidationError;

import java.util.List;
import java.util.Map;

public class BuildCoursePage {
    private String title;
    private String description;
    private Map<String, List<ValidationError<Object>>> errors;

    public BuildCoursePage(String title, String description, Map<String, List<ValidationError<Object>>> errors) {
        this.title = title;
        this.description = description;
        this.errors = errors;
    }

    public BuildCoursePage() {
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
