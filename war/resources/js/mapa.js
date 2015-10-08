var where = "";
var variablesMapa = new Array();
var map;
var markerClusterer;
var markers;
var puntosMapa;
var heatmap;
var tipo="cluster";
var cambio=0;
var puntosMapa_fijado ;
var markers_fijado ;

//controlar que no vuelva de un select option con diálogo y se cierre
$(document).on('tap',"[title='Close']",function(event) { 
	cambio=1; 
});


//esto salta cuando se pincha en una aplicacion
$(document).on('tap', '[action="selectAppMap"]', function(event) { 
	
	localStorage.setItem("idAplicacion", this.id);
	 
	if (this.id != null) {  
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
		'[action="informe"]',
		function(event) { 
			if (localStorage.getItem('idAplicacion') != null) {
				$.ajax({
					type : "GET",
					url : domain() + "/api/application/"+ localStorage.getItem('idAplicacion'),
					contentType : "application/json",
					success : function(data) {
						$.mobile.changePage(domain()
								+ '/private/report?nombreaplicacion='
								+ data.nombreaplicacion + "&idaplicacion="
								+ localStorage.getItem('idAplicacion'), {reloadPage : true});

					},
					error : onLoadError,
					async : false
				});
			}

});

 

$(document).on("pageshow","#mapa-page",function() {
	
	$('#aplicaciones_creadas').listview('refresh');
	
	
	var idapp = localStorage.getItem('idAplicacion'); 
	$("#li_" + idapp).closest('ul').find('li').removeClass('ui-bar-b');
	$("#li_" + idapp).addClass('ui-bar-b'); 
	setTimeout(init, 100); 
	 
});


$(document).on("tap","#menu_gestion",function() {
	window.location.href= domain()+ '/private/dashboard/';
});


function init(){ 
	
	 fijado=false;
 
	 $('#id_comparar').addClass('ui-disabled');
	 $('#id_eliminarfijado').addClass('ui-disabled');
	//controlar que no vuelva de un select option
	$("select").change(function(){
		cambio=1; 
	});

	
	if(cambio!=1 ){//si cambio realizado en un select NO recargar nada  
		
		var latMapa = localStorage.getItem('latitudMapa');
		var lngMapa = localStorage.getItem('longitudMapa');
		var zomMapa = localStorage.getItem('zoomMapa');
		
		zoomFinal='1';
		if (zomMapa!='0')
			zoomFinal=zomMapa;
		

		if (latMapa!=0)
		{
			 latitude = latMapa;
			 longitude = lngMapa;

		}
		else
		{
			latitude = 40.446947;
			longitude = -6.855469;
			 
		}
		
		
		var mapOptions = {
			zoom : parseInt(zoomFinal),
			center : new google.maps.LatLng(latitude, longitude),
			mapTypeId : google.maps.MapTypeId.SATELLITE
		};
		
	

		//activamos el boton de cluster y desactivamos el del heatmap
		$("#id_heatmap").removeClass('ui-btn-active');
		$("#id_cluster").addClass('ui-btn-active');
		
		map = new google.maps.Map($("#map_canvas")[0], mapOptions); 
		var defaultBounds = new google.maps.LatLngBounds(
			      new google.maps.LatLng(40.504402, -3.900146),
			      new google.maps.LatLng(40.695217, -13.161621)); 

	    var input = /** @type {HTMLInputElement} */(document.getElementById('target'));
	    var searchBox = new google.maps.places.SearchBox(input, {
	    	  bounds: defaultBounds
	    });
	    var markers = [];
			  
		google.maps.event.addListener(searchBox, 'places_changed', function() {
		    var places = searchBox.getPlaces();

		    for (var i = 0, marker; marker = markers[i]; i++) {
		      marker.setMap(null);
		    }

		    markers = [];
		    var bounds = new google.maps.LatLngBounds();
		    for (var i = 0, place; place = places[i]; i++) {
		      var image = {
		        url: place.icon,
		        size: new google.maps.Size(71, 71),
		        origin: new google.maps.Point(0, 0),
		        anchor: new google.maps.Point(17, 34),
		        scaledSize: new google.maps.Size(25, 25)
		      };

		      var marker = new google.maps.Marker({
		        map: map,
		        icon: image,
		        title: place.name,
		        position: place.geometry.location
		      });

		      markers.push(marker);

		      bounds.extend(place.geometry.location);
		    }

		    map.fitBounds(bounds);
		  });
		
		google.maps.event.addListener(map, 'bounds_changed', function() {
		    var bounds = map.getBounds();
		    searchBox.setBounds(bounds);
		  });
		
		
		// cambiar aspecto a los controles que no est�n activos
		$('.flip').change(function(event) {
							var id = event.target.id;
							var aux = id.split("flip");
							var idClean = aux[1];
							var active = $("#h_" + idClean).data('active');
							var $btn_text = $('#h_' + idClean).find('.ui-btn-text');
							var varname = $("#h_" + idClean).data('varname');

							if (active == 1) {// desactivarlo
								$btn_child = $btn_text.find('.ui-collapsible-heading-status');
								// var texto =
								// "<strike>"+varname+"</strike>";
								var texto = "<span style='color: grey;'>"+ varname + "</span> ";

								$btn_text.html(texto).append($btn_child);
								$("#h_" + idClean).data('active','0');
							} else if (active == 0) {
								$btn_child = $btn_text.find('.ui-collapsible-heading-status');
								var texto = "<span style='color: black;'>"+ varname + "</span> ";
								$btn_text.html(texto).append($btn_child);
								$("#h_" + idClean).data('active','1');
							}

			});

		}
	 

	cambio=0;//resetear		
	
	
	
}

$(document).on("expand", "div[data-role='collapsible']", function(){ 
	$(".resize").rangeSlider('resize');
}); 

$(document).on('tap','[action="consultar"]',function(event) {
					
				
					$('#mapa_num_usuarios').html('2');
	
					var myArray = [];
					var fecha_desde = $("#fecha_desde").val();
					var fecha_hasta = $("#fecha_hasta").val();
					
					if (fecha_desde == "" || fecha_hasta == "") {
						$('#filtro-error').html('<font color="#FF0000">Fechas obligatorio</font>');
					} else {
						var f_desde=esFecha(fecha_desde);
						var f_hasta=esFecha(fecha_hasta);
						
						if (f_desde==true && f_hasta==true){
							
							var d = Date.parse(fecha_desde);
							var h = Date.parse(fecha_hasta);
							if (d > h) {
								$('#filtro-error').html('<font color="#FF0000">Fecha desde tiene que ser menor que hasta</font>');
							}
	
							else {
								$('#filtro-error').html('');
								//creo el listado de variables que quiero consultar
								$(".flip").each(function(index) {
									var valorflip = $(this).val();
	
									if (valorflip == 'on') {
	
										var flipId = $(this).attr('id');
										var id = flipId.substring(4, flipId.length);
										var clase = $("#" + id).data('tipo');
										var valor = new Object();
										if (clase == "2" || clase == "3") {
											valor.idvariable = id;
											valor.tipo = clase;
											console.log($("#" + id).val());
											valor.valorvariable = $("#" + id).val().toString();
	
										} else {
											if (clase == "1") {
	
												var basicSliderMin = $("#" + id+"_inicio").val();
												var basicSliderMax = $("#" + id+"_fin").val();
												valor.idvariable = id;
												valor.tipo = clase;
												valor.valormin = basicSliderMin;
												valor.valormax = basicSliderMax;
	
											} else {
												if (clase == "4") {
	
													var basicSliderMin = $("#" + id+"_inicio").val();
													var basicSliderMax = $("#" + id+"_fin").val();
													valor.idvariable = id;
													valor.tipo = clase;
													valor.valormin = basicSliderMin;
													valor.valormax = basicSliderMax;
												}
											}
										}
	
										myArray.push(valor);
									}
	
								});
	
								var arrayListJSON = JSON.stringify(myArray);
								var json = arrayListJSON.toString();
	
								// elimino la capa cluster
								if (markerClusterer != null
										&& markerClusterer != undefined) {
									markerClusterer.clearMarkers();
								}
	
								// elimino la capa heatmap
								if (heatmap != null && heatmap != undefined) {
									puntosMapa = [];
									heatmap.setMap(null);
								}
								if (fijado)  $('#id_comparar').removeClass('ui-disabled');
								
								mostrarPuntos(fecha_desde,fecha_hasta, json);
								
								mostrarUsuarios(fecha_desde,fecha_hasta, json);
			
							}
					}else{
						$('#filtro-error').html('<font color="#FF0000">Fecha no válida</font>');
					}
				}
					

});

function esFecha (strValue)
{

	var valido=false;
	var re=/^[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]$/;
		
	if(!re.exec(strValue))
	{
		valido=false;
	}
	else{
	    var arrayDate = strValue.split("-");
	    var arrayLookup = { '01' : 31,'03' : 31,
    		      '04' : 30,'05' : 31,
    		      '06' : 30,'07' : 31,
    		      '08' : 31,'09' : 30,
    		      '10' : 31,'11' : 30,'12' : 31
    		    };

	    var intDay = parseInt(arrayDate[2],10);
	    var intMonth = parseInt(arrayDate[1],10);
	    var intYear = parseInt(arrayDate[0],10);
	    
	    //check if month value and day value agree

	    if (arrayLookup[arrayDate[1]] != null) {
	      if (intDay <= arrayLookup[arrayDate[1]] && intDay != 0
	        && intYear > 1975 && intYear < 2050)
	    	  valido=true;     //found in lookup table, good date
	    }

	    if (intMonth == 2) {
	      var intYear = parseInt(arrayDate[0]);

	      if (intDay > 0 && intDay < 29) {
	    	  valido=true;
	      }
	      else if (intDay == 29) {
	        if ((intYear % 4 == 0) && (intYear % 100 != 0) ||
	            (intYear % 400 == 0)) {
	          // year div by 4 and ((not div by 100) or div by 400) ->ok
	        	valido=true;
	        }
	      }
	    }
	}
    return valido;
}
//dibujo el cluster
$(document).on('tap', '[action="cluster"]', function(event) {

	tipo="cluster";
	
	if (markerClusterer != null && markerClusterer != undefined) {
		markerClusterer.clearMarkers();
		markerClusterer = new MarkerClusterer(map, markers);
	}
	if (heatmap != null && heatmap != undefined) {
		heatmap.setMap(null);
	}

});
//dibujo el heatmap
$(document).on('tap', '[action="heatmap"]', function(event) {
	
	tipo="heatmap";
	if (markerClusterer != null && markerClusterer != undefined) {
		markerClusterer.clearMarkers();
		
	}
	if (heatmap != null && heatmap != undefined) {
		heatmap.setMap(map);
	}

});

$(document).on('tap', '[action="delete_points"]', function(event) {
	$(this).simpledialog(
			{
				'mode' : 'bool',
				'prompt' : '¿Desea eliminar todos los puntos?',
				'useModal' : false,
				'buttons' : {
					'OK' : {	click : function() {
						$.ajax({
							type : "DELETE",
							url : domain()+ "/api/application/puntos?idapplication="	+ localStorage.getItem('idAplicacion'),
							cache : false,
							success : function(
									data) {
							
							
								
								if (markerClusterer != null && markerClusterer != undefined) {
									markerClusterer.clearMarkers();
									
									
								}
								if (heatmap != null && heatmap != undefined) {
									
									heatmap.setMap(null);
								}
								
								
								if (data==1)
								{
									
									
									$('#popupDelete').popup('open');
									setTimeout(function(){$('#popupDelete').popup('close')},3000);
									
								}
								
							},
							error : resultado = -1,
							async : false
						});
						}
					},
					'Cancel' : {
						click : function() {
						},
						icon : "delete",
						theme : "c"
					}
				}
			});

});

$(document).on('tap', '[action="store_location"]', function(event) {
	
	var latitudeMap = map.getCenter().lat();
	var longitudeMap = map.getCenter().lng();
	var zoomMap = map.getZoom(); 
	
	$.ajax({type : "GET",
		url : domain()+ "/api/application/updateloc?idapplication="+ localStorage.getItem('idAplicacion')+ "&lat='"	+ latitudeMap	+ "'&lng='"	+ longitudeMap+"'&zoom="+zoomMap,
		contentType : "application/json",
		success : function(data) 
		{
			$('#popupBasic').popup('open');
			setTimeout(function(){$('#popupBasic').popup('close')},3000); 
		},
		error : null,
		async : true
	});

});


function mostrarPuntos (fecha_desde,fecha_hasta, json)
{
	var currentTimeMillis = Date.now();
	
//	var basicHourValues = $("#hour_slider").rangeSlider("values");
//	console.log(basicHourValues.min);
	 
	var basicHourValuesmin =  $('#hora-desde').val(); 
	var basicHourValuesmax =  $('#hora-hasta').val(); 

	
	
	 $.mobile.loading( "show");
	$.ajax({type : "POST",url : domain()
		+ "/api/puntos/latlongs?fecha_desde='"
		+ fecha_desde
		+ "'&fecha_hasta='"
		+ fecha_hasta
		+ "'&horaMin="
		+ basicHourValuesmin
		+ "&horaMax="
		+ basicHourValuesmax
		+ "&idaplicacion="
		+ localStorage
				.getItem('idAplicacion'),
		
		contentType : "application/json",
		data : json,
		success : function(data) {
	
			var currentTimeMillis = Date.now();
			
			markers = [];
			puntosMapa = [];

		
	
		$.each(data,function(indice,puntos) 
		{
			var latLng = new google.maps.LatLng(puntos.latitud,puntos.longitud);
			var marker = new google.maps.Marker({position : latLng});
			puntosMapa.push(latLng);
			markers.push(marker);
				
			
		});
	

		var currentTimeMillis = Date.now();
		
		heatmap = new google.maps.visualization.HeatmapLayer({data : puntosMapa, radius: 16, dissipate: true, maxIntensity: 10});
		markerClusterer = new MarkerClusterer(map, markers);
		
		var currentTimeMillis = Date.now();
		
		if (tipo=="cluster")
		{
			heatmap.setMap(null);
		
		}
		else
		{
			heatmap.setMap(map);
			markerClusterer.clearMarkers();
		}
		 
		 $.mobile.loading( "hide");
		 
		 var currentTimeMillis = Date.now();

},
error : false});

}


function mostrarUsuarios (fecha_desde,fecha_hasta, json)
{
//	var basicHourValues = $("#hour_slider").rangeSlider("values");
//	console.log(basicHourValues.min);
	
 
	var basicHourValuesmin =  $('#hora-desde').val(); 
	var basicHourValuesmax =  $('#hora-hasta').val(); 
		
	$.ajax({type : "POST",url : domain()
		+ "/api/puntos/usuarios?fecha_desde='"
		+ fecha_desde
		+ "'&fecha_hasta='"
		+ fecha_hasta
		+ "'&horaMin="
		+ basicHourValuesmin
		+ "&horaMax="
		+ basicHourValuesmax
		+ "&idaplicacion="
		+ localStorage
				.getItem('idAplicacion'),
		
		contentType : "application/json",
		data : json,
		success : function(data) { $('#mapa_num_usuarios').html(data); },
error : false});

}




$(document).on('tap', '[action="localiza_mapa"]', function(event) {
	
geocoder = new google.maps.Geocoder();
	

	var direccion = $('#ir_mapa').val();

	geocoder.geocode({
	    address: direccion
	}, function(locResult) {
		if(locResult[0] != undefined){
		    latD = locResult[0].geometry.location.lat();
		    longD = locResult[0].geometry.location.lng();
		    map.setCenter(new google.maps.LatLng(latD,longD));
		    
		}
		else{}
	});

});





 
