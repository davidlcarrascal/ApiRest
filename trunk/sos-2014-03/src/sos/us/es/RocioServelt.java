package sos.us.es;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;


@SuppressWarnings("serial")
public class RocioServelt extends HttpServlet {
	
	public static List<universitySeville> uni = new LinkedList<>();
	public static Gson json = new Gson();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
		String json;
		Gson gson= new Gson();
		
		//crear datos de prueba: Crear lista 2 
		universitySeville u1 = new universitySeville (2010, 10000);
		universitySeville u2 = new universitySeville (2011, 200000);
		
		uni.add(u1);
		uni.add(u2);
		
		//Serializar a JSON
		json = gson.toJson(uni);
		
		//responder
		resp.setContentType("text/json");
		resp.getWriter().println(json);		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		resp.setContentType("text/json");
		resp.getWriter().print(json);
	}
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		resp.setContentType("text/json");
		resp.getWriter().print(json);
	}
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		uni.clear();
		resp.setContentType("text/json");
		resp.getWriter().println(json);
	}
}
