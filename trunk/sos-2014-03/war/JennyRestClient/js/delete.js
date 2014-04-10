$(document).ready(function(){
	$("#borrar").on("click", function(){
			    request=$.ajax({
                url: "../api/v1/Seville/" + $("#ano").val(),
                type: "DELETE",
                contentType: "application/json"
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