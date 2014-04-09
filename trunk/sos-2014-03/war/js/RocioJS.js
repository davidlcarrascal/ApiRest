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
			$('#data').html(status);
		});
		
		});
	
	

});
