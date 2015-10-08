<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%
String token = request.getParameter("token");
String email = request.getParameter("email");
 %>
<!DOCTYPE html> 
<html> 
	<head>   
		<%@include file="/WEB-INF/views/header.jsp"%>		
	</head> 

	<body>  
		<div id="restorepassword-page" data-role="page">  
			<div data-role="content"> 
				<div align="center" style="min-width:300px; margin-left: 30%; margin-right: 30%;">
					
					<form id="restorepassword-form" class="ui-body ui-body-a ui-corner-all" method="post"  data-ajax="false"> 
<!-- 					  <h3><spring:message code="userPass"/></h3> -->
						<h3>Introduce 2 veces la nueva contraseña</h3>
					  

			          			          
			          <label for="password1" class="ui-hidden-accessible">Contraseña:</label>
			          <input type="password" name="password1" id="password1" value="" placeholder="Contraseña" data-theme="a" />
			          <label for="password2" class="ui-hidden-accessible">Repite contraseña:</label>	 
			          <input type="password" name="password2" id="password2" value="" placeholder="Repite contraseña " data-theme="a" />
			          
	 				  <div class="ui-grid-a">
				    	  <div class="ui-block-a"><a id="submitRestore" href="#" data-theme="b" data-role="button">Aceptar</a></div>
				    	  <div class="ui-block-b"><a id="cancel" href="./" data-role="button" data-theme="a">Cancelar</a></div>
				   	  </div>
			    	  <div id="errorRestorePassword" style="color: red; font-weight: bold;">
					  </div>
					  
					</form>  
				</div>
			</div> 
			<input type="hidden" id="token" value="<%=token %>" />
			<input type="hidden" id="email" value="<%=email %>" />
		</div> 
	</body>	
</html>