@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

@template.layout.page(
content = @`
    <form action="/courses" method="post">
        <div>
            <label>
                Имя
                <input type="text" name="title" />
            </label>
        </div>
        <div>
            <label>
                Опмсание
                <input type="text" required name="description" />
            </label>
        </div>
        <input type="submit" value="Создать" />
    </form>
`
)