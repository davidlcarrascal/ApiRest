package sos.us.es;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class David extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String json;

		// Crear datos de prueba- Crear lista de 2 años
		Gson gson = new Gson();
		
		SpainStd stad1 = new SpainStd(1999,30000021,1500000,2,234234,55555);
		SpainStd stad2 = new SpainStd(2000,30000002,1231240,1,212100,66666);
		
		List<SpainStd> l =new ArrayList<SpainStd>();
		l.add(stad1);
		l.add(stad2);
		
		// Serializar a JSON
		json=gson.toJson(l);
		
		
		resp.setContentType("text/json");
		resp.getWriter().println(json);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Estoy haciendo un POST");
	}
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/plain");
		resp.getWriter().println("Estoy haciendo un PUT");
	}
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/plain");
		resp.getWriter().println("Estoy haciendo un DELETE");
	}
	
}
