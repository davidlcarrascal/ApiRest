package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Jenny extends HttpServlet {
	
	DatastoreService bd = DatastoreServiceFactory.getDatastoreService();
	
	//Método para poder acceder a la base de datos para coger todos los datos
	private List<String> getSeville(){
		
		Gson gson= new Gson();
		Seville sev = null;
		List<String> lsev = new ArrayList<String>();
		
		Query query = new Query("Seville");//Quiero todos los objetos del tipo Seville
		PreparedQuery pQuery = bd.prepare(query);//Conecto con la base de datos
		Iterator<Entity> e = pQuery.asIterator();//Lo que le he pedido me lo devuelve en un iterador
		Entity entity = null;
		
		while(e.hasNext()){//Me recorro mi iterador y me creo el tipo json
			entity = e.next();
			Integer year = (Integer) entity.getProperty("year");
			Integer population = (Integer) entity.getProperty("population");
			Integer unemployed = (Integer) entity.getProperty("unemployed");
			Double educationBudget = (Double) entity.getProperty("educationBudget");
			Integer migrants = (Integer) entity.getProperty("migrants");
			Double pib = (Double) entity.getProperty("pib");
			sev = new Seville(year, population, unemployed, educationBudget, migrants, pib);//Tengo un objeto creado con todos los atributos
			lsev.add(gson.toJson(sev));//Paso mi objeto a json y lo introduzco en la lista, lsev
		}
		return lsev;
	}
	
	//para un objeto con los seis atributos
	private String getCity(String url){
		Integer year = new Integer(url.split("/")[1]);//Aqui tengo el año, como me lo da en string, ,e lo paso a integer
		FilterPredicate predicate = new FilterPredicate("year", Query.FilterOperator.EQUAL, year);//apunta al año que yo le ponga
		Query query = new Query("Seville").setFilter(predicate);//consulta del año que yo kiero
		PreparedQuery pQuery = bd.prepare(query);//conecto con la base de datos, kiero esto
		Entity en = pQuery.asSingleEntity();//tengo el objeto Sevilla del año especificado
		if(en == null){
			return null;
		}else{
			Gson gson = new Gson();
			Integer year1 = (Integer) en.getProperty("year");
			Integer population = (Integer) en.getProperty("population");
			Integer unemployed = (Integer) en.getProperty("unemployed");
			Double educationBudget = (Double) en.getProperty("educationBudget");
			Integer migrants = (Integer) en.getProperty("migrants");
			Double pib = (Double) en.getProperty("pib");
			Seville sev = new Seville(year, population, unemployed, educationBudget, migrants, pib);
			return gson.toJson(sev);
		}
	}
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		resp.setHeader("Content-Type", "application/json");
		resp.setCharacterEncoding("UTF-8");
		
		String url = req.getPathInfo();
		System.out.println(url);
		System.out.println(url.split("/").length);
		//Si la longitud es 0 es porque despues de Seville, en mi caso, he puesto una barra
		//System.out.println(url.split("/").length == 0);
		PrintWriter out = resp.getWriter();
		List<String> sev = new ArrayList<String>();
		
		if (url == null ||url.split("/").length == 0){//Si pongo Seville o Seville/
			sev = getSeville();
			out.println(sev);
			if(sev.isEmpty()){
				resp.setStatus(601);
				out.write("{\"error\"); \"Empty database\"}");
			}
		}else if(url.split("/").length == 2){
			String en = getCity(url);
			
			if(en == null){
				resp.setStatus(404);
				out.write("{\"error\"); \"Not found\"}");
			}else{
				sev.add(en);
				out.println(sev);
			}
		}else{//pedir en la url más de un atributo
			resp.setStatus(400);
			out.write("{\"error\"); \"Wrong request\"}");
		}
		out.close();

	}
		/*
		String json = "";
		Gson gson = new Gson();
		
		String url = req.getRequestURI();
		if(url.split("/").length == 2){
			resp.setStatus(404);
			out.write("{\"error\"); \"Not found\"}");
		}else{
			json = gson.toJson(sev);
			resp.setContentType("text/json");
			resp.getWriter().println(json);
		}
		//	sev = new ArrayList<Seville>();
		//	sev.add(getYear());
		//	out.println(sev);
		//}
		//Crear datos de prueba : Crear lista de 2 poblaciones
		//List<Seville> sev = new LinkedList<Seville>();
		//Seville s1 = new Seville(2008, 20000000);
		//Seville s2 = new Seville(2010, 5000000);
		
		//sev.add(s1);
		//sev.add(s2);
		
		//Serializar
		json = gson.toJson(sev);
		
		//responder
		resp.setContentType("text/json");
		resp.getWriter().println(json);
	}*/
	
	//Método doPut
	public void doPut(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		resp.setContentType("text/json");
		//                           resp.getWriter().println(json);
		
		//String json = gson.toJson(sev);
	}
	
	//Método doPost
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		Seville s = null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br =req.getReader();
		
		String jsonString;
		
		while( (jsonString = br.readLine()) != null ){
			sb.append(jsonString);
		}
		
		jsonString = sb.toString();
		
		try{
			s = gson.fromJson(jsonString, Seville.class);
		}catch(Exception e){
			System.out.println("ERROR parsing Seville: "+e.getMessage());
		}
		
		String json;
		json=gson.toJson(s);
		
		resp.setContentType("text/json");
		resp.getWriter().println(json);
	}
	
	//Método doDelete
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		
		resp.setContentType("text/json");
		resp.getWriter().println("Borrando");
		
		//sev.clear();
	}
}
