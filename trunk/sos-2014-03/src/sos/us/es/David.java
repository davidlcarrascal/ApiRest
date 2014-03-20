package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class David extends HttpServlet {
	
	static List<SpainStd> l =new ArrayList<SpainStd>();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String json;

		// Crear datos de prueba- Crear lista de 2 a�os
		Gson gson = new Gson();
		
		SpainStd stad1 = new SpainStd(1999,30000021,1500000,2,234234,55555);
		SpainStd stad2 = new SpainStd(2000,30000002,1231240,1,212100,66666);
		
		l.add(stad1);
		l.add(stad2);
		
		// Serializar a JSON
		json=gson.toJson(l);
		
		
		resp.setContentType("text/json");
		resp.getWriter().println(json);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		//Crear un objeto estadistica( un objeto pueblo)
		//sobre la lista da error
		
		//metemos datos con el ARC(advance rest client), estos datos eran JSON
		//entonces una vez recibimos los datos lo cogemos y lo transformamos de JSON
		//a objeto Java
		
		SpainStd spStad =null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		
		String jsonString;
		
		while((jsonString = br.readLine()) != null){
			sb.append(jsonString);
		}
		
		jsonString= sb.toString();
		
		try{
			
			spStad= gson.fromJson(jsonString, SpainStd.class);
			
		}catch(Exception e){
			System.out.println("ERROR parsing SpainStd:" + e.getMessage());
		}
		
		l.add(spStad);
		
		/*
		
		Para PRUEBA
		1)
		Con un log, que se ejecuta solo en consola. No responde datos.
		System.out.println("El json es: "+"jsonString");
		
		2)
		String json;
		json=gson.toJson(spStad);

		resp.setContentType("text/json");
		resp.getWriter().println(json);
		
		*/
		
	}
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		//put sobre la lista--> da error
		//put sobre un objeto de la lista--> Actualiza
		
		SpainStd spStad =null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		
		String jsonString;
		
		while((jsonString = br.readLine()) != null){
			sb.append(jsonString);
		}
		
		jsonString= sb.toString();
		
		try{
			
			spStad= gson.fromJson(jsonString, SpainStd.class);
			
		}catch(Exception e){
			System.out.println("ERROR parsing SpainStd:" + e.getMessage());
		}
		
		Boolean contiene =false;
		SpainStd objeto_a_borrar =new SpainStd();
		
		for (SpainStd o : l){
			
			if (o.getYear().equals(spStad.getYear())){
				objeto_a_borrar=o;
				contiene = true;
			}
			
		}
		
		if(contiene){	
			l.remove(objeto_a_borrar);
			l.add(spStad);
			
		}else{
			//Da error
			System.out.println("ERROR parsing SpainStd");
		}
		
		
	}
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		//delete la lista
		//delete un objeto
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Estoy haciendo un DELETE");
	}
	
}
