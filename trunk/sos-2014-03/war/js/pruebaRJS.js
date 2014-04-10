$(document).ready(function(){
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
});