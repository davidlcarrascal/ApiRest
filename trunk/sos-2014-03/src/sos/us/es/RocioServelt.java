package sos.us.es;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.*;

import sun.awt.RepaintArea;

import com.google.gson.Gson;


@SuppressWarnings("serial")
public class RocioServelt extends HttpServlet {
	//static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	static List<universitySeville> luni = new LinkedList<>();
//	public static Gson gson = new Gson();
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		
		String json="";
		Gson gson= new Gson();
		
		
		String ruta[]=req.getRequestURI().split("/");
		if(ruta.length==4){
			if(luni.size()==0){
				resp.sendError(404);
				
			}else{
				for(universitySeville uni: luni){
					System.out.println(uni);

				}
			json=gson.toJson(luni);
			resp.setContentType("text/json");
			resp.getWriter().println(json);	
			}
		}else{
			List<universitySeville> prov=new LinkedList<universitySeville>();
			for(universitySeville uni:luni){
				if(uni.getYear().equals(Integer.parseInt(ruta[ruta.length-1]))){
					System.out.println(uni);
					prov.add(uni);
				}else{
					resp.sendError(404);
				}
			}
			json=gson.toJson(prov);
			resp.setContentType("text/json");
			resp.getWriter().println(json);	
		}
		
		
		
	
	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		
        universitySeville uni = null;

        String ruta[]=req.getRequestURI().split("/");
        if(ruta.length == 4){
        	
        	Map mapa=req.getParameterMap();
        	
        	
        	mapa.get("enrolled");
        	mapa.get("budget");
        	mapa.get("employability");
        	mapa.get("studentMigrants");
        	
        	Integer year=Integer.parseInt(mapa.get("year").toString());
        	Integer enrolled=Integer.parseInt((String)mapa.get("enrolled").toString());
        	Integer budget = Integer.parseInt((String)mapa.get("budget").toString());
        	Integer employability = Integer.getInteger((String)mapa.get("employability").toString());
        	Integer studentMigrants = Integer.getInteger((String)mapa.get("studentMigrants").toString());
        	uni=new universitySeville(year,enrolled,budget,employability,studentMigrants);
        	System.out.println(uni);


    	luni.add(uni);
     	resp.setStatus(201);
        }else{
        	resp.sendError(400);
        }
        
}
		

	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		
		
		universitySeville uni = null;
		Integer year=Integer.parseInt(req.getParameter("year"));
    	Integer enrolled=Integer.parseInt(req.getParameter("enrolled"));
    	Integer budget = Integer.parseInt(req.getParameter("budget"));
    	Integer employability = Integer.getInteger(req.getParameter("employability"));
    	Integer studentMigrants = Integer.getInteger(req.getParameter("studentMigrants"));
    	uni=new universitySeville(year,enrolled,budget,employability,studentMigrants);
    	
        String ruta[]=req.getRequestURI().split("/");
        if(ruta.length == 5){
        	
        	for(universitySeville i:luni){
				if(i.getYear().equals(Integer.parseInt(ruta[ruta.length-1]))){
					i.setEnrolled(enrolled);
					i.setBudget(budget);
					i.setEmployability(employability);
					i.setStudentMigrants(studentMigrants);
    			}else{
    				resp.sendError(400);
    			}
    		}
    		
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
        		}else{
        			resp.sendError(400);
        		}
        	}
        }
        
	}
}
	
