package org.example.hexlet.dto.courses;

import org.example.hexlet.model.Course;

import java.util.List;

public class CoursesPage extends BasePage {
    private List<Course> courses;
    private String term;

    public CoursesPage(List<Course> courses, String term) {
        this.courses = courses;
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
