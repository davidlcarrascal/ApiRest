$(document).ready(function(){
/*
	Insertar <input type= "radio" id= "insertar">
    Modificar <input type= "radio" id = "modificar">
    Consultar <input type= "radio" id = "consultar"> 
    Elminar<input type= "radio" id="eliminar">
    */
    $(".modify_form").hide();
    $(".getter_form").hide();
    $(".delete_form").hide();

    $("#modificar").click(function(){
    	   $(".modify_form").show();
    	    $(".getter_form").hide();
    	    $(".delete_form").hide();   
    	    $(".insert_form").hide();
    	    $("#data").html("");
    });
    $("#consultar").click(function(){
    	   $(".modify_form").hide();
    	    $(".getter_form").show();
    	    $(".delete_form").hide();   
    	    $(".insert_form").hide();
    	    $("#data").html("");
    });    
    $("#eliminar").click(function(){
    	   $(".modify_form").hide();
    	    $(".getter_form").hide();
    	    $(".delete_form").show();   
    	    $(".insert_form").hide();
    	    $("#data").html("");
    });
    $("#insertar").click(function(){
    	   $(".modify_form").hide();
    	    $(".getter_form").hide();
    	    $(".delete_form").hide();   
    	    $(".insert_form").show();
    	    $("#data").html("");
    }); 
    
    
    
	$(".btn-get").on('click',function(){
		var url="/api/v1/universitySeville";
		if($("#year_get").val() != null){
			url+="/"+$("#year_get").val();
		}
		
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json"
		});
		request.done(function(data,status, jqXHR){

			obj = JSON.stringify(data);
			mensaje="<table class='table table-hover'>";
			mensaje+="<tr><td>Year</td><td>Enrolled</td><td>Budget</td><td>Employability</td><td>Student Migrants</td></tr>"
			$.each(data,function(index, value) {
				mensaje+="<tr><td>"+value.year+"</td><td>"+value.enrolled+"</td><td>"+value.budget+"</td><td>"+value.employability+"</td><td>"+value.studentMigrants+"</td></tr>";
				
			});	
			mensaje+="</table>";
			$('#data').html(mensaje);
		});
		request.fail(function(data,jqXHR,status){
			$('#data').html("No hay respuesta");
		});
		
		});
	
	$(".btn-post").on('click',function(){
		
		var year=$("#year_post").val();
		var budget=$("#budget_post").val();
		var employability=$("#employability_post").val();
		var enrolled=$("#enrolled_post").val();
		var student=$("#student_post").val();
		
		var url="/api/v1/universitySeville";
		var jsoncito='{ "year" : '+year+', "enrolled" :'+enrolled+', "budget" : '+budget+', "employability" : '+employability+', "studentMigrants" : '+student+'}';    
		
		var request = $.ajax({
			url: url,
			type: "POST",
			dataType: "text",
			data: jsoncito
			});
		request.done(function(data,status,jqXHR){
			
			$('#data').html('Datos anadidos');
		});
		request.fail(function(data,jqXHR,status){
			if(data.status == "400"){
				$('#data').html("Bad Request");
			}
			if(data.status == "409"){
				$('#data').html("Conflict");
			}
			
		});
		
		});
	$(".btn-put").on('click',function(){
		var year=$("#year_put").val();
		var budget=$("#budget_put").val();
		var employability=$("#employability_put").val();
		var enrolled=$("#enrolled_put").val();
		var student=$("#student_put").val();
		var url="/api/v1/universitySeville/"+year;
		var jsoncito='{ "year" : '+year+', "enrolled" :'+enrolled+', "budget" : '+budget+', "employability" : '+employability+', "studentMigrants" : '+student+'}';    
		var request = $.ajax({
			url: url,
			type: "PUT",
			dataType: "text",
			data:jsoncito
			});
		request.done(function(data,status,jqXHR){
			
			$('#data').html('Datos modificados');
		});
		request.fail(function(data,jqXHR,status){
			
			$('#data').html("No hay respuesta");
			
		});
		
		});
	
	
	
		$(".btn-delete").on('click', function(){
		var url="/api/v1/universitySeville";
		if($("#year_delete").val() != null){
				url+="/"+$("#year_delete").val();
		}
			
		var request = $.ajax({
			url: url,
			type: "DELETE",
			dataType: "text"
		});
		request.done(function(data,status,jqXHR){
			$('#data').html('Borrado');
		});
		request.fail(function(jqXHR,status){
			
			$('#data').html("no hay respuesta");
			
			
		});
		
	});	

    


});