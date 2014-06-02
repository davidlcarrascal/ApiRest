$(document).ready(function(){
	

	var dat=[['Year', 'Enrolled', 'Budget','Employability', 'StudentMigrants']];
	

		var url = "/api/v2/universitySeville" 
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json"
		    
		});

		request.done(function(data,status, jqXHR){
					
			for(var i=0;i<=data.length-1;i++){
				var dato=[];
				var year=parseInt(data[i].year);
				dato.push(""+year);
				info=data[i];
				dato.push(info.enrolled);
				dato.push(info.budget);
				dato.push(info.employability);
				dato.push(info.studentMigrants);

				
				dat.push(dato);
			}

		    google.setOnLoadCallback(drawVisualization(dat));
		
		});
	
		

	

	
	function drawVisualization(data) {
    // Create and populate the data table.

		var char=google.visualization.arrayToDataTable(data);

      
        // Create and draw the visualization.
    new google.visualization.BarChart(document.getElementById('visualization')).
    draw(char,
        {title:"UniversitySeville", width:600, height:400,
               vAxis: {title: "Year"},
               hAxis: {title: ""}}
            );
    }
      

 
});