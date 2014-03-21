package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;


@SuppressWarnings("serial")
public class RocioServelt extends HttpServlet {
	
	static List<universitySeville> luni = new LinkedList<>();
//	public static Gson gson = new Gson();
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
		String json;
		Gson gson= new Gson();
		
		//crear datos de prueba: Crear lista 2 
		//List<universitySeville> uni = new LinkedList<>();
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
        luni.add(uni);
}
		
/*		universitySeville uni = null;
		Gson gson = new Gson();
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		
		String json;
		
		while((json = br.readLine())  != null){
			sb.append(json);
		}
		json = sb.toString();
		
		try{
			uni = gson.fromJson(json, universitySeville.class);
		}catch(Exception e){
			System.out.println("ERROR universitySeville: "+e.getMessage());
		}
		System.out.println("el json procesado es"+json+"");
		luni.add(uni);
*/		
		//json = gson.toJson(uni);
		//resp.setContentType("text/json");
		//resp.getWriter().print(json);
		
//	}
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		
		Gson gson = new Gson();
        Enumeration en = req.getParameterNames();
        universitySeville uni = null;
        while (en.hasMoreElements()) {
            uni = gson.fromJson((String) en.nextElement(), universitySeville.class);
        }
        
   //    if(req.getPathInfo() != null){}
        
       
        universitySeville del = new universitySeville();
		for(universitySeville i:luni){
			if(i.getYear().equals(uni.getYear())){
				del = i;
			}
		}
		luni.remove(del);
		luni.add(uni);
/*		universitySeville uni = null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		
		String json;
		
		while((json = br.readLine())  != null){
			sb.append(json);
		}
		json = sb.toString();
		
		try{
			uni = gson.fromJson(json, universitySeville.class);
		}catch(Exception e){
			System.out.println("ERROR universitySeville: "+e.getMessage());
		}
		System.out.println("el json procesado es"+json+"");
		
		universitySeville del = new universitySeville();
		for(universitySeville i:luni){
			if(i.getYear().equals(uni.getYear())){
				del = i;
			}
		}
		luni.remove(del);
		luni.add(uni);
*/		
}	
		
		
	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException{
/*		universitySeville uni = null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		
		String json;
		
		while((json = br.readLine())  != null){
			sb.append(json);
		}
		json = sb.toString();
		
		try{
			uni = gson.fromJson(json, universitySeville.class);
		}catch(Exception e){
			System.out.println("ERROR universitySeville: "+e.getMessage());
		}
*/		
		Gson gson = new Gson();
        Enumeration en = req.getParameterNames();
        universitySeville uni = null;
        while (en.hasMoreElements()) {
            uni = gson.fromJson((String) en.nextElement(), universitySeville.class);
        }
		
		universitySeville del= new universitySeville();
		for(universitySeville i: luni){
			if(i.getYear().equals(uni.getYear())){
					del = i;
			}else{
				System.out.println("LOS DATOS A BORRAR NO SE ENCUENTRAN EN LA API");
			}
		}
		luni.remove(del);
		
	}
}
