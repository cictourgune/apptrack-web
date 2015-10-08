$(document).on("pageshow", "#dashboard-page", function() {
	 
	$('#aplicaciones_creadas').listview('refresh');
	
			//cargar datos de app y marcarla
			
			var idapp = localStorage.getItem("idAplicacion");
			
			$("#li_" + idapp).closest('ul').find('li').removeClass('ui-bar-b');
			$("#li_" + idapp).addClass('ui-bar-b');
			$('#aplicaciones_creadas').listview('refresh');
			if (idapp != null && idapp != undefined) {
				cargarDatosAplicacion();
				setListaVariables(idapp);
				cargarDatosVisores();
				$('#tipovar').selectmenu('enable');	
				$("#mostrar_mapa_dashboard").removeClass('ui-disabled');
				$("#mostrar_informe").removeClass('ui-disabled');
				$("#eliminar_app").removeClass('ui-disabled');
				$("#duplicar").removeClass('ui-disabled');
				$("#nuevavar").removeClass('ui-disabled');
				$("#newVisor").removeClass('ui-disabled');
				$('#tipovar').selectmenu('enable');	
			}
			
			if (idapp == null && idapp == undefined)
			{
				$('#tipovar').selectmenu('disable');	
			}

			if (localStorage.getItem('nombreVariable')) {
				setListaVariables(localStorage.getItem('idAplicacion'));
			}
	
});


$(document).on("click", "#salir", function(){
	window.location.href=domain()+'/j_spring_security_logout';
});

$(document).on(
		'tap',
		'[action="informeDashboard_2"]',
		function(event) {
			window.location.href=domain()
			+ '/private/informes';

		});


$(document).on(
		'tap',
		'[action="informeDashboard"]',
		function(event) {
			//localStorage.setItem('desdeDashboard', 1); //para que al acceder desde el informe al mapa, se recargue
			if (localStorage.getItem('idAplicacion') != null) {
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

$(document).on(
		'tap',
		'[action="mapaDashboard"]',
		function(event) { 
			if (localStorage.getItem('idAplicacion') != null) {  
				$.ajax({
					type : "GET",
					url : domain() + "/api/application/"+ localStorage.getItem('idAplicacion'),
					contentType : "application/json",
					success : function(data) { 
					 
						window.location.href=domain()
						+ '/private/mapa?nombreaplicacion='
						+ data.nombreaplicacion + "&idaplicacion="
						+ localStorage.getItem('idAplicacion');

					},
					error : onLoadError,
					async : false
				});
			}

});


$(document).on(
		'tap',
		'#confirmacion-eliminacion-ok',
		function(event) { 
			var resultado = "";
			$.ajax({
						type : "DELETE",
						url : domain() + "/api/application/"+ localStorage.getItem('idAplicacion'),
						cache : false,
						success : function(data) 
						{
							resultado = 1;
						},
						error : resultado = -1,
						async : false
					});
			if (resultado == 1) {
				$('#li_'+ localStorage.getItem('idAplicacion')).remove();
				$(".livariable").remove();
				$("#id_nombre_aplicacion").val("");
				$("#id_descripcion_aplicacion").val("");
				$("#id_token_aplicacion").val("");

				localStorage.removeItem("idAplicacion"); 
			}

});




$(document).on('tap', '[action="delete-var"]', function(event) {
 
	var deleteid = localStorage.getItem("idDeleteVar"); 

	var resultado = "";
	$.ajax({
		type : "DELETE",
		url : domain() + "/api/variable/" + deleteid,
		cache : false,
		success : function(data) {resultado = 1;},
		error : resultado = -1,
		async : false
	});

	if (resultado == 1)
		$('#listitem' + deleteid).remove();

});

$(document).on(	'tap','[action="edit-var"]',function(event) 
{

	var identificador = this.id;
	
	$.ajax({type : "GET",
			url : domain() + "/api/variable/" + identificador.substring(4,identificador.length),
			contentType : "application/json",
			success : function(data) 
			{
					var idapp = localStorage.getItem('idAplicacion');
					localStorage.setItem('editar-var', 'true');
	
					guardarEstadoVariables(data);
	
					if (data.idtipo == '1') 
					{
						$.mobile.changePage(domain() + "/private/variable/int?idaplicacion=" + idapp, {reloadPage : true});
	
					} else {
						if (data.idtipo == '2') {
							// hay que conseguir todas las
							// opciones
							$.ajax({type : "GET",
									url : domain() + "/api/variable/opciones?idVariable=" + identificador.substring(4,identificador.length),
									contentType : "application/json",
									success : function(data) {
											var lista = '';
											$.each(data,function(indice,opcion) 
											{
												lista = lista + opcion.nombreopcion + ";";
	
											});
											localStorage.setItem("listaOpciones",lista);
											$.mobile.changePage(domain() + "/private/variable/option?idaplicacion=" + idapp,
															{
																reloadPage : true
															});
										},
										error : onLoadError,
										async : false
									});
						} else {
							if (data.idtipo == '3') {
								$.mobile.changePage(domain() + "/private/variable/date?idaplicacion=" + idapp,
												{
													reloadPage : true
												});
							} else {
								$.mobile.changePage( domain()+ "/private/variable/decimal?idaplicacion="+ idapp,
												{
													reloadPage : true
												});
							}
	
						}
					}
	
				},
				error : onLoadError,
				async : false
			});

				});

function onLoadError(data, status) {
	console.log("data: " + data);
	console.log("status: " + status);
}

// esto salta cuando se pincha en una aplicacion
$(document).on('tap', '[action="edit"]', function(event) {

	$('#tipovar').selectmenu('enable');	
	
	localStorage.setItem("idAplicacion", this.id);

	$("#li_" + this.id).closest('ul').find('li').removeClass('ui-bar-b');
	$("#li_" + this.id).addClass('ui-bar-b');
	
	$("#mostrar_mapa_dashboard").removeClass('ui-disabled');
	$("#mostrar_informe").removeClass('ui-disabled');
 
	$("#eliminar_app").removeClass('ui-disabled');
	$("#duplicar").removeClass('ui-disabled');
	$("#nuevavar").removeClass('ui-disabled');
	$("#newVisor").removeClass('ui-disabled');

	cargarDatosAplicacion();
	cargarDatosVisores();

});


function cargarDatosAplicacion() {

	var aplicacion = localStorage.getItem("idAplicacion");

	$.ajax({
		type : "GET",
		dataType: "json",
		url : domain() + "/api/application/" + aplicacion,
		contentType : "application/json",
		success : function(data) {
			localStorage.setItem("nombreAplicacion", data.nombreaplicacion);
			setNombre(data.nombreaplicacion);

			localStorage.setItem("descripcionAplicacion", data.descripcion);
			setDescripcion(data.descripcion);

			localStorage.setItem("tokenAplicacion", data.tokkenaplicacion);
			setToken(data.tokkenaplicacion);

			localStorage.setItem("latitudMapa", data.latitud);
			localStorage.setItem("longitudMapa", data.longitud);
			localStorage.setItem("zoomMapa", data.zoom);

			setListaVariables(aplicacion);

		},
		error : null,
		async : true
	});
}

function cargarDatosVisores() {
	var aplicacion = localStorage.getItem("idAplicacion");

	$.ajax({
			type : "GET",
			url : domain() + "/api/application/visor/" + aplicacion,
			contentType : "application/json",
			dataType: "json",
			success : function(data) {

				$(".livisor").remove();
				var visores = [];
				$.each(data,function(indice, visor) 
				{
					
					// añado elementos después del control
					// de botones
					visores[indice] = visor.usuario;
					$(
							"<li id='listitemVisor"
									+ visor.idvisor
									+ "' class='livisor' data-icon='delete' ><a href='#' id=edit"
									+ visor.idvisor
									+ " >"
									+ "<h1 class='nombreVisor' data-val='"
									+ visor.usuario
									+ "'>"
									+ visor.usuario
									+ "</h1>"
									+ "</a><a id=visor" 
									+ visor.idvisor
									+ "  action='deleteVisor' data-rel='popup' data-position-to='window' href='#popupDeleteVisor'></a></li>")
							.insertAfter(
									$("#li_botones_visores"));
				});
				var jsonVisores = JSON.stringify(visores);
				localStorage.setItem('listaVisores', jsonVisores);
				$("#visores_creados").listview("refresh");

				},
				error : null,
				async : true
			});
}



function setNombre(nombreAplicacion) {
	$("#id_nombre_aplicacion").val(nombreAplicacion);

}

function setDescripcion(descripcionAplicacion) {
	$("#id_descripcion_aplicacion").val(descripcionAplicacion);
}

function setToken(tokenAplicacion) {
	$("#id_token_aplicacion").val(tokenAplicacion);
}

function setListaVariables(idAplicacion) {

	$.ajax({type : "GET",
			url : domain() + "/api/variable/variables?idAplicacion=" + idAplicacion,
			contentType : "application/json",
			dataType: "json",
			success : function(data) 
			{
					$(".livariable").remove(); // borro las variables cargadas
					// de otra aplicacion
					$.each( data, function(indice, variable) 
					{
							// añado elementos después del control
							// de botones
							var tipo = "";
							if (variable.idtipo == 1)
								tipo = "Entero";
							if (variable.idtipo == 2)
								tipo = "Opción";
							if (variable.idtipo == 3)
								tipo = "Fecha";
							if (variable.idtipo == 4)
								tipo = "Decimal";
							$("<li id='listitem"
								+ variable.idvariable
								+ "' class='livariable' data-icon='delete' ><a href='#' id=edit"
								+ variable.idvariable
								+ " action='edit-var'>"
								+ "<h1 class='nombreVariable' data-val='"
								+ variable.nombrevariable
								+ "'>"
								+ variable.nombrevariable
								+ "</h1>"
								+ "<span class='ui-li-count ui-btn-up-c ui-btn-corner-all'>id: "
								+ variable.idvariable
								+ "</span><p>"
								+ tipo
								+ "</p>"
								+ "</a><a id=delete"
								+ variable.idvariable
								+ " action='deleteVar' data-rel='popup' data-position-to='window' href='#popupDeleteVar'></a></li>").insertAfter($("#li_botones_variables"));										
							
						});
						$("#variables_creadas").listview("refresh");
				},
				error : null,
				async : false
			});

}

$(document).on( "tap", '[action="deleteVisor"]',
		function(event) {  
			var deleteidString = this.id; 
			var deleteid = deleteidString.substring(5, deleteidString.length); 
		    localStorage.setItem("idDeleteVisor", deleteid); 
});


$(document).on( "tap", '[action="deleteVar"]',
		function(event) {  
			var deleteidString = this.id; 
			var deleteid = deleteidString.substring(6, deleteidString.length); 
		    localStorage.setItem("idDeleteVar", deleteid); 
});




$(document).on(
		"tap",
		"#nuevavar",
		function(event) {
			localStorage.setItem("editar-var", 'false');

			var idapp = localStorage.getItem("idAplicacion");

			if (idapp != null) {
				var valor = $("#tipovar").val();
				if (valor == 'int') {

					$.mobile.changePage(domain()+ "/private/variable/int?idaplicacion=" + idapp, {reloadPage : true});

				} else {
					if (valor == 'mul_option') {
						$.mobile.changePage(domain()+ "/private/variable/option?idaplicacion="+ idapp, {reloadPage : true});
					} else {
						if (valor == 'date') {
							$.mobile.changePage(domain()+ "/private/variable/date?idaplicacion="+ idapp, {reloadPage : true});
						} else {
							$.mobile.changePage(domain()+ "/private/variable/decimal?idaplicacion="+ idapp, {reloadPage : true});
						}
					}
				}
			}

		});

function guardarEstadoVariables(data) {
	localStorage.setItem("fechadesdeVariable", data.fechadesde);
	localStorage.setItem("fechahastaVariable", data.fechahasta);
	localStorage.setItem("idaplicacionVariable", data.idaplicacion);
	localStorage.setItem("idtipoVariable", data.idtipo);
	localStorage.setItem("idvariableVariable", data.idvariable);
	localStorage.setItem("maxVariable", data.max);
	localStorage.setItem("minVariable", data.min);
	localStorage.setItem("multiopcionVariable", data.multiopcion);
	localStorage.setItem("nombrevariableVariable", data.nombrevariable);
}

$(document).on('tap', '[action="info"]', function(event) {
	$.ajax({
		type : "GET",
		url : domain() + "/api/developer/token",
		contentType : "application/json",
		success : function(data) {
			localStorage.setItem("token", data);
			$.mobile.changePage(domain() + '/private/info', {reloadPage : true});
		},
		error : onLoadError,
		async : false
	});

});

$(document).on('tap','[action="delete-visor"]',
				function(event) {
 
					var deleteid =  localStorage.getItem("idDeleteVisor"); 
					var resultado = "1";
					
					$.ajax({type : "DELETE",
							url : domain() + "/api/application/visor/"+deleteid,
							cache : false,
							success : function(data) {resultado = 1;},
							error : resultado = -1,
							async : false
					});

					if (resultado == 1){

						$('#listitemVisor'+ deleteid).remove();
					}else{ 
					}
	
				});


$(document).on('tap', '[action="nuevo-visor"]', function(event) {
	
	if (localStorage.getItem('idAplicacion') != null) {
		$.mobile.changePage(domain() + '/private/aplicacion/visor', {reloadPage : false});
	}
	

	
});