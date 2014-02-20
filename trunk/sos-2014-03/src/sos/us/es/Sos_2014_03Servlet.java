package sos.us.es;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Sos_2014_03Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
