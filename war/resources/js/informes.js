var variabletipoEst;
var variablenombreEst;
var dataset;
var mes;
var mesAnterior;
var year;
var yearAnterior;
var yAxis2; 
var yAxis1;
var dateValues = new Array();
var xAxisNumbers;
var myTicks2;
var metodo='';
var options;
var weekday = new Array();
weekday[0] = "Domingo";
weekday[1] = "Lunes";
weekday[2] = "Martes";
weekday[3] = "Miércoles";
weekday[4] = "Jueves";
weekday[5] = "Viernes";
weekday[6] = "Sábado";
var nombreMes;
var totUsers=0;
var retUsers=0;
var retUsersPreviousMonth=0;
var newUsers=0;
var retUsersPerc=0;
var newUsersPreviousMonth=0;
var newUsersPerc=0;
var usersNoDest=0;
var usersNoDestPreviousMonth=0;
var usersNoDestPerc=0;
var usersTotTowns=0;
var usersTotRegions=0;
var townsUsersPerc=0;
var townsUsersTotalPerc=0;
var regionsUsersPerc=0;
var regionsUsersTotalPerc=0;


$(document).on("pageshow","#informes-page",function() {
	//loading
	var interval = setInterval(function(){
        $.mobile.loading('show');
        clearInterval(interval);
    },1);
	$('#aplicaciones_creadas').listview('refresh');

	var idapp = localStorage.getItem('idAplicacion'); 
	$("#li_" + idapp).closest('ul').find('li').removeClass('ui-bar-b');
	$("#li_" + idapp).addClass('ui-bar-b'); 
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/anios?idaplicacion="+localStorage.getItem('idAplicacion'),
		contentType : "application/json",
		success : function(data) {
			var radio_name;
			var radioString="";
			$.each(data, function (i, val) {
				  $.each(val, function(innerKey, innerValue) {
					  if(innerKey=="years"){
						  radio_name=innerValue;
						  radioString=radioString+'<input type="radio" name="radio-choice" id="radio-choice-'+ radio_name +'" value="'+ radio_name +'" checked="checked" /><label for="radio-choice-'+ radio_name +'">'+ radio_name +'</label>';
						  console.log(radioString);
					  }
				  });
			});
			$('#year_radio').append(radioString);
			$('#year_radio').controlgroup();
			//finalizar loading
			var interval2 = setInterval(function(){
		        $.mobile.loading('hide');
		        clearInterval(interval2);
		    },100);
		},
		error : onLoadError,
		async : true
	});
	
	
 
});


$(document).on('tap', '[action="selectAppInforme"]', function(event) { 
	
	localStorage.setItem("idAplicacion", this.id);
	 
	if (this.id != null) {  
		$.ajax({
			type : "GET",
			url : domain() + "/api/application/"+ localStorage.getItem('idAplicacion'),
			contentType : "application/json",
			success : function(data) { 
			 
				window.location.href=domain()
				+ '/private/informe?nombreaplicacion='
				+ data.nombreaplicacion + "&idaplicacion="
				+ localStorage.getItem('idAplicacion');
	
			},
			error : onLoadError,
			async : false
		}); 
	}  

});


$(document).on('tap','[action="mostrar_report"]',	function(event) {
	
	
	
	var interval = setInterval(function(){
        $.mobile.loading('show');
        clearInterval(interval);
    },1);
	
	
	mes=$(this).attr("mes");
	nombreMes=$(this).attr("id");
	$("#li_"+nombreMes).closest('ul').find('li').removeClass('ui-bar-b');
	$("#li_"+nombreMes).addClass('ui-bar-b'); 
	$('#listado_meses').listview('refresh');
	year=$('input[name="radio-choice"]:checked').val();
	if(mes==1){
		mesAnterior=12;
		yearAnterior=year-1;
	}else{
		mesAnterior=mes-1;
		yearAnterior=year;
	}
	
	launchCalls();	
});

function launchCalls(){
	//obtener los usuarios totales de una aplicación
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/totales/"+localStorage.getItem('idAplicacion'),
		contentType : "application/json",
		success : function(data) {
			
			totUsers=data;

		},
		error : onLoadError,
		async : true
	});
	

	//obtener los usuarios recurrentes de una aplicacion por mes
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/recurrentes?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {

			retUsers=data;

		},
		error : onLoadError,
		async : true
	});
	
	//obtener los usuarios recurrentes del mes pasado
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/recurrentes?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+yearAnterior+"&mes="+mesAnterior,
		contentType : "application/json",
		success : function(data) {

			retUsersPreviousMonth=data;

		},
		error : onLoadError,
		async : true
	});
		
	//obtener cantidad de usuarios nuevos en un mes en concreto
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/nuevos?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {

			newUsers=data;

		},
		error : onLoadError,
		async : true
	});
	
	//obtener cantidad de usuarios nuevos del mes anterior
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/nuevos?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+yearAnterior+"&mes="+mesAnterior,
		contentType : "application/json",
		success : function(data) {

			newUsersPreviousMonth=data;

		},
		error : onLoadError,
		async : true
	});
	
	//obtener la cantidad de usuarios que no entran en el destino
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/usuariosNoEnDestino?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {
			usersNoDest=data;
		},
		error : onLoadError,
		async : true
	});
	
	//obtener la cantidad de usuarios que no entran en el destino en el mes anterior
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/usuariosNoEnDestino?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+yearAnterior+"&mes="+mesAnterior,
		contentType : "application/json",
		success : function(data) {
			usersNoDestPreviousMonth=data;
		},
		error : onLoadError,
		async : true
	});
	
	//obtener usuarios nuevos y recurrentes por mes
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/nuevosyrec?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {
					
			metodo='PuntosUsuariosPorMes';
			xAxisNumbers=daysInMonth(mes, year);
			myTicks2=fillArrays(data, xAxisNumbers,'1');
			fillDataSetMultipleValues(yAxis1, yAxis2, 'Usuarios nuevos','Usuarios recurrentes');
			fillOptions("Días del mes", myTicks2, 1, xAxisNumbers, 'Cantidad de usuarios');
			$.plot($("#estudio_usuarios_nuevos_recurrentes"), dataset, options);
			$("#estudio_usuarios_nuevos_recurrentes").UseTooltip();
			document.getElementById("h2_usuarios_nuevos_recurrentes").style.visibility = "visible";
			hideLoader();
		},
		error : onLoadError,
		async : true
	});
	
	//obtener cantidad de puntos y usuarios por mes
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/puntosUsuariosPorMes?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {
			metodo='PuntosUsuariosPorMes';
			xAxisNumbers=daysInMonth(mes, year);
			myTicks2=fillArrays(data, xAxisNumbers,'1');
						
			fillDataSet(yAxis2,'Cantidad usuarios');
			fillOptions("Días del mes", myTicks2, 1, xAxisNumbers, 'Cantidad usuarios');
			$.plot($("#estudio_temporal_mes_usuarios"), dataset, options);
			$("#estudio_temporal_mes_usuarios").UseTooltip();
			
			fillDataSet(yAxis1,'Cantidad puntos');
			fillOptions("Días del mes", myTicks2, 1, xAxisNumbers, 'Cantidad puntos');
			$.plot($("#estudio_temporal_mes_puntos"), dataset, options);
			$("#estudio_temporal_mes_puntos").UseTooltip();
			
			fillDataSet(yAxis3,'Puntos por usuario');
			fillOptions("Días del mes", myTicks2, 1, xAxisNumbers, 'Puntos por usuario');
			$.plot($("#estudio_temporal_mes"), dataset, options);
			$("#estudio_temporal_mes").UseTooltip();
			
			document.getElementById("h2_dias_del_mes").style.visibility = "visible";
			document.getElementById("p_dias_del_mes").style.visibility = "visible";
		},
		error : onLoadError,
		async : true
	});
	
	//obtener cantidad de puntos y usuarios por semana
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/puntosUsuariosPorSemana?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {
			metodo='PuntosUsuariosPorSemana';
			var myTicks2=fillArrays(data,7,'0');
			
			
			fillDataSet(yAxis2,'Cantidad usuarios');
			fillOptions("Días de la semana", myTicks2, 0, 6, 'Cantidad usuarios');
			$.plot($("#estudio_temporal_semana_usuarios"), dataset, options);
			$("#estudio_temporal_semana_usuarios").UseTooltip();
			
			fillDataSet(yAxis1,'Cantidad puntos');
			fillOptions("Días de la semana", myTicks2, 0, 6, 'Cantidad puntos');
			$.plot($("#estudio_temporal_semana_puntos"), dataset, options);
			$("#estudio_temporal_semana_puntos").UseTooltip();
			
			fillDataSet(yAxis3,'Puntos por usuario');
			fillOptions("Días de la semana", myTicks2, 0, 6, 'Puntos por usuario');
			$.plot($("#estudio_temporal_semana"), dataset, options);
			$("#estudio_temporal_semana").UseTooltip();
			
			
			document.getElementById("h2_dias_semana").style.visibility = "visible";
			document.getElementById("p_dias_semana").style.visibility = "visible";
		},
		error : onLoadError,
		async : true
	});
	
	
	//obtener cantidad de puntos y usuarios por hora
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/puntosUsuariosPorHora?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {
			metodo='PuntosUsuariosPorHora';
			var myTicks2=fillArrays(data,24,'0');
			
			fillDataSet(yAxis2, 'Cantidad usuarios');
			fillOptions("Distribución de horas", myTicks2, 0, 23, 'Cantidad usuarios');
			$.plot($("#estudio_temporal_hora_usuarios"), dataset, options);
			$("#estudio_temporal_hora_usuarios").UseTooltip();
			
			fillDataSet(yAxis1, 'Cantidad puntos');
			fillOptions("Distribución de horas", myTicks2, 0, 23, 'Cantidad puntos');
			$.plot($("#estudio_temporal_hora_puntos"), dataset, options);
			$("#estudio_temporal_hora_puntos").UseTooltip();
			
			fillDataSet(yAxis3,'Puntos por usuario');
			fillOptions("Distribución de horas", myTicks2, 0, 23, 'Puntos por usuario');
			$.plot($("#estudio_temporal_hora"), dataset, options);
			$("#estudio_temporal_hora").UseTooltip();
			
			document.getElementById("h2_horas").style.visibility = "visible";
			document.getElementById("p_horas").style.visibility = "visible";
		},
		error : onLoadError,
		async : true
	});

	//Obtener ranking de descargas por comunidades
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/rankingDescargas?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {
			
			document.getElementById("h2_ranking_descargas").style.visibility = "visible";
			$('#ranking_descargas').empty();
			$('#ranking_descargas').append('<table style="width: 100%;" data-role="table" id="table-downloads" data-mode="columntoggle" class="ui-body-d ui-shadow table-stripe ui-responsive" data-column-btn-theme="b" data-column-btn-text="Columns to display..." data-column-popup-theme="a"><thead><tr class="ui-bar-d"><th data-priority="2">Ranking</th><th  data-priority="1">Comunidad</th><th data-priority="3">Cantidad usuarios</th></thead><tbody"></tbody></table>');
			var comunidad;
			var cant;
			$.each(data, function (i, val) {
				  $.each(val, function(innerKey, innerValue) {
					  if(innerKey=="comunidades"){
						  comunidad=innerValue;
					  }else{
						  if(innerKey=="cantidad"){
							  cant=innerValue;
						  }
					  }
				  });
				  $('#table-downloads').append('<tr><th>'+ (i+1) +'</th><td style="text-align: center; vertical-align: middle;">'+ comunidad +'</td><td>'+cant+'</td></tr>');
			});
		},
		error : onLoadError,
		async : true
	});
	//obtener ranking de municipios
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/rankingMunicipios?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {
			document.getElementById("h2_ranking_municipios").style.visibility = "visible";
			$('#ranking_municipios').empty();
			$('#ranking_municipios').append('<table style="width: 100%;" data-role="table" id="table-towns" data-mode="columntoggle" class="ui-body-d ui-shadow table-stripe ui-responsive" data-column-btn-theme="b" data-column-btn-text="Columns to display..." data-column-popup-theme="a"><thead><tr class="ui-bar-d"><th data-priority="2">Ranking</th><th  data-priority="1">Comunidad</th><th data-priority="3">Cantidad usuarios</th><th data-priority="4">Porcentaje respecto al total</th><th data-priority="5">Porcentaje periodo anterior</th></thead><tbody"></tbody></table>');
			//Cantital total de usuarios para un mes determinado
			$.ajax({
				type : "GET",
				url : domain() + "/api/usuario/usuariosTotMunicipios?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
				contentType : "application/json",
				success : function(data) {
					usersTotTowns=data;
				},
				error : onLoadError,
				async : false
			});
			var idmunicipio;
			var cantMunicipio;
			var nombreMunicipio;
			var cantMesAnteriorMuni;
			$.each(data, function (i, val) {
				  $.each(val, function(innerKey, innerValue) {
					  if(innerKey=="idmunicipio"){
						  idmunicipio=innerValue;
						  //obtener cantidad de usuarios de ese municipio en el mes anterior
						  $.ajax({
								type : "GET",
								url : domain() + "/api/usuario/unMunicipio?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+yearAnterior+"&mes="+mesAnterior+"&idmunicipio="+idmunicipio,
								contentType : "application/json",
								success : function(data) {
									cantMesAnteriorMuni=data;
								},
								error : onLoadError,
								async : false
							});

					  }else{
						  if(innerKey=="municipio"){
							  nombreMunicipio=innerValue;
						  }else{
							  if(innerKey=="cantidad"){
								  cantMunicipio=innerValue;
							  }
						  }
					  }
				  });
				  //se calculan los porcentajes
				  //se calculan los porcentajes
				  if (usersTotTowns!=0){
					  townsUsersTotalPerc= ((cantMunicipio*100)/usersTotTowns);
					  townsUsersTotalPerc=townsUsersTotalPerc.toFixed(2);
				  }else{
					  townsUsersTotalPerc="-";
				  }
				  if (cantMesAnteriorMuni!=0){
					  townsUsersPerc= ((cantMunicipio*100)/cantMesAnteriorMuni)-100;
					  townsUsersPerc=townsUsersPerc.toFixed(2);
				  }else{
					  //si es 0 es que no hay datos en el mes anterior por tanto se considera -
					  townsUsersPerc="-";
				  }
				//si es mayor que cero será positivo, de color verde
				  if (townsUsersPerc>0){
					  $('#table-towns').append('<tr><th>'+ (i+1) +'</th><td style="text-align: center; vertical-align: middle;">'+ nombreMunicipio +'</td><td>'+cantMunicipio+'</td><td>'+townsUsersTotalPerc+'%</td><td style="color:#04B404;">+'+townsUsersPerc+'%</td></tr>');
				  }else{//color rojo
					  $('#table-towns').append('<tr><th>'+ (i+1) +'</th><td style="text-align: center; vertical-align: middle;">'+ nombreMunicipio +'</td><td>'+cantMunicipio+'</td><td>'+townsUsersTotalPerc+'%</td><td style="color:#FF0000;">'+townsUsersPerc+'%</td></tr>');
				  }
				  
			});
		},
		error : onLoadError,
		async : true
	});
	//obtener ranking de comunidades
	$.ajax({
		type : "GET",
		url : domain() + "/api/usuario/rankingComunidades?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
		contentType : "application/json",
		success : function(data) {
			document.getElementById("h2_ranking_comunidades").style.visibility = "visible";
			 $('#ranking_comunidades').empty();
			$('#ranking_comunidades').append('<table style="width: 100%;" data-role="table" id="table-regions" data-mode="columntoggle" class="ui-body-d ui-shadow table-stripe ui-responsive" data-column-btn-theme="b" data-column-btn-text="Columns to display..." data-column-popup-theme="a"><thead><tr class="ui-bar-d"><th data-priority="2">Ranking</th><th  data-priority="1">Comunidad</th><th data-priority="3">Cantidad usuarios</th><th data-priority="4">Porcentaje respecto al total</th><th data-priority="5">Porcentaje periodo anterior</th></thead><tbody"></tbody></table>');
			//Cantital total de usuarios para un mes determinado
			$.ajax({
				type : "GET",
				url : domain() + "/api/usuario/usuariosTotComunidades?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+year+"&mes="+mes,
				contentType : "application/json",
				success : function(data) {
					usersTotRegions=data;
				},
				error : onLoadError,
				async : false
			});
			var idComunidad;
			var cantComunidad=0;
			var nombreComunidad;
			var cantMesAnterior=0;
			$.each(data, function (i, val) {
				  $.each(val, function(innerKey, innerValue) {
					  if(innerKey=="idcomunidad"){
						  idComunidad=innerValue;
						  //obtener cantidad de usuarios de ese municipio en el mes anterior
						  $.ajax({
								type : "GET",
								url : domain() + "/api/usuario/unaComunidad?idaplicacion="+localStorage.getItem('idAplicacion')+"&anio="+yearAnterior+"&mes="+mesAnterior+"&idcomunidad="+idComunidad,
								contentType : "application/json",
								success : function(data) {
									cantMesAnterior=data;
								},
								error : onLoadError,
								async : false
							});

					  }else{
						  if(innerKey=="comunidad"){
							  nombreComunidad=innerValue;
						  }else{
							  if(innerKey=="cantidad"){
								  cantComunidad=innerValue;
							  }
						  }
					  }
				  });
				//se calculan los porcentajes
				  if (usersTotRegions!=0){
					  regionsUsersTotalPerc= ((cantComunidad*100)/usersTotRegions);
					  regionsUsersTotalPerc=regionsUsersTotalPerc.toFixed(2);
				  }else{
					  townsUsersTotalPerc="-";
				  }
				  if (cantMesAnterior!=0){
					  regionsUsersPerc= ((cantComunidad*100)/cantMesAnterior)-100;
					  regionsUsersPerc=regionsUsersPerc.toFixed(2);
				  }else{
					//si es 0 es que no hay datos en el mes anterior por tanto se considera -
					  regionsUsersPerc="-";
				  }
				  //si es mayor que cero será positivo, de color verde
				  if (regionsUsersPerc>0){
					  $('#table-regions').append('<tr><th>'+ (i+1) +'</th><td style="text-align: center; vertical-align: middle;">'+ nombreComunidad +'</td><td>'+cantComunidad+'</td><td>'+regionsUsersTotalPerc+'%</td><td style="color:#04B404;">+'+regionsUsersPerc+'%</td></tr>');
				  }else{//color rojo
					  $('#table-regions').append('<tr><th>'+ (i+1) +'</th><td style="text-align: center; vertical-align: middle;">'+ nombreComunidad +'</td><td>'+cantComunidad+'</td><td>'+regionsUsersTotalPerc+'%</td><td style="color:#FF0000;">'+regionsUsersPerc+'%</td></tr>');
				  }
				  
			});
			showReport();
			
			
		},
		error : onLoadError,
		async : true
	});
}
function showReport(){

	
	//calcular el porcentaje de usuarios recurrentes respecto al mes anterior
	if (retUsersPreviousMonth!=0){
		
		retUsersPerc= ((retUsers*100)/retUsersPreviousMonth)-100;
		retUsersPerc=retUsersPerc.toFixed(2);
	}else{
		retUsersPerc="-";
	}
	//calcular el porcetanje de nuevos usuarios respecto al mes anterior
	if (newUsersPreviousMonth!=0){
		
		newUsersPerc= ((newUsers*100)/newUsersPreviousMonth)-100;
		newUsersPerc=newUsersPerc.toFixed(2);

	}else{
		newUsersPerc="-";
	}
	//calcular el porcentaje de usuarios que no llegan al destino
	if (usersNoDestPreviousMonth!=0){
		
		usersNoDestPerc= ((usersNoDest*100)/usersNoDestPreviousMonth)-100;
		usersNoDestPerc=usersNoDestPerc.toFixed(2);
		
	}else{
		usersNoDestPerc="-";
	}

	document.getElementById('informe_periodico').innerHTML = 'INFORME DE '+nombreMes;
	document.getElementById("tabla_resumen").style.visibility = "visible";
	document.getElementById('usuarios_tot').innerHTML='<h2>Visitantes en la plataforma<h2><p>Número de usuarios que han descargado la app</p><p style="font-size: 2em;">'+ totUsers +'</p>';
	
	
	if(retUsersPerc>0){//porcentaje en verde
		document.getElementById('usuarios_rec').innerHTML='<h2>Visitantes recurrentes<h2><p>Usuarios que han vuelto al destino</p><p style="font-size: 2em;">'+retUsers+' <span style="color:#04B404;">(+'+retUsersPerc+'%)</span></p>';
	}else{//porcentaje en rojo
		document.getElementById('usuarios_rec').innerHTML='<h2>Visitantes recurrentes<h2><p>Usuarios que han vuelto al destino</p><p style="font-size: 2em;">'+retUsers+' <span style="color:#FF0000;">('+retUsersPerc+'%)</span></p>';
	}
	if(newUsersPerc>0){//porcentaje en verde
		document.getElementById('visitantes_nuevos').innerHTML='<h2>Visitantes nuevo en el periodo<h2><p style="font-size: 2em;">'+newUsers+' <span style="color:#04B404;">(+'+newUsersPerc+'%)</span></p>';
	}else{//porcentaje en rojo
		document.getElementById('visitantes_nuevos').innerHTML='<h2>Visitantes nuevo en el periodo<h2><p style="font-size: 2em;">'+newUsers+' <span style="color:#FF0000;">('+newUsersPerc+'%)</span></p>';
	}
	if(usersNoDestPerc>0){//porcentaje en verde
		document.getElementById('visitantes_no_destino').innerHTML='<h2>Visitantes que no llegan al destino<h2><p>Usuarios que se descargan la app en origen pero no llegan al destino</p><p style="font-size: 2em;">'+usersNoDest+' <span style="color:#04B404;">(+'+usersNoDestPerc+'%)</span></p>';
	}else{//porcentaje en rojo
		document.getElementById('visitantes_no_destino').innerHTML='<h2>Visitantes que no llegan al destino<h2><p>Usuarios que se descargan la app en origen pero no llegan al destino</p><p style="font-size: 2em;">'+usersNoDest+' <span style="color:#FF0000;">('+usersNoDestPerc+'%)</span></p>';
	}
	
	
}

//Función que separa los datos en dos arrays, puntos y usuarios
function fillArrays(data, xAxisNumbers, startNumber){
	
	yAxis1 = new Array();
	yAxis2 = new Array();
	yAxis3 = new Array();
	var filtroValue="";
	dateValues = new Array();
	var myTicks=[];
	//se rellenan los arrays con valores 0 
	//porque puede haber días del mes, días de la semana, horas que no tenga datos
	if(startNumber=='1'){
		for (var i = 0; i < xAxisNumbers; i++){
			yAxis1[i]=new Array();
			yAxis1[i][0]=i+1+"";
			yAxis1[i][1]="0";
			
			yAxis2[i]=new Array();
			yAxis2[i][0]=i+1+"";
			yAxis2[i][1]="0";
			
			yAxis3[i]=new Array();
			yAxis3[i][0]=i+1+"";
			yAxis3[i][1]="0";
			
			dateValues[i]=i+1+"";
			
		}
	}else{
		for (var i = 0; i < xAxisNumbers; i++){
			yAxis1[i]=new Array();
			yAxis1[i][0]=i+"";
			yAxis1[i][1]="0";
			
			yAxis2[i]=new Array();
			yAxis2[i][0]=i+"";
			yAxis2[i][1]="0";
			
			yAxis3[i]=new Array();
			yAxis3[i][0]=i+"";
			yAxis3[i][1]="0";
			
			dateValues[i]=i+"";
		}
	}
	
	//para los días del mes, porque el mes empieza por 1 no por 0
	if(startNumber=='1'){
		$.each(data, function (i, val) {
			  $.each(val, function(innerKey, innerValue) {
				  if(innerKey=="filtro"){
					  filtroValue=innerValue;
				  }
				  else if(innerKey=="puntos" || innerKey=="newusers"){
					  yAxis1[filtroValue-1]=new Array();
					  yAxis1[filtroValue-1][0]=filtroValue;
					  yAxis1[filtroValue-1][1]=innerValue;
				  }else if (innerKey=="usuarios" || innerKey=="retusers"){
					  yAxis2[filtroValue-1]=new Array();
					  yAxis2[filtroValue-1][0]=filtroValue;
					  yAxis2[filtroValue-1][1]=innerValue;
				  }else  if (innerKey=="ratio"){
					  yAxis3[filtroValue-1]=new Array();
					  yAxis3[filtroValue-1][0]=filtroValue;
					  yAxis3[filtroValue-1][1]=innerValue.toFixed(2);
				  }
				  
			  });
			  
		});
	}//para los dias de la semana y horas, porque pueden empezar con un valor 0
	else{
		$.each(data, function (i, val) {
			  $.each(val, function(innerKey, innerValue) {
				  if(innerKey=="filtro"){
					  filtroValue=innerValue;
				  }
				  else if(innerKey=="puntos"){
					  yAxis1[filtroValue]=new Array();
					  yAxis1[filtroValue][0]=filtroValue;
					  yAxis1[filtroValue][1]=innerValue;
				  }else if (innerKey=="usuarios"){
					  yAxis2[filtroValue]=new Array();
					  yAxis2[filtroValue][0]=filtroValue;
					  yAxis2[filtroValue][1]=innerValue;
				  }else  if (innerKey=="ratio"){
					  yAxis3[filtroValue]=new Array();
					  yAxis3[filtroValue][0]=filtroValue;
					  yAxis3[filtroValue][1]=innerValue.toFixed(2);
				  }
				  
			  });
			  
		});	
	}
	
	if (metodo=='PuntosUsuariosPorMes'){
		
		for (var i = 0; i < dateValues.length; i++)
		{
			myTicks.push( [ i+1, dateValues[i] ] ); 
		}
		
	} else {
		if(metodo=='PuntosUsuariosPorSemana'){
			for (var i = 0; i < dateValues.length; i++)
			{
				//weekday me da un número por cada día de la semana
				var n = weekday[dateValues[i]];
				myTicks.push( [ i, n ] ); 
			}
			
		}else{
			for (var i = 0; i < dateValues.length; i++)
			{
				myTicks.push( [ i, dateValues[i] ] ); 
			}
		}
	}
	return myTicks;
}
//arrays con nuestros datos
//hay que asignarlos a los ejes
function fillDataSet(data1, yLabel1){
	
	if(yLabel1=="Cantidad usuarios"){
		dataset = [
		           {
		               label: yLabel1,
		               data: data1,
		               yaxis: 1,
		               color: "#0062FF",
		               points: { symbol: "circle", fillColor: "#0062FF", show: true },
		               lines: { show: true }
		           }
		       ];
	}else{
		if(yLabel1=="Cantidad puntos"){
			dataset = [
			           {
			               label: yLabel1,
			               data: data1,
			               yaxis: 1,
			               color: "#FF0000",
			               points: { symbol: "circle", fillColor: "#FF0000", show: true },
			               lines: { show: true }
			           }
			       ];
		}else{
			dataset = [
			           {
			               label: yLabel1,
			               data: data1,
			               yaxis: 1,
			               color: "#088A08",
			               points: { symbol: "circle", fillColor: "#088A08", show: true },
			               lines: { show: true }
			           }
			       ];
		}
		
	}
	
	
}
//Para la gráfica de los usuarios nuevos y recurrentes, ya que hay que comparar los dos datos en la misma grafica
function fillDataSetMultipleValues(data1, data2, yLabel1, yLabel2){
	dataset = [
	           {
	               label: yLabel1,
	               data: data1,
	               yaxis: 1,
	               color: "#FF0000",
	               points: { symbol: "circle", fillColor: "#FF0000", show: true },
	               lines: { show: true }
	           }, {
	               label: yLabel2,
	               data: data2,
	               yaxis: 1,
	               color: "#0062FF",
	               points: { symbol: "triangle", fillColor: "#0062FF", show: true },
	               lines: {show:true, fill:true}
	           }
	       ];
	
}
//escala de los ejes y formato
function fillOptions(xLabel, myTicks,minXAxisNumbers,maxXAxisNumbers,yLabel1){
	options = {
		    xaxis: {
		        mode: "time",
	            timeFormat: "%d",
	            ticks: myTicks,
	            min:minXAxisNumbers,
	            max:maxXAxisNumbers,
				color: "black",        
			    axisLabel: xLabel,
			    axisLabelUseCanvas: true,
			    axisLabelFontSizePixels: 12,
			    axisLabelFontFamily: 'Verdana, Arial',
			    axisLabelPadding: 10
		    },
		    yaxes: [{
		        position: "left",
		        color: "black",
		        min:0,
		        tickDecimals: 0,
		        axisLabel: yLabel1,
		        axisLabelUseCanvas: true,
		        axisLabelFontSizePixels: 12,
		        axisLabelFontFamily: 'Verdana, Arial',
		        axisLabelPadding: 3
		    }
		    ],
			grid: {
		        hoverable: true,
		        borderWidth: 3,
		        mouseActiveRadius: 50,
		        backgroundColor: { colors: ["#ffffff", "#EDF5FF"] },
		        axisMargin: 20
		    }
		};
}

//Para mostrar los labels de cada valor de la gráfica
var previousPoint = null, previousLabel = null;

$.fn.UseTooltip = function () {
  $(this).bind("plothover", function (event, pos, item) {
      if (item) {
          if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
              previousPoint = item.dataIndex;
              previousLabel = item.series.label;
              $("#tooltip").remove();
              
              var x = item.datapoint[0];
              var y = item.datapoint[1];

              var color = item.series.color;

              showTooltip(item.pageX, item.pageY, color,
                          "<strong>" + item.series.label + "</strong>"  +
                          " : <strong>" + y + "</strong> ");
          }
      } else {
          $("#tooltip").remove();
          previousPoint = null;
      }
  });
};
//para dar formato a la gráfica
function showTooltip(x, y, color, contents) {
	$('<div id="tooltip" >' + contents + '</div>').css({
      position: 'absolute',
      display: 'none',
      top: y - 40,
      left: x - 120,
      border: '2px solid ' + color,
      padding: '3px',
      'font-size': '9px',
      'border-radius': '5px',
      'background-color': '#fff',
      'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
      opacity: 0.9
  }).appendTo("body").fadeIn(200);

}
//para obtener el número de días de cada mes
function daysInMonth(month,year) {
	return new Date(year, month, 0).getDate();
}










function showLoader() {
$.mobile.loading( 'show', {
   text: 'CARGAAANDOOO...',
   textVisible: true,
   theme: 'b',
   html: ""
  });

};

function hideLoader() {
	var interval2 = setInterval(function(){
        $.mobile.loading('hide');
        clearInterval(interval2);
    },100);
};