//var where = "";
//var variablesMapa = new Array();
//var map;
//var markerClusterer;
//var markers;
//var puntosMapa;
//var heatmap;
//var tipo="cluster";
var cambiovista=0;

var puntosMapa_fijado ;
var markers_fijado ;

var map1;
var map2;
var mapOptions;
var zoomFinal;
var latitude;
var longitude;
var fijado;


//controlar que no vuelva de un select option con diálogo y se cierre
$(document).on('tap',"[title='Close']",function(event) {
	
	cambiovista=1; 
});


function initvista(){
	 
	 $('#id_comparar').addClass('ui-disabled');
	 $('#id_eliminarfijado').addClass('ui-disabled');
	 fijado=false;
	  
	
	localStorage.setItem('idAplicacion',$("#idAplicacion").attr("data-val"));
	
	//controlar que no vuelva de un select option
	$("select").change(function(){
		cambiovista=1; 
	});
	
	
	if(cambiovista!=1){//si cambiovista realizado en un select NO recargar nada 
	
		var latMapa;
		var lngMapa;
		var zomMapa;
		
		
		$.ajax({type : "GET",
			url : domain()+ "/api/visores/visor",
			contentType : "application/json",
			success : function(data) 
			{
				latMapa=data.latitud;
				lngMapa=data.longitud;
				zomMapa= data.zoom;
				
			},
			error : null,
			async : false
		});
		
		
		
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
		
		 mapOptions = {
			zoom : parseInt(zoomFinal),
			center : new google.maps.LatLng(latitude, longitude),
			mapTypeId : google.maps.MapTypeId.SATELLITE
		};
	
		//activamos el boton de cluster y desactivamos el del heatmap
		$("#id_heatmap").removeClass('ui-btn-active');
		$("#id_cluster").addClass('ui-btn-active');
		map = new google.maps.Map($("#map_canvas")[0], mapOptions);
		// cambiar aspecto a los controles que no est�n activos
		
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
	 

	cambiovista=0;//resetear			
}



$(document).on("pageshow","#vista-page",function() { 
	
	setTimeout(initvista, 300); 

});



$(document).on(
		'tap',
		'[action="mapaDashboardVisor"]',
		function(event) { 
			if (localStorage.getItem('idAplicacion') != null) {  
				window.location.href=domain()+ '/private/dashboard';
			}
});


$(document).on(
		'tap',
		'[action="informeDashboardVisor"]',
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







$(document).on('tap', '[action="fijar"]', function(event) {
	


	puntosMapa_fijado = puntosMapa;
	markers_fijado = markers;
	fijado = true;
	
	$('#popupFijado').popup('open');
	setTimeout(function(){$('#popupFijado').popup('close')},3000); 
	
	
	 //$('#id_comparar').removeClass('ui-disabled');
	 $('#id_eliminarfijado').removeClass('ui-disabled');
});


$(document).on('tap', '[action="delete_fijado"]', function(event) {
	


	puntosMapa_fijado = undefined;
	markers_fijado = undefined;
	fijado=false;
	
	
	$('#popupFijadoEliminar').popup('open');
	setTimeout(function(){$('#popupFijadoEliminar').popup('close')},3000); 
	
	 $('#id_comparar').addClass('ui-disabled');
	 $('#id_eliminarfijado').addClass('ui-disabled');

});

$(document).on('tap', '[action="comparar"]', function(event) {
	;
	$.mobile.changePage(domain()+ "/private/mapa/comprador", {reloadPage : false});
});



$(document).on('tap', '[action="store_location_visor"]', function(event) {
	
	var latitudeMap = map.getCenter().lat();
	var longitudeMap = map.getCenter().lng();
	var zoomMap = map.getZoom(); 
	
	$.ajax({type : "GET",
		url : domain()+ "/api/visores/updateloc?lat='"	+ latitudeMap	+ "'&lng='"	+ longitudeMap+"'&zoom="+zoomMap,
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






 
