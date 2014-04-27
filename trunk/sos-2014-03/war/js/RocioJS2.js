$(document).ready(function(){

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
		var url="/api/v2/universitySeville";
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
			$('#data').html("No response - Year does not exist");
		});
		
		});
	
	$(".btn-post").on('click',function(){
	
		$(".input-form").css('border-color',"black");
		var year=$("#year_post").val();
		var budget=$("#budget_post").val();
		var employability=$("#employability_post").val();
		var enrolled=$("#enrolled_post").val();
		var student=$("#student_post").val();
		var errores=0;
		if(year==""){
			errores++;
	
			$('#year_post').css("border-color","red");
		}
		if(budget=="" ){
			errores++;
	
			$('#budget_post').css("border-color","red");
		}
		if(employability==""){
			errores++;

			$('#employability_post').css("border-color","red");
		}
		if(enrolled==""){
			errores++;

			$('#enrolled_post').css("border-color","red");
		}
		if(student==""){
			errores++;

			$('#student_post').css("border-color","red");
		}
		if(errores==0){
		
		var url="/api/v2/universitySeville";
		var jsoncito='{ "year" : '+year+', "enrolled" :'+enrolled+', "budget" : '+budget+', "employability" : '+employability+', "studentMigrants" : '+student+'}';    
		
		var request = $.ajax({
			url: url,
			type: "POST",
			dataType: "text",
			data: jsoncito
			});
		request.done(function(data,status,jqXHR){
			
			$('#data').html('Data added');
		});
		request.fail(function(data,jqXHR,status){
			if(data.status == "400"){
				$('#data').html("Bad Request");
			}
			if(data.status == "409"){
				$('#data').html("Conflict - Year content");
			}
			
		});
		}
		
		});
	$(".btn-put").on('click',function(){
		
		$(".input-form").css('border-color',"black");
		var year=$("#year_put").val();
		var budget=$("#budget_put").val();
		var employability=$("#employability_put").val();
		var enrolled=$("#enrolled_put").val();
		var student=$("#student_put").val();
		
		var errores=0;
		if(year==""){
			errores++;
	
			$('#year_put').css("border-color","red");
		}
		if(budget=="" ){
			errores++;
	
			$('#budget_put').css("border-color","red");
		}
		if(employability==""){
			errores++;

			$('#employability_put').css("border-color","red");
		}
		if(enrolled==""){
			errores++;

			$('#enrolled_put').css("border-color","red");
		}
		if(student==""){
			errores++;

			$('#student_put').css("border-color","red");
		}
		if(errores==0){
			
		var url="/api/v2/universitySeville/"+year;
		var jsoncito='{ "year" : '+year+', "enrolled" :'+enrolled+', "budget" : '+budget+', "employability" : '+employability+', "studentMigrants" : '+student+'}';    
		var request = $.ajax({
			url: url,
			type: "PUT",
			dataType: "text",
			data:jsoncito
			});
		request.done(function(data,status,jqXHR){
			
			$('#data').html('Change data');
		});
		request.fail(function(data,jqXHR,status){
			
			$('#data').html("No response - Year does not exist");
			
		});
		}
		});
	
	
	
		$(".btn-delete").on('click', function(){
		var url="/api/v2/universitySeville";
		if($("#year_delete").val() != null){
				url+="/"+$("#year_delete").val();
		}
			
		var request = $.ajax({
			url: url,
			type: "DELETE",
			dataType: "text"
		});
		request.done(function(data,status,jqXHR){
			$('#data').html('Delete');
		});
		request.fail(function(jqXHR,status){
			
			$('#data').html("No response - Year does not exist");
			
			
		});
		
	});	

    


});