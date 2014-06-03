

$(document).ready(function(){

	var valor="";
	var tam = 0;
	var cont = 0;
	

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
		.domain(["Emigrants Spain", "Migrants US","Students Seville"])
		.range(["#a05d56", "#7b6888","#98abc5"]);
		//.domain(["Year", "Emigrants Spain", "Migrants US"])
		//.range(["#98abc5", "#a05d56", "#7b6888"]);

	
	function nextYear (){
		var labels = color.domain();
		return labels.map(function(label){

			$("#año").html("Año: "+valor[cont][0]);
			
			if(label =="Emigrants Spain")
				var r = { label: label, value: valor[cont][1] };
			if(label =="Migrants US")
				var r = { label: label, value: valor[cont][2] };
			if (label =="Students Seville") 
				var r = { label: label, value: valor[cont][3] };
			return r;
		});
	}


	d3.select(".change")
		.on("click", function(){
			change(nextYear());
			cont=(cont+1)%tam;
			
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

	var request = $.ajax({
	    url:    "/api/v2/SpainStd",
	    type:   'GET',
	    dataType: 'json' 
    });

	var request2 = $.ajax({
	    url:    "/api/v3/universitySeville",
	    type:   'GET',
	    dataType: 'json' 
	});

	var request3 = $.ajax({
	    url:    "/api/v1/proxydavid",
	    type:   'GET',
	    dataType: 'json' 
    });

    request.done(function(data,status,jqXHR) {
		var emigrants = [];
		$.each(data,function(index, value) {
     		emigrants.push([value.year,value.emigrants]);
   	 	});
		request2.done(function(data,status,jqXHR) {
			var migrantsUs = [];
			
			$.each(data,function(index, value) {
				migrantsUs.push([value.year,value.studentMigrants])
	   	 	});


			request3.done(function(data,status,jqXHR) {
		        var students =[];
		        $.each(data,function(index, value) {
		          students.push([value.year,value.students]);
		        });

		        var concat=[];
		        for(var i=0; i<=emigrants.length-1; i++){
		          for (var j=0; j<= migrantsUs.length-1; j++) {
		            for (var z=0; z<=students.length-1; z++){
		              if(emigrants[i][0] == migrantsUs[j][0] && students[z][0]==migrantsUs[j][0]){
		                concat.push([emigrants[i][0],emigrants[i][1],migrantsUs[j][1],students[z][1]]);
		              }
		            }
		          }
		        }
		        valor = concat;
	   	 		tam = concat.length;
				change(nextYear());	
			
			});//done3	
		});//done2
    });//done1
});
