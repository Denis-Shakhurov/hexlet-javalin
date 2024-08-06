@import org.example.hexlet.dto.users.EditUserPage
@import org.example.hexlet.routes.NamedRoutes
@param EditUserPage page

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
    <form action="${NamedRoutes.userPath(page.getId())}" method="post">
        <div>
            <label>
                Name
                <input type="text" name="name" value="${page.getName()}" />
            </label>
        </div>
        <div>
            <label>
                Email
                <input type="email" required name="email" value="${page.getEmail()}" />
            </label>
        </div>
        <div>
            <label>
                Password
                <input type="password" required name="password" />
            </label>
        </div>
        <div>
            <label>
                Password Confirmation
                <input type="password" required name="passwordConfirmation" />
            </label>
        </div>
        <input type="submit" value="Сохранить изменения" />
    </form>
`
)