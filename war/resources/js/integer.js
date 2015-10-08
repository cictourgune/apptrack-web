var editar;
$(document).on(
		"pageshow",
		"#integer-page",
		function(event) {
			$('.id_app').hide();
			$('.id_tipo').hide();
			editar = localStorage.getItem('editar-var');
			if (editar == 'true') {
				// relleno los datos de las variables
				$("#idaplicacion").val(
						localStorage.getItem("idaplicacionVariable"));
				$("#id_tipo_var").val(localStorage.getItem("idtipoVariable"));
				$("#id_nombre_var").val(
						localStorage.getItem("nombrevariableVariable"));
				$("#id_slider_fill_min").val(
						localStorage.getItem("minVariable"));
				$("#id_slider_fill_max").val(
						localStorage.getItem("maxVariable"));

			}
			$("#id_nombre_var").focus();
		});
 
$(document).on(	"tap","#id_alta_var_int",function() 
		{

					if (!$("#id_nombre_var").val()) {
						$('#integer-error').html(
								'Introduzca un nombre a la variable');

					} else {
						var valormin = $("#id_slider_fill_min").val();
						var valormax = $("#id_slider_fill_max").val();
						
						if (valormin=='' || valormax=='')
						{
							$('#integer-error')	.html('Introduzca valores');
						}
						else
						{
							if (is_int(valormin) && is_int(valormax)) { 
								if ($("#id_slider_fill_min").val() > $(
										"#id_slider_fill_max").val()) {
									$('#integer-error')	.html('El valor mínimo es mas alto que el máximo');
								} else {
									if ($("#id_slider_fill_min").val() == $(
											"#id_slider_fill_max").val()) {
										$('#integer-error')
												.html(
														'El valor mínimo y máximo tienen que ser distintos');
									} else {
										if (editar == 'true') {
											// relleno los datos de las variables

											modificarVariableInt();
											localStorage.setItem("editar-var",
													'false');
										} else {
											var existe = comprobarNombreInteger($(
													"#id_nombre_var").val());
											if (existe === false) {
												crearVariableInt();
											} else {
												$('#integer-error')
														.html(
																'El nombre de esa variable ya existe');
											}
										}

									}
								}
							} else { 
								$('#integer-error').html('El valor debe de ser entero');
							}
						}
						
						
						

					}

					return false;
				});
function is_int(value) {
	for (i = 0; i < value.length; i++) {
		
		if ((value.charAt(i) < '0') || (value.charAt(i) > '9'))
			return false;
	}
	return true;
}

function crearVariableInt() {

	var formData = $("#id_variable-form").serialize();
	var idapp = $("#idaplicacion").val();
	var nombreVar = $("#id_nombre_var").val();

	$.ajax({
		type : "POST",
		url : domain() + "/api/variable/nuevaint",
		cache : false,
		data : formData,
		success : function(data) {
			$.mobile.changePage(domain() + '/private/dashboard', {
				reloadPage : false
			});

			localStorage.setItem('nombreVariable', nombreVar);

		},
		error : {}
	});

}

function modificarVariableInt() {

	var formData = $("#id_variable-form").serialize();
	var idapp = $("#idaplicacion").val();
	var nombreVar = $("#id_nombre_var").val();
	$.ajax({
		type : "POST",
		url : domain() + "/api/variable/updateint/"
				+ localStorage.getItem("idvariableVariable"),
		cache : false,
		data : formData,
		success : function(data) {
			$.mobile.changePage(domain() + '/private/dashboard', {
				reloadPage : false
			});

			localStorage.setItem('nombreVariable', nombreVar);

		},
		error : onRegistrationError
	});
}
function onRegistrationError(data, status) {
	$.mobile.hidePageLoadingMsg();// Parece ser que est� deprecated y hay que
	// usar ".loading('show/hide')
	$('#integer-error').html('Upps!! Error en la creaci�n de la variable!');
}
function comprobarNombreInteger(nombre) {

	var existe = false;
	$(".nombreVariable").each(function(index) {
		var valor = $(this).attr('data-val');
		if (valor === nombre) {
			existe = true;
		}
	});
	return existe;

}