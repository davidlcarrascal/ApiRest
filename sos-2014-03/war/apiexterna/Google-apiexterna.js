$(document).ready(function(){


		var requestUS = $.ajax({
			url: "/api/v3/universitySeville",
			type: "GET",
			dataType: "json"
		});
		var request = $.ajax({
			url: "/api/v2/SpainStd/",
			type: "GET",
			dataType: "json"
		    
		});
		
		var dat=[['Year', 'Budget US', 'Budget Spain']];
		var datosUS;
		var datosSpain;
		requestUS.done(function(data,status, jqXHR){
			datosUS = data;

			request.done(function(data,status, jqXHR){
				datosSpain = data;
				
				for(var i=0;i<=datosUS.length-1;i++){
					for(var j=0 ;j<=datosSpain.length-1; j++){
						if(datosUS[i].year==datosSpain[j].year){
							var year = parseInt(datosUS[i].year);
							var budgetUS = datosUS[i].budget;
							var budgetSpain = datosSpain[j].educationBudget;
							var dato = [];
							dato.push(""+year);
							dato.push(budgetUS);
							dato.push(budgetSpain);
							
							dat.push(dato);
						}
					}
				}
				
				if(dat.length==1){
					$('#error').append("NO HAY AÑOS COMUNES - NO SE PUEDE REALIZAR LA GRAFICA - CONSULTE API");
				}else{
					$('#grafica_valida').append("<p>Esta gráfica analiza el presupuesto"+
					"que tiene destinado la universidad de sevilla"+
					" y el presupuesto que da España a la educación en los años comunes en ambas api.</p>");	
				google.setOnLoadCallback(drawVisualization(dat));
				}
			});
		
		});
		
		

	
	
	function drawVisualization(data) {

		var char=google.visualization.arrayToDataTable(data);
		
		
		

      
        // Create and draw the visualization.
    new google.visualization.BarChart(document.getElementById('visualization')).
    draw(char,
        {title:"Universidad Sevilla vs Sevilla  ", width:600, height:400,
               vAxis: {title: "Year"},
               hAxis: {title: "Euros"}}
            );
    }
      

 
});