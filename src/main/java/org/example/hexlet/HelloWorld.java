package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.apache.commons.text.StringEscapeUtils;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
    private final static List<User> USERS = UserRepository.getEntities();
    private final static List<Course> COURSES = CourseRepository.getEntities();

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

        app.get("/users/build", ctx -> {
            var page = new BuildUserPage();
            ctx.render("users/build.jte", model("page", page));
        });

        app.post("/users", ctx -> {
           var name = ctx.formParam("name").trim();
           var email = ctx.formParam("email").trim().toLowerCase();
           try {
               var passwordConfirmation = ctx.formParam("passwordConfirmation");
               var password = ctx.formParamAsClass("password", String.class)
                       .check(value -> value.equals(passwordConfirmation), "Пароли не совпадают")
                       .get();

               var user = new User(name, email, password);
               UserRepository.save(user);
               ctx.redirect("/users");
           } catch (ValidationException e) {
               var page = new BuildUserPage(name, email, e.getErrors());
               ctx.render("users/build.jte", model("page", page));
           }
        });

        app.get("/users/{id}", ctx -> {
           var id = ctx.pathParamAsClass("id", Long.class).get();
           var user = UserRepository.find(id).get();
           var page = new UserPage(user);
           ctx.render("users/show.jte", model("page", page));
        });

        app.get("/courses/build", ctx -> {
            var page = new BuildCoursePage();
            ctx.render("courses/build.jte", model("page", page));
        });

        app.post("/courses", ctx -> {

            var title1 = ctx.formParam("title");
            var description1 = ctx.formParam("description");
            try {
                var title = ctx.formParamAsClass("title", String.class)
                        .check(value -> value.length() > 2, "У названия недостаточная длмна")
                        .get();
                var description = ctx.formParamAsClass("description", String.class)
                        .check(value -> value.length() > 10, "У описания недостаточная длмна")
                        .get();
                var course = new Course(title, description);
                CourseRepository.save(course);
                ctx.redirect("/courses");
            } catch (ValidationException e) {
                var page = new BuildCoursePage(title1, description1, e.getErrors());
                ctx.render("courses/build.jte", model("page", page));
            }
        });

        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).get();
            var course = CourseRepository.find(id).get();
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get("/courses", ctx -> {
            var term = ctx.queryParam("term");
            List<Course> courses;
            if (term != null) {
                courses = CourseRepository.search(term);
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
