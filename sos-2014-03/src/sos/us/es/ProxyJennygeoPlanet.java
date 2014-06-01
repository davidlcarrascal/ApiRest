package sos.us.es;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyJennygeoPlanet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		PrintWriter out = resp.getWriter();
		String url = req.getPathInfo();
		String urlPrincipio = "http://where.yahooapis.com/v1/places.q(";
		String applicationID = ")?format=json&appid=XfM2RBTV34GlwfmjDImzwrCCwJgirB3nLgNhLcabcK44qrDTAFPMzfrNMS4fVtTWk4hH0Gv0cyGU";
		String place = "";
		URL api = null;
		
		if(url == null || url.split("/").length!=2){
			
			api = new URL("http://where.yahooapis.com/v1/places?format=json&appid=XfM2RBTV34GlwfmjDImzwrCCwJgirB3nLgNhLcabcK44qrDTAFPMzfrNMS4fVtTWk4hH0Gv0cyGU");
			
		}else if(url.split("/").length==2){
			place = req.getPathInfo().split("/")[1];
			api = new URL(urlPrincipio + place + applicationID);
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(
				api.openStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			out.println(inputLine);
		}
		in.close();
	}
}
