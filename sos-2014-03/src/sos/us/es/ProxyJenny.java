package sos.us.es;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyJenny extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		String urlPrincipio = "https://api.fullcontact.com/v2/person.json?email=";
		String urlFinal = "&apiKey=3b719c8eb5aa23c4";
		String path = req.getPathInfo();
		URL api = null;
		if(path==null){
			api = new URL("https://api.fullcontact.com/v2/person.json?"
					+ urlFinal);
		}
		else if (path.split("/").length == 2) {
			String correo = path.split("/")[1];
			api = new URL(urlPrincipio + correo + urlFinal);
		} else {
			api = new URL("https://api.fullcontact.com/v2/person.json?"
					+ urlFinal);
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

