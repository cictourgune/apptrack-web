$(document).on("pageinit", "#registro-page", function(){
	
});

$(document).on("tap", "#registro-submit", function(){
	var errores = registroUsuarioValidar();
	
	if(errores.length==0){ 
		registrarUsuario();	 
	}else{
		//incorrecto
		var mensajeError = '';
		$.each(errores, function(index, value) { 
			mensajeError = mensajeError + '<p>&nbsp; *'+value+'</p>';
		 }); 
		$('#registro-error').html(mensajeError); 
	}

    return false;  
}); 

$(document).on("tap", "#registro-cancel-submit", function(){
	history.back(); 
	return false; 
});


function registrarUsuario(){
	
	$.mobile.loading('show');  
	
	var formData = $("#registro-form").serialize(); 
	 $.ajax({
		 type: "POST",
		 url: domain()+"/open/developer/",
		 cache: false,
		 data: formData,
		 success: function(data) {
			 if(data == 1){  
				 $.mobile.loading('hide');  
				$.mobile.changePage(domain() + '/validation');  
			 }
		 	},
		 error: onRegistrationError
	 });
}

function onRegistrationError(data, status)
{   $.mobile.loading('hide');  
	$('#registro-error').html('Upps!! Error en el registro!');
}  
 

function registroUsuarioValidar(){  
	var errores = new Array();  
	var i = 0;      
	var registroNombre = $("#registro-usuario").val();
 
	if(registroNombre.length<5){
		errores[i]="Usuario demasiado corto"; i++;
	}else{//comprobar que no exista
		$.ajax({
			 type: "GET",
			 url: domain()+"/open/developer/exists?developername="+registroNombre,
			 contentType : "application/json",
			 success: function(data) { 
				 
					 if(data >= 1){
						 errores[i]="Usuario existente, cambie el nombre"; i++;
					 }
			 }, 
			 error : errorBusqueda, 
			 async : false
			 
		 }); 
	} 
	if($("#registro-password").val().length<7){
		errores[i]="Contraseña demasiado corta"; i++;
	} 
	if($("#registro-password").val()!=$("#registro-repite-password").val()){
		errores[i]="Contraseñas diferentes!"; i++;
	} 
	if($("#email").val().length<1){
		errores[i]="Introduzca email"; i++;
	} 
	else{
		var email=$("#email").val();
		if(!validarEmail(email))
		{
			errores[i]="Formato de mail incorrecto"; i++;
		}
		else
		{
	
				$.ajax({
				 type: "GET",
				 url: domain()+"/open/developer/existsEmail?email="+email,
				 cache: false,
				 success: function(data) { 
						 if(data == 1){
						
							 errores[i]="Mail existente"; i++;
						 }
					}, 
					error : errorBusqueda, 
					async : false
			 }); 
		}
		
			
	}
		

	return errores;
}
function validarEmail(valor) {
	 var filtro = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	 if (!filtro.test(valor)) {
		 return false;
	 }else{
		 return true;
	 }

}
function errorBusqueda(data, status) {

	console.log("data: " + data);
	console.log("status: " + status);
}

 