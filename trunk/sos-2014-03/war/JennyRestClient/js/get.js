$(document).ready(function(){
	$("#enviar").on("click", function(){
		request=$.ajax({
            url: "../api/v2/Seville/" + $("#ano").val(),
            type: "GET",
            contentType: "application/json"
		});
            request.done(function(data, status, jqXHR){
                var datos = data;
                $("#resultado").text("");
                for(i = 0; i<datos.length; i++){
                	$("#resultado").append("<p> year="+datos[i].year+", population="+datos[i].population+", unemployed="+datos[i].unemployed+", educationBudget="+datos[i].educationBudget+", migrants="+datos[i].migrants+", pib="+datos[i].pib+"</p>");
                }
                $("#errores").text(jqXHR.status + " " + jqXHR.statusText);
            });
            request.fail(function(jqXHR, status){
            	$("#resultado").text("");
                $("#errores").text(jqXHR.status + " " + jqXHR.statusText);
            });
	});
});