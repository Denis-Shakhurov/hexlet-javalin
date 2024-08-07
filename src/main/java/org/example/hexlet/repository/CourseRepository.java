package org.example.hexlet.repository;

import org.example.hexlet.model.Course;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository extends BaseRepository {

    public static void save(Course course) throws SQLException {
        var sql = "INSERT INTO courses (title, description) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
            var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.executeUpdate();
            var generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                course.setId(generatedKey.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<Course> search(String term) throws SQLException {
        var sql = "SELECT * FROM courses WHERE title LIKE %?%";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, term);
            var resultSet = stmt.executeQuery();
            List<Course> searchCourse = new ArrayList<>();
            while (resultSet.next()) {
                var title = resultSet.getString("title");
                var description = resultSet.getString("description");
                var id = resultSet.getLong("id");
                var course = new Course(title, description);
                course.setId(id);
                searchCourse.add(course);
            }
            return searchCourse;
        }
    }

    public static Optional<Course> find(Long id) throws SQLException {
        var sql = "SELECT * FROM courses WHERE id = ?";
        try (var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var title = resultSet.getString("title");
                var description = resultSet.getString("description");
                var course = new Course(title, description);
                course.setId(id);
                return Optional.of(course);
            }
        }
        return Optional.empty();
    }

    public static boolean existsByTitle(String name) throws SQLException {
        var sql = "SELECT * FROM courses WHERE title = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();
            List<Course> searchCourse = new ArrayList<>();
            if (resultSet.next()) {
                var title = resultSet.getString("title");
                var description = resultSet.getString("description");
                var id = resultSet.getLong("id");
                var course = new Course(title, description);
                course.setId(id);
                searchCourse.add(course);
            }
            return searchCourse.isEmpty();
        }
    }

    public static List<Course> getEntities() throws SQLException {

        var sql = "SELECT * FROM courses";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                var title = resultSet.getString("title");
                var description = resultSet.getString("description");
                var id = resultSet.getLong("id");
                var course = new Course(title, description);
                course.setId(id);
                courses.add(course);
            }
            return courses;
        }
    }

    public static void update(Course course) throws SQLException {
        var sql = "UPDATE courses SET table = ?, description = ?";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, course.getTitle());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.executeUpdate();
        }
    }
}
