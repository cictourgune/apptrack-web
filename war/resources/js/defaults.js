$(document).bind("mobileinit", function(){ 
	$.mobile.defaultPageTransition = 'none';
	 
});


$( document ).on( "mobileinit", function() {
	  $.mobile.loader.prototype.options.text = "Cargando";
	  $.mobile.loader.prototype.options.textVisible = true;
	  $.mobile.loader.prototype.options.theme = "a";
	  $.mobile.loader.prototype.options.html = "";
	});
 