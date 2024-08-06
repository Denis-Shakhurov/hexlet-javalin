@import org.example.hexlet.dto.courses.CoursePage
@import org.example.hexlet.routes.NamedRoutes
@param CoursePage page

@template.layout.page(
content = @`
    <h1>${page.getCourse().getTitle()}</h1>
    <p>${page.getCourse().getDescription()}</p>
    <a href="${NamedRoutes.editCoursePath(page.getCourse().getId())}">Редактировать</a>
`
)