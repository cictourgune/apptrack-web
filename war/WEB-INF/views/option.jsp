<!DOCTYPE html>
<html>
<head> 
<%@include file="/WEB-INF/views/header.jsp"%>
</head>

<body>
	<div id="option-page" data-role="page" data-dialog="true" data-close-btn="right" data-corners="false"   data-overlay-theme="o"> 
		
			<div data-role="header">
				<h1>Opción</h1> 
			</div>  
			
			<div data-role="content" > 
			
			<form id="id_variable-form"
				style=" padding: 2%; margin-top: 2%;"
				method="post" data-ajax="false">
				<input class="id_app" type="hidden" name="idaplicacion"
					id="idaplicacion" value="${idAplicacion}" />
				<input class="id_tipo" type="hidden" name="tipo"
					id="id_tipo_var" value="option" />
				 <label
					for="nombre-variable">Nombre de la variable:</label> <input
					type="text" name="nombre" id="id_nombre_var"
					placeholder="min. 1 caracter" />
				<div id="result" style="margin-left: 100px; margin-right: 100px;"></div>

				<div id="div_mul_option" class="div_mul_option" style="margin-bottom: 5%">
					<label for="textarea-a">Introduzca las opciones separadas
						por un punto y coma</label>
					<textarea name="opciones" id="id_opciones">
							</textarea>
					<label><input type="checkbox" name="chk_mul_option" id="chk_mul_option" size="10" /> Multiopción</label> 
				</div>

				<fieldset class="ui-grid-a">
					<div class="ui-block-a">
						<a href="#" id="id_alta_var_option" data-role="button"
							data-transition="none" data-icon="check" data-iconpos="right">Aceptar</a>
					</div>
					<div class="ui-block-b">
						<a href="#" id="id_altaoption_cancel" data-rel="back"
							data-role="button" data-transition="none" data-icon="delete"
							data-iconpos="right">Cancelar</a>
					</div>
				</fieldset>
			</form>

		</div>
		<div id='option-error' data-role="content"
			style="text-align: center;background: rgb(238, 238, 238);"></div>
	</div>
</body>
</html>
