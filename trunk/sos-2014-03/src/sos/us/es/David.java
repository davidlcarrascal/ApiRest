package sos.us.es;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class David extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//String json;
		
		// Crear datos de prueba- Crear lista de 2 anios
		
		// Serializar a JSON
		
		resp.setContentType("text/json");
		//resp.getWriter().println(json);
	}
	
}
