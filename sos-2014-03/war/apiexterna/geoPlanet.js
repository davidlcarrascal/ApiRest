$(document).ready(function() {
	$("#enviar").on('click', function() {
		$('#map_canvas').gmap('destroy');
		var lugar = $("#lugar").val().toString();
		var latitud = "";
		var longitud = "";
		var request = $.ajax({
			url : "/api/v1/geoPlanet/" + lugar,
			type : "GET",
			dataType : "json",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {
			var place = data.places.place;
			var mensaje = "";
			var tipoAdministracion = "";
			$("#div_lugar").html("");
			$.each(place, function(indice,lugar){
				mensaje = "<p>El pueblo de " + lugar.admin3;
				mensaje += " se encuentra en el/la Comunidad Autonoma de "+ lugar.admin1;
				mensaje += " en " +lugar.country+ "</p>";
				mensaje += "<p>Es la ciudad numero " +lugar.areaRank + " con mayor extension</p>";
				mensaje += "<p>Es la ciudad numero " +lugar.popRank + " con mayor poblacion</p>";
				latitud = lugar.centroid.latitude;//centroid es un json donde tengo la latitud y la longitud
				longitud = lugar.centroid.longitude;
				var center = latitud +","+longitud;
				$('#map_canvas').gmap({'center':center, 'bounds':true});
				$('#map_canvas').gmap('option','zoom',12);
				
			});
			$("#div_lugar").append(mensaje);
		});
		request.fail(function(jqXHR, status) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	});
});