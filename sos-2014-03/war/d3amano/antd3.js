$(document).ready(function(){
	
	var dato = [["2001",100000,10000,300000],
				["2002",200000,10000,30000],
				["2003",300000,100000,300000],
				["2004",222222,222212,222221],
				["2005",222222,222212,222221]];


	var width=600;
	var height=1500;
	var max = d3.max(d3.max(dato)); //maximo del conjunto
	var widthEscala = d3.scale.linear()
						.domain([0,max])
						.range([0,500]);


	var axis = d3.svg.axis()
			.scale(widthEscala);


	var variable =1;
	function selecionarvar(){
		if($("#select").val() =="emigrants"){
			variable=1;
		}	
		if($("#select").val() =="migrants"){
			variable=2;
		}if($("#select").val() =="students"){
			variable=3;
		}
		return variable
	}
	


	

	var canvas = d3.select("body")
		.append("svg")
		.attr("width",width)
		.attr("height",height)
		.append("g")
		.attr("transform","translate(60,40)");
		//g, Grupo. Con transform y translate podemos
		//moverlo hacia arriba y abajo.
		//.append("g")
		//.attr("transform","translate(50,50)");
	
	//por cada año crea un rectangulo, d[1], decide si es el presupuesto, imigracion,etc..
	var rects = canvas.selectAll("rect")
				.data(dato)
				.enter()
					.append("rect")
					.attr("width",50)
					.attr("height",50)
					.attr("y",function(d,i){return i*100;}); //d es el dato del array, i es el index.
					
	var text = canvas.selectAll("text")
				.data(dato)
				.enter()
					.append("text")
					.attr("fill","red")
					.text(function(d){return d[0];})
					.attr("y",function(d,i){return i*100+24;});
	
	d3.select("#start")
		.on("click", function(){
		
		variable=selecionarvar();
		rects.transition().remove();
		rects.attr("fill","black");
		rects.transition().duration(1500)	
			.attr("x",function(d){return widthEscala(d[variable]);})
			.delay(1000)
			.each("end",function(){d3.select(this).attr("fill","green");});
			
		});
	

	canvas.append("g")
			.attr("transform","translate(0,-27)")
			.call(axis);


	/*var circle = canvas.append("circle")
		.attr("cx",200)
		.attr("cy",150)
		.attr("r",25)
		.attr("fill","red");

	circle.transition()
		.duration(1500)
		.attr("cx",250);

	var rect = canvas.append("rect")
		.attr("height",100)
		.attr("width",50)
		.attr("fill","green");

	var tri = canvas.append("ellipse")
		.attr("cx",50)
		.attr("cy",220)
		.attr("rx",50)
		.attr("ry",25)
		.attr("fill","orange");
*/
	});