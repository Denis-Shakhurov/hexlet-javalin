@import org.example.hexlet.dto.users.UsersPage
@import org.example.hexlet.routes.NamedRoutes
@param UsersPage page

@template.layout.page(
content = @`
    <h1>Пользователи</h1>
    @if(page.getUsers().isEmpty())
        <p>Пока не добавлено ни одного пользователя</p>
    @else
        	<table class="table table-sm table-striped">
  			<thead class="thead-dark">
    				<tr>
      					<th scope="col">#</th>
      					<th scope="col">Имя</th>
      					<th scope="col">Email</th>
    				</tr>
  			</thead>
            		@for(var user : page.getUsers())
            		 <tbody class="table-striped">
    					<tr>
      					<th scope="row">${page.getUsers().indexOf(user) + 1}</th>
      					<td><a href="${NamedRoutes.userPath(user.getId())}">${user.getName()}</a></td>
      					<td>${user.getEmail()}</td>
    					</tr>
    				</tbody>
            @endfor
    @endif
`
)
