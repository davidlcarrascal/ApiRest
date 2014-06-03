$(document).ready(function(){
	function obtenerDato(year){
		var url = "/api/v2/universitySeville/" + year
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		var dato;
		request.done(function(data,status, jqXHR){			
			dato = data[0];		
		});
		
		return dato;
	};

	
	
	function obtenerYear(){
		var url = "/api/v2/universitySeville" 
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		var aniosBuenos;
		request.done(function(data,status, jqXHR){
			a = data;
			var anios= new Array();
			for(var i=0;i<a.length;i++){
				anios[i]=(a[i].year);
			}
			aniosBuenos=anios;
			//obtener todos los year 
		
		});
		return aniosBuenos;
		

	};
	
	
	function drawVisualization() {
    // Create and populate the data table.
		anios=obtenerYear();
		/*var data = google.visualization.arrayToDataTable([                                               
        ['Year', 'Enrolled', 'Budget', 'Employability', 'Migrant Students'],
        [y, e,   b,    10,   4]]);*/
		var data=[['Year', 'Enrolled', 'Budget','Employability', 'StudentMigrants']];
		
		for(var i=0;i<=anios.length-1;i++){
		var dato=[];
		var year=parseInt(anios[i]);
		dato.push(""+year);
		info=obtenerDato(year);
		dato.push(info.enrolled);
		dato.push(info.budget);
		dato.push(info.employability);
		dato.push(info.studentMigrants);
	
		
		data.push(dato);
		}
		var char=google.visualization.arrayToDataTable(data);

      
        // Create and draw the visualization.
    new google.visualization.BarChart(document.getElementById('visualization')).
    draw(char,
        {title:"UniversitySeville", width:600, height:400,
               vAxis: {title: "Year"},
               hAxis: {title: ""}}
            );
    }
      

    google.setOnLoadCallback(drawVisualization);
 
});