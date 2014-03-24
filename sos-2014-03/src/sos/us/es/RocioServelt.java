package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
	//static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	static List<universitySeville> luni = new LinkedList<>();
//	public static Gson gson = new Gson();
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		
		PrintWriter out=resp.getWriter();
		String json="";
		Gson gson= new Gson();
		
		
		String ruta[]=req.getRequestURI().split("/");
		if(ruta.length==4){
			if(luni.size()==0){
				resp.sendError(404);
				
			}else{
			json=gson.toJson(luni);
			resp.setContentType("text/json");
			resp.getWriter().println(json);	
			}
		}else{
			List<universitySeville> prov=new LinkedList<universitySeville>();
			for(universitySeville uni:luni){
				if(uni.getYear().equals(Integer.parseInt(ruta[ruta.length-1]))){
					prov.add(uni);
				}
			}
			json=gson.toJson(prov);
			resp.setContentType("text/json");
			resp.getWriter().println(json);	
		}
		
		
		
		//responder
	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		Gson gson = new Gson();
        Enumeration en = req.getParameterNames();
        universitySeville uni = null;
        /*while (en.hasMoreElements()) {
            uni = gson.fromJson((String) en.nextElement(), universitySeville.class);
        }*/
        String ruta[]=req.getRequestURI().split("/");
        if(ruta.length == 4){
        	Integer year=Integer.parseInt(req.getParameter("year"));
        	Integer enrolled=Integer.parseInt(req.getParameter("enrolled"));
        	uni=new universitySeville(year,enrolled);
        	
        	luni.add(uni);
        	resp.setStatus(201);
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
	
