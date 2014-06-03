var aniosBuenos;
var dato;
var contador =0;
var year;
var obtenerYear;
var color;
$(document).ready(function(){
	
var svg = d3.select("body")
	.append("svg")
	.append("g")

svg.append("g")
	.attr("class", "slices");

svg.append("g")
	.attr("class", "labels");

svg.append("g")
	.attr("class", "lines");

//tamanio del circulo
var width = 960,
    height = 450,
	radius = Math.min(width, height) / 2;

var pie = d3.layout.pie()
	.sort(null)
	.value(function(d) {
		return d.value;
	});


//cambia los valores del circulo del centro o del los bordes
var arc = d3.svg.arc()
	.outerRadius(radius * 0.8)
	.innerRadius(radius * 0.4);

//donde colaca el label 
var outerArc = d3.svg.arc()
	.innerRadius(radius * 0.9)
	.outerRadius(radius * 0.9);

svg.attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

var key = function(d){ 
	return d.data.label; 
};


function getColor(year){
	var colors=new Array();
	for(var i=0;i<year.length;i++){
			colors[i]='#'+(0x1000000+(Math.random())*0xffffff).toString(16).substr(1,6)
	}
	return colors;
}



function databudget(){
	contador=0;
	var labels = color.domain();
	return labels.map(function(label){
			for(var i=0;i<=obtenerYear.length;i++){
				if(obtenerYear[i]==label){
					contador=i;
					break;
				}
			}	
			var r = { label: label, value: dato[contador].budget};
		contador++;
		return r;
		
	});
}
function dataenrolled(){
	contador=0;
	var labels = color.domain();
	return labels.map(function(label){
			for(var i=0;i<=obtenerYear.length;i++){
				if(obtenerYear[i]==label){
					contador=i;
					break;
				}
			}	
			var r = { label: label, value: dato[contador].enrolled};
		contador++;
		return r;
		
	});
}
function dataemployability(){
	contador=0;
	var labels = color.domain();
	return labels.map(function(label){
			for(var i=0;i<=obtenerYear.length;i++){
				if(obtenerYear[i]==label){
					contador=i;
					break;
				}
			}	
			var r = { label: label, value: dato[contador].employability};
		contador++;
		return r;
		
	});
}


function datamigrants(){
	contador=0;
	var labels = color.domain();
	return labels.map(function(label){
			for(var i=0;i<=obtenerYear.length;i++){
				if(obtenerYear[i]==label){
					contador=i;
					break;
				}
			}	
			var r = { label: label, value: dato[contador].studentMigrants};
		contador++;
		return r;
		
	});
}

d3.select(".property").on('change',function(){
	if(document.getElementById('ppp').value=="budget"){
	change(databudget());
	}	
	if(document.getElementById('ppp').value=="employability"){
	change(dataemployability());
	}
	if(document.getElementById('ppp').value=="migrant"){
	change(datamigrants());
	}
	if(document.getElementById('ppp').value=="enrolled"){
	change(dataenrolled());
	}
	
});


function change(data) {

	/* ------- PIE SLICES -------*/
	//Le da una porcion a cada uno(divide el circulo en los data que hay, las key son los anios)
	var slice = svg.select(".slices").selectAll("path.slice")
		.data(pie(data), key);

	slice.enter()
		.insert("path")
		.style("fill", function(d) { return color(d.data.label); })
		.attr("class", "slice");

	slice		
		.transition().duration(1000)
		.attrTween("d", function(d) {
			this._current = this._current || d;
			var interpolate = d3.interpolate(this._current, d);
			this._current = interpolate(0);
			return function(t) {
				return arc(interpolate(t));
			};
		})

	slice.exit()
		.remove();

	/* ------- TEXT LABELS -------*/

	var text = svg.select(".labels").selectAll("text")
		.data(pie(data), key);

	text.enter()
		.append("text")
		.attr("dy", ".35em")
		.text(function(d) {
			return d.data.label;
		});
	
	function midAngle(d){
		return d.startAngle + (d.endAngle - d.startAngle)/2;
	}

	text.transition().duration(1000)
		.attrTween("transform", function(d) {
			this._current = this._current || d;
			var interpolate = d3.interpolate(this._current, d);
			this._current = interpolate(0);
			return function(t) {
				var d2 = interpolate(t);
				var pos = outerArc.centroid(d2);
				pos[0] = radius * (midAngle(d2) < Math.PI ? 1 : -1);
				return "translate("+ pos +")";
			};
		})
		.styleTween("text-anchor", function(d){
			this._current = this._current || d;
			var interpolate = d3.interpolate(this._current, d);
			this._current = interpolate(0);
			return function(t) {
				var d2 = interpolate(t);
				return midAngle(d2) < Math.PI ? "start":"end";
			};
		});

	text.exit()
		.remove();

	/* ------- SLICE TO TEXT POLYLINES -------*/

	var polyline = svg.select(".lines").selectAll("polyline")
		.data(pie(data), key);
	
	polyline.enter()
		.append("polyline");

	polyline.transition().duration(1000)
		.attrTween("points", function(d){
			this._current = this._current || d;
			var interpolate = d3.interpolate(this._current, d);
			this._current = interpolate(0);
			return function(t) {
				var d2 = interpolate(t);
				var pos = outerArc.centroid(d2);
				pos[0] = radius * 0.95 * (midAngle(d2) < Math.PI ? 1 : -1);
				return [arc.centroid(d2), outerArc.centroid(d2), pos];
			};			
		});
	
	polyline.exit()
		.remove();
};




	var url = "/api/v3/universitySeville"
	var request = $.ajax({
		url: url,
		type: "GET",
		dataType: "json",
	   
	});
	
	request.done(function(data,status, jqXHR){			
		dato = data;
		var anios= new Array();
		for(var i=0;i<data.length;i++){
			anios[i]=""+(data[i].year);
		}
		obtenerYear = anios;
		range=getColor(obtenerYear);
		
		color = d3.scale.ordinal()
			.domain(obtenerYear)
			.range(range);
	});
	
	


});
