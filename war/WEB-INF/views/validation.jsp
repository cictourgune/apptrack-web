<!DOCTYPE html> 
<html> 
	<head>  
		<%@ include file="/WEB-INF/views/header.jsp"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	</head> 

	<body> 
	 
		<div id="validation-page" data-role="page" align="center"> 
			<br>
			<br>
			<br>
			<h1>Mira la bandeja de entrada para validar tu cuenta de usuario</h1>  
			<div class="ui-content">
				<a href="<c:url value="/private/dashboard"/>" class="ui-btn ui-btn-inline ui-icon-check ui-btn-icon-right">Aceptar</a>  
			</div>
			
		</div>		 
		
	</body>	
</html>