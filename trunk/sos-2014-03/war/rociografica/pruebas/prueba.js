$(document).ready(function(){
	function obtenerDato(year){
		var url = "/api/v2/universitySeville" 
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		var dato=[];
		request.done(function(data,status, jqXHR){			
			a = data;		
		});
		
		return dato;
	};
	
	
});