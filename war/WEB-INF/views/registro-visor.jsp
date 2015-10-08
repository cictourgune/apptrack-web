<!DOCTYPE html> 
<html> 
	<head>   
		<%@include file="/WEB-INF/views/header.jsp"%>		
	</head> 

	<body>  
		<div id="visor-page"  data-role="page" data-dialog="true" data-close-btn="right" data-corners="false"   data-overlay-theme="a"> 
		
			<div data-role="header">
				<h1>Nuevo visor</h1> 
			</div>  
			
			<div data-role="content" > 
								
					<form id="id_newvisor_form" method="post" data-ajax="false"> 
					  <label for="visor_name" >Nombre</label>
			          <input type="text" name="visor_name" id="id_visor_name" placeholder="min. 3 caracteres"/>
			          <label for="visor_pwd" >Contraseña</label>
			          <input type="password" name="visor_pwd" id="id_visor_pwd" />
			          <label for="visor_pwd_rep" >Repita contraseña</label>
			          <input type="password" name="visor_pwd_rep" id="id_visor_pwd_rep" />
					  
					  
					  
					  <fieldset class="ui-grid-a">
						<div class="ui-block-a">
							<a href="#" id="id_altavisor_submit" data-role="button" data-transition="none" data-icon="check" data-iconpos="right">Aceptar</a> 
						</div>
						<div class="ui-block-b">
							<a href="#" id="id_altavisor_cancel" data-rel="back" data-role="button" data-transition="none" data-icon="delete" data-iconpos="right">Cancelar</a> 
						</div>
		   			  </fieldset>
					</form>  
				
			</div> 
				<div id='application-error-visor' data-role="content" style="text-align: center;background: rgb(238, 238, 238);">  
			</div> 
		</div> 
	</body>	
</html>
