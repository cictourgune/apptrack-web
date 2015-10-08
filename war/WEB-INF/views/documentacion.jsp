<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html> 
<html> 
	<head>  
		<%@ include file="/WEB-INF/views/header.jsp"%>
	</head> 

	<body> 
			
		<div id="documentacion-page" data-role="page">
	
	
		<div data-role="header">
		 	<%@include file="/WEB-INF/views/pageheader.jsp"%>  
		</div>  
		
		
		<!-- MENU  -->
		<div data-role="panel" id="leftmenu" data-position="left"
			data-display="overlay" data-theme="a">
			<ul data-role="listview" style="margin-top:37px">
				<li data-icon="false" ><a href="#" id="menu_gestion">Configuración</a></li>  
				<li data-icon="false" ><a href="#" id="menu_mapas" action="mapaDashboard">Mapas</a></li>  
				<li data-icon="false" ><a href="#" id="menu_informes" action="informeDashboard">Consultas</a></li>  
				<li data-icon="false">
				<a id="info" href="#" action="info">Token desarrollador</a> 
				</li> 
				 <li data-icon="false" data-theme="b">
					<a id="docu" action="docu">Documentación</a> 
				</li> 
				<li data-icon="false"><a
					href="<%=domain%>/j_spring_security_logout">Salir</a></li>
			</ul> 
		</div>


		<div class="ui-content" style="margin-left:140px;margin-right:140px"> 
				<h1>AppTrack v1.3</h1>
				<br>
				
				<p>Mejoras respecto a la versión anterior:<br>
				- Entre capturas de puntos pasarán mínimo 120 segundos (en android)<br>
				- Se almacenará la plataforma con la que está accediendo al sistema cada usuario (android/iOS/javascript)</p>
				
				<h2>Android</h2>
				<p>Para descargar la librería apptrack para el desarrollo de aplicaciones android de modo nativo, pulse <a href="https://www.dropbox.com/s/vq2ltjqc50poxc3/apptrack-android-sdk-1.3.zip">AQUI</a>. Dentro encontrará la documentación junto a un proyecto de ejemplo</p>

				<h2>Android + PhoneGap</h2>
				<p>Para descargar la librería apptrack para el desarrollo de aplicaciones android utilizando PhoneGap, pulse <a href="https://www.dropbox.com/s/ea1dpiltr2ril1a/apptrack-androidPhonegap-sdk-1.3.zip">AQUI</a>. Dentro encontrará la documentación junto a un proyecto de ejemplo</p>

				<h2>JavaScript</h2>
				<p>Para descargar la librería apptrack para el desarrollo de aplicaciones bajo la plataforma JavaScript, pulse <a href="https://www.dropbox.com/s/j2jsf1xrrm7wh3i/apptrack-javascript-sdk-1.3.zip">AQUI</a>. Dentro encontrará la documentación junto a un proyecto de ejemplo</p>
				
				<h2>iOS (frecuencia de localización alta)</h2>
				<p>Para descargar la librería apptrack para el desarrollo de aplicaciones bajo la plataforma iOS obteniendo la localización frecuentemente basandose en la distancia recorrida, pulse <a href="https://www.dropbox.com/s/y2c3hr402xlyll2/apptrack-iOS-sdk%28%2Bfrec%29-1.3.zip">AQUI</a>. Dentro encontrará la documentación</p>
				
				<h2>iOS + PhoneGap (frecuencia de localización alta)</h2>
				<p>Para descargar la librería apptrack para el desarrollo de aplicaciones iOS utilizando PhoneGap obteniendo la localización cada vez que el dispositivo se conecta a una nueva antena, pulse <a href="https://www.dropbox.com/s/am8s1u8tr59jiv1/apptrack-iOSPhonegap-sdk%28%2Bfrec%29-1.3.zip">AQUI</a>. Dentro encontrará la documentación</p>
				
				<h2>iOS (frecuencia de localización baja)</h2>
				<p>Para descargar la librería apptrack para el desarrollo de aplicaciones bajo la plataforma iOS obteniendo la localización frecuentemente basandose en la distancia recorrida, pulse <a href="https://www.dropbox.com/s/yngg5nkayus8rm9/apptrack-iOS-sdk%28-frec%29-1.3.zip">AQUI</a>. Dentro encontrará la documentación</p>
				
				<h2>iOS + PhoneGap (frecuencia de localización baja)</h2>
				<p>Para descargar la librería apptrack para el desarrollo de aplicaciones iOS utilizando PhoneGap obteniendo la localización cada vez que el dispositivo se conecta a una nueva antena, pulse <a href="https://www.dropbox.com/s/pcpwduvzpuklh04/apptrack-iOSPhonegap-sdk%28-frec%29-1.3.zip">AQUI</a>. Dentro encontrará la documentación</p>
		 
			<br>
		
		</div>	
		
		</div>	 
		
	</body>	
</html>
