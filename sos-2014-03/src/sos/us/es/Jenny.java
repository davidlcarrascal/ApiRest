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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Jenny extends HttpServlet {

	DatastoreService bd = DatastoreServiceFactory.getDatastoreService();

	// M�todo para poder acceder a la base de datos para coger todos los datos
	private List<String> getSeville() {

		Gson gson = new Gson();
		Seville sev = null;
		List<String> lsev = new ArrayList<String>();

		Query query = new Query("Seville");// Quiero todos los objetos del tipo
											// Seville
		PreparedQuery pQuery = bd.prepare(query);// Conecto con la base de datos
		Iterator<Entity> e = pQuery.asIterator();// Lo que le he pedido me lo
													// devuelve en un iterador
		Entity entity = null;

		while (e.hasNext()) {// Me recorro mi iterador y me creo el tipo json
			entity = e.next();
			Long year = (Long) entity.getProperty("year");
			Long population = (Long) entity.getProperty("population");
			Long unemployed = (Long) entity.getProperty("unemployed");
			Double educationBudget = (Double) entity
					.getProperty("educationBudget");
			Long migrants = (Long) entity.getProperty("migrants");
			Double pib = (Double) entity.getProperty("pib");
			sev = new Seville(year, population, unemployed, educationBudget,
					migrants, pib);// Tengo un objeto creado con todos los
									// atributos
			lsev.add(gson.toJson(sev));// Paso mi objeto a json y lo introduzco
										// en la lista, lsev
		}
		return lsev;
	}

	// para un objeto con los seis atributos
	private String getCity(String url) {
		Long year = new Long(url.split("/")[1]);// Aqui tengo el a�o, como
												// me lo da en string,
												// ,e lo paso a Long
		FilterPredicate predicate = new FilterPredicate("year",
				Query.FilterOperator.EQUAL, year);// apunta al a�o que yo le
													// ponga
		Query query = new Query("Seville").setFilter(predicate);// consulta del
																// a�o que yo
																// kiero
		PreparedQuery pQuery = bd.prepare(query);// conecto con la base de
													// datos, kiero esto
		Entity en = pQuery.asSingleEntity();// tengo el objeto Sevilla del a�o
											// especificado
		if (en == null) {
			return null;
		} else {
			Gson gson = new Gson();
			Long year1 = (Long) en.getProperty("year");
			Long population = (Long) en.getProperty("population");
			Long unemployed = (Long) en.getProperty("unemployed");
			Double educationBudget = (Double) en.getProperty("educationBudget");
			Long migrants = (Long) en.getProperty("migrants");
			Double pib = (Double) en.getProperty("pib");
			Seville sev = new Seville(year, population, unemployed,
					educationBudget, migrants, pib);
			return gson.toJson(sev);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setHeader("Content-Type", "application/json");
		resp.setCharacterEncoding("UTF-8");

		String url = req.getPathInfo();
		// System.out.println(url);
		// System.out.println(url.split("/").length);
		// Si la longitud es 0 es porque despues de Seville, en mi caso, he
		// puesto una barra
		// System.out.println(url.split("/").length == 0);
		PrintWriter out = resp.getWriter();
		List<String> sev = new ArrayList<String>();

		if (url == null || url.split("/").length == 0) {// Si pongo Seville o
														// Seville/
			sev = getSeville();
			
			if (sev.isEmpty()) {
				resp.setStatus(601);
				out.write("{\"error\": \"Empty database\"}");
			}else{
				out.println(sev);
			}
			
		} else if (url.split("/").length == 2) {
			String en = getCity(url);

			if (en == null) {
				resp.setStatus(404);
				out.write("{\"error\": \"Not found\"}");
			} else {
				sev.add(en);
				out.println(sev);
			}
		} else {// pedir en la url m�s de un atributo
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");
		}
		out.close();

	}

	// M�todo doPost
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setHeader("Content-Type", "application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		Seville sev = null;
		Entity en = new Entity("Seville");
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		String url = req.getPathInfo();
		String jsonString;
		if (url == null || url.split("/").length == 0) {
			// Me lee el json
			while ((jsonString = br.readLine()) != null) {
				sb.append(jsonString);
			}

			jsonString = sb.toString();
			try {
				sev = gson.fromJson(jsonString, Seville.class);
				FilterPredicate predicate = new FilterPredicate("year",
						FilterOperator.EQUAL, sev.getYear());
				Query query = new Query("Seville").setFilter(predicate);
				PreparedQuery pQuery = bd.prepare(query);
				Entity ent = pQuery.asSingleEntity();
				if (ent == null) {// Si el entity es nulo, me crea un objeto
					en.setProperty("year", sev.getYear());
					en.setProperty("population", sev.getPopulation());
					en.setProperty("unemployed", sev.getUnemployed());
					en.setProperty("educationBudget", sev.getEducationBudget());
					en.setProperty("migrants", sev.getMigrants());
					en.setProperty("pib", sev.getPib());
					bd.put(en);// Meto en la base de datos el objeto de tipo
								// entity
					resp.setStatus(201);
					out.write("{\"error\": \"Created\"}");
				} else {
					resp.setStatus(603);
					out.write("{\"error\": \"Year already exists\"}");
				}
			} catch (Exception e) {
				resp.setStatus(300);
				out.write("{\"error\": \"Not created\"}");
				// System.out.println("ERROR parsing Seville: " +
				// e.getMessage());
				// porque el json no est� bien puesto, hay mas o menos
				// parametros o no est�n puestos en el orden correcto
			}
		} else {
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");
		}
	}

	// M�todo doPut
	public void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setHeader("Content-Type", "application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		String url = req.getPathInfo();
		String jsonString;

		if (url == null || url.split("/").length != 2) {// Si pongo Seville o
														// Seville/ o que no
														// haya puesto el a�o
														// s�lo, sino que mas
														// atributos como
														// poblacion...
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");
		} else {
			// Me lee el json
			while ((jsonString = br.readLine()) != null) {
				sb.append(jsonString);
			}
			jsonString = sb.toString();
			Seville sev = gson.fromJson(jsonString, Seville.class);
			Long yearJson = sev.getYear();
			Long yearUrl = new Long(url.split("/")[1]);
			if (!yearUrl.equals(yearJson)) {
				resp.setStatus(401);
				out.write("{\"error\": \"Not authorized\"}");
			} else {
				FilterPredicate predicate = new FilterPredicate("year",
						Query.FilterOperator.EQUAL, yearUrl);// apunta al a�o
																// que yo le
																// ponga
				Query query = new Query("Seville").setFilter(predicate);// consulta
																		// del
																		// a�o
																		// que
																		// yo
																		// kiero
				PreparedQuery pQuery = bd.prepare(query);// conecto con la base
															// de
															// datos, kiero esto
				Entity en = pQuery.asSingleEntity();// tengo el objeto Sevilla
													// del a�o
													// especificado
				if (en == null) {
					resp.setStatus(404);
					out.write("{\"error\": \"Not found\"}");
				} else {
					try {
						bd.delete(en.getKey());
						en.setProperty("year", sev.getYear());
						en.setProperty("population", sev.getPopulation());
						en.setProperty("unemployed", sev.getUnemployed());
						en.setProperty("educationBudget",
								sev.getEducationBudget());
						en.setProperty("migrants", sev.getMigrants());
						en.setProperty("pib", sev.getPib());
						bd.put(en);// Meto en la base de datos el objeto de tipo
									// entity
						resp.setStatus(200);
						out.write("{\"error\": \"OK\"}");// Objeto actualizado
					} catch (Exception e) {
						resp.setStatus(304);
						out.write("{\"error\": \"Not modified\"}");
					}
				}
			}
		}
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setHeader("Content-Type", "application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		String url = req.getPathInfo();
		//Para la todo lo ke tega en la base de datos, la lista
		if (url == null || url.split("/").length == 0) {
			List<Key> lk = new ArrayList<Key>();
			Query query = new Query("Seville");
			PreparedQuery pQuery = bd.prepare(query);
			Iterator<Entity> ite = pQuery.asIterator();
			Entity en = null;
			while(ite.hasNext()){
				en = ite.next();
				lk.add(en.getKey());
			}
			if(lk.isEmpty()){
				resp.setStatus(601);
				out.write("{\"error\": \"Empty database\"}");
			}else{
				bd.delete(lk);
				resp.setStatus(200);
				out.write("{\"error\": \"OK\"}");// Objetos borrados
			}
			//para un objeto nada mas de la base de datos
		} else if (url.split("/").length == 2) {
			Long yearUrl = new Long(url.split("/")[1]);// Tengo el a�o de la url
			FilterPredicate predicate = new FilterPredicate("year",
					FilterOperator.EQUAL, yearUrl);
			Query query = new Query("Seville").setFilter(predicate);
			PreparedQuery pQuery = bd.prepare(query);
			Entity en = pQuery.asSingleEntity();
			if (en == null) {
				resp.setStatus(404);
				out.write("{\"error\": \"Not found\"}");
			}else{
				Key k = en.getKey();
				bd.delete(k);
			}
		}else{
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");
		}
	}
}
