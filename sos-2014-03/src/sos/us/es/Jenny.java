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
	
	private List<String> getSeville() {

		Gson gson = new Gson();
		Seville sev = null;
		List<String> lsev = new ArrayList<String>();

		Query query = new Query("Seville");
		PreparedQuery pQuery = bd.prepare(query);
		Iterator<Entity> e = pQuery.asIterator();
		Entity entity = null;

		while (e.hasNext()) {
			entity = e.next();
			Long year = (Long) entity.getProperty("year");
			Long population = (Long) entity.getProperty("population");
			Long unemployed = (Long) entity.getProperty("unemployed");
			Double educationBudget = (Double) entity
					.getProperty("educationBudget");
			Long migrants = (Long) entity.getProperty("migrants");
			Double pib = (Double) entity.getProperty("pib");
			sev = new Seville(year, population, unemployed, educationBudget,
					migrants, pib);
			lsev.add(gson.toJson(sev));
		}
		return lsev;
	}

	private String getCity(String url) {

		Long year = new Long(url.split("/")[1]);
		FilterPredicate predicate = new FilterPredicate("year",
				Query.FilterOperator.EQUAL, year);
		Query query = new Query("Seville").setFilter(predicate);
		PreparedQuery pQuery = bd.prepare(query);
		Entity en = pQuery.asSingleEntity();
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
			Seville sev = new Seville(year1, population, unemployed,
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

		if (url == null || url.split("/").length == 0) {
			sev = getSeville();

			if (sev.isEmpty()) {
				out.write("{\"error\": \"Empty database\"}");
			} else {
				out.println(sev);
			}

		} else if (url.split("/").length == 2) {
			String en = null;
			if (url.split("/")[1].matches("[0-9]*")) {
				en = getCity(url);
			}
			if (en == null) {
				resp.setStatus(404);
				out.write("{\"error\": \"Not found\"}");
			} else {
				sev.add(en);
				out.println(sev);
			}
		} else {
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");
		}
		out.close();

	}

	// Método doPost
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
				if (ent == null) {
					en.setProperty("year", sev.getYear());
					en.setProperty("population", sev.getPopulation());
					en.setProperty("unemployed", sev.getUnemployed());
					en.setProperty("educationBudget", sev.getEducationBudget());
					en.setProperty("migrants", sev.getMigrants());
					en.setProperty("pib", sev.getPib());
					bd.put(en);
					resp.setStatus(201);
					out.write("{\"error\": \"Created\"}");
				} else {
					resp.setStatus(409);
					out.write("{\"error\": \"Conflict\"}");
				}
			} catch (Exception e) {
				resp.setStatus(409);
				out.write("{\"error\": \"Conflict\"}");
				// System.out.println("ERROR parsing Seville: " +
				// e.getMessage());
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

		if (url == null || url.split("/").length != 2) {
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");
		} else {
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
							Query.FilterOperator.EQUAL, yearUrl);
					Query query = new Query("Seville").setFilter(predicate);
					PreparedQuery pQuery = bd.prepare(query);
					Entity en = pQuery.asSingleEntity();
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
							bd.put(en);
							resp.setStatus(200);
							out.write("{\"error\": \"OK\"}");
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
		if (url == null || url.split("/").length == 0) {
			List<Key> lk = new ArrayList<Key>();
			Query query = new Query("Seville");
			PreparedQuery pQuery = bd.prepare(query);
			Iterator<Entity> ite = pQuery.asIterator();
			Entity en = null;
			while (ite.hasNext()) {
				en = ite.next();
				lk.add(en.getKey());
			}
			if (lk.isEmpty()) {
				out.write("{\"error\": \"Empty database\"}");
			} else {
				bd.delete(lk);
				resp.setStatus(200);
				out.write("{\"error\": \"OK\"}");
			}
			
		} else if (url.split("/").length == 2) {
			if (url.split("/")[1].matches("[0-9]*")) {
				Long yearUrl = new Long(url.split("/")[1]);
				FilterPredicate predicate = new FilterPredicate("year",
						FilterOperator.EQUAL, yearUrl);
				Query query = new Query("Seville").setFilter(predicate);
				PreparedQuery pQuery = bd.prepare(query);
				Entity en = pQuery.asSingleEntity();
				if (en == null) {
					resp.setStatus(404);
					out.write("{\"error\": \"Not found\"}");
				} else {
					Key k = en.getKey();
					bd.delete(k);
				}
			}else{
				resp.setStatus(404);
				out.write("{\"error\": \"Not found\"}");
			}
		} else {
			resp.setStatus(400);
			out.write("{\"error\": \"Bad request\"}");
		}
	}
}