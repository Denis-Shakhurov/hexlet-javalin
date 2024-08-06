@import org.example.hexlet.dto.users.UserPage
@import org.example.hexlet.routes.NamedRoutes
@param UserPage page

@template.layout.page(
content = @`
    <h1>${page.getUser().getName()}</h1>
    <p>${page.getUser().getEmail()}</p>
    <a href="${NamedRoutes.editUserPath(page.getUser().getId())}">Редактировать</a>
`
)