 
$(document).on("tap", "#id_altaapp_submit", function(){
	
	if( !$("#id_application_name").val() ) {
		$('#application-error').html('Introduzca un nombre a la aplicación');
	}else{
		var existe=comprobarNombreAplicacion($("#id_application_name").val());
		if ((existe===false) && ($("#id_application_name").val().length>1)){
			registrarApplication();	
		}else{
			$('#application-error').html('El nombre de esa aplicación ya existe');
		}
		
	}
	
    return false;  
}); 

function registrarApplication(){
	var formData = $("#id_newapplication_form").serialize(); 
	$.ajax({
		 type: "POST",
		 url: domain()+"/api/application/application/",
		 cache: false,
		 data: formData,
		 success: function(data) {
			 	localStorage.setItem('idAplicacion', data); 
			 	
			 	window.location.href=domain()+'/private/dashboard';  
				
		 	},
		 error: onRegistrationError
	 });
}

function onRegistrationError(data, status)
{ 
	 
	$('#application-error').html('Upps!! Error en la creación de la aplicación!');
}  
function comprobarNombreAplicacion(nombre){

	var existe=false;
	$(".nombreApplicacion").each(function (index) {
		var valor= $(this).attr('data-val');
		
		if(valor==nombre){
			existe=true;
		}
	});
	return existe;
	
}
  


