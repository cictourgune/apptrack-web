var editar;
$(document).on(
		"pageshow",
		"#decimal-page",
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

 
$(document).on("tap","#id_alta_var_decimal",function() 
{

					if (!$("#id_nombre_var").val()) 
					{
						$('#decimal-error').html(
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
							if (is_decimal(valormin) && is_decimal(valormax)) {
	
								if ($("#id_slider_fill_min").val() > $(
										"#id_slider_fill_max").val()) {
									$('#decimal-error')
											.html(
													'El valor mínimo es mas alto que el máximo');
								} else {
									if ($("#id_slider_fill_min").val() == $(
											"#id_slider_fill_max").val()) {
										$('#decimal-error')
												.html(
														'El valor mínimo y máximo tienen que ser distintos');
									} else {
										if (editar == 'true') {
											// relleno los datos de las variables
	
											modificarVariableDecimal();
											localStorage.setItem("editar-var",
													'false');
										} else {
											var existe = comprobarNombreDecimal($(
													"#id_nombre_var").val());
											if (existe === false) {
												crearVariableDecimal();
											} else {
												$('#decimal-error')
														.html(
																'El nombre de esa variable ya existe');
											}
										}
									}
	
								}
							} else {
								$('#decimal-error').html(
										'El valor debe ser decimal');
							}
						}
					}

					return false;
				});

function is_decimal(value) {
	for (i = 0; i < value.length; i++) {
		if ((value.charAt(i) < '0') || (value.charAt(i) > '9')  )
		{
			if ((value.charAt(i) != '.'))
				return false;
		}

	}
	return true;
}

function crearVariableDecimal() {

	var formData = $("#id_variable-form").serialize();
	var idapp = $("#idaplicacion").val();
	var nombreVar = $("#id_nombre_var").val();
	$.ajax({
		type : "POST",
		url : domain() + "/api/variable/nuevadecimal",
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

function modificarVariableDecimal() {

	var formData = $("#id_variable-form").serialize();
	var idapp = $("#idaplicacion").val();
	var nombreVar = $("#id_nombre_var").val();
	$.ajax({
		type : "POST",
		url : domain() + "/api/variable/updatedecimal/"
				+ localStorage.getItem("idvariableVariable"),
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
function comprobarNombreDecimal(nombre) {

	var existe = false;
	$(".nombreVariable").each(function(index) {
		var valor = $(this).attr('data-val');
		if (valor === nombre) {
			existe = true;
		}
	});
	return existe;

}