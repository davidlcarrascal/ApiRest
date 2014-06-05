$(document).ready(function() { 
	$("#enviar").on('click', function() { 
		var correo = $("#email").val().toString();
		var request = $.ajax({
			url : "/api/v1/fullContact/" + correo,
			type : "GET",
			dataType: "json",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {
			$("#photos").html("");
			$("#socialProfiles").html("");
			var photos = data.photos;
			var socialNetworks = data.socialProfiles;
			
			$.each(photos, function(indice, photo){
				$.each(photo, function(propiedad,valor){
					if(propiedad=="typeName"){
						$("#photos").append("<div>"+"Fotos de "+ valor+"</div>");
					}
					if(propiedad=="url"){
						$("#photos").append("<div");
						$("#photos").append("<img src="+valor.toString()+">");
						$("#photos").append("</div>");
					}
				});
			});
			$.each(socialNetworks, function(prop, redSocial){
				var socialProfile = redSocial.typeName;
				var username = redSocial.username;
				var url = "<a href="+redSocial.url+">"+redSocial.url+"</a>"; 
				var mensaje = "El usuario de "+socialProfile+" es: "+username+". Acceda a su perfil aqui: "+url;
				$("#socialProfiles").append("<div>"+mensaje+"</div>");
			});
		});
		request.fail(function(jqXHR, status) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	});
});