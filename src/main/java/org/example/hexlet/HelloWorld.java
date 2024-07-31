package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.util.ArrayList;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {

    public static void main(String[] args) {
        // Создаем приложение
        Javalin app = getApp();
        app.start(7070); // Стартуем веб-сервер
    }
    public static Javalin getApp() {
        Course course1 = new Course("Java", "learning java programming");
        Course course2 = new Course("Phyton", "learning phyton programming");
        Course course3 = new Course("Go", "learning go programming");
        course1.setId(1);
        course2.setId(2);
        course3.setId(3);
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte());
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        app.get("/courses", ctx -> {
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
        });
        return app;
    }
}
