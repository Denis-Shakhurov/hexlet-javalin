@import org.example.hexlet.routes.NamedRoutes

<!-- sessions/build.jte -->
<!-- Нужно не забыть добавить маршрут в NamedRoutes -->
@template.layout.page(
content = @`
    <form action="/sessions" method="post">
        <input type="text" placeholder="Nickname" name="nickname">
        <input type="password" placeholder="Password" name="password">
        <input type="submit">
    </form>
`
)