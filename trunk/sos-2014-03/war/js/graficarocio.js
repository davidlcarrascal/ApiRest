$(document).ready(function drawVisualization() {
  var data = google.visualization.arrayToDataTable([
    ['Year',  'budget', 'enrolled'],
    ['2000', 1,         1],
    ['2001',     2,         3],
    ['2002',        1,         9],
    ['2003',      5,         4]
  ]);

  var options = {
    width: 600, height: 400,
    title: 'The decline of \'The 39 Steps\'',
    vAxis: {title: 'Accumulated Rating'},
    isStacked: true
  };

  var chart = new google.visualization.SteppedAreaChart(document.getElementById('visualization'));
  $('#graficagoogle').html(chart.draw(data, options));
});