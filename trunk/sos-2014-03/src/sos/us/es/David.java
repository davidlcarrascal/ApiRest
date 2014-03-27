package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class David extends HttpServlet {
	
	static List<SpainStd> l =new ArrayList<SpainStd>();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		

		// Crear datos de prueba- Crear lista de 2 años
		SpainStd stad1 = new SpainStd(1999,30000021,1500000,2,234234,55555);
		l.add(stad1);
		
		//SpainStd stad2 = new SpainStd(2000,30000002,1231240,1,212100,66666);
		//l.add(stad2);
		
		
		// Serializar a JSON
		
		String json="";
		Gson gson = new Gson();
		List<String> rute  = Arrays.asList(req.getRequestURI().split("/"));
		
		if(rute.size()==3 && rute.get(2).equals("SpainStd")){
		
			json=gson.toJson(l);
			resp.setContentType("text/json");
			resp.getWriter().println(json);
			
		}else if(rute.size()==4 && rute.get(2).equals("SpainStd") ){ 
			
			Boolean contiene=false;
			SpainStd objeto=null;
			
			for(SpainStd o : l){
				if (o.getYear().equals(Integer.parseInt(rute.get(3)))){
					contiene = true;
					objeto =o;
				}
			}
				
			if (contiene){
				json=gson.toJson(objeto);
				resp.setContentType("text/json");
				resp.getWriter().println(json);
			}else{
				resp.setStatus(404);
			}
			
		}else{
			resp.setStatus(400);
		}
		
		
		
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
		
				
		List<String> rute  = Arrays.asList(req.getRequestURI().split("/"));
		
		if(rute.size()==3 && rute.get(2).equals("SpainStd")){
			l.add(spStad);
			
		}else{
			resp.setStatus(404);
		}
			
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
		
		List<String> rute  = Arrays.asList(req.getRequestURI().split("/"));
		
		if(rute.size()==4 && rute.get(2).equals("SpainStd")){
			
			Boolean contiene =false;
			SpainStd objeto_a_actualizar =new SpainStd();
			
			for (SpainStd o : l){
				
				if (o.getYear().equals(spStad.getYear())){
					objeto_a_actualizar=o;
					contiene = true;
				}
					
			}
				
			if(contiene){	
				
				objeto_a_actualizar.setEducationBudget(spStad.getEducationBudget());
				objeto_a_actualizar.setEmigrants(spStad.getEmigrants());
				objeto_a_actualizar.setPib(spStad.getPib());
				objeto_a_actualizar.setPopulation(spStad.getPopulation());
				objeto_a_actualizar.setUnemployed(spStad.getUnemployed());
					
			}else{
				
				resp.setStatus(404);
			}
			
		}else{
			
			resp.setStatus(400);
		}
		
		
	}
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		//delete la lista
		//delete un objeto
		
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
		
		List<String> rute  = Arrays.asList(req.getRequestURI().split("/"));
		
		if(rute.size()==3 && rute.get(2).equals("SpainStd")){
			
			l.clear();
			
		}else if(rute.size()==4 && rute.get(2).equals("SpainStd")){
		
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
				
			}else{
				resp.setStatus(404);
			}
			
		}else{
			
			resp.setStatus(400);
		}	
	}
}
