package sos.us.es;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class David extends HttpServlet {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	private List<SpainStd> listaDatastore(){
		
		/*Metodo que devuelve una lista de objetos SpainStd.
		 Recorre la base de datos, y transforma los objetos entidad
		 en SpainStd, luego lo añade a una lista "lfin" que es lo que
		 se devuelve*/
		
		SpainStd std = null;
		List<SpainStd> lfin = new ArrayList<SpainStd>();

		Query q = new Query("SpainStd");//Petición de todos los objetos SpainStd
		PreparedQuery pq = datastore.prepare(q);//Preparación de la consulta
		Iterator<Entity> ite = pq.asIterator();//Iterador del resultado de la consulta
		Entity entity = null;

		while(ite.hasNext()){
			entity = ite.next();

			Long year = (Long) entity.getProperty("year");
			Long population = (Long) entity.getProperty("population");
			Long unemployed = (Long) entity.getProperty("unemployed");
			Long pib = (Long) entity.getProperty("pib");
			Long emigrants = (Long) entity.getProperty("emigrants");
			Long educationBudget = (Long) entity.getProperty("educationBudget");
			std = new SpainStd(year, population, unemployed, pib, emigrants, educationBudget);//Tengo un objeto creado con todos los atributos
			lfin.add(std);
		}
		return lfin;
	}

	private void putDatastore (SpainStd s, String metodo, List<String> rute){

		/*Métdo encargado de añadir un objeto en la base de datos en el caso 
		 * en el que se le pase por el parametro post -->metodo=post. Si por 
		 * el parametro se le pasa put --> metodo=put este se encarga de actualizar
		 * un objeto en la base de datos. 
		 */
		
		if(metodo.equals("post")){

			Entity e = new Entity("SpainStd");

			e.setProperty("year",s.getYear());
			e.setProperty("population",s.getPopulation());
			e.setProperty("unemployed",s.getUnemployed());
			e.setProperty("pib",s.getPib());
			e.setProperty("emigrants",s.getEmigrants());
			e.setProperty("educationBudget",s.getEducationBudget());

			datastore.put(e);

		}else if(metodo.equals("put")){

			Integer year = new Integer(rute.get(4));
			Query q = new Query("SpainStd").setFilter(new FilterPredicate(
					"year", Query.FilterOperator.EQUAL, year));
			PreparedQuery pq = datastore.prepare(q);

			Entity e = pq.asSingleEntity();

			e.setProperty("population",s.getPopulation());
			e.setProperty("unemployed",s.getUnemployed());
			e.setProperty("pib",s.getPib());
			e.setProperty("emigrants",s.getEmigrants());
			e.setProperty("educationBudget",s.getEducationBudget());

			datastore.delete(e.getKey());
			datastore.put(e);
		}


	}


	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


		String json="";
		Gson gson = new Gson();
		List<String> rute  = Arrays.asList(req.getRequestURI().split("/"));
		List<SpainStd> ld=listaDatastore();
		//System.out.println(listaDatastore());

		if(rute.size()==4 && rute.get(3).equals("SpainStd")){

			json=gson.toJson(ld);
			resp.setContentType("text/json");
			resp.getWriter().println(json);

		}else if(rute.size()==5 && rute.get(3).equals("SpainStd") ){ 

			Boolean contiene=false;
			SpainStd objeto=null;

			for(SpainStd o : ld){
				if (o.getYear().equals(Long.parseLong(rute.get(4)))){
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
		List<SpainStd> ld=listaDatastore();

		if(rute.size()==4 && rute.get(3).equals("SpainStd")){
			//ver si contiene
			Boolean contiene =false;

			for (SpainStd o : ld){

				if (o.getYear().equals(spStad.getYear())){
					contiene = true;
				}
			}
			if(!contiene){
				putDatastore(spStad,"post",rute);
				resp.setStatus(201);
			}else{
				resp.setStatus(409);
			}
			
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
		List<SpainStd> ld=listaDatastore();

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

		if(rute.size()==5 && rute.get(3).equals("SpainStd")){

			Boolean contiene =false;
			SpainStd objeto_a_actualizar =new SpainStd();

			for (SpainStd o : ld){

				if (o.getYear().equals(spStad.getYear())){
					objeto_a_actualizar=o;
					contiene = true;
				}

			}

			if(contiene && objeto_a_actualizar.getYear().equals(Long.parseLong(rute.get(4)))){

				putDatastore(spStad, "put",rute);

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

		List<String> rute  = Arrays.asList(req.getRequestURI().split("/"));
		List<SpainStd> ld=listaDatastore();

		if(rute.size()==4 && rute.get(3).equals("SpainStd")){

			Query q = new Query("SpainStd");
			PreparedQuery pq = datastore.prepare(q);//Prepatación de la consulta
			Iterator<Entity> ite = pq.asIterator();//Iterador del resultado de la consulta
			Entity entity = null;

			while(ite.hasNext()){
				entity = ite.next();
				datastore.delete(entity.getKey());
			}

		}else if(rute.size()==5 && rute.get(3).equals("SpainStd")){

			Boolean contiene =false;

			for (SpainStd o : ld){

				if (o.getYear().equals( Long.parseLong(rute.get(4)))){
					
					contiene = true;
				}

			}

			if(contiene){
				
				Long year = new Long(rute.get(4));
				Query q = new Query("SpainStd").setFilter(new FilterPredicate(
						"year", Query.FilterOperator.EQUAL, year));
				PreparedQuery pq = datastore.prepare(q);

				Entity e = pq.asSingleEntity();

				datastore.delete(e.getKey());

			}else{
				resp.setStatus(404);
			}

		}else{

			resp.setStatus(400);
		}
	}
}
