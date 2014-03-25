package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Jenny extends HttpServlet {
	
	static List<Seville> sev = new ArrayList<Seville>();
	//public static Gson gson = new Gson();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		PrintWriter out = resp.getWriter();
		
		String json = "";
		Gson gson = new Gson();
		
		String url = req.getRequestURI();
		if(url.split("/").length == 2){
			resp.setStatus(404);
			out.write("{\"error\"); \"Not found\"}");
		}else{
			json = gson.toJson(sev);
			resp.setContentType("text/json");
			resp.getWriter().println(json);
		}
		//	sev = new ArrayList<Seville>();
		//	sev.add(getYear());
		//	out.println(sev);
		//}
		//Crear datos de prueba : Crear lista de 2 poblaciones
		//List<Seville> sev = new LinkedList<Seville>();
		//Seville s1 = new Seville(2008, 20000000);
		//Seville s2 = new Seville(2010, 5000000);
		
		//sev.add(s1);
		//sev.add(s2);
		
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
		//                           resp.getWriter().println(json);
		
		//String json = gson.toJson(sev);
	}
	
	//Método doPost
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		Seville s = null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br =req.getReader();
		
		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
			sb.append(jsonString);
		}
		
		jsonString = sb.toString();
		
		try{
			s = gson.fromJson(jsonString, Seville.class);
		}catch(Exception e){
			System.out.println("ERROR parsing Seville: "+e.getMessage());
		}
		
		String json;
		json=gson.toJson(s);
		
		resp.setContentType("text/json");
		resp.getWriter().println(json);
	}
	
	//Método doDelete
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		
		resp.setContentType("text/json");
		resp.getWriter().println("Borrando");
		
		//sev.clear();
	}
}
