function doGet(url){

    var request = $.ajax({
        url:    url,
        type:   'GET',
        dataType: 'json'
    });
    
    request.done(function(data,status,jqXHR) {
        $("#tbody").empty();
        $.each(data,function(index, value) {
            $("#tbody").append("<tr>"+"<th>"+value.year+"</th>"+"<th>"+value.population+"</th>"+"<th>"+value.unemployed+"</th>"+"<th>"+value.pib+"</th>"+"<th>"+value.emigrants+"</th>"+"<th>"+value.educationBudget+"</th>"+"</tr>");          
        }); 
    });
}
function doGetone(url){
    var request = $.ajax({
        url:    url,
        type:   'GET',
        dataType: 'json'
    });
    request.done(function(data,status,jqXHR) {
         $("#tbody").empty();
         $("#tbody").append("<tr>"+"<th>"+data.year+"</th>"+"<th>"+data.population+"</th>"+"<th>"+data.unemployed+"</th>"+"<th>"+data.pib+"</th>"+"<th>"+data.emigrants+"</th>"+"<th>"+data.educationBudget+"</th>"+"</tr>");

    });
    request.fail(function(){
        new Messi('Se ha producido un error. Intentelo de nuevo.', {title: 'Error', titleClass: 'anim error', buttons: [{id: 0, label: 'Cerrar', val: 'X'}]});
    });

}
function doPost(url,post){

    var request = $.ajax({
        url:    url,
        type:   'POST',
        data:   post
    });
    
    request.done(function(data,status,jqXHR) {
        var statusCode = jqXHR.status;
        new Messi(statusCode+' Objeto creado. Haga click en Consultar Tabla para comprobarlo', {title: 'Creado', titleClass: 'success', buttons: [{id: 0, label: 'Cerrar', val: 'X'}]});
        $("#form").empty();
    });
    request.fail(function(){
        new Messi('Se ha producido un error. Intentelo de nuevo.', {title: 'Error', titleClass: 'anim error', buttons: [{id: 0, label: 'Cerrar', val: 'X'}]});
    });
}
function doPut(url,post){
    var request = $.ajax({
        url:    url,
        type:   'PUT',
        data:   post
    });
    
    request.done(function(data,status,jqXHR) {
        var statusCode = jqXHR.status;
        new Messi(statusCode+' El objeto ha sido modificado correctamente.', {title: 'Modificado', titleClass: 'success', buttons: [{id: 0, label: 'Cerrar', val: 'X'}]});
        $("#form").empty();
    });
    request.fail(function(){
        new Messi('Se ha producido un error. Intentelo de nuevo.', {title: 'Error', titleClass: 'anim error', buttons: [{id: 0, label: 'Cerrar', val: 'X'}]});
    });
}
function doDelete(url){
    var request = $.ajax({
        url:    url,
        type:   'DELETE'
    });
    
    request.done(function(data,status,jqXHR) {
        var statusCode = jqXHR.status;
        new Messi(statusCode+' Eliminado correctamente.', {title: 'Eliminado', titleClass: 'success', buttons: [{id: 0, label: 'Cerrar', val: 'X'}]});
    });
    request.fail(function(){
        new Messi('Se ha producido un error. Intentelo de nuevo.', {title: 'Error', titleClass: 'anim error', buttons: [{id: 0, label: 'Cerrar', val: 'X'}]});
    });

}


$(document).ready(function() {
    //run when the DOM is ready

    var estado="";

    $('#consultarTodo').on('click',function(){
        estado="getAll";
        $("#form").empty();
        $("#form").append($("<br><p id='myP'>Pulsa enviar para resfrescar la tabla.</p>"));
        $("#myP").animate({
        width: 500,
        height: 300,
        opacity: 0.6, 
        fontSize: "3em",
        }, 800 );
        
    });

    $('#consultar').on('click',function(){
        estado="getOne";
        $("#form").empty();

        var year = $('<label>Año</label>'+'<input type="text" class="form-control" id="year" maxlength="5" placeholder="Ex. 1993">'+'<span class="help-block">Introduce aquí el año.</span>');
        $("#form").append(year); 
    });

    $('#crear').on('click',function(){
        estado="post";
        $("#form").empty();

        var year = $('<label>Year</label>'+'<input type="text" class="form-control" id="year" maxlength="5" placeholder="Ex. 1993"><br>');
        var population = $('<label>Population</label>'+'<input type="text" class="form-control" id="population" maxlength="30" placeholder="Ex. 365000"><br>');
        var unemployed = $('<label>Unemployed</label>'+'<input type="text" class="form-control" id="unemployed" maxlength="30" placeholder="Ex. 200000"><br>');
        var pib = $('<label>Pib</label>'+'<input type="text" class="form-control" id="pib" maxlength="5" placeholder="Ex. 2"><br>');
        var emigrants = $('<label>Emigrants</label>'+'<input type="text" class="form-control" id="emigrants" maxlength="30" placeholder="Ex. 10000"><br>');
        var educationBudget = $('<label>EducationBudget</label>'+'<input type="text" class="form-control" id="educationBudget" maxlength="30" placeholder="Ex. 12300000"><br>');
    
        $("#form").append(year);
        $("#form").append(population);
        $("#form").append(unemployed);
        $("#form").append(pib);
        $("#form").append(emigrants);
        $("#form").append(educationBudget);
    });
   
    $('#modificar').on('click',function(){

        estado="put";
        $("#form").empty();

        var year = $('<label>Year</label>'+'<input type="text" class="form-control" id="year" maxlength="5" placeholder="Ex. 1993"><br>');
        var population = $('<label>Population</label>'+'<input type="text" class="form-control" id="population" maxlength="30" placeholder="Ex. 365000"><br>');
        var unemployed = $('<label>Unemployed</label>'+'<input type="text" class="form-control" id="unemployed" maxlength="30" placeholder="Ex. 200000"><br>');
        var pib = $('<label>Pib</label>'+'<input type="text" class="form-control" id="pib" maxlength="5" placeholder="Ex. 2"><br>');
        var emigrants = $('<label>Emigrants</label>'+'<input type="text" class="form-control" id="emigrants" maxlength="30" placeholder="Ex. 10000"><br>');
        var educationBudget = $('<label>EducationBudget</label>'+'<input type="text" class="form-control" id="educationBudget" maxlength="30" placeholder="Ex. 12300000"><br>');
    
        $("#form").append(year);
        $("#form").append(population);
        $("#form").append(unemployed);
        $("#form").append(pib);
        $("#form").append(emigrants);
        $("#form").append(educationBudget);
    });

    $('#borrar').on('click',function(){
        estado="delete";
        $("#form").empty();

        var year = $('<label>Año</label>'+'<input type="text" class="form-control" id="year" maxlength="5" placeholder="Ex. 1993">'+'<span class="help-block">Introduce aquí el año.</span>');
        $("#form").append(year);
    });

    $('#borrartodo').on('click',function(){
        estado="deleteAll"
        $("#form").empty();

        $("#form").append($("<br><p id='myP'>Atención pulse Enviar para eliminar toda la tabla.</p>"));
        $("#myP").animate({
        width: 500,
        height: 300,
        opacity: 0.6, 
        fontSize: "3em",
        }, 800 );
    });
    


    doGet("/api/v1/SpainStd");

    $('#send').on('click',function(){
        switch (estado){

            case "getAll":
                doGet("/api/v1/SpainStd");
                break;

            case "getOne":
                doGetone("/api/v1/SpainStd/"+$("#year").val());
                break;

            case "post":
                var post='{"year":'+$("#year").val()+',"population":' + $("#population").val() +',"unemployed":'+$("#unemployed").val()+',"pib":'+$("#pib").val()+',"emigrants":'+$("#emigrants").val()+',"educationBudget":'+$("#educationBudget").val()+'}';
                doPost('/api/v1/SpainStd',post);
                break;

            case "put":
                var post='{"year":'+$("#year").val()+',"population":' + $("#population").val() +',"unemployed":'+$("#unemployed").val()+',"pib":'+$("#pib").val()+',"emigrants":'+$("#emigrants").val()+',"educationBudget":'+$("#educationBudget").val()+'}';
                doPut('/api/v1/SpainStd/'+$("#year").val(),post);
                break;

            case "delete":
                doDelete('/api/v1/SpainStd/'+$("#year").val());
                break;

            case "deleteAll":
                new Messi('Se va a borrar toda la tabla. ¿Estas seguro?', {title: 'Atención', buttons: [{id: 0, label: 'Si', val: 'Y'}, {id: 1, label: 'No', val: 'N'}], callback: function(val) {
                    if(val=="Y"){
                        doDelete('/api/v1/SpainStd/');
                    }
                }});
        }
    });
    
});