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
public class ProxyRocio extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		String ruta[] = req.getRequestURI().split("/");
		PrintWriter out = resp.getWriter();
		if(ruta.length==5){
			

				String ciudad=ruta[ruta.length-1];
				String direccion=ciudad+",spain";
				String url="http://maps.googleapis.com/maps/api/geocode/json?address=";
				String url2="&sensor=false";
				String urlfin=url+direccion+url2;
			

				URL api = new URL(urlfin);

				BufferedReader in = new BufferedReader(new InputStreamReader(api.openStream()));
				String inputLine;
				String datos="";
				while((inputLine = in.readLine()) != null){
					datos+=inputLine;
				}
				JSONObject jsonObj=null;
				JSONObject southwest=null;
				JSONObject northeast=null;
				String slong=null;
				String slat=null;
				String nlong=null;
				String nlat=null;
				try {
					jsonObj = new JSONObject(datos);
					JSONArray nosequees=jsonObj.getJSONArray("results");
					jsonObj=(JSONObject)(nosequees.get(0));
					jsonObj=(JSONObject)(jsonObj.get("geometry"));
					jsonObj=(JSONObject)(jsonObj.get("bounds"));
					
					southwest=(JSONObject)(jsonObj.get("southwest"));
					northeast=(JSONObject)(jsonObj.get("northeast"));
					
					slong=southwest.get("lng").toString();
					slat=southwest.get("lat").toString();
					nlong=northeast.get("lng").toString();
					nlat=northeast.get("lat").toString();
								
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String urlpano="http://www.panoramio.com/map/get_panoramas.php?set=public&from=0&to="
						+ "30&minx="+slong+"&miny="+slat+"&maxx="+nlong+"&maxy="+nlat+"&size=small&mapfilter=true";
				
				URL api2 = new URL(urlpano);

				BufferedReader in2 = new BufferedReader(new InputStreamReader(api2.openStream()));
				String inputLine2;
				String datospano="";
				while((inputLine2 = in2.readLine()) != null){
					datospano+=inputLine2;
				}
				
				out.println(datospano);
				


		}else{
			resp.sendError(400);
			resp.getWriter().close();
		}
		out.close();
		
		}


}
