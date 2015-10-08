<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>

<head>
<%@ include file="/WEB-INF/views/header.jsp"%>
</head>
<body>
	<div id="dashboard-page" data-role="page">	
			<div class="ui-content" style="margin-top:40px;" align="center">
				<div class="row">
					<div class="col12">
							<img alt=""
							src="<c:url value="/resources/img/logo.png"/>" height="70px"
							style="margin-top:0px">
					</div>
				</div>
				<div class="row" style="width: 30%" >
					<div class="col6">
							<a href="<%=domain%>/private/dashboard" id="entrar"   
							data-role="button" data-corners="false">Entra</a>
					</div>
					<div class="col6">
							<a href="<%=domain%>/registro" id="registrar" data-theme="b"
							data-role="button" data-corners="false">Regístrate</a>
					</div>
				</div> 
			</div>	 
	</div>
</body>


</html>