package sos.us.es;
import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.http.*;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

@SuppressWarnings("serial")
public class RocioServelt extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
		String json;
		Gson gson= new Gson();
		
		//crear datos de prueba: Crear lista 2 
		
		

		universitySeville u1 = new universitySeville (2010, 10000, 2000);
		universitySeville u2 = new universitySeville (2011, 200000, 89000);
		
	//	UniversitySevilles.add(u1);
		//UniversitySevilles.add(u2);'''
		
		//Serializar a JSON
		
	//	json = gson.toJson(UniversitySevilles);
		
		//responder
		resp.setContentType("text/json");
//		resp.getWriter().println(json);
	
		
		
		
		
	}
}
