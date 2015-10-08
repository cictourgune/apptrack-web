$(document).on("tap", "#id_dup_app_submit", function(){
	
	if( !$("#id_dup_application_name").val() ) {
		$('#application-error').html('Introduzca un nombre a la aplicación');
	}else{
		var existe=comprobarNombreDuplicado($("#id_dup_application_name").val());
		
		if ((existe===false) && ($("#id_dup_application_name").val().length>1)){
			registrarApplicationDuplicado();	
		}else{
			$('#application-error').html('El nombre de esa aplicación ya existe');
		}
		
	}
	
    return false;  
}); 

function registrarApplicationDuplicado(){
	var formData = $("#id_dup_application_form").serialize(); 
	var idDupApp =  localStorage.getItem('idAplicacion');
	$.ajax({
		 type: "POST",
		 url: domain()+"/api/application/duplicar?idDuplicar="+idDupApp,
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
	$.mobile.hidePageLoadingMsg();// Parece ser que está deprecated y hay que usar ".loading('show/hide')
	$('#application-error').html('Upps!! Error en la creación de la aplicación!');
}  


function comprobarNombreDuplicado(nombre){

	var existe=false;
	$(".nombreApplicacion").each(function (index) {
		var valor= $(this).attr('data-val');
		if(valor===nombre){
			existe=true;
		}
	});
	return existe;
	
}  
  


