$(document).ready(function() {
	$("#enviar").on('click', function() {
		var lugar = $("#lugar").val().toString();
		var request = $.ajax({
			url : "/api/v1/geoPlanet/" + lugar,
			type : "GET", // Le indico que es una petición get
			dataType : "json", // los datos la api externa me los proporciona
								// en
			// formato json
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {
			var place = data.places.place;
			var mensaje = "";
			$("#div_lugar").html("");
			var latitud = 0;
			var longitud = 0;
			$.each(place, function(indice,lugar){
				mensaje = "<p>El pueblo de " + lugar.admin3;
				mensaje += " se encuentra en la Comunidad Autonoma de "+ lugar.admin1;
				mensaje += " en " +lugar.country+ "</p>";
				mensaje += "<p>Es la ciudad numero " +lugar.areaRank + " con mayor extension</p>";
				mensaje += "<p>Es la ciudad numero " +lugar.popRank + " con mayor poblacion</p>";
				latitud = lugar.centroid.latitude;
				longitud = lugar.centroid.longitude;
			});
			var center = latitud.toString()+","+longitud.toString();
			
			$("#div_lugar").append(mensaje);
		});
		request.fail(function(jqXHR, status) {
			alert("error");
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	});
});