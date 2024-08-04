@import org.example.hexlet.dto.courses.CoursesPage
@import org.example.hexlet.routes.NamedRoutes
@param CoursesPage page

@template.layout.page(
content = @`
    <h1>Курсы программирования</h1>
	<form action="${NamedRoutes.coursesPath()}" method="get">
		<input type="search" required name="term" value="${page.getTerm()}">
		<input type="submit" value="Искать">
	</form>
	@if(page.getCourses().isEmpty())
		<p>Пока не добавлено ни одного курса</p>
	@else
        	<table class="table table-sm table-striped">
  			<thead class="thead-dark">
    				<tr>
      					<th scope="col">#</th>
      					<th scope="col">Title</th>
      					<th scope="col">Description</th>
    				</tr>
  			</thead>
            		@for(var course : page.getCourses())
            		 <tbody class="table-striped">
    					<tr>
      					<th scope="row">${page.getCourses().indexOf(course) + 1}</th>
      					<td><a href="${NamedRoutes.coursePath(course.getId())}">${course.getTitle()}</a></td>
      					<td>${course.getDescription()}</td>
    					</tr>
    				</tbody>
            @endfor
	@endif
`
)