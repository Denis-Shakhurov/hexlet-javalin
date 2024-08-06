package org.example.hexlet.conroller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.routes.NamedRoutes;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class CoursesController {
    private final static List<Course> COURSES = CourseRepository.getEntities();

    public static void index(Context ctx) {
        var term = ctx.queryParam("term");
        List<Course> courses;
        if (term != null) {
            courses = CourseRepository.search(term);
        } else {
            courses = COURSES;
        }
        var page = new CoursesPage(courses, term);
        ctx.render("courses/index.jte", model("page", page));
    }

    public static void show(Context ctx) {
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

    public static void create(Context ctx) {
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
    }
}
