@import gg.jte.Content
@import org.example.hexlet.routes.NamedRoutes
@param Content content
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
          crossorigin="anonymous">
    <title>Hexlet Javalin Example</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${NamedRoutes.usersPath()}">Users</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${NamedRoutes.coursesPath()}">Courses</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${NamedRoutes.buildUserPath()}">New User</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${NamedRoutes.buildCoursePath()}">New Course</a>
            </li>
        </ul>
    </div>
</nav>

<div class="mx-auto p-4 py-md-5">
    ${content}
</div>
<footer class="fixed-bottom">
    <a href="https://github.com/Denis-Shakhurov">Denis Shakhurov</a>
</footer>
</body>
</html>