package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.*;

import com.google.gson.Gson;


@SuppressWarnings("serial")
public class RocioServelt extends HttpServlet {
	//static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	static List<universitySeville> luni = new ArrayList<universitySeville>();

	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//		universitySeville u1 = new universitySeville(2011,2,4,5,6);
//		luni.add(u1);
		
		String json="";
		Gson gson= new Gson();
		
		
		String ruta[]=req.getRequestURI().split("/");
		if(ruta.length==4){
			
			json=gson.toJson(luni);
			resp.setContentType("text/json");
			resp.getWriter().println(json);	
			
		}else{
			List<universitySeville> prov=new LinkedList<universitySeville>();
			Integer contiene = null;
			for(universitySeville i :luni){
				if(i.getYear().equals(Integer.parseInt(ruta[ruta.length-1]))){			
					prov.add(i);
				}else{
					contiene = 1;
				}
			}
			if(contiene == 1){
				resp.setStatus(400);
			}
			json=gson.toJson(prov);
			resp.setContentType("text/json");
			resp.getWriter().println(json);	
		}
	
	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		

		universitySeville uni=null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		
		String jsonString;
		
		while((jsonString = br.readLine()) != null){
			sb.append(jsonString);
		}
		
		jsonString= sb.toString();
		
		try{
			
			uni= gson.fromJson(jsonString, universitySeville.class);
			
		}catch(Exception e){
			System.out.println("ERROR parsing universitySeville:" + e.getMessage());
		}
		
		 String ruta[]=req.getRequestURI().split("/");
		if(ruta.length == 4){
			 luni.add(uni);
			 resp.setStatus(201);
		}else{
			resp.setStatus(400);
       }
		
			
		
		
		
		
		

}
		

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		universitySeville uni=null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		
		String jsonString;
		
		while((jsonString = br.readLine()) != null){
			sb.append(jsonString);
		}
		
		jsonString= sb.toString();
		
		try{
			
			uni= gson.fromJson(jsonString, universitySeville.class);
			
		}catch(Exception e){
			System.out.println("ERROR parsing universitySeville:" + e.getMessage());
		}

        String ruta[]=req.getRequestURI().split("/");
        if(ruta.length == 5){
        	
        	for(universitySeville i:luni){
				if(i.getYear().equals(Integer.parseInt(ruta[ruta.length-1]))){
					i.setBudget(uni.getBudget());
					i.setEmployability(uni.getEmployability());
					i.setEnrolled(uni.getEnrolled());
					i.setStudentMigrants(uni.getStudentMigrants());
					
					
    			}else{
    				resp.setStatus(400);
    			}
    		}
    		
        }else{
        	resp.setStatus(400);	
        }
        
       
	
}	
		
		
	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        String ruta[]=req.getRequestURI().split("/");
        if(ruta.length == 4){
        	luni.clear();
        }else{
        	for(universitySeville i :luni){
        		if(i.getYear().equals(Integer.valueOf(ruta[ruta.length-1]))){
        			luni.remove(i);
        		}else{
        			resp.setStatus(400);
        		}
        	}
        }
        
	}
}
	

