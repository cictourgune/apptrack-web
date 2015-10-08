$(document).on("pageshow", "#date-page", function(event) {
	$('.id_app').hide();
	$('.id_tipo').hide();
	editar = localStorage.getItem('editar-var');
	if (editar == 'true') {
		//relleno los datos de las variables
		$("#idaplicacion").val(localStorage.getItem("idaplicacionVariable"));
		$("#id_tipo_var").val(localStorage.getItem("idtipoVariable"));
		$("#id_nombre_var").val(localStorage.getItem("nombrevariableVariable"));
		$("#id_fecha_desde").val(localStorage.getItem("fechadesdeVariable"));
		$("#id_fecha_hasta").val(localStorage.getItem("fechahastaVariable"));
		localStorage.setItem("editar-var", 'false');
	}
	$("#id_nombre_var").focus();
});
 

$(document).on("tap", "#id_alta_var_date", function() {
	
	if( !$("#id_nombre_var").val() ) 
	{
		$('#date-error').html('Introduzca un nombre a la variable');
	}
	else{
		if ((!$("#id_fecha_desde").val())||(!$("#id_fecha_hasta").val())){
			$('#date-error').html('Introduzca valores para las fechas');
		}else{
			if($("#id_fecha_desde").val()>$("#id_fecha_hasta").val()){
				$('#date-error').html('La fecha desde no puede ser mayor que la fecha hasta');
			}else{
				if (editar == 'true') {
					//relleno los datos de las variables

					modificarVariableDate();
					localStorage.setItem("editar-var", 'false');
				}
				else
				{
					var existe=comprobarNombreDecimal($("#id_nombre_var").val());
					if (existe===false){
						crearVariableDate();
					}else{
						$('#date-error').html('El nombre de esa variable ya existe');
					}
				}
			}
		}
		
	}

	return false;
});

function crearVariableDate() {
	var formData = $("#id_variable-form").serialize();
	var idapp = $("#idaplicacion").val();
	var nombreVar = $("#id_nombre_var").val();

	$.ajax({
		type : "POST",
		url : domain() + "/api/variable/nuevadate",
		cache : false,
		data : formData,
		success : function(data) {
			$.mobile.changePage(domain() + '/private/dashboard',{reloadPage : false} );
			localStorage.setItem('nombreVariable',nombreVar);
		},
		error : onRegistrationError
	});
}

function modificarVariableDate()
{

	var formData = $("#id_variable-form").serialize();
	var idapp = $("#idaplicacion").val();
	var nombreVar = $("#id_nombre_var").val();
	$.ajax({
		type : "POST",
		url : domain() + "/api/variable/updatedate/" + localStorage.getItem("idvariableVariable"),
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
function comprobarNombreDate(nombre){

	var existe=false;
	$(".nombreVariable").each(function (index) {
		var valor= $(this).attr('data-val');
		if(valor===nombre){
			existe=true;
		}
	});
	return existe;
	
}