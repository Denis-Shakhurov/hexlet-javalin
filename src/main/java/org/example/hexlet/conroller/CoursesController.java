package org.example.hexlet.conroller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.courses.EditCoursePage;
import org.example.hexlet.model.Course;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.routes.NamedRoutes;

import java.sql.SQLException;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class CoursesController { ;

    public static void index(Context ctx) throws SQLException {
        var term = ctx.queryParam("term");
        List<Course> courses;
        if (term != null) {
            courses = CourseRepository.search(term);
        } else {
            courses = CourseRepository.getEntities();
        }
        var page = new CoursesPage(courses, term);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("courses/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new CoursePage(course);
        ctx.render("courses/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        var page = new BuildCoursePage();
        ctx.render("courses/build.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var title1 = ctx.formParam("title");
        var description1 = ctx.formParam("description");
        try {
            var title = ctx.formParamAsClass("title", String.class)
                    .check(value -> value.length() > 2, "У названия недостаточная длмна")
                    .check(value -> {
                        try {
                            return CourseRepository.existsByTitle(title1);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }, "Курс с таким названием уже существует")
                    .get();
            var description = ctx.formParamAsClass("description", String.class)
                    .check(value -> value.length() > 10, "У описания недостаточная длмна")
                    .get();
            var course = new Course(title, description);
            CourseRepository.save(course);
            ctx.sessionAttribute("flash", "Курс успешно создан");
            ctx.redirect(NamedRoutes.coursesPath());
        } catch (ValidationException e) {
            ctx.status(422);
            var page = new BuildCoursePage(title1, description1, e.getErrors());
            ctx.render("courses/build.jte", model("page", page));
        }
    }

    public static void edit(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new EditCoursePage(course.getId(), course.getTitle(), course.getDescription());
        ctx.render("courses/edit.jte", model("page", page));
    }

    public static void update(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var title1 = ctx.formParam("title");
        var description1 = ctx.formParam("description");
        try {
            var title = ctx.formParamAsClass("title", String.class)
                    .check(value -> value.length() > 2, "У названия недостаточная длмна")
                    .get();
            var description = ctx.formParamAsClass("description", String.class)
                    .check(value -> value.length() > 10, "У описания недостаточная длмна")
                    .get();
            var course = CourseRepository.find(id)
                    .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
            course.setTitle(title);
            course.setDescription(description);
            CourseRepository.update(course);
            ctx.redirect(NamedRoutes.coursePath(id));
        } catch (ValidationException e) {
            var page = new EditCoursePage(title1, description1, e.getErrors());
            ctx.render("courses/edit.jte", model("page", page)).status(422);
        } catch (SQLException e) {
        }
    }
}
