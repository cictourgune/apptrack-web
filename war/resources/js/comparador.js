var map1;
var map2;
var heatmapComparar1;
var heatmapComparar2;


$(document).on("pageshow", "#comparar-page", function() {

	mapOptions = {
		zoom : parseInt(zoomFinal),
		center : new google.maps.LatLng(latitude, longitude),

		navigationControl : false,
		streetViewControl : false,
		mapTypeId : google.maps.MapTypeId.SATELLITE
	};

	map1 = new google.maps.Map($("#map_canvas1")[0], mapOptions);
	map2 = new google.maps.Map($("#map_canvas2")[0], mapOptions);

	if (puntosMapa_fijado != undefined) {
		// añado heatmap al primer mapa
		heatmapComparar1 = new google.maps.visualization.HeatmapLayer({
			data : puntosMapa_fijado,
			radius : 16,
			dissipate : true,
			maxIntensity : 10
		});
		heatmapComparar1.setMap(map1);

	}

	if (puntosMapa != undefined) {
		// añado heat map al segundo mapa
		heatmapComparar2 = new google.maps.visualization.HeatmapLayer({
			data : puntosMapa,
			radius : 16,
			dissipate : true,
			maxIntensity : 10
		});
		heatmapComparar2.setMap(map2);
	
		
	}
});
