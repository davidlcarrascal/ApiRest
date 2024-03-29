package sos.us.es;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class CopyOfRocioServelt extends HttpServlet {
	static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	static List<UniversitySeville> luni = new ArrayList<UniversitySeville>();

	private List<String> getUniversitySeville() {
		Query q = new Query("UniversitySeville");
		PreparedQuery p = datastore.prepare(q);
		Iterable<Entity> resultados = p.asIterable();
		List<String> todos = new ArrayList<String>();
		Gson gson = new Gson();
		for (Entity us : resultados) {

			Long year = (Long) us.getProperty("year");
			Long enrolled = (Long) us.getProperty("enrolled");
			Long budget = (Long) us.getProperty("budget");
			Long employability = (Long) us.getProperty("employability");
			Long studentMigrants = (Long) us.getProperty("studentMigrants");
			UniversitySeville univer = new UniversitySeville(year, enrolled,
					budget, employability, studentMigrants);
			todos.add(gson.toJson(univer));
		}
		return todos;
	}

	private Entity getEntityBy(Long year) {
		FilterPredicate predicate = new FilterPredicate("year",
				Query.FilterOperator.EQUAL, year);
		Query q = new Query("UniversitySeville").setFilter(predicate);
		PreparedQuery p = datastore.prepare(q);
		Entity us = p.asSingleEntity();
		return us;
	}

	private String getUniversitySevilleBy(Long year) {
		FilterPredicate predicate = new FilterPredicate("year",
				Query.FilterOperator.EQUAL, year);
		Query q = new Query("UniversitySeville").setFilter(predicate);
		PreparedQuery p = datastore.prepare(q);
		Entity us = p.asSingleEntity();
		Gson gson = new Gson();
		UniversitySeville univer = new UniversitySeville();
		try {
			Long year2 = (Long) us.getProperty("year");
			Long enrolled = (Long) us.getProperty("enrolled");
			Long budget = (Long) us.getProperty("budget");
			Long employability = (Long) us.getProperty("employability");
			Long studentMigrants = (Long) us.getProperty("studentMigrants");
			univer = new UniversitySeville(year2, enrolled, budget,
					employability, studentMigrants);
		} catch (NullPointerException e) {
			return null;

		}
		return gson.toJson(univer);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		String ruta[] = req.getRequestURI().split("/");
		if (ruta.length == 4) {

			try {
				List<String> todo = getUniversitySeville();
				out.println(todo);
			} catch (Exception e) {
				resp.setStatus(404);
				resp.getWriter().close();
			}
			
		} else {
			Long year = (Long.parseLong(ruta[ruta.length - 1]));
			List<String> todo = new LinkedList<String>();
			String uni = getUniversitySevilleBy(year);
			todo.add(uni);
			if (uni == null) {
				resp.setStatus(404);
				out.println("{\"error\": \"404 Not Found\"}");
			} else {
				out.println(todo);
			}
		}
		out.close();

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		String ruta[] = req.getRequestURI().split("/");

		Entity university = new Entity("UniversitySeville");

		UniversitySeville uni = null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;

		while ((jsonString = br.readLine()) != null) {
			sb.append(jsonString);
		}

		jsonString = sb.toString();
		Boolean error = false;
		try {
			uni = gson.fromJson(jsonString, UniversitySeville.class);
			
		} catch (Exception e) {
			resp.setStatus(404);
			resp.getWriter().close();
			error = true;
		}

		if (ruta.length == 4 && !error) {

			if (this.getUniversitySevilleBy(uni.year) != null) {
				resp.setStatus(409);
				out.close();
			} else if (uni != null) {
				university.setProperty("year", uni.getYear());
				university.setProperty("enrolled", uni.getEnrolled());
				university.setProperty("budget", uni.getBudget());
				university.setProperty("employability", uni.getEmployability());
				university.setProperty("studentMigrants",
						uni.getStudentMigrants());
				datastore.put(university);

				resp.setStatus(201);
			}

		} else {
			resp.setStatus(400);
		}

		out.close();
	}

	public void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UniversitySeville uni = null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();

		String jsonString;

		while ((jsonString = br.readLine()) != null) {
			sb.append(jsonString);
		}

		jsonString = sb.toString();
		Boolean error = false;
		try {
			uni = gson.fromJson(jsonString, UniversitySeville.class);
		} catch (Exception e) {
			resp.setStatus(404);
			resp.getWriter().close();
			error = true;
		}

		String ruta[] = req.getRequestURI().split("/");
		if (ruta.length == 5 && !error) {
			Long year = Long.parseLong(ruta[ruta.length - 1]);
			Entity dato = this.getEntityBy(year);
			if (dato == null) {
				resp.setStatus(404);
				resp.getWriter().close();
			} else {

				dato.setProperty("year", uni.getYear());
				dato.setProperty("enrolled", uni.getEnrolled());
				dato.setProperty("budget", uni.getBudget());
				dato.setProperty("employability", uni.getEmployability());
				dato.setProperty("studentMigrants", uni.getStudentMigrants());

				datastore.put(dato);
			}
		} else {
			resp.setStatus(400);
		}

	}

	private Key toKey(Entity from) {
		return from.getKey();
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");

		String ruta[] = req.getRequestURI().split("/");
		List<String> todo = getUniversitySeville();
		List<Key> claves = new ArrayList<Key>();


		
		if (ruta.length == 4) {
			Query q = new Query("UniversitySeville");
			PreparedQuery p = datastore.prepare(q);
			Iterable<Entity> entitis = p.asIterable();
			for (Entity i : entitis) {
				claves.add(toKey(i));
			}
			datastore.delete(claves);
		} else {
			Long year = Long.parseLong(ruta[ruta.length - 1]);
			FilterPredicate predicate = new FilterPredicate("year",
					Query.FilterOperator.EQUAL, year);
			Query q = new Query("UniversitySeville").setFilter(predicate);
			PreparedQuery p = datastore.prepare(q);
			Entity entitis = p.asSingleEntity();
			if (entitis == null) {
				resp.setStatus(404);
			} else {
				datastore.delete(toKey(entitis));
			}
		}
		resp.getWriter().close();
	}

}
