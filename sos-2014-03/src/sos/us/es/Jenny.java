package sos.us.es;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Jenny extends HttpServlet {
	
		
		List<Seville> sev = new LinkedList<>();
		//String json;
		Gson gson = new Gson();
		
		//Crear datos de prueba : Crear lista de 2 poblaciones
		public void init(){
			Seville s1 = new Seville(2008, 20000000);
			Seville s2 = new Seville(2010, 5000000);
		
		//List<Seville> sev = new LinkedList<Seville>();
			sev.add(s1);
			sev.add(s2);
		}
		//Serializar
		//json = gson.toJson(sev);
		
		public void doGet(HttpServletRequest req, HttpServletResponse resp) 
				throws IOException {
			String json = gson.toJson(sev);
			
		//responder
			resp.setContentType("text/json");
			resp.getWriter().println(json);
	}
}
