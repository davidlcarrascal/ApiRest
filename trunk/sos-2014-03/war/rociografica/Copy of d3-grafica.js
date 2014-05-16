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

var color = d3.scale.ordinal()
	.domain(["2014", "2011", "2012", "2003"])
	.range(["#98abc5", "#8a89a6", "#7b6888", "#6b486b"]);

function databudget(){
	var labels = color.domain();
	return labels.map(function(label){
		
		if(label == "2014"){
			alert(label);
			var valor = 0;
			var value =0;
			var url = "/api/v2/universitySeville" + "/" + label
			var request = $.ajax({
				url: url,
				type: "GET",
				dataType: "json"
			});
			request.done(function(data,status, jqXHR){
				obj = JSON.stringify(data);
				$.each(data,function(index, val) {
					valor = val.budget;
				
					label:label;
					
					alert(valor);
					
					
				});	
			
			});
			
			return { label: label, value: valor }
		}
		
		if(label == "2011"){	
		return { label: label, value: 2 }
		}
		if(label == "2012"){	
		return { label: label, value: 3 }
		}
		if(label == "2003"){	
		return { label: label, value: 5 }
		}
	});
}
function dataemployability(){
	var labels = color.domain();
	return labels.map(function(label){
		if(label == "2014"){

			return { label: label, value: 1 }
		 
		}
		if(label == "2001"){	
		return { label: label, value: 2 }
		}
		if(label == "2002"){	
		return { label: label, value: 5 }
		}
		if(label == "2003"){	
		return { label: label, value: 3 }
		}
	});
}
function datamigrants(){
	var labels = color.domain();
	return labels.map(function(label){
		if(label == "2014"){
        return { label: label, value: 1 }
		 
		}
		if(label == "2001"){	
		return { label: label, value: 10 }
		}
		if(label == "2002"){	
		return { label: label, value: 3 }
		}
		if(label == "2003"){	
		return { label: label, value: 4 }
		}
	});
}
function dataenrolled (){
	var labels = color.domain();
	return labels.map(function(label){
		if(label == "2014"){
        return { label: label, value: 5 }
		 
		}
		if(label == "2001"){	
		return { label: label, value: 5 }
		}
		if(label == "2002"){	
		return { label: label, value: 1 }
		}
		if(label == "2003"){	
		return { label: label, value: 1 }
		}
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
});
