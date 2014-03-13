package sos.us.es;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Jenny extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String json;
		Gson gson = new Gson();
		
		//Crear datos de prueba : Crear lista de 2 poblaciones
		
		Seville s1 = new Seville(20000000, 2008);
		Seville s2 = new Seville(5000000, 2010);
		
		List<Seville> sev = new LinkedList<Seville>();
		
		sev.add(s1);
		sev.add(s2);
		
		//Serializar
		json = gson.toJson(sev);
		
		//responder
		resp.setContentType("text/json");
		resp.getWriter().println(json);
	}
}
