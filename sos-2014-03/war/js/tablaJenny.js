$(document).ready(function() {//Cuando el html esté todo cargado, realizare la siguiente función
	var tabla = $('#tabla').dataTable({//Me inicializo la tabla, en el div de tabla meto la tabla
		"bJQueryUI": true, //para indicarle ke se pueda pintar usando el fichero de jQuery.ui
		"sPaginationType": "full_numbers", //para ke me salga lo de la paginación
		"aoColumns" : [ {//Para pintar las columnas
			"sTitle" : "Year"
		}, {
			"sTitle" : "Population"
		}, {
			"sTitle" : "Unemployed"
		}, {
			"sTitle" : "Education budget"
		}, {
			"sTitle" : "Migrants"
		}, {
			"sTitle" : "PIB"
		} ]
	});

	get();//Hago uso del get
	$("#formularioBuscar").on('submit', buscar);//Cuando hago submit del formulario hara lo ke este en buscar
	$("#formularioCrear").on('submit', post);//Cuando hago submit del formulario hara lo ke este en post
	$("#formularioModificar").on('submit', put);
	$("#formularioBorrar").on('submit', deleteYear);
	$("#formularioBorrarTodo").on('submit', deleteTodo);
	$("#formularioBuscar").on('submit', buscar);
	$("#recargar").on('click', get);
	
	$("#li_crear").on('click', function(){//Cuando haga click en el li_crear ejecuto la siguiente funcion
		$('#div_buscar').css("display", "none");//div_buscar es el formulario dentro de cada li, es decir, seria el formulario
		$('#div_modificar').css("display", "none");//display para ke aparezca el formulario. si pongo none, ese campo no aparece y cuando pongo block si aparece
		$('#div_borrar').css("display", "none");
		$('#div_crear').css("display", "block");
		$('#div_borrarTodo').css("display", "none");
});
	$("#li_modificar").on('click', function(){
		$('#div_buscar').css("display", "none");
		$('#div_modificar').css("display", "block");
		$('#div_borrar').css("display", "none");
		$('#div_crear').css("display", "none");
		$('#div_borrarTodo').css("display", "none");
});
	$("#li_borrar").on('click', function(){
		$('#div_buscar').css("display", "none");
		$('#div_modificar').css("display", "none");
		$('#div_borrar').css("display", "block");
		$('#div_crear').css("display", "none");
		$('#div_borrarTodo').css("display", "none");
});
	$("#li_borrarTodo").on('click', function(){
		$('#div_buscar').css("display", "none");
		$('#div_modificar').css("display", "none");
		$('#div_borrar').css("display", "none");
		$('#div_crear').css("display", "none");
		$('#div_borrarTodo').css("display", "block");
});
	$("#li_buscar").on('click', function(){
		$('#div_buscar').css("display", "block");
		$('#div_modificar').css("display", "none");
		$('#div_borrar').css("display", "none");
		$('#div_crear').css("display", "none");
		$('#div_borrarTodo').css("display", "none");
});

	function get() {
		request = $.ajax({
			url : "/api/v2/Seville/",
			type : "GET",
			dataType : "json",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {

			tabla.fnClearTable();// borro la tabla completa
			drawTable(data);// dibujo la tabla
		});
		request.fail(function(jqXHR, status) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	}
	
	//get de un año, le especifico el año en buscar y ,e cogera el año ke filtre
	function buscar() {
		var year = $("#yearBuscar").val();//me coja el valor ke le pase por el input de yearBuscar 
		request = $.ajax({
			url : "/api/v2/Seville/" + year.toString(),//como si pongo en la url api/v2/Seville/2014
			type : "GET",
			dataType : "json",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {

			tabla.fnClearTable();// borro la tabla completa
			drawTable(data);// dibujo la tabla
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);//para ke me salgan los errores, y el 200 y OK X EJEMPLO
		});
		request.fail(function(jqXHR, status) {
			tabla.fnClearTable();// borro la tabla completa
			drawTable(data);// dibujo la tabla
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		return false;//porke la funcion me devolvia algo, y lo mire en internet y me decia ke pusiera return false
	}

	function post() {//para crear
		//me va cogiendo los valores del formulario
		var year = $("#year").val(); //me coje el valor ke le pase por el input de year
		var population = $("#population").val();
		var unemployed = $("#unemployed").val();
		var educationBudget = $("#educationBudget").val();
		var migrants = $("#migrants").val();
		var pib = $("#pib").val();

		//me creo el objeto y le especifico las propiedades k tiene, en este caso, de tipo Number
		var seville = new Object();
		seville.year = new Number();
		seville.population = new Number();
		seville.unemployed = new Number();
		seville.educationBudget = new Number();
		seville.migrants = new Number();
		seville.pib = new Number();
		
		//le paso los valores ke tienen las propiedades, si son integer o float
		seville.year = parseInt(year);//(year) son los valores ke le he indicado en el formulario
		seville.population = parseInt(population);
		seville.unemployed = parseInt(unemployed);
		seville.educationBudget = parseFloat(educationBudget);
		seville.migrants = parseInt(migrants);
		seville.pib = parseFloat(pib);

		var json = JSON.stringify(seville);//MI objeto Seville lo paso a json, porke para crear un objeto lo tengo ke poner en json
		var request = $.ajax({
			url : "/api/v2/Seville/",
			type : "POST",
			dataType : "json",
			contentType : "application/json",
			data : json//aki le especifico ke le voy a pasar unos datos a la petición ke estan en json
		});
		request.done(function(data, status, jqXHR) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});

		request.fail(function(jqXHR, status) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	}
	
	function put() {
		//Me coje los valores del formulario
		var year = $("#yearPut").val();
		var population = $("#populationPut").val();
		var unemployed = $("#unemployedPut").val();
		var educationBudget = $("#educationBudgetPut").val();
		var migrants = $("#migrantsPut").val();
		var pib = $("#pibPut").val();
		
		//Me creo el objeto y las propiedades asociadas ke son de tipo Number
		var seville = new Object();
		seville.year = new Number();
		seville.population = new Number();
		seville.unemployed = new Number();
		seville.educationBudget = new Number();
		seville.migrants = new Number();
		seville.pib = new Number();

		//Y le asocio el valor ke tengo, year=2014, population=50000.... diciendo de ke tipo es cada uno
		seville.year = parseInt(year);
		seville.population = parseInt(population);
		seville.unemployed = parseInt(unemployed);
		seville.educationBudget = parseFloat(educationBudget);
		seville.migrants = parseInt(migrants);
		seville.pib = parseFloat(pib);

		var json = JSON.stringify(seville);//Paso el objeto a json, siempre para modificar necesito pasar el objeto a json
		var request = $.ajax({
			url : "/api/v2/Seville/" + year.toString(),
			type : "PUT",
			dataType : "json",
			contentType : "application/json",
			data : json //este es el objeto ke le paso para modificar
		});
		request.done(function(data, status, jqXHR) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});

		request.fail(function(jqXHR, status) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		return false;
	}
	
	//Método para borrar un año
	function deleteYear() {

		var year = $("#yearDelete").val();//cojo el año ke kiero borrar
		request = $.ajax({
			url : "../api/v2/Seville/" + year.toString(),
			type : "DELETE",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		request.fail(function(jqXHR, status) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		return false;

	}
	
	//Método para borrarlo todo
	function deleteTodo() {

		request = $.ajax({
			url : "../api/v2/Seville/",
			type : "DELETE",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		request.fail(function(jqXHR, status) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		return false;

	}

	function drawTable(data) {//le paso los datos ke me ha cogido la petición get 

		var arrayData = [];
		var dataTable = [];
		$.each(data, function(indice, objeto) {//para cada dato(fila) ke me he obtenido en el get,indice es la posicion del array y objeto es el array 
			$.each(objeto, function(nombre, valor) {//nombre:year y valor:2010
				arrayData.push(valor);//meto los valores en un array
			});
			dataTable.push(arrayData);//me mete cada array en la matriz
			arrayData = [];
		});
		tabla.fnAddData(dataTable);//meteme la matriz en la tabla
	}
});