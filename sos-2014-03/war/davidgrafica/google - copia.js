$(document).ready(function(){

  var request = $.ajax({
          url:    "/api/v2/SpainStd",
          type:   'GET',
          dataType: 'json' 
        });
  var request2 = $.ajax({
      url:    "/api/v2/universitySeville",
      type:   'GET',
      dataType: 'json' 
  });

  var request3 = $.ajax({
      url:    "/api/v1/Seville",
      type:   'GET',
      dataType: 'json' 
  });


      
  request.done(function(data,status,jqXHR) {

    var education =[];
    $.each(data,function(index, value) {
      education.push([value.year.toString(),value.educationBudget]);
    });

    request2.done(function(data,status,jqXHR) {
      
      var budgetus =[];
      
      $.each(data,function(index, value) {
        budgetus.push([value.year.toString(),value.budget]);

      });

    
      request3.done(function(data,status,jqXHR) {
        var educationSeville =[];
        $.each(data,function(index, value) {
          educationSeville.push([value.year.toString(),value.educationBudget]);
        });

        var concat=[];
        concat.push(['Year', 'Ed. Budget Spain','Budget US','Ed. Budget Seville']);

        for(var i=0; i<=education.length-1; i++){
          for (var j=0; j<= budgetus.length-1; j++) {
            for (var z=0; z<=educationSeville.length-1; z++){
              if(education[i][0] == budgetus[j][0] && educationSeville[z][0]==budgetus[j][0]){
                concat.push([education[i][0],education[i][1],budgetus[j][1],educationSeville[z][1]]);
              }
            }
          }
        }

        function drawVisualization(concat) {

        // Create and populate the data table.
        var array = google.visualization.arrayToDataTable(concat);
        // Create and draw the visualization.
        new google.visualization.ColumnChart(document.getElementById('visualization')).
            draw(array,
                 {title:"Widget de Google",
                  width:600, height:400,
                  hAxis: {title: "Year"}}
            );
        }

        google.setOnLoadCallback(drawVisualization(concat));

      });//done3
    });//done2
  });//done1


});