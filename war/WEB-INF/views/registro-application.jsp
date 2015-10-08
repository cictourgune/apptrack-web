<!DOCTYPE html> 
<html> 
	<head>   
		<%@include file="/WEB-INF/views/header.jsp"%>		
	</head> 

	<body>  
		<div id="aplication-page"  data-role="page" data-dialog="true" data-close-btn="right" data-corners="false"   data-overlay-theme="a"> 
			<div data-role="header">
				<h1>Nueva aplicación</h1> 
			</div> 
			<div data-role="content"> 
								
					<form id="id_newapplication_form" method="post" data-ajax="false"> 
					  <label for="application_name" >Nombre</label>
			          <input type="text" name="application_name" id="id_application_name" placeholder="min. 1 caracteres"/>
			          <label for="application_description" >Descripción</label>
			          <textarea name="application_description" id="id_application_description"></textarea>
			          <label for="application_origen" >Origen</label>
			          <select name="application_origen" id="id_application_origen">
        					<option value="38">España</option>
        					<option value="39">Andalucía</option>
        					<option value="41">Aragon</option>
        					<option value="42">Baleares</option>
       	 					<option value="43">Canarias</option>
       	 					<option value="44">Cantabria</option>
        					<option value="45">Castilla y león</option>
        					<option value="46">Castilla la mancha</option>
       	 					<option value="47">Cataluña</option>
       	 					<option value="55">Ceuta</option>
       	 					<option value="49">Extremadura</option>
        					<option value="54">Euskadi</option>
        					<option value="50">Galicia</option>
        					<option value="55">La Rioja</option>
       	 					<option value="51">Madrid</option>
       	 					<option value="55">Melilla</option>
       	 					<option value="52">Murcia</option>
        					<option value="53">Navarra</option>      	 					
       	 					<option value="48">Valencia</option>
       	 					
   				 	  </select>
					  
					  <fieldset class="ui-grid-a">
						<div class="ui-block-a">
							<a href="#" id="id_altaapp_submit" data-role="button" data-transition="none" data-icon="check" data-iconpos="right">Aceptar</a> 
						</div>
						<div class="ui-block-b">
							<a href="#" id="id_altaapp_cancel" data-rel="back" data-role="button" data-transition="none" data-icon="delete" data-iconpos="right">Cancelar</a> 
						</div>
		   			  </fieldset>
					</form>  
				
			</div> 
				<div id='application-error' data-role="content" style="text-align: center;background: rgb(238, 238, 238);">  
			</div> 
		</div> 
	</body>	
</html>
