$(document).ready(function(){
	$(".enviarCity").on('click',function(){
		$('#fotos').empty();
		var lugar = $("#city").val()
		var url = "/api/v2/panoramio/" + lugar
		var request = $.ajax({
			url: url,
			type: "GET", 
			dataType: "json"
			
		});
	
		request.done(function(data,status, jqXHR){		
			var fot=data.photos;
			for (var i=0; i<20; i++){
				var aux= fot[i].photo_file_url
				$('#fotos').append("<img src="+aux+">\n");

			}
		});
	});
});



