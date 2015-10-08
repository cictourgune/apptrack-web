<!DOCTYPE html> 
<html> 
	<head> 
		<%@ include file="/WEB-INF/views/header.jsp"%>		
	</head> 

	<body>  
 		<div id="registro-page" data-role="page" data-dialog="true" data-close-btn="right" data-corners="false"   data-overlay-theme="a"> 
 		
			<div data-role="header">
				<h1>Registro</h1> 
			</div> 
			 
			<div data-role="content"> 
				<form id="registro-form" method="post" data-ajax="false">
					<label for="registro-usuario">Nombre de usuario</label> 
					<input type="text" name="name" id="registro-usuario" placeholder="min. 5 caracteres"/> 
					<label for="registro-password">Contrase�a</label> 
					<input type="password" name="password" id="registro-password" placeholder="min. 7 caracteres"/>
					<label for="registro-repite-password">Repita contrase�a</label> 
					<input type="password" name="repite-password" id="registro-repite-password"/> 
				    <label for="email">Correo electr�nico</label>
		            <input type="email" name="email" id="email" value="" placeholder="Recibir� un correo de confirmaci�n en esta direcci�n"/>
		            
					<fieldset class="ui-grid-a">
						<div class="ui-block-a">
							<a href="#" id="registro-submit" data-role="button" data-transition="none" data-icon="check" data-iconpos="right">Aceptar</a> 
						</div>
						<div class="ui-block-b">
							<a href="#" id="registro-cancel-submit" data-role="button" data-transition="none" data-icon="delete" data-iconpos="right">Cancelar</a> 
						</div>
		   			</fieldset>
				</form>	  
			</div>	
			
			<div id='registro-error' data-role="content" style="text-align: center;">  
			</div> 	 
		</div>		
 	
 	
 	
	</body>	
</html>