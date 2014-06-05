var stats,svg;

$( document ).ready(function() {
	
$.ajax({
	 url: "/api/v2/Seville",
	 context: document.body
	 }).done(function(data) {
	  stats = data;//en la variable stats voy a tener todos los datos
	  loadButtons();//función donde voy a cargar cada uno de los botones ke tengo, uno por cada propiedad.
	  
	  svg = d3.select("#graph").append("svg").attr("width",700).attr("height",300);//svg etiqueta de html5 ke se utiliza para pintar
	  //lo ke hagoes pintar el grafico
	  svg.append("g").attr("id","statsDonut");//es para dibujar figuras complejas. es una forma de guardarlas de tal forma que si
	  //quiero dibujar otra vez, le paso al svg el id del g y me lo pinta de nuevo sin hacerlo a mano.
	  var i = 0;
	  $.each(stats[0],function(k,v){ //me quito la propiedad year, q no la quiero representar.
		  if (i == 0) { 
			  i++; return 2; // return de cualquier cosa != a false se considera en el $.each un continue
		  }
		  
		  var arr = loadData(k); //cargas las propiedades de mi objeto: poblacion, unemployed, educationBudget....
		  Donut3D.draw("statsDonut", arr, 450, 150, 130, 100, 30, 0);//le indico donde lo dibujo, le digo las propiedades, el centro x,
		  //el centro y, el radio x, el radio y, la altura y el inner radius.
		  $("#info_attr").html(k);
		  
			var html = '<ul class="list-inline">';//para tenerlo todo alineado, me creo el ul, diciendole ke los li van en linea
			
			// We generate the legend here
			$.each(arr, function(k,v){//
				html += '<li><span style="background-color: '+ v.color+'">&nbsp;&nbsp;</span> '+ v.label + '</li>';
			});
			
			html += '</ul>';//Cuando acabo de meter todos los li, cierro el ul
			
			$("#info_years").html(html);//en el div ese, me pinte lo ke he puesto en la variable
		  
		  return false; // break en el $.each
	  });
	});	
});

function loadButtons(){
	var button;
	$.each(stats[0], function(k,v){
		if (k == "year") return 2;//para año no kiero boton
		//btn-primary es una clase de css de bootstrap, cuando haga click en el boton de cada propiedad,(botones azules)
		button= $('<button id="button_'+ k +'" class="btn btn-primary" style="margin: 0.4em" onClick="changeGraph(\''+ k +'\')">Show attribute '+ k +'</button></div>');
		$("#buttons").append(button);
		button.hide().fadeIn('slow');
	});
}

function changeGraph(attribute){//
	$("#info_attr").html(attribute);//pone el titulo del cuadro mas grande
	var arr = loadData(attribute);
	
	Donut3D.transition("statsDonut", arr, 130, 100, 30, 0);//cambia el grafico, rotando
}

function loadData(attribute){//
	var arr =  stats.map(function(d){
		return {label:d['year']+"", value:d[attribute], color:'#'+Math.floor(Math.random()*16777215).toString(16)}
	});
	return arr;
	
}