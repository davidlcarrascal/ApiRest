package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;


@SuppressWarnings("serial")
public class RocioServelt extends HttpServlet {
	
	static List<universitySeville> luni = new LinkedList<>();
//	public static Gson gson = new Gson();
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
		
//		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		Entity entity = new Entity("Year");
//		entity.setProperty("year",u.year);
//		entity.setProperty("enrolled",u.enrolled);
//		datastore.put(entity);
		
		String json;
		Gson gson= new Gson();
		
		//crear datos de prueba: Crear lista 2 
		
		universitySeville u1 = new universitySeville (2010, 10000);
		universitySeville u2 = new universitySeville (2011, 200000);
		
		luni.add(u1);
		luni.add(u2);
		
		//Serializar a JSON
		json = gson.toJson(luni);
		
		//responder
		resp.setContentType("text/json");
		resp.getWriter().println(json);		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		Gson gson = new Gson();
        Enumeration en = req.getParameterNames();
        universitySeville uni = null;
        while (en.hasMoreElements()) {
            uni = gson.fromJson((String) en.nextElement(), universitySeville.class);
        }
        String ruta[]=req.getRequestURI().split("/");
        if(ruta.length == 4){
        	luni.add(uni);
        }else{
        	resp.sendError(400);
        }
}
		

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		
		Gson gson = new Gson();
        Enumeration en = req.getParameterNames();
        universitySeville uni = null;
        while (en.hasMoreElements()) {
            uni = gson.fromJson((String) en.nextElement(), universitySeville.class);
        }
        String ruta[]=req.getRequestURI().split("/");
        if(ruta.length == 5){
        	universitySeville del = new universitySeville();
    		for(universitySeville i:luni){
    			if(i.getYear().equals(uni.getYear())){
    				del = i;
    			}
    		}
    		luni.remove(del);
    		luni.add(uni);
        }else{
        	resp.sendError(400);	
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
        		}
        	}
        }
        
	}
}
	
