$(document).ready(function(){


		var requestUS = $.ajax({
			url: "/api/v2/universitySeville",
			type: "GET",
			dataType: "json"
		});
		var request = $.ajax({
			url: "/api/v1/SpainStd/",
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
				
				
				google.setOnLoadCallback(drawVisualization(dat));
			
			});
		
		});
		
		

	
	
	

		

				
//if (year.length == 0){
//			
//			$('#error').append("NO HAY AÑOS COMUNES - NO SE PUEDE REALIZAR LA GRAFICA - CONSULTE API");
//			var data=[['Year', 'Budget US', 'Budget Spain'],["-",0,0]];
//			
//			var char=google.visualization.arrayToDataTable(data);
//			

	
	
	
	
	
	
	
	
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