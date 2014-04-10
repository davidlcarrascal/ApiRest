
function doGet(url){

	var request = $.ajax({
		url: 	url,
		type: 	'GET',
		dataType: 'json'
	});
	
	request.done(function(data,status,jqXHR) {
		var statusCode = jqXHR.status;
		var envio_status = $("<span>"+statusCode+"</span>");
		$(".status").append(envio_status);

		var envio_data = $("<span>"+ JSON.stringify(data)+"</span>");
		$(".data").append(envio_data);
		
		$.each(data,function(index, value) {

			$(".list").append("<li>"+"Year: "+value.year+", Population: "+value.population+", Unemployed: "+value.unemployed+", Pib: "+value.pib+", Emigrants: "+value.emigrants+", Education Budget: "+value.educationBudget+"</li>");			
			$("#tbody").append("<tr>"+"<th>"+value.year+"</th>"+"<th>"+value.population+"</th>"+"<th>"+value.unemployed+"</th>"+"<th>"+value.pib+"</th>"+"<th>"+value.emigrants+"</th>"+"<th>"+value.educationBudget+"</th>"+"</tr>");			
		});	
	});
}
function doPost(url,post){

	var request = $.ajax({
  		url: 	url,
		type: 	'POST',
	  	data: 	post
	});
	
	request.done(function(data,status,jqXHR) {
		var statusCode = jqXHR.status;
		var envio_status = $("<span>"+statusCode+"</span>");
		$(".status").append(envio_status);
	});
	request.fail(function(){
		var envio_status = $("<span>"+"Error"+"</span>");
		$(".status").append(envio_status);
	});
}
function doPut(url,post){
	var request = $.ajax({
  		url: 	url,
		type: 	'PUT',
	  	data: 	post
	});
	
	request.done(function(data,status,jqXHR) {
		var statusCode = jqXHR.status;
		var envio_status = $("<span>"+statusCode+"</span>");
		$(".status").append(envio_status);
	});
}
function doDelete(url){
	var request = $.ajax({
  		url: 	url,
		type: 	'DELETE'
	});
	
	request.done(function(data,status,jqXHR) {
		var statusCode = jqXHR.status;
		var envio_status = $("<span>"+statusCode+"</span>");
		$(".status").append(envio_status);
	});

}


$(document).ready(function() {
	//run when the DOM is ready
	//$('button').on('click',doGet);
	$('#reset').on('click',function() {
		window.location.reload(true);
	});
	
	$('#enviar').on('click',function(){
		
		switch ($( "input:checked" ).val())
		{
 		  	case "GET":
 		  		var url =$("#url").val(); 
 		  		doGet(url);
 		  		break;
   			case "POST":
   				var post = $("#payload").val();
   				var url =$("#url").val(); 
   				doPost(url,post);
   				break;
		   	case "PUT":
		   		var post = $("#payload").val();
   				var url =$("#url").val(); 
   				doPut(url,post);
		   		break;
		   	case "DELETE":
   				var url =$("#url").val(); 
   				doDelete(url);
		   		break;

		   	default: 
		       doGet();
		       break;
		}
	});
	
});