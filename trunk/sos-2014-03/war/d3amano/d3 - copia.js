$(document).ready(function(){

	var request = $.ajax({
	    url:    "/api/v1/SpainStd",
	    type:   'GET',
	    dataType: 'json' 
    });

	var request2 = $.ajax({
	    url:    "/api/v2/universitySeville",
	    type:   'GET',
	    dataType: 'json' 
	});

	var request3 = $.ajax({
	    url:    "/api/v1/proxydavid",
	    type:   'GET',
	    dataType: 'json' 
    });

    request.done(function(data,status,jqXHR) {
		var emigrants = [];
		$.each(data,function(index, value) {
     		emigrants.push([value.year,value.emigrants]);
   	 	});
		request2.done(function(data,status,jqXHR) {
			var migrantsUs = [];
			
			$.each(data,function(index, value) {
				migrantsUs.push([value.year,value.studentMigrants])
	   	 	});


			request3.done(function(data,status,jqXHR) {
		        var students =[];
		        $.each(data,function(index, value) {
		          students.push([value.year,value.students]);
		        });

		        var concat=[];
		        for(var i=0; i<=emigrants.length-1; i++){
		          for (var j=0; j<= migrantsUs.length-1; j++) {
		            for (var z=0; z<=students.length-1; z++){
		              if(emigrants[i][0] == migrantsUs[j][0] && students[z][0]==migrantsUs[j][0]){
		                concat.push([emigrants[i][0].toString(),emigrants[i][1],migrantsUs[j][1],students[z][1]]);
		              }
		            }
		          }
		        }
		        dato = concat;			
			
				var width=600;
				var height=1500;
				var max = d3.max(d3.max(dato)); //maximo del conjunto
				var widthEscala = d3.scale.linear()
						.domain([0,max])
						.range([0,500]);


				var axis = d3.svg.axis()
						.scale(widthEscala);
						
				var variable =1;
				function selecionarvar(){
					if($("#select").val() =="emigrants"){
						variable=1;
					}	
					if($("#select").val() =="migrants"){
						variable=2;
					}if($("#select").val() =="students"){
						variable=3;
					}
					return variable
				}
	


	

				var canvas = d3.select("body")
					.append("svg")
					.attr("width",width)
					.attr("height",height)
					.append("g")
					.attr("transform","translate(60,40)");
					//g, Grupo. Con transform y translate podemos
					//moverlo hacia arriba y abajo.
					//.append("g")
					//.attr("transform","translate(50,50)");
				
				//por cada año crea un rectangulo, d[1], decide si es el presupuesto, imigracion,etc..
				
				

				


			    var rects = canvas.selectAll("rect")
							.data(dato)
							.enter()
								.append("rect")
								.attr("width",50)
								.attr("height",50)
								.attr("y",function(d,i){return i*100;}); //d es el dato del array, i es el index.
								
				var text = canvas.selectAll("text")
							.data(dato)
							.enter()
								.append("text")
								.attr("fill","red")
								.text(function(d){return d[0];})
								.attr("y",function(d,i){return i*100+24;});
				
				d3.select("#start")
					.on("click", function(){
					
					variable=selecionarvar();
					rects.transition().remove();
					rects.attr("fill","black");
					rects.transition().duration(1500)	
						.attr("x",function(d){return widthEscala(d[variable]);})
						.delay(1000)
						.each("end",function(){d3.select(this).attr("fill","green");});
						
					});//onclick
					
				canvas.append("g")
						.attr("transform","translate(0,-27)")
						.call(axis);

			});//done3	
		});//done2
    });//done1
		
});