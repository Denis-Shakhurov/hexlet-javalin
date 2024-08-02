package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
    private final static List<User> USERS = Data.getUsers();
    private final static List<Course> COURSES = Data.getCourses();

    public static void main(String[] args) {
        // Создаем приложение
        Javalin app = getApp();
        app.start(7070); // Стартуем веб-сервер
    }
    public static Javalin getApp() {
        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte());
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        app.get("/users", ctx -> {
            var header = "Пользователи";
            var page = new UsersPage(USERS, header);
            ctx.render("users/index.jte", model("page", page));
        });

        app.get("/users/{id}", ctx -> {
           var id = ctx.pathParamAsClass("id", Long.class).get();
           var user = USERS.stream()
                   .filter(usr -> usr.getId() == id)
                   .findFirst()
                   .orElseThrow(() -> new NotFoundResponse("User not found"));
           var page = new UserPage(user);
           ctx.render("users/show.jte", model("page", page));
        });

        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).get();
            var course = COURSES.stream()
                    .filter(crs -> crs.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Course not found"));
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get("/courses", ctx -> {
            var term = ctx.queryParam("term");
            List<Course> courses;
            // Фильтруем, только если была отправлена форма
            if (term != null) {
                courses = COURSES.stream()
                        .filter(course -> course.getTitle().equals(term))
                        .toList();
            } else {
                courses = COURSES;
            }
            var page = new CoursesPage(courses, term);
            ctx.render("courses/index.jte", model("page", page));
        });

        /*app.get("/users/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var escapedId = StringEscapeUtils.escapeHtml4(id);
            ctx.contentType("text/html");
            ctx.result(escapedId);
        });*/
        return app;
    }
}
