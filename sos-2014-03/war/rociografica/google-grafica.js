      function drawVisualization() {
        // Create and populate the data table.
        var data = google.visualization.arrayToDataTable([
          ['Year', 'Enrolled', 'Budget', 'Employability', 'Migrant Students'],
          ['2000',  11,    1,    10,   4],
          ['2001',  15,    9,    10,   9],
          ['2002',  10,    14,    6,    3],
          ['2003',  5,    2,    10,   8],
          ['2004',  3,    2,    9,    1],
          ['2005',  19,    5,    16,    16]
        ]);
      
        // Create and draw the visualization.
        new google.visualization.BarChart(document.getElementById('visualization')).
            draw(data,
                 {title:"UniversitySeville",
                  width:600, height:400,
                  vAxis: {title: "Year"},
                  hAxis: {title: ""}}
            );
      }
      

      google.setOnLoadCallback(drawVisualization);