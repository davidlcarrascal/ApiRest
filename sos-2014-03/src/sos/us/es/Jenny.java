package sos.us.es;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Jenny extends HttpServlet {
	
	public static List<Seville> sev = new LinkedList<Seville>();
	public static Gson gson = new Gson();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		String json;
		//Gson json = new Gson();
		
		//Crear datos de prueba : Crear lista de 2 poblaciones
		
		Seville s1 = new Seville(2008, 20000000);
		Seville s2 = new Seville(2010, 5000000);
		
		sev.add(s1);
		sev.add(s2);
		
		//Serializar
		json = gson.toJson(sev);
		
		//responder
		resp.setContentType("text/json");
		resp.getWriter().println(json);
	}
	
	//Método doPut
	public void doPut(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		resp.setContentType("text/json");
		resp.getWriter().println(gson);
		
		//String json = gson.toJson(sev);
	}
	
	//Método doPost
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		resp.setContentType("text/json");
		resp.getWriter().println(gson);
	}
	
	//Método doDelete
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		resp.setContentType("text/json");
		resp.getWriter().println(gson);
		
		sev.remove(sev.size()-1);
	}	
}
