var variabletipoEst;
var variablenombreEst;

$(document).on("pageshow","#informe-page",function() {
	 
	$('#aplicaciones_creadas').listview('refresh');
	
	var idapp = localStorage.getItem('idAplicacion'); 
	
	$("#li_" + idapp).closest('ul').find('li').removeClass('ui-bar-b');
	$("#li_" + idapp).addClass('ui-bar-b'); 
	$('#aplicaciones_creadas').listview('refresh');
 
	$.ajax({type : "POST",
		url : domain()+ "/api/valores/muestra?idaplicacion="+ localStorage.getItem('idAplicacion')+"&fecha_desde="+"&fecha_hasta=",
			  contentType : "application/json",
			  success : function(data) 
			  {
				  drawChartMuestra(data);
			  }});

});


//esto salta cuando se pincha en una aplicacion
$(document).on('tap', '[action="selectAppReport"]', function(event) { 
	
	localStorage.setItem("idAplicacion", this.id);
	 
	if (this.id != null) {  
		$.ajax({
			type : "GET",
			url : domain() + "/api/application/"+ localStorage.getItem('idAplicacion'),
			contentType : "application/json",
			success : function(data) { 
			 
				window.location.href=domain()
				+ '/private/report?nombreaplicacion='
				+ data.nombreaplicacion + "&idaplicacion="
				+ localStorage.getItem('idAplicacion');
	
			},
			error : onLoadError,
			async : false
		}); 
	}  

});


$(document).on("tap","#mostrar_mapa",function() {
	if(localStorage.getItem('desdeDashboard')==1){
		cambio=0; //para cargar si se accede al mapa
		cambiovista=0; 
		if (localStorage.getItem('idAplicacion') != null) { 
			$.ajax({
				type : "GET",
				url : domain() + "/api/application/"+ localStorage.getItem('idAplicacion'),
				contentType : "application/json",
				success : function(data) {

					$.mobile.changePage(domain()
							+ '/private/mapa?nombreaplicacion='
							+ data.nombreaplicacion + "&idaplicacion="
							+ localStorage.getItem('idAplicacion'), {reloadPage : true});

				},
				error : onLoadError,
				async : false
			});
		}
	}else{
		history.back();
	}
	 
});


$(document).on('tap','[action="consultar-estadisticas"]',	function(event) {

	 var interval = setInterval(function(){
	        $.mobile.loading('show');
	        clearInterval(interval);
	    },1);
 
	
	var fecha_desde_est = $("#fecha_desde_est").val();
	var fecha_hasta_est = $("#fecha_hasta_est").val();

	if (fecha_desde_est == "" || fecha_hasta_est == "") {
		$('#filtro-estadisticas-error').html('<font color="#FF0000">Fechas obligatorio</font>');
	} else {
		var f_desde_est=esFecha(fecha_desde_est);
		var f_hasta_est=esFecha(fecha_hasta_est);
		
		if (f_desde_est==true && f_hasta_est==true){
			
			var desde = Date.parse(fecha_desde_est);
			var hasta = Date.parse(fecha_hasta_est);
			if (desde > hasta) {
				$('#filtro-estadisticas-error').html('<font color="#FF0000">Fecha desde tiene que ser menor que hasta</font>');
			}
			else {
				$('#filtro-estadisticas-error').html('');
				//creo el listado de variables que quiero consultar
				//Esto se hace para saber que variable ha elegido
			 
				$(".radio").each(function(index) 
				{
					if ($(this).is(":checked")) 
					{
						var val = $(this).val();
						
						//conseguimos el nombre y el tipo de variable
						variablenombreEst = $(this).attr('namevar');
						variabletipoEst = $(this).attr('tipovar');
						localStorage.setItem('idVariableEst', val);

						if (variabletipoEst=="tipo_muestras"){
							$.ajax({type : "POST",
								url : domain()+ "/api/valores/muestra?idaplicacion="+ localStorage.getItem('idAplicacion') + 
																	"&fecha_desde='" + fecha_desde_est
																 + "'&fecha_hasta='"+ fecha_hasta_est+"'",
									  contentType : "application/json",
									  async:true,
									  success : function(data) 
									  {
										
										  
										  drawChartMuestra(data);
											 var interval2 = setInterval(function(){
											        $.mobile.loading('hide');
											        clearInterval(interval2);
											    },100);
									  }});
						}else{
							$.ajax({type : "POST",
								url : domain()+ "/api/valores/valores?idvariable="+ val
											  + "&fecha_desde='" + fecha_desde_est
											  + "'&fecha_hasta='"+ fecha_hasta_est
											  + "'&idaplicacion="+ localStorage.getItem('idAplicacion')
											  + "&tipo="+ variabletipoEst,
									  contentType : "application/json",
									  async:true,
									  success : function(data) {drawChart(data); 
									  
									  var interval2 = setInterval(function(){
									        $.mobile.loading('hide');
									        clearInterval(interval2);
									    },100);  
									  }}); 
						}
						
					}
				});
			}
		}else{
			$('#filtro-estadisticas-error').html('<font color="#FF0000">Fecha no válida</font>');
			 var interval2 = setInterval(function(){
			        $.mobile.loading('hide');
			        clearInterval(interval2);
			    },200);
		}
		
	}
	
	
	
	
	
	
	
});

function drawChartMuestra (lista)
{
	var data;
	var dataFecha = new google.visualization.DataTable();
	dataFecha.addColumn('date', 'Date');
	dataFecha.addColumn('number', 'Numero personas');
	dataFecha.addColumn('number', 'Media');
	var cantidadMedia=0;
	$.each(lista, function(indice, muestras) 
	{
		cantidadMedia=cantidadMedia + muestras.cantidad;
	});
	
	var media = cantidadMedia/lista.length;
	
	$.each(lista, function(indice, muestras) 
	{
		
		dataFecha.addRow([new Date (muestras.fecha.toString()),muestras.cantidad, media]);
		
	});

	var options = {title : 'Variable: ' + variablenombreEst};
	var chart;
	chart = new google.visualization.AnnotatedTimeLine(document.getElementById('informe'));
	
	chart.draw(dataFecha, options);

}


function drawChart(lista) {

	var data;
	if ((variabletipoEst == '3'))
	{
		
		var dataFecha = new google.visualization.DataTable();
		dataFecha.addColumn('date', 'Date');
		dataFecha.addColumn('number', 'Numero personas');
		
		$.each(lista, function(indice, valores) 
		{ 
			dataFecha.addRow([new Date (valores.valorvariable.toString()),valores.cantidad]);

		}); 
	}	
	else
	{
		arrayVariable = new Array([ variablenombreEst.toString(), 'Número de personas' ]);
		$.each(lista, function(indice, valores) 
		{
			var arrayNewData = new Array(valores.valorvariable.toString(),valores.cantidad);
			arrayVariable.push(arrayNewData);

		});
		 data = google.visualization.arrayToDataTable(arrayVariable);
		
	}
	
	var options = {title : 'Variable: ' + variablenombreEst};
	var chart;
	
	//chequeamos de que tipo tiene que ser el grafico. Barras entero y decimal, tarta string, lineal fecha.
	if ((variabletipoEst == '1') || (variabletipoEst == '4')) {
		chart = new google.visualization.ColumnChart(document.getElementById('informe'));
	} else {
		
		if ((variabletipoEst == '2')) {
			chart = new google.visualization.PieChart(document.getElementById('informe'));
		} else {
			chart = new google.visualization.AnnotatedTimeLine(document.getElementById('informe'));
		}
	}
	//pintamos el grafico
	if ((variabletipoEst == '3'))
		chart.draw(dataFecha, options);
	else
		chart.draw(data, options);
}
