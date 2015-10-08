<!DOCTYPE html> 
<html> 
	<head>   
		<%@include file="/WEB-INF/views/header.jsp"%>		
	</head> 

	<body>  
		<div id="forgotpassword-page" data-role="page">  
			<div data-role="content"> 
				<div align="center" style="min-width:300px; margin-left: 30%; margin-right: 30%;">
					
					<form id="forgotpassword-form" class="ui-body ui-body-a ui-corner-all" method="POST"  data-ajax="false"> 
						<h3>Por favor introduce el email con el que te registraste</h3>
					  
			        
			          			          
						<label for="email" class="ui-hidden-accessible">Email:</label>
						<input type="text" name="email" id="email" value=""  data-theme="a" />
						<div class="ui-grid-a">
							<div class="ui-block-a">
								<!-- OJO que tiene que ser de tipo SUBMIT para que el "enter" lo entienda como un "click" 
									(además en el JS, tiene que ser CLICK y no TAP!!) -->
								<input id="submitForgot" type="submit" data-theme="b" value="Aceptar" />
							</div>
							<div class="ui-block-b"><a id="cancel" href="./" data-role="button" data-theme="a">Cancelar</a></div>
						</div>
						<div id="errorForgotPassword" style="color: red; font-weight: bold;"></div>					  
					</form>  
				</div>
			</div>
			<input type="hidden" id="hdnLang" value="${pageContext.response.locale}" /> 
		</div> 
	</body>	
</html>