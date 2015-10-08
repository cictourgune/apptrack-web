
$(document).on("pageshow", "#forgotpassword-page", function(){

	$('#email').focus();
});

$(document).on("pageshow", "#restorepassword-page", function(){
	
	$('#password1').focus();
});


// Tiene que ser CLICK!!!! Como el jsp es un FORM pero sin ACTION, para que el "enter" 
// lo interprete bien, tiene que ser CLICK.
$(document).on("click", "#submitForgot", function(){
	var errores = forgotPasswordValidar();
	
	if(errores.length==0){
		
		 
		forgotPassword();	
		
	}else{
		//incorrecto
		var mensajeError = '';
		$.each(errores, function(index, value) { 
			mensajeError = mensajeError + '<p style=\"color: red; font-weight: bold;\">*'+value+'</p>';
		 });

		$('#errorForgotPassword').html(mensajeError); 
	}

    return false;  
}); 

$(document).on("tap", "#submitRestore", function(){
	var errores = restorePasswordValidar();
	
	if(errores.length==0){
	 
		restorePassword();	
		
	}else{
		//incorrecto
		var mensajeError = '';
		$.each(errores, function(index, value) { 
			mensajeError = mensajeError + '<p style=\"color: red; font-weight: bold;\">*'+value+'</p>';
		 });

		$('#errorRestorePassword').html(mensajeError); 
	}

    return false;  
});

// Solicitamos envio del email con link para restablecer contraseñas
function forgotPassword(){
	var email = $("#email").val();
	
	
	$.ajax({
		 type: "POST",
		 url: domain()+"/open/developer/forgotPassword?email=" + email,
		 cache: false,
		 success: function(data) {
			 if(data == 1){ 
				$.mobile.changePage(domain()+'/confirmForgotPassword');  
			 }
		 	},
		 error: onForgotPasswordError
	 });
}

function onForgotPasswordError(data, status)
{ 
	var msgerr="Error al solicitar nueva contraseña";
	
	$('#errorForgotPassword').html(msgerr);
}  
 
// El usuario a trav√©s del link en su mail, ha introducido las nuevas contrase√±as y se almacenan en la BBDD
function restorePassword(){
	var pass = $("#password1").val();
	var token = $("#token").val();
	var email = $("#email").val();
	$.ajax({
		 type: "POST",
		 url: domain()+"/open/developer/restorePassword?token=" + token + "&email=" + email + "&pass=" + pass,
		 cache: false,
		 success: function(data) {
		
			 if(data == 1){ 
					$.mobile.changePage(domain()+"/index", {transition: "none", reloadPage: true}); 
					
					//$.mobile.changePage(domain()+'/login'); 
			 }
		 	},
		 error: onRestorePasswordError
	 });
}

function onRestorePasswordError(data, status) { 
	var msgerr="";
	msgerr = 'Upps!! Error restableciendo la nueva contraseña!';
	
	
	$('#errorRestorePassword').html(msgerr);
}  

function forgotPasswordValidar(){  
	var errores = new Array();  
	var i = 0;      
	var email = $("#email").val();
	var msgerr="";
	
	if(!validarEmail(email)){
		msgerr = 'Email no válido';
		errores[i] = msgerr; i++;
	} else{//comprobar que el mail exista
		 $.ajax({
			 type: "GET",
			 url: domain()+"/open/developer/existsEmail?email="+email,
			 cache: false,
			 success: function(data) { 
					 if(data == -1){
						 msgerr = 'Usuario no existente en el sistema';
						
						 errores[i]=msgerr; i++;
					 }
				}, 
			 async:   false
		 }); 
	} 
	return errores;
}

function restorePasswordValidar(){  
	var errores = new Array();  
	var i = 0;      
	var pass1 = $("#password1").val();
	var pass2 = $("#password2").val();
	var msgerr="";
	
	if (pass1 == "" || pass2 == "") {
		msgerr = 'La contrase√±a no puede ser vac√≠a';
		errores[i] = msgerr; i++;
	}
	if(pass1.length < 7 || (pass2.length < 7)){
		msgerr = 'Contrase√±a demasiado corta';
		errores[i]=msgerr; i++;
	} 
	if(pass1 != pass2){
		msgerr = '¬°Contrase√±as diferentes!';
		errores[i] = msgerr; i++;
	} 
	return errores;
}

