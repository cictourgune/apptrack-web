<!DOCTYPE html> 
<html> 
	<head>   
		<%@include file="/WEB-INF/views/header.jsp"%>		
	</head> 

	<body>   
			
	 <div id="duplicationapp-page"  data-role="page" data-dialog="true" data-close-btn="right" data-corners="false"   data-overlay-theme="a"> 
			<div data-role="header">
			<h1>Duplicar aplicación</h1> 
			</div> 
			<div data-role="content"> 
								
			 	<form id="id_dup_application_form" method="post" data-ajax="false"> 
					  <label for="dup_application_name" >Nombre</label>
			          <input type="text" name="du_application_name" id="id_dup_application_name" placeholder="min. 1 caracteres"/>
			          <label for="dup_application_description" >Descripción</label>
			          <textarea name="dup_application_description" id="id_dup_application_description"></textarea>
					  <fieldset class="ui-grid-a">
						<div class="ui-block-a">
							<a href="#" id="id_dup_app_submit" data-role="button" data-transition="none" data-icon="check" data-iconpos="right">Aceptar</a> 
						</div>
						<div class="ui-block-b">
							<a href="#" id="id_dup_app_cancel" data-rel="back" data-role="button" data-transition="none" data-icon="delete" data-iconpos="right">Cancelar</a> 
						</div>
		   			  </fieldset>
					</form>  
				
			</div> 
			 	<div id='application-error' data-role="content" style="text-align: center;background: rgb(238, 238, 238);">    
				</div> 
	 	</div>
	</body>	
</html>
