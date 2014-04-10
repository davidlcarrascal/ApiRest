$(document).ready(function(){
	$("#get").on('click',function(){
		var request = $.ajax({
			url: $('#url').val(),
			type: "GET",
			dataType: "json",
			success:function(data,status,jqXHR){
				
				
				obj = JSON.stringify(data);
				$('#data').html(obj);
			}
			});
		request.fail(function(data,status,jqXHR){
			var statusCode = jqXHR.status;
			
			$("#status").add(statusCode);
			$('#data').html(status);
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
			alert('!!!!');
			$('#data').html('ahora!');
		});
		request.fail(function(jqXHR,status){
			alert('no funciona');
			$('#data').html('error');
			
		});
		
		});
			
		
	
	
	
	
	$("#delete").on('click', function(){
		alert('VAS A BORRAR')
		var request = $.ajax({
			url: $('#url').val(),
			type: "DELETE"
		});
		request.done(function(data,status,jqXHR){
			alert('!!!!');
			$('#data').html('Acabas de borrar');
		});
		request.fail(function(jqXHR,status){
			alert('Error');
			$('#data').html('error');
			
		});
		/*request.done(function(data,status,jqXHR){
			$('#data').add('Borrado');
		});*/
	});	
});
