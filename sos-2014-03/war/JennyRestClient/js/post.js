$(document).ready(function(){
	$("#crear").on("click", function(){
		request=$.ajax({
		    url: "../api/v2/Seville",
		    type: "POST",
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