package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.UsersController;

public class HelloWorld {

    public static void main(String[] args) {
        Javalin app = getApp(); // Создаем приложение
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

        app.get("/users", UsersController :: index);
        app.get("/users/{id}", UsersController :: show);
        app.get("/users/build", UsersController :: build);
        app.post("/users", UsersController :: create);

        app.get("/courses/build", CoursesController :: build);
        app.post("/courses", CoursesController :: create);
        app.get("/courses/{id}", CoursesController :: show);
        app.get("/courses", CoursesController :: index);

        /*app.get("/users/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var escapedId = StringEscapeUtils.escapeHtml4(id);
            ctx.contentType("text/html");
            ctx.result(escapedId);
        });*/
        return app;
    }
}
