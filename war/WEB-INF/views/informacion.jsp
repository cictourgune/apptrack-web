<!DOCTYPE html>
<html>
<head> 
<%@include file="/WEB-INF/views/header.jsp"%>
</head>

<body> 
		
	 <div id="information-page"  data-role="page" data-dialog="true" data-close-btn="right" data-corners="false"   data-overlay-theme="a"> 
			<div data-role="header">
				<h1>Información</h1> 
			</div> 
			<div data-role="content"> 

			<form id="id_info-form"
				style=" padding: 2%; margin-top: 2%; "
				method="post" data-ajax="false">
				<label for="info_developer"><h3>Token del desarrollador:</h3></label> 
				<input type="text" name="nombre" id="id_info_dev" readonly="readonly" />
	
			</form> 
		</div>
		 
	</div>
</body>
</html>
