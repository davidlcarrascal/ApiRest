$(document).ready(function(){
	function obtenerBudgetUS(year){
		var url = "/api/v2/universitySeville/" + year
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		var dato;
		request.done(function(data,status, jqXHR){			
			dato = data[0].budget;		
		});
		
		return dato;
	};

	function obtenerBudgetSpain(year){
		var url = "/api/v1/SpainStd/" + year
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		var datoSpain;
		request.done(function(data,status, jqXHR){	
			
			datoSpain = data.educationBudget;		
		});
		
		return datoSpain;
	};
	
	
	
	
	function obtenerYearUS(){
		var url = "/api/v2/universitySeville" 
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		var aniosUS;
		request.done(function(data,status, jqXHR){
			a = data;
			var anios= new Array();
			for(var i=0;i<a.length;i++){
				anios[i]=(a[i].year);
			}
			aniosUS=anios;

		
		});
		return aniosUS;
		

	};
	function obtenerYearSpain(){
		var url = "/api/v1/SpainStd/" 
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		var aniosSpain;
		request.done(function(data,status, jqXHR){
			a = data;
			var anios= new Array();
			for(var i=0;i<a.length;i++){
				anios[i]=(a[i].year);
			}
			aniosSpain=anios;

		
		});
		return aniosSpain;
		

	};
	
	
	
	function drawVisualization() {
		aniosUS=obtenerYearUS();
		aniosSpain=obtenerYearSpain();
		
		var year= new Array();
		for(var i=0; i<aniosUS.length;i++){
			for(var j=0; j<aniosSpain.length;j++){
				if(aniosUS[i]==aniosSpain[j]){
					year.push(aniosUS[i]);
				}
			}
		}
		
		if (year.length == 0){
			
			$('#error').append("NO HAY AÑOS COMUNES - NO SE PUEDE REALIZAR LA GRAFICA - CONSULTE API");
			var data=[['Year', 'Budget US', 'Budget Spain'],["-",0,0]];
			
			var char=google.visualization.arrayToDataTable(data);
			
			
		}else{
			var data=[['Year', 'Budget US', 'Budget Spain']];
			
			for(var i=0;i<=year.length-1;i++){
				var dato=[];
				var yearT=parseInt(year[i]);
				dato.push(""+yearT);
				budgetUS=obtenerBudgetUS(yearT);
				budgetSpain=obtenerBudgetSpain(yearT);
				dato.push(budgetUS);
				dato.push(budgetSpain);
				data.push(dato);
			}
			var char=google.visualization.arrayToDataTable(data);
		}
		
		

      
        // Create and draw the visualization.
    new google.visualization.BarChart(document.getElementById('visualization')).
    draw(char,
        {title:"Presupuesto de que dispone la universidad Sevilla vs España", width:600, height:400,
               vAxis: {title: "Year"},
               hAxis: {title: "Euros"}}
            );
    }
      

    google.setOnLoadCallback(drawVisualization);
 
});