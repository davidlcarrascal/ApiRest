$(document).ready(function() {
	var tabla = $('#tabla').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"aoColumns" : [ {
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

	get();
	$("#formularioBuscar").on('submit', buscar);
	$("#formularioCrear").on('submit', post);
	$("#formularioModificar").on('submit', put);
	$("#formularioBorrar").on('submit', deleteYear);
	$("#formularioBorrarTodo").on('submit', deleteTodo);
	
	$("#li_crear").on('click', function(){
		$('#div_buscar').css("display", "none");
		$('#div_modificar').css("display", "none");
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
			url : "/api/v1/Seville/",
			type : "GET",
			dataType : "json",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {

			tabla.fnClearTable();// borro la tabla completa
			drawTable(data);// dibujo la tabla
		});
		request.fail(function(jqXHR, status) {
			$("#resultado").text("");
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	}
	
	//get de un año
	function buscar() {
		var year = $("#yearBuscar").val();
		request = $.ajax({
			url : "/api/v1/Seville/" + year.toString(),
			type : "GET",
			dataType : "json",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {

			tabla.fnClearTable();// borro la tabla completa
			drawTable(data);// dibujo la tabla
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		request.fail(function(jqXHR, status) {
			$("#resultado").text("");
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	}

	function post() {

		var year = $("#year").val();
		var population = $("#population").val();
		var unemployed = $("#unemployed").val();
		var educationBudget = $("#educationBudget").val();
		var migrants = $("#migrants").val();
		var pib = $("#pib").val();

		var seville = new Object();
		seville.year = new Number();
		seville.population = new Number();
		seville.unemployed = new Number();
		seville.educationBudget = new Number();
		seville.migrants = new Number();
		seville.pib = new Number();

		seville.year = parseInt(year);
		seville.population = parseInt(population);
		seville.unemployed = parseInt(unemployed);
		seville.educationBudget = parseFloat(educationBudget);
		seville.migrants = parseInt(migrants);
		seville.pib = parseFloat(pib);

		var json = JSON.stringify(seville);
		var request = $.ajax({
			url : "/api/v1/Seville/",
			type : "POST",
			dataType : "json",
			contentType : "application/json",
			data : json
		});
		request.done(function(data, status, jqXHR) {
			//setTimeout(get, 3000);
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});

		request.fail(function(jqXHR, status) {
			$("#resultado").text("");
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	}
	
	function put() {

		var year = $("#yearPut").val();
		var population = $("#populationPut").val();
		var unemployed = $("#unemployedPut").val();
		var educationBudget = $("#educationBudgetPut").val();
		var migrants = $("#migrantsPut").val();
		var pib = $("#pibPut").val();

		var seville = new Object();
		seville.year = new Number();
		seville.population = new Number();
		seville.unemployed = new Number();
		seville.educationBudget = new Number();
		seville.migrants = new Number();
		seville.pib = new Number();

		seville.year = parseInt(year);
		seville.population = parseInt(population);
		seville.unemployed = parseInt(unemployed);
		seville.educationBudget = parseFloat(educationBudget);
		seville.migrants = parseInt(migrants);
		seville.pib = parseFloat(pib);

		var json = JSON.stringify(seville);
		var request = $.ajax({
			url : "/api/v1/Seville/" + year.toString(),
			type : "PUT",
			dataType : "json",
			contentType : "application/json",
			data : json
		});
		request.done(function(data, status, jqXHR) {
			//setTimeout(get, 5000);
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});

		request.fail(function(jqXHR, status) {

			$("#resultado").text("");
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
	}
	
	//Método para borrar un año
	function deleteYear() {

		var year = $("#yearDelete").val();
		request = $.ajax({
			url : "../api/v1/Seville/" + year.toString(),
			type : "DELETE",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {
			//setTimeout(get, 1000);
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		request.fail(function(jqXHR, status) {
			$("#resultado").text("");
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
			//setTimeout(get, 1000);
		return false;

	}
	
	//Método para borrarlo todo
	function deleteTodo() {

		var year = $("#yearDelete").val();
		request = $.ajax({
			url : "../api/v1/Seville/",
			type : "DELETE",
			contentType : "application/json"
		});
		request.done(function(data, status, jqXHR) {
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		request.fail(function(jqXHR, status) {
			$("#resultado").text("");
			$("#errores").text(jqXHR.status + " " + jqXHR.statusText);
		});
		//setTimeout(get, 6000);
		return false;

	}

	function drawTable(data) {

		var arrayData = [];
		var dataTable = [];
		$.each(data, function(indice, objeto) {
			$.each(objeto, function(nombre, valor) {
				arrayData.push(valor);
			});
			dataTable.push(arrayData);
			arrayData = [];
		});
		tabla.fnAddData(dataTable);
	}
});