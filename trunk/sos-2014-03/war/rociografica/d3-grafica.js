var aniosBuenos;
var dato;
var contador =0;
var year;
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

var width = 960,
    height = 450,
	radius = Math.min(width, height) / 2;

var pie = d3.layout.pie()
	.sort(null)
	.value(function(d) {
		return d.value;
	});

var arc = d3.svg.arc()
	.outerRadius(radius * 0.8)
	.innerRadius(radius * 0.4);

var outerArc = d3.svg.arc()
	.innerRadius(radius * 0.9)
	.outerRadius(radius * 0.9);

svg.attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

var key = function(d){ return d.data.label; };




year = obtenerYear();
range=getRange(year);

function getRange(year){
	var colors=new Array();
	for(var i=0;i<year.length;i++){
			colors[i]='#'+(0x1000000+(Math.random())*0xffffff).toString(16).substr(1,6)
	}
	return colors;
}

var color = d3.scale.ordinal()
	.domain(year)
	.range(range);



function databudget(){
	contador=0;
	var labels = color.domain();
	return labels.map(function(label){
			for(var i=0;i<=year.length;i++){
				if(year[i]==label){
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
			for(var i=0;i<=year.length;i++){
				if(year[i]==label){
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
			for(var i=0;i<=year.length;i++){
				if(year[i]==label){
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
			for(var i=0;i<=year.length;i++){
				if(year[i]==label){
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

function obtenerYear(){
	
	var request = $.ajax({
		url: "/api/v2/universitySeville",
		type: "GET",
		dataType: "json",
		async: false
	});
	
	request.done(function(data,status, jqXHR){
		a = data;
		var anios= new Array();
		for(var i=0;i<a.length;i++){
			anios[i]=""+(a[i].year);
		}
		aniosBuenos = anios;
		return aniosBuenos;
	});
	
	return aniosBuenos;

};


	var url = "/api/v2/universitySeville"
	var request = $.ajax({
		url: url,
		type: "GET",
		dataType: "json",
	   
	});
	
	request.done(function(data,status, jqXHR){			
		dato = data;

	});
	
	


});
