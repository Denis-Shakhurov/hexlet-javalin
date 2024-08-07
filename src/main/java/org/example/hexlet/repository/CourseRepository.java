package org.example.hexlet.repository;

import org.example.hexlet.model.Course;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository extends BaseRepository {
    private static List<Course> entities = new ArrayList<>();

    public static void save(Course course) throws SQLException {
        var sql = "INSERT INTI courses (title, description) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
            var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getDescription());
            var generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                course.setId(generatedKey.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<Course> search(String term) throws SQLException {
        List<Course> searchCourse = new ArrayList<>();
        var sql = "SELECT * FROM courses WHERE title LIKE '%?%'";
        try (var conn = dataSource.getConnection();
             var stmt = conn
                     .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, term);
            var generatedKey = stmt.getGeneratedKeys();
            if (generatedKey.next()) {
                var title = generatedKey.getString("title");
                var description = generatedKey.getString("description");
                var id = generatedKey.getLong("id");
                var course = new Course(title, description);
                course.setId(id);
                searchCourse.add(course);
            }
        }
        return searchCourse;
    }

    public static Optional<Course> find(Long id) throws SQLException {
        var sql = "SELECT * FROM courses WHERE id = ?";
        try (var conn = dataSource.getConnection();
            var stmt = conn
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, id);
            var generatedKey = stmt.getGeneratedKeys();
            if (generatedKey.next()) {
                var title = generatedKey.getString("title");
                var description = generatedKey.getString("description");
                var course = new Course(title, description);
                course.setId(id);
                return Optional.of(course);
            }
        }
        return Optional.empty();
    }

    public static boolean existsByTitle(String name) throws SQLException {
        List<Course> searchCourse = new ArrayList<>();
        var sql = "SELECT * FROM courses WHERE title = '?'";
        try (var conn = dataSource.getConnection();
             var stmt = conn
                     .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            var generatedKey = stmt.getGeneratedKeys();
            if (generatedKey.next()) {
                var title = generatedKey.getString("title");
                var description = generatedKey.getString("description");
                var id = generatedKey.getLong("id");
                var course = new Course(title, description);
                course.setId(id);
                searchCourse.add(course);
            }
        }
        return searchCourse.isEmpty();
    }

    public static List<Course> getEntities() throws SQLException {
        List<Course> courses = new ArrayList<>();
        var sql = "SELECT * FROM courses";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var title = resultSet.getString("title");
                var description = resultSet.getString("description");
                var id = resultSet.getLong("id");
                var course = new Course(title, description);
                course.setId(id);
                courses.add(course);
            }
        }
        return courses;
    }
}
