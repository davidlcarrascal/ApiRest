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
			throws IOException { //Hago una petición get
		PrintWriter out = resp.getWriter(); //Para poder pintar lo que tengo
		String urlPrincipio = "https://api.fullcontact.com/v2/person.json?email=";//Contemplo la url del principio como ese tocho y luego
		//le voy a pasar el correo escrito directamente en la url para ke me de el json.
		String urlFinal = "&apiKey=3b719c8eb5aa23c4";//Esta parte de url es la ke ira al final de la url en googleChrome
		String path = req.getPathInfo();//devuelve una cadena, decodificada por el contenedor web, especificando la información de ruta 
		//adicional que viene después de la ruta del servlet,
		URL api = null; //inicializo la url a null, para ke exista despues de haberla modificado en los divs
		if(path==null){//Si la ruta es igual a null, es ke no le he metido ningun correo
			api = new URL("https://api.fullcontact.com/v2/person.json?"
					+ urlFinal); //en la variable api, guardaré la nueva url añadiéndole la url del final
		}
		else if (path.split("/").length == 2) { //en caso contrario, si la longitud de la ruta es igual a 2, le meto un correo
			String correo = path.split("/")[1];//Divido la ruta por barras y cojo lo ke haya en la posición 1, es decir, el correo 
			api = new URL(urlPrincipio + correo + urlFinal); //en api tengo ya mi url completa, incluido el correo tb. 
		} else { //sino
			api = new URL("https://api.fullcontact.com/v2/person.json?" //en api tendré la url del pcpio sin el correo y añadiendole la url final
 					+ urlFinal);//esto significa ke no he metido el correo o ke he metido mas cosas de la cuenta
		}
			BufferedReader in = new BufferedReader(new InputStreamReader(//me lee lo ke venga
					api.openStream())); //Se hace para poder leer todo lo ke me venga de la api
			//abro la conexion con la api
			String inputLine; //
			while ((inputLine = in.readLine()) != null) { //mientras tenga lineas para leer, voy leyendo y me las meto en el inputLine
				out.println(inputLine); //
			}
			in.close();//cierro la entrada para ke no me entre mas nada ya ke no tengo mas datos ke leer
		}
	}