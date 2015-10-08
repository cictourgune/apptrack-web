$(document).on("pageshow", "#option-page", function(event) {
	$('.id_app').hide();
	$('.id_tipo').hide();
	editar = localStorage.getItem('editar-var');
	if (editar == 'true') {
		//relleno los datos de las variables
		$("#idaplicacion").val(localStorage.getItem("idaplicacionVariable"));
		$("#id_tipo_var").val(localStorage.getItem("idtipoVariable"));
		$("#id_nombre_var").val(localStorage.getItem("nombrevariableVariable"));
		if(localStorage.getItem("multiopcionVariable")=="true"){
			$("#chk_mul_option").attr("checked",true).checkboxradio("refresh");
		}else{
			$("#chk_mul_option").attr("checked",false).checkboxradio("refresh");
		}

		$("#id_opciones").val(localStorage.getItem("listaOpciones"));
		
		
		localStorage.setItem("editar-var", 'false');
	}
	$("#id_nombre_var").focus();
});

$(document).on("tap", "#id_alta_var_option", function() {
	
	if( !$("#id_nombre_var").val() ) 
	{
		$('#option-error').html('Introduzca un nombre a la variable');
		
	}else{
		var opc = $("#id_opciones").val().trim();
		if (!opc){
			$('#option-error').html('Introduzca los valores de las opciones');
		}else{
			if (editar == 'true') {
				//relleno los datos de las variables

				modificarVariableOption();
				localStorage.setItem("editar-var", 'false');
			}
			else
			{
				var existe=comprobarNombreOption($("#id_nombre_var").val());
				if (existe===false){
					crearVariableOption();
				}else{
					$('#option-error').html('El nombre de esa variable ya existe');
				}
			}
		}	
	}
		
	
	

	return false;
});
 

function crearVariableOption() {
	var formData = $("#id_variable-form").serialize();
	var idapp = $("#idaplicacion").val();
	var nombreVar = $("#id_nombre_var").val();
	
	$.ajax({
		type : "POST",
		url : domain() + "/api/variable/nuevaoption",
		cache : false,
		data : formData,
		success : function(data) {
			$.mobile.changePage(domain() + '/private/dashboard',{reloadPage : false} );
			localStorage.setItem('nombreVariable',nombreVar);

		},
		error : onRegistrationError 
	});
}


function modificarVariableOption()
{

	var formData = $("#id_variable-form").serialize();
	
	var idapp = $("#idaplicacion").val();
	var nombreVar = $("#id_nombre_var").val();
	
	$.ajax({
		type : "POST",
		url : domain() + "/api/variable/updateoption/" + localStorage.getItem("idvariableVariable"),
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
function comprobarNombreOption(nombre){

	var existe=false;
	$(".nombreVariable").each(function (index) {
		var valor= $(this).attr('data-val');
		if(valor===nombre){
			existe=true;
		}
	});
	return existe;
	
}