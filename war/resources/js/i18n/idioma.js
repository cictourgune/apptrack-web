Idioma = function() {
	return{
		prefix : function() { return "idioma@";}
	};
};

Idioma.add = function(key, object) {	//Añade las traducciones de un idioma en localstorage
	var str = JSON.stringify(object);
	localStorage.setItem(key, str);
};

Idioma.change = function(lang) {	//Guarda en localstorage el idioma seleccionado por el usuario
	localStorage.setItem("selected", lang);
};

Idioma.selected = function()	//Devuelve el JSON del idioma seleccionado en localstorage
{
	if(localStorage.getItem("selected")==null){
		localStorage.setItem("selected", "es");
	}
	var selng = localStorage.getItem("selected");
	return JSON.parse(localStorage.getItem(selng));
};