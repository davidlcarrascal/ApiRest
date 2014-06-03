package sos.us.es;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
@SuppressWarnings("serial")
public class ProxyDavid extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
			URL api = new URL("http://sos-2014-01.appspot.com/api/v1/universities/Sevilla");
			
			String datos = "";
				
			BufferedReader in = new BufferedReader(new InputStreamReader(api.openStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null){
				datos=inputLine;
			}
			
			in.close();
			
		
			resp.setContentType("text/json");
			resp.getWriter().println(datos);				
	}

}
