$(document).ready(function() { //Cuando el html este listo para funcionar, entonces 
	$("#enviar").on('click', function() { //cuando haga click en el botón con id enviar, realizaré la siguiente función
		$('#map_canvas').gmap('destroy');
		var lugar = $("#lugar").val().toString();//Ahora me creo la variable lugar, dónde almacenaré el lugar que he puesto en el input
		var latitud = "";
		var longitud = "";
		var request = $.ajax({//Hago una petición ajax
			url : "/api/v1/geoPlanet/" + lugar,//accedo al servlet de la api externa. Yo he exo en el servlet ke cuando 
			//le paso el lugar en la url me lo meta en la url de la api externa: places.q(lugar)
			type : "GET", // Le indico que es una petición get
			dataType : "json", // los datos la api externa los quiero en formato json
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) { //Cuando la petición ha sido satisfactoria
			var place = data.places.place;//El json ke me devuelve me kedo con la propiedad places, ke es un array de elementos json, 
			//donde en cada elemento tengo un lugar con la ciudad, con la comunidad autónoma...
			var mensaje = "";//inicializo el mensaje a vacío
			var tipoAdministracion = "";
			$("#div_lugar").html("");//Vacío los divs para que cuando haga otra petición, me vacíe los divs.
			$.each(place, function(indice,lugar){//Me recorro el array de lugar, donde su índice es la posición k ocupa en el array y lugar es el elemento del array 
				mensaje = "<p>El pueblo de " + lugar.admin3;//Voy cogiendo los valores de los nombres para poder mostrar bien el mensaje
				mensaje += " se encuentra en el/la Comunidad Autonoma de "+ lugar.admin1;
				mensaje += " en " +lugar.country+ "</p>";
				mensaje += "<p>Es la ciudad numero " +lugar.areaRank + " con mayor extension</p>";
				mensaje += "<p>Es la ciudad numero " +lugar.popRank + " con mayor poblacion</p>";
				latitud = lugar.centroid.latitude;//centroid es un json donde tengo la latitud y la longitud
				longitud = lugar.centroid.longitude;
				var center = latitud +","+longitud;
				$('#map_canvas').gmap({'center':center, 'bounds':true});//bound pa ke te centre el sitio
				$('#map_canvas').gmap('option','zoom',12);
				
			});
			$("#div_lugar").append(mensaje);//Escribo en el div_lugar, los mensajes anteriormente mencionados. 
											//Uno debajo de otros, dándo la información que quiero
		});
		request.fail(function(jqXHR, status) {//En caso de ke la peticón no sea satisfactoria
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);//ke me pinte en el div errores el código y el texto asociado al error.
		});
	});
});