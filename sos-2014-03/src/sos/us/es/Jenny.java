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

	DatastoreService bd = DatastoreServiceFactory.getDatastoreService(); // aquí
																			// declaro
																			// mi
																			// base
																			// de
																			// datos

	// Método para poder acceder a la base de datos para coger todos los datos
	private List<String> getSeville() {

		Gson gson = new Gson();
		Seville sev = null;
		List<String> lsev = new ArrayList<String>();

		Query query = new Query("Seville");// Quiero todos los objetos del tipo
											// Seville
		PreparedQuery pQuery = bd.prepare(query);// Conecto con la base de datos
		Iterator<Entity> e = pQuery.asIterator();// Lo que le he pedido me lo
													// devuelve en un iterador
		Entity entity = null; // me declaro la variable entity

		while (e.hasNext()) {// Me recorro mi iterador y me creo mi tipo Seville
								// con mis propiedades
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
		return lsev; // Tengo un array de elementos en formato json.
	}

	// para un objeto con los seis atributos, para obtener la ciudad de un año
	// concreto, del año ke le paso en la url.
	private String getCity(String url) {

		Long year = new Long(url.split("/")[1]);// la posicion 1 es el año, Aqui
												// tengo el año, como
												// me lo da en string,
												// lo paso a Long
		FilterPredicate predicate = new FilterPredicate("year",
				Query.FilterOperator.EQUAL, year);// apunta al año que yo le
													// especifique en la url
		Query query = new Query("Seville").setFilter(predicate);// consulta
																// del
																// año que
																// yo
																// kiero
		PreparedQuery pQuery = bd.prepare(query);// conecto con la base de
													// datos, kiero esto
		Entity en = pQuery.asSingleEntity();// tengo el objeto Sevilla del
											// año
											// especificado (asSingleEntity)
											// porke solo voy a tener un
											// elemento de un año, ya ke año es
											// mi id
		if (en == null) {// Significa ke no existe el año especificado en la url
			return null;
		} else {
			Gson gson = new Gson();
			Long year1 = (Long) en.getProperty("year");
			Long population = (Long) en.getProperty("population");
			Long unemployed = (Long) en.getProperty("unemployed");
			Double educationBudget = (Double) en.getProperty("educationBudget");
			Long migrants = (Long) en.getProperty("migrants");
			Double pib = (Double) en.getProperty("pib");
			Seville sev = new Seville(year1, population, unemployed,
					educationBudget, migrants, pib);// Me creo el año seville y
													// lo paso a json
			return gson.toJson(sev);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setHeader("Content-Type", "application/json");
		resp.setCharacterEncoding("UTF-8");

		String url = req.getPathInfo(); // Me coge la url a partir de /Seville
		// System.out.println(url);
		// System.out.println(url.split("/").length);
		// Si la longitud es 0 es porque despues de Seville, en mi caso, he
		// puesto una barra
		// System.out.println(url.split("/").length == 0);
		PrintWriter out = resp.getWriter();
		List<String> sev = new ArrayList<String>();// lista de String porke el
													// json es String.

		if (url == null || url.split("/").length == 0) {// Si pongo Seville o
														// Seville/
			sev = getSeville();// coge todos los objetos

			if (sev.isEmpty()) {
				out.write("{\"error\": \"Empty database\"}");
			} else {
				out.println(sev);
			}

		} else if (url.split("/").length == 2) {// si pongo api/v1/Seville/2005
												// o api/v1/Seville/2005/
			String en = null;
			if (url.split("/")[1].matches("[0-9]*")) { // Si lo ke le pongo
														// detras de la barra es
														// uno o varios números
				en = getCity(url);// aki obtengo la ciudad del año especificado
			}
			if (en == null) {
				resp.setStatus(404);
				out.write("{\"error\": \"Not found\"}");
			} else {
				sev.add(en);
				out.println(sev);
			}
		} else {// pedir en la url más de un atributo
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");
		}
		out.close();

	}

	// Método doPost
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setHeader("Content-Type", "application/json");// pa decirle ke es
															// una aplicación
															// json
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();// para pintar en el navegador

		Seville sev = null;
		Entity en = new Entity("Seville");
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();// es un constructor de String
		BufferedReader br = req.getReader();// para leer
		String url = req.getPathInfo();// Me coge la url a partir de /Seville
		String jsonString;
		if (url == null || url.split("/").length == 0) {
			// Mientras ke haya lineas escritas, estaré leyendo, Me lee el json
			while ((jsonString = br.readLine()) != null) {
				sb.append(jsonString); // meto en el jsonString cada linea.
			}

			jsonString = sb.toString();// En jsonString tengo todo lo ke le he
										// pasado en formato String
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
					resp.setStatus(409);
					out.write("{\"error\": \"Conflict\"}");// no se puede crear
															// porke ya existe
				}
			} catch (Exception e) {// para contemplar bien los tipos
				resp.setStatus(409);
				out.write("{\"error\": \"Conflict\"}");
				// System.out.println("ERROR parsing Seville: " +
				// e.getMessage());
				// porque el json no está bien puesto, hay mas o menos
				// parametros o no están puestos en el orden correcto
			}
		} else {
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");// En el caso de ke ponga
														// /Seville/XXXXXXX O
														// /Seville/2014/XXXXXX
		}
	}

	// Método doPut
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
														// haya puesto el año
														// sólo, sino que mas
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
			if (url.split("/")[1].matches("[0-9]*")) {
				Long yearUrl = new Long(url.split("/")[1]);
				if (!yearUrl.equals(yearJson)) {
					resp.setStatus(401);
					out.write("{\"error\": \"Not authorized\"}");
				} else {
					FilterPredicate predicate = new FilterPredicate("year",
							Query.FilterOperator.EQUAL, yearUrl);// apunta al
																	// año
																	// que yo le
																	// ponga
					Query query = new Query("Seville").setFilter(predicate);// consulta
																			// del
																			// año
																			// que
																			// yo
																			// kiero
					PreparedQuery pQuery = bd.prepare(query);// conecto con la
																// base
																// de
																// datos, kiero
																// esto
					Entity en = pQuery.asSingleEntity();// tengo el objeto
														// Sevilla
														// del año
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
							bd.put(en);// Meto en la base de datos el objeto de
										// tipo
										// entity
							resp.setStatus(200);
							out.write("{\"error\": \"OK\"}");// Objeto
																// actualizado
						} catch (Exception e) {
							resp.setStatus(304);
							out.write("{\"error\": \"Not modified\"}");
						}
					}
				}
			} else {
				resp.setStatus(304);
				out.write("{\"error\": \"Not modified\"}");
			}
		}
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setHeader("Content-Type", "application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();

		String url = req.getPathInfo();
		// Para la todo lo ke tega en la base de datos, la lista
		if (url == null || url.split("/").length == 0) {
			List<Key> lk = new ArrayList<Key>();
			Query query = new Query("Seville");// Hago una consulta para todos
												// los objetos Seville
			PreparedQuery pQuery = bd.prepare(query);// Preparo la base de datos
														// pasandole la consulta
			Iterator<Entity> ite = pQuery.asIterator();// Me devuelve un
														// iterador con todos
														// los objetos, es
														// decir, con todas las
														// entity Seville ke hay
			Entity en = null; //
			while (ite.hasNext()) {
				en = ite.next();
				lk.add(en.getKey());// Cojo la clave de la entity y la meto en
									// la lista, en lk tengo una lista con todas
									// las claves
			}
			if (lk.isEmpty()) {// Si la lista está vacía, es ke tengo la base de
								// datos vacías y no puedo borrar
				out.write("{\"error\": \"Empty database\"}");
			} else {
				bd.delete(lk);// se borrara en la base de datos las entitys con
								// la clave especifica
				resp.setStatus(200);
				out.write("{\"error\": \"OK\"}");// Objetos borrados
			}
			// para un objeto nada mas de la base de datos
		} else if (url.split("/").length == 2) {
			if (url.split("/")[1].matches("[0-9]*")) {
				Long yearUrl = new Long(url.split("/")[1]);// Tengo el año de la
															// url
				FilterPredicate predicate = new FilterPredicate("year",
						FilterOperator.EQUAL, yearUrl);
				Query query = new Query("Seville").setFilter(predicate);//Hago una consulta para el entity seville con el predicado anterior, ke el año sea igual al de la url
				PreparedQuery pQuery = bd.prepare(query);//Se lo paso a la base de datos 
				Entity en = pQuery.asSingleEntity();//Me traigo la entity del año especificado
				if (en == null) {
					resp.setStatus(404);
					out.write("{\"error\": \"Not found\"}");
				} else {
					Key k = en.getKey();
					bd.delete(k);
				}
			}else{
				resp.setStatus(404);
				out.write("{\"error\": \"Not found\"}");//Si son una cadena de caracteres lo k le paso en vez de un año
			}
		} else {
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");//En el caso de ke le pase Seville/2014/jasddnflajkf
		}
	}
}
