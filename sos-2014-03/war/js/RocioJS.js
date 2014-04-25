$(document).ready(function(){
	$("#get").on('click',function(){
		var request = $.ajax({
			url: $('#url').val(),
			type: "GET",
			dataType: "json"
		});
		request.done(function(data,status, jqXHR){

			$("#status").html(jqXHR.status);
			obj = JSON.stringify(data);
			mensaje="<table class='table table-hover'>";
			mensaje+="<tr><td>Year</td><td>Enrolled</td><td>Budget</td></tr>"
			$.each(data,function(index, value) {
				mensaje+="<tr><td>"+value.year+"</td><td>"+value.enrolled+"</td><td>"+value.budget+"</td></tr>";
				
			});	
			mensaje+="</table>";
			alert(mensaje);
			$('#data').html(mensaje);
			$('#log').append("200 status has been received.<br>");
		});
		request.fail(function(data,jqXHR,status){
			
			$("#status").html(data.status);
			$('#data').html("no hay respuesta");
			$('#log').append("404 status error has been received.<br>");
		});
		
		});
	
	
	
	
	$("#post").on('click',function(){
		var request = $.ajax({
			url: $('#url').val(),
			type: "POST",
			dataType: "text",
			data: $("#payload").val()
			});
		request.done(function(data,status,jqXHR){
			
			$('#data').html('Datos anadidos');
		});
		request.fail(function(data,jqXHR,status){
			alert('***');
			if(data.status == "400"){
				$("#status").html(400);
				$('#data').html("Bad Request");
				$('#log').append("400 status error has been received.<br>")
			}
			if(data.status == "409"){
				$("#status").html(409);
				$('#data').html("Conflict");
				$('#log').append("409 status error has been received.<br>")
			}
			
		});
		
		});
			
	$("#put").on('click',function(){
		var request = $.ajax({
			url: $('#url').val(),
			type: "PUT",
			dataType: "text",
			data: $("#payload").val()
			});
		request.done(function(data,status,jqXHR){
			
			$('#data').html('Datos modificados');
		});
		request.fail(function(data,jqXHR,status){
			$("#status").html(data.status);
			$('#data').html("no hay respuesta");
			$('#log').append("404 status error has been received.<br>");
			
		});
		
		});
			
	
	
	
	
	$("#delete").on('click', function(){
		
		var request = $.ajax({
			url: $('#url').val(),
			type: "DELETE",
			dataType: "text"
		});
		request.done(function(data,status,jqXHR){
			$('#data').html('Borrado');
		});
		request.fail(function(jqXHR,status){
			$("#status").html(data.status);
			$('#data').html("no hay respuesta");
			$('#log').append("404 status error has been received.<br>");
			
		});
		
	});	
});
