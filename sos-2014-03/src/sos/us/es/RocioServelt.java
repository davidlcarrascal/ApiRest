package sos.us.es;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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
public class RocioServelt extends HttpServlet {
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
// hace un filtro por anio
//query crea una consulta
//prepara
	
	private String getUniversitySevilleBy(Long year) {
		FilterPredicate predicate = new FilterPredicate("year", Query.FilterOperator.EQUAL, year);
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

	private Integer getLongitud(HttpServletRequest req){
		String ruta[] = req.getRequestURI().split("/");
		return ruta.length;
	}

	private Boolean esPeticionGenerica(HttpServletRequest req){
		return getLongitud(req)==4;
	}

	private Boolean esPeticionEspecifica(HttpServletRequest req){
		return getLongitud(req)==5;
	}

	private Boolean esMalaPeticion(HttpServletRequest req){
		Integer longitud=getLongitud(req);
		Boolean muyLargo = longitud>5;
		Boolean muyCorto= longitud<4;
		Boolean malParametro=false;
		if(longitud==5){
		try{
			getAnioRuta(req);
		}catch(Exception e){
			malParametro=true;
		}
		}
		return malParametro || muyLargo || muyCorto;
	}

	private String getUltimoParametro(HttpServletRequest req){
		String ruta[] = req.getRequestURI().split("/");
		return ruta[ruta.length-1];
	}

	private Long getAnioRuta(HttpServletRequest req){
		return Long.parseLong(getUltimoParametro(req));
	}

	private void send(HttpServletResponse resp, Object o) throws IOException{
		resp.setContentType("application/json");
		resp.getWriter().println(o);
	}
	private void close(HttpServletResponse resp) throws IOException{
		resp.getWriter().close();
	}

	private void send404(HttpServletResponse resp) throws IOException{
		resp.setStatus(404);
		String mensaje="{\"error\": \"404 Not Found\"}";
		send(resp,mensaje);
	}

	private void send400(HttpServletResponse resp) throws IOException{
		resp.setStatus(400);
		String mensaje="{\"error\": \"400 Bad Request\"}";
		send(resp,mensaje);
	}
	
	private void send409(HttpServletResponse resp) throws IOException{
		resp.setStatus(409);
		String mensaje="{\"error\": \"409 Conflict\"}";
		send(resp,mensaje);
	}
	
	private void send201(HttpServletResponse resp) throws IOException{
		resp.setStatus(201);
		String mensaje="{\"status\": \"201 Created\"}";
		send(resp,mensaje);
	}
	private void send200(HttpServletResponse resp) throws IOException{
		resp.setStatus(200);
		String mensaje="{\"status\": \"20O OK\"}";
		send(resp,mensaje);
	}

	private Boolean yaExiste(UniversitySeville uni){
		return this.getUniversitySevilleBy(uni.year) != null;
	}

	private Entity build(UniversitySeville uni){
		Entity empty = new Entity("UniversitySeville");
		empty.setProperty("year", uni.getYear());
		empty.setProperty("enrolled", uni.getEnrolled());
		empty.setProperty("budget", uni.getBudget());
		empty.setProperty("employability", uni.getEmployability());
		empty.setProperty("studentMigrants",uni.getStudentMigrants());
		return empty;
	}
	private void modify(UniversitySeville uni){
		Entity dato = this.getEntityBy(uni.getYear());
		dato.setProperty("year", uni.getYear());
		dato.setProperty("enrolled", uni.getEnrolled());
		dato.setProperty("budget", uni.getBudget());
		dato.setProperty("employability", uni.getEmployability());
		dato.setProperty("studentMigrants", uni.getStudentMigrants());
		datastore.put(dato);
		
	}

	private Key toKey(Entity from) {
		return from.getKey();
	}

	private void deleteAll(){
		Query q = new Query("UniversitySeville");
		PreparedQuery p = datastore.prepare(q);
		Iterable<Entity> entitis = p.asIterable();
		List<Key> claves = new ArrayList<Key>();
		for (Entity i : entitis) {
			claves.add(toKey(i));
		}
		datastore.delete(claves);
	}
	
	private void deleteOne(HttpServletRequest req) {
			Long year = getAnioRuta(req);
			FilterPredicate predicate = new FilterPredicate("year",
					Query.FilterOperator.EQUAL, year);
			Query q = new Query("UniversitySeville").setFilter(predicate);
			PreparedQuery p = datastore.prepare(q);
			Entity entitis = p.asSingleEntity();
			datastore.delete(toKey(entitis));


	}
	//coge el json que le pasas y lo tranforma a un universitysevilla
	private UniversitySeville getUniversityFromRequest(HttpServletRequest req){
		try{
			UniversitySeville uni=null;
			Gson gson = new Gson();
			StringBuilder sb = new StringBuilder();
			BufferedReader br = req.getReader();
			String jsonString;
			while ((jsonString = br.readLine()) != null) {
				sb.append(jsonString);
		}
			jsonString = sb.toString();			
			uni = gson.fromJson(jsonString, UniversitySeville.class);
			return uni;
		}catch(Exception e){
			return null;
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		if(esMalaPeticion(req)){
			send400(resp);
		}
		else if (esPeticionGenerica(req)) {
			
			List<String> todo = getUniversitySeville();
			send(resp,todo);			
		}
		else if(esPeticionEspecifica(req)){
			Long year = getAnioRuta(req);
			String uni = getUniversitySevilleBy(year);
			if (uni == null) {
				send404(resp);
			} else {
				
				send(resp,uni);
			}
		}
		close(resp);

	}


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		if(esMalaPeticion(req)){
			send400(resp);
		
		}
		else if (esPeticionGenerica(req)) {
			UniversitySeville uni = getUniversityFromRequest(req);
			if(uni == null){
				send400(resp);
			}else if(yaExiste(uni)){
				send409(resp);
			}else{
				Entity university=build(uni);
				datastore.put(university);
				send201(resp);
			}
		}
		else if(esPeticionEspecifica(req)){
			send400(resp);
		}

		close(resp);
	}

	public void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		
		if(esMalaPeticion(req)){
			send400(resp);
		
		}
		else if (esPeticionGenerica(req)) {
			send400(resp);
		}
		else if(esPeticionEspecifica(req)){
			UniversitySeville uni = getUniversityFromRequest(req);
			
			if(uni==null){
				send400(resp);
			}else if(!yaExiste(uni)){
				send404(resp);
			}else{
				modify(uni);
				send200(resp);
			}
		}
		close(resp);

	}

	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		if(esMalaPeticion(req)){
			send400(resp);
		}
		else if (esPeticionGenerica(req)) {
			deleteAll();		
		}
		else if(esPeticionEspecifica(req)){
			String uni = getUniversitySevilleBy(getAnioRuta(req));
			if(uni == null) {
				send404(resp);
			}else{
			    deleteOne(req);
			}
		
		}
	close(resp);
	}

}