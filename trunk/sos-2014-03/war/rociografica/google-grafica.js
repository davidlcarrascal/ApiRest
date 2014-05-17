$(document).ready(function(){
	function obtenerBudget(){
		var url = "/api/v2/universitySeville" + "/2014"
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		request.done(function(data,status, jqXHR){
			
			dato = data[0].budget;
			dat = parseInt(dato);
			return dat;
			
		
		});
		return dat;
	};
	
	function obtenerEnrolled(){
		var url = "/api/v2/universitySeville" + "/2014"
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		request.done(function(data,status, jqXHR){
			
			dato = data[0].enrolled;
			dat = parseInt(dato);
			return dat;
			
		
		});
		return dat;
	};
	
	
	function obtenerYear(){
		var url = "/api/v2/universitySeville" 
		var request = $.ajax({
			url: url,
			type: "GET",
			dataType: "json",
		    async: false
		});
		request.done(function(data,status, jqXHR){
			a = data;
			var year1, year2, year3;

		
			
		
		});
		return dat;
	};
	
	
	function drawVisualization() {
    // Create and populate the data table.
		e = obtenerEnrolled();
		b = obtenerBudget();
		y= obtenerYear();
		var data = google.visualization.arrayToDataTable([                                               
        ['Year', 'Enrolled', 'Budget', 'Employability', 'Migrant Students'],
        [y, e,   b,    10,   4],
        ['2001',  15,    9,    10,   9],
        ['2002',  10,    14,    6,    3],
        ['2003',  5,    2,    10,   8],
        ['2004',  3,    2,    9,    1],
        ['2005',  19,    5,    16,    16]
        ]);
      
        // Create and draw the visualization.
    new google.visualization.BarChart(document.getElementById('visualization')).
    draw(data,
        {title:"UniversitySeville", width:600, height:400,
               vAxis: {title: "Year"},
               hAxis: {title: ""}}
            );
    }
      

    google.setOnLoadCallback(drawVisualization);
 
});