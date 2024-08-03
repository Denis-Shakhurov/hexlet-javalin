package org.example.hexlet.repository;

import io.javalin.http.NotFoundResponse;
import org.example.hexlet.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static List<User> entities = new ArrayList<>();

    public static void save(User user) {
        user.setId((long) entities.size() + 1);
        entities.add(user);
    }

    public static List<User> search(String term) {
        return entities.stream()
                .filter(user -> user.getName().startsWith(term))
                .toList();
    }

    public static Optional<User> find(Long id) {
        return Optional.ofNullable(entities.stream()
                .filter(user -> user.getId() == id)
                .findAny()
                .orElseThrow(() -> new NotFoundResponse("User not found")));
    }

    public static List<User> getEntities() {
        return entities;
    }
}
