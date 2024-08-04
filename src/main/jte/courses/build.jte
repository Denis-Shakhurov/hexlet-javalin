@import org.example.hexlet.dto.courses.BuildCoursePage
@import org.example.hexlet.routes.NamedRoutes
@param BuildCoursePage page

@template.layout.page(
content = @`
    @if(page.getErrors() != null)
        <ul>
            @for(var validator : page.getErrors().values())
                @for(var error : validator)
                    <li>${error.getMessage()}</li>
                @endfor
            @endfor
        </ul>
    @endif
    <form action="${NamedRoutes.coursesPath()}" method="post">
        <div>
            <label>
                Имя
                <input type="text" name="title" value="${page.getTitle()}"/>
            </label>
        </div>
        <div>
            <label>
                Опмсание
                <input type="text" required name="description" value="${page.getDescription()}" />
            </label>
        </div>
        <input type="submit" value="Создать" />
    </form>
`
)