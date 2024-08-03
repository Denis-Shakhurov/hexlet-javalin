package org.example.hexlet.repository;

import io.javalin.http.NotFoundResponse;
import org.example.hexlet.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {
    private static List<Course> entities = new ArrayList<>();

    public static void save(Course course) {
        course.setId((long) entities.size() + 1);
        entities.add(course);
    }

    public static List<Course> search(String term) {
        return entities.stream()
                .filter(course -> course.getDescription().contains(term))
                .toList();
    }

    public static Optional<Course> find(Long id) {
        return Optional.ofNullable(entities.stream()
                .filter(course -> course.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NotFoundResponse("Course not found")));
    }

    public static List<Course> getEntities() {
        return entities;
    }
}
