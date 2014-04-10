$(document).ready(function(){
	$("#modificar").on("click", function(){
		request=$.ajax({
		    url: "../api/v1/Seville/"+ $("#ano").val(),
		    type: "PUT",
		    data: $("#payload").val()
		});
	    request.done(function(data, status, jqXHR){
	    	
	    	//if(data.split(',').length>1){
	    		var payloadT = jQuery.parseJSON(data);
	    		//$.each(payloadT, function(key, value){
	    		//	$('#resultado').append("<li>" + key + ": " + value + "</li>");
	    		//})
	    	$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
	    	//}
	    });
		request.fail(function(jqXHR, status){
			$("#resultado").text("");
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		
	});
});