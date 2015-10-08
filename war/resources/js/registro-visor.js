$(document).on(
		"tap",
		"#id_altavisor_submit",
		function() {

			if (!$("#id_visor_name").val()) {
				$('#application-error-visor').html(
						'Introduzca un nombre al usuario visor');
			} else {

				if (($("#id_visor_name").val().length > 3)) {
					var pwd1 = $("#id_visor_pwd").val();
					var pwd2 = $("#id_visor_pwd_rep").val();
			
					if (pwd1 != pwd2)
						$('#application-error-visor').html(
								'Las contraseñas no coinciden');
					else
						registrarVisor();
				} else {
					$('#application-error-visor').html(
							'Usuario demasiado corto');
				}

			}

			return false;
		});

function registrarVisor() {
	var formData = $("#id_newvisor_form").serialize();
	$.ajax({
		type : "POST",
		url : domain() + "/api/application/visor/"
				+ localStorage.getItem('idAplicacion'),
		cache : false,
		data : formData,
		success : function(data) {
			console.log(data);
			if (data == "-1")
				$('#application-error-visor').html('Nombre de usuario no permitido');
			else { 
				window.location.href=domain()+'/private/dashboard';  
			}  
			 
		},
		error : onRegistrationVisorError
	});
}

function onRegistrationVisorError(data, status) {
	console.log('error');
	$('#application-error-visor').html('Upps!! Error en la creación del visor!');
}
