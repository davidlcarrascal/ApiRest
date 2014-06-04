var stats,svg;

$( document ).ready(function() {
	
$.ajax({
	 url: "/api/v1/Seville",
	 context: document.body
	  
	}).done(function(data) {
	  stats = data;
	  loadButtons();
	  
	  svg = d3.select("#graph").append("svg").attr("width",700).attr("height",300);
	  svg.append("g").attr("id","statsDonut");
	  var i = 0;
	  $.each(stats[0],function(k,v){
		  if (i == 0) { 
			  i++; return 2; // return de cualquier cosa != a false se considera en el $.each un continue
		  }
		  
		  var arr = loadData(k);
		  Donut3D.draw("statsDonut", arr, 450, 150, 130, 100, 30, 0);
		  $("#info_attr").html(k);
		  
			var html = '<ul class="list-inline">';
			
			// We generate the legend here
			$.each(arr, function(k,v){
				html += '<li><span style="background-color: '+ v.color+'">&nbsp;&nbsp;</span> '+ v.label + '</li>';
			});
			
			html += '</ul>';
			
			$("#info_years").html(html);
		  
		  return false; // break en el $.each
	  });
	});	
});

function loadButtons(){
	var button;
	$.each(stats[0], function(k,v){
		if (k == "year") return 2;
		
		button= $('<button id="button_'+ k +'" class="btn btn-primary" style="margin: 0.4em" onClick="changeGraph(\''+ k +'\')">Show attribute '+ k +'</button></div>');
		$("#buttons").append(button);
		button.hide().fadeIn('slow');
	});
}

function changeGraph(attribute){
	$("#info_attr").html(attribute);
	var arr = loadData(attribute);
	
	Donut3D.transition("statsDonut", arr, 130, 100, 30, 0);
}

function loadData(attribute){
	var arr =  stats.map(function(d){
		return {label:d['year']+"", value:d[attribute], color:'#'+Math.floor(Math.random()*16777215).toString(16)}
	});
	return arr;
	
}