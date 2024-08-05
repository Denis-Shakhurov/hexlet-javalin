package gg.jte.generated.ondemand.users;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.routes.NamedRoutes;
public final class JteindexGenerated {
	public static final String JTE_NAME = "users/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,4,4,6,6,8,8,10,10,19,19,22,22,22,23,23,23,23,23,23,23,23,23,23,23,23,24,24,24,27,27,29,29,30,30,30,31,31,31,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UsersPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <h1>Пользователи</h1>\n    ");
				if (page.getUsers().isEmpty()) {
					jteOutput.writeContent("\n        <p>Пока не добавлено ни одного пользователя</p>\n    ");
				} else {
					jteOutput.writeContent("\n        \t<table class=\"table table-sm table-striped\">\n  \t\t\t<thead class=\"thead-dark\">\n    \t\t\t\t<tr>\n      \t\t\t\t\t<th scope=\"col\">#</th>\n      \t\t\t\t\t<th scope=\"col\">Имя</th>\n      \t\t\t\t\t<th scope=\"col\">Email</th>\n    \t\t\t\t</tr>\n  \t\t\t</thead>\n            \t\t");
					for (var user : page.getUsers()) {
						jteOutput.writeContent("\n            \t\t <tbody class=\"table-striped\">\n    \t\t\t\t\t<tr>\n      \t\t\t\t\t<th scope=\"row\">");
						jteOutput.setContext("th", null);
						jteOutput.writeUserContent(page.getUsers().indexOf(user) + 1);
						jteOutput.writeContent("</th>\n      \t\t\t\t\t<td><a");
						var __jte_html_attribute_0 = NamedRoutes.userPath(user.getId());
						if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
							jteOutput.writeContent(" href=\"");
							jteOutput.setContext("a", "href");
							jteOutput.writeUserContent(__jte_html_attribute_0);
							jteOutput.setContext("a", null);
							jteOutput.writeContent("\"");
						}
						jteOutput.writeContent(">");
						jteOutput.setContext("a", null);
						jteOutput.writeUserContent(user.getName());
						jteOutput.writeContent("</a></td>\n      \t\t\t\t\t<td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(user.getEmail());
						jteOutput.writeContent("</td>\n    \t\t\t\t\t</tr>\n    \t\t\t\t</tbody>\n            \t\t");
					}
					jteOutput.writeContent("\n\t\t\t</table>\n    ");
				}
				jteOutput.writeContent("\n");
			}
		});
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UsersPage page = (UsersPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
