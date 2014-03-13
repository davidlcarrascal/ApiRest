package sos.us.es;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.Gson;


@SuppressWarnings("serial")
public class RocioServelt extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
		String json;
		Gson gson= new Gson();
		
		//crear datos de prueba: Crear lista 2 
		
		

		universitySeville u1 = new universitySeville (2010, 10000);
		universitySeville u2 = new universitySeville (2011, 200000);
		
		List<universitySeville> uni =new ArrayList<universitySeville>();
		uni.add(u1);
		uni.add(u2);
		
		//Serializar a JSON
		
		json = gson.toJson(uni);
		
		//responder
		resp.setContentType("text/json");
		resp.getWriter().println(json);
	
		
		
		
		
	}
}
