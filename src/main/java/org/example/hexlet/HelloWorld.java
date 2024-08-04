package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.ContinueResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UnprocessableContentResponse;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.Params;
import io.javalin.validation.ValidationError;
import io.javalin.validation.ValidationException;
import io.javalin.validation.Validator;
import kotlin.jvm.functions.Function1;
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
import org.example.hexlet.routes.NamedRoutes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        app.get(NamedRoutes.usersPath(), ctx -> {
            var header = "Пользователи";
            var page = new UsersPage(USERS, header);
            ctx.render("users/index.jte", model("page", page));
        });

        app.get(NamedRoutes.buildUserPath(), ctx -> {
            var page = new BuildUserPage();
            ctx.render("users/build.jte", model("page", page));
        });

        app.post(NamedRoutes.usersPath(), ctx -> {
           var name = ctx.formParam("name").trim();
           var email = ctx.formParam("email").trim().toLowerCase();
           try {
               var passwordConfirmation = ctx.formParam("passwordConfirmation");
               var password = ctx.formParamAsClass("password", String.class)
                       .check(value -> value.equals(passwordConfirmation), "Пароли не совпадают")
                       .getOrThrow(val -> new UnprocessableContentResponse());
               var user = new User(name, email, password);
               UserRepository.save(user);
               ctx.redirect(NamedRoutes.usersPath());
           } catch (ValidationException e) {
               ctx.status(422);
               var page = new BuildUserPage(name, email, e.getErrors());
               ctx.render("users/build.jte", model("page", page));
           }
        });

        app.error(404, ctx -> {
            ctx.result("Generic 404 message");
        });

        app.get(NamedRoutes.userPath("{id}"), ctx -> {
           var id = ctx.pathParamAsClass("id", Long.class).get();
           var user = UserRepository.find(id).get();
           var page = new UserPage(user);
           ctx.render("users/show.jte", model("page", page));
        });

        app.get(NamedRoutes.buildCoursePath(), ctx -> {
            var page = new BuildCoursePage();
            ctx.render("courses/build.jte", model("page", page));
        });

        app.post(NamedRoutes.coursesPath(), ctx -> {

            var title1 = ctx.formParam("title");
            var description1 = ctx.formParam("description");
            try {
                var title = ctx.formParamAsClass("title", String.class)
                        .check(value -> value.length() > 2, "У названия недостаточная длмна")
                        .check(value -> !CourseRepository.existsByTitle(title1), "Курс с таким названием уже существует")
                        .get();
                var description = ctx.formParamAsClass("description", String.class)
                        .check(value -> value.length() > 10, "У описания недостаточная длмна")
                        .get();
                var course = new Course(title, description);
                CourseRepository.save(course);
                ctx.redirect(NamedRoutes.coursesPath());
            } catch (ValidationException e) {
                ctx.status(422);
                var page = new BuildCoursePage(title1, description1, e.getErrors());
                ctx.render("courses/build.jte", model("page", page));
            }
        });

        app.get(NamedRoutes.coursePath("{id}"), ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).get();
            var course = CourseRepository.find(id).get();
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get(NamedRoutes.coursesPath(), ctx -> {
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
