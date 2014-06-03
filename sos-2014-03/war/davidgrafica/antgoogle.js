$(document).ready(function(){

  var request = $.ajax({
          url:    "/api/v1/SpainStd",
          type:   'GET',
          dataType: 'json' 
        });
      
  request.done(function(data,status,jqXHR) {

    var trans =[];
    trans.push(['Year', 'Population', 'Unemployed', 'Pib', 'Emigrants', 'Education Budget']);

    $.each(data,function(index, value) {
      trans.push([value.year.toString(),value.population,value.unemployed,value.pib,value.emigrants,value.educationBudget]);
    });
    
    function drawVisualization(trans) {

        // Create and populate the data table.
        var array = google.visualization.arrayToDataTable(trans);
        // Create and draw the visualization.
        new google.visualization.ColumnChart(document.getElementById('visualization')).
            draw(array,
                 {title:"Widget de Google",
                  width:600, height:400,
                  hAxis: {title: "Year"}}
            );
      }
      

    google.setOnLoadCallback(drawVisualization(trans));


  });
});