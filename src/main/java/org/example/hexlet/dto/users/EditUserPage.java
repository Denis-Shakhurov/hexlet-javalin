package org.example.hexlet.dto.users;

import io.javalin.validation.ValidationError;

import java.util.List;
import java.util.Map;

public class EditUserPage {
    private Long id;
    private String name;
    private String email;
    private Map<String, List<ValidationError<Object>>> errors;

    public EditUserPage() {
    }

    public EditUserPage(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public EditUserPage(String name, String email, Map<String, List<ValidationError<Object>>> errors) {
        this.name = name;
        this.email = email;
        this.errors = errors;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, List<ValidationError<Object>>> getErrors() {
        return errors;
    }
}
