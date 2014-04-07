package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;


@SuppressWarnings("serial")
public class RocioServelt extends HttpServlet {
	static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	static List<universitySeville> luni = new ArrayList<universitySeville>();

	
	private List<String> getUniversitySeville(){
		Query q=new Query("UniversitySeville");
		PreparedQuery p=datastore.prepare(q);
		Iterable<Entity> resultados=p.asIterable();
		List<String> todos = new ArrayList<String>();
		Gson gson= new Gson();
		for(Entity us : resultados){
			Integer year=(Integer)us.getProperty("year");
			Integer enrolled=(Integer)us.getProperty("enrolled");
			Integer budget=(Integer)us.getProperty("budget");
			Integer employability=(Integer)us.getProperty("employability");
			Integer studentMigrants=(Integer)us.getProperty("studentMigrants");
			universitySeville luis=new universitySeville(year, enrolled, budget, employability, studentMigrants);
			todos.add(gson.toJson(luis));
		}
		return todos;
	}
	private String getUniversitySevilleBy(Integer year){
		FilterPredicate predicate = new FilterPredicate("year",Query.FilterOperator.EQUAL, year);
		Query q = new Query("UniversitySeville").setFilter(predicate);
		PreparedQuery p=datastore.prepare(q);
		Entity us=p.asSingleEntity();
		Gson gson= new Gson();
		universitySeville luis=new universitySeville();
		try{
			Integer year2=(Integer)us.getProperty("year");
			Integer enrolled=(Integer)us.getProperty("enrolled");
			Integer budget=(Integer)us.getProperty("budget");
			Integer employability=(Integer)us.getProperty("employability");
			Integer studentMigrants=(Integer)us.getProperty("studentMigrants");
			luis=new universitySeville(year2, enrolled, budget, employability, studentMigrants);
		}catch(NullPointerException e){
			return null;
			
		}
			return gson.toJson(luis);	
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//		universitySeville u1 = new universitySeville(2011,2,4,5,6);
//		luni.add(u1);


		resp.setContentType("application/json");
		PrintWriter out=resp.getWriter();
		String ruta[]=req.getRequestURI().split("/");
		if(ruta.length==4){
			
		List<String> todo=getUniversitySeville();
		out.println(todo);
		
			
		}else{
			Integer year=(Integer.parseInt(ruta[ruta.length-1]));
			String uni=getUniversitySevilleBy(year);
			if(uni==null){
				resp.setStatus(404);
				out.println("{\"error\": \"404 Not Found\"}");
			}else{
			out.println(uni);
			}
		}
		out.close();
	
	
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
		Boolean contiene = false;
		for(universitySeville i :luni){
			if(i.getYear().equals(uni.getYear())){			
				contiene = true;
			}
		}
		if(contiene){
			resp.setStatus(409);
		}else if(ruta.length == 4){
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
    				resp.setStatus(404);
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
        			resp.setStatus(404);
        		}
        	}
        }
        
	}
}
	

