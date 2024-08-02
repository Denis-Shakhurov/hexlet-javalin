package gg.jte.generated.ondemand.courses;
import org.example.hexlet.dto.courses.CoursesPage;
public final class JteindexGenerated {
	public static final String JTE_NAME = "courses/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,3,3,5,5,8,8,8,8,8,8,8,8,8,11,11,13,13,22,22,25,25,25,26,26,26,26,26,26,26,27,27,27,30,30,31,31,32,32,32,32,32,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, CoursesPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <h1>Курсы программирования</h1>\n\t<form action=\"/courses\" method=\"get\">\n\t\t<input type=\"search\" required name=\"term\"");
				var __jte_html_attribute_0 = page.getTerm();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("input", "value");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("input", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n\t\t<input type=\"submit\" value=\"Искать\">\n\t</form>\n\t");
				if (page.getCourses().isEmpty()) {
					jteOutput.writeContent("\n\t\t<p>Пока не добавлено ни одного курса</p>\n\t");
				} else {
					jteOutput.writeContent("\n        \t<table class=\"table table-sm table-striped\">\n  \t\t\t<thead class=\"thead-dark\">\n    \t\t\t\t<tr>\n      \t\t\t\t\t<th scope=\"col\">#</th>\n      \t\t\t\t\t<th scope=\"col\">Title</th>\n      \t\t\t\t\t<th scope=\"col\">Description</th>\n    \t\t\t\t</tr>\n  \t\t\t</thead>\n            \t\t");
					for (var course : page.getCourses()) {
						jteOutput.writeContent("\n            \t\t <tbody class=\"table-striped\">\n    \t\t\t\t\t<tr>\n      \t\t\t\t\t<th scope=\"row\">");
						jteOutput.setContext("th", null);
						jteOutput.writeUserContent(page.getCourses().indexOf(course) + 1);
						jteOutput.writeContent("</th>\n      \t\t\t\t\t<td><a href=\"/courses/");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(course.getId());
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\">");
						jteOutput.setContext("a", null);
						jteOutput.writeUserContent(course.getTitle());
						jteOutput.writeContent("</a></td>\n      \t\t\t\t\t<td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(course.getDescription());
						jteOutput.writeContent("</td>\n    \t\t\t\t\t</tr>\n    \t\t\t\t</tbody>\n            ");
					}
					jteOutput.writeContent("\n\t");
				}
				jteOutput.writeContent("\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		CoursesPage page = (CoursesPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
