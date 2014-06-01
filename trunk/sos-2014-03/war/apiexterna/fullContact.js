$(document).ready(function() { //Cuando el html este listo para furular, entonces
	$("#enviar").on('click', function() { //cuando haga click en el botón con id enviar, realizaré la siguiente función
		var correo = $("#email").val().toString();//Ahora me creo la variable correo, dónde almacenaré el correo que he puesto en el input

		var request = $.ajax({//Hago una petición ajax
			url : "/api/v1/fullContact/" + correo,//accedo al servlet de la api externa. Yo he exo en el servlet ke cuando 
			//le paso el correo en la url me lo meta en la url de la api externa: email=correo
			type : "GET", //Le indico que es una petición get
			dataType: "json", //los datos la api externa me los proporciona en formato json
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {//Cuando la petición ha sido satisfactoria
			$("#photos").html("");//Vacío los divs para que cuando haga otra petición, me vacíe los divs.
			$("#socialProfiles").html("");//Vacío los divs para que cuando haga otra petición, me vacíe los divs.
			var photos = data.photos;//El json ke me devuelve me kedo con la propiedad photo, ke es un array de arrays, 
			//donde en cada array tengo una photo con la red social...
			var socialNetworks = data.socialProfiles;   //El json ke me devuelve me kedo con la propiedad social profile, 
			//donde en cada array tengo las propiedades del usuario, como por ejemplo la foto del perfil... 
			
			$.each(photos, function(indice, photo){  //me recorro el array photos, dónde 
				$.each(photo, function(propiedad,valor){
					if(propiedad=="typeName"){ //Como kiero nada mas el nombre de la red social y la foto asociada al correo ke meta 
						$("#photos").append("<div>"+"Fotos de "+ valor+"</div>");
						//Escribo en el div de photos, Fotos de la red social ke sea.
					}
					if(propiedad=="url"){
						$("#photos").append("<div");//por cada foto me creo un div donde le asocio la foto, con la etiqueta img
						$("#photos").append("<img src="+valor.toString()+">");//toString para asegurarme ke me lo de como string
						$("#photos").append("</div>");
					}
				});
			});
			$.each(socialNetworks, function(prop, redSocial){//En social Network tengo la variable profile
				var socialProfile = redSocial.typeName;//aki tengo el nombre de la red social
				var username = redSocial.username;//Aquí tengo el nombre de usuario la persona de la red social
				var url = "<a href="+redSocial.url+">"+redSocial.url+"</a>"; //tengo el link a la pagina de perfil
				var mensaje = "El usuario de "+socialProfile+" es: "+username+". Acceda a su perfil aqui: "+url;//Aqui genero el sms que
				//quiero mostrar en la pagina html
				$("#socialProfiles").append("<div>"+mensaje+"</div>");//Aqui pinto el sms en el html, un div por cada red social, con el sms
				//que he creado
			});
		});
		request.fail(function(jqXHR, status) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	});
});