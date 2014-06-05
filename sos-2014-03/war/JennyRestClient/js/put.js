$(document).ready(function(){
	$("#modificar").on("click", function(){
		request=$.ajax({
		    url: "../api/v2/Seville/"+ $("#ano").val(),
		    type: "PUT",
		    data: $("#payload").val()
		});
	    request.done(function(data, status, jqXHR){
	    	$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
	    });
		request.fail(function(jqXHR, status){
			$("#resultado").text("");
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		
	});
});