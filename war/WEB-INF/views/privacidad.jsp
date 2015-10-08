<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
	<head> 
		<%@ include file="/WEB-INF/views/header.jsp"%>  
	</head> 

<body>

  <link rel="stylesheet" href="./resources/landing/bootstrap.css" />  
    <script src="./resources/landing/bootstrap.js"></script>  
 	<link href="./resources/landing/main.css" rel="stylesheet">  
	 
		<div id="privacidad-page"> 
			 <!-- Navbar -->
		   	<div class="navbar navbar-fixed-top">
		      <div class="navbar-inner">
		        <div class="container">
		    		<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
		          	</a>
		          	<a id="nombreweb" class="brand scroller" href="#">apptrack</a>
				  	<div class="nav-collapse collapse">
		                <ul class="nav pull-right">
		                    <li><a href="<%=domain%>/index" class="scroller" data-section="#about"><font color="#d54872">Descripción</font></a></li>
		                    <li><a href="<%=domain%>/index" class="scroller" data-section="#team"><font color="#d54872">Equipo</font></a></li>
		                    <li><a href="<%=domain%>/index" class="scroller" data-section="#contact"><font color="#d54872">Contacto</font></a></li> 
		                    <li><a href="<%=domain%>/privacidad"><font color="#d54872">Privacidad</font></a></li> 
		                    <li><a href="<%=domain%>/documentacion"><font color="#d54872">Documentacion</font></a></li> 
		                </ul>
			        </div>
		        </div>
		      </div>
		    </div>  
		    <!-- end Navbar -->
    
    
			<div class="container" style="margin-top:80px">
				<br>
				<h1>Política de cookies</h1>
				<h3>¿Qué son las cookies?</h3>
				<p>Esta web, como muchas otras, usa cookies.  Una cookie es un archivo simple de texto que es almacenado en tu ordenador o móvil por el servidor web. Contiene información anónima que permite a un website recordar cosas, como tus preferencias. 
				Las cookies pueden ayudarte a conseguir una mejor experiencia de navegación y nos ayudan a mejorar nuestra web.</p>
				
				<p>Hemos usado dos tipos de cookies en esta web:</p>
				<ul>
					<li>Cookies de sesión. Son cookies temporales que solo existen hasta que cierras el navegador que estás usando para acceder a la web.</li>
					<li>Cookies duraderas. Estas son cookies que permanecen en tu archivo de cookies después de que hayas visitado nuestra web. Estas cookies nos ayudan a identificarte cuando vuelves a nuestra web.</li>
				</ul> 
				
				<h3>¿Para que usamos las cookies?</h3>
				<p>Ninguna de las cookies usadas en nuestra web recoge información personal identificable sobre tu persona.
				Utilizamos cookies para monitorear el tráfico de nuestra web. Usamos Google Analytics, un servicio de análisis web proporcionado por Google. Google Analytics utiliza cookies para ayudarnos a analizar cómo los visitantes usan nuestra web. Para saber más sobre cómo se utilizan esas cookies visita el Google Privacy.
				</p>
				<h3>Cómo controlar y borrar cookies</h3> 
				<p>No usamos cookies para recoger información personal identificable sobre ti. Si deseas restringir o bloquear todas las cookies que hemos establecido, o de otras webs puedes hacerlo a través de tus opciones de navegador. 
				La función Ayuda de tu navegador te dirá cómo hacerlo.
				Alternativamente, puedes visitar www.aboutcookies.org que contiene información completa de cómo hacerlo en una gran variedad de navegadores. 
				Encontrarás detalles sobre cómo borrar las cookies de tu ordenaro (incluidas aquellas sobre esta visita) así cómo información más general sobre cookies. 
				Para hacer esto en el navegador de tu dispositivo móvil necesitarás consultar el manual del mismo.</p>
				<br>
				<h1>Datos de localización</h1>
				<p>Cuando un usuario utiliza una aplicación con apptrack, se le pide permiso explícitamente para acceder a su localización en el proceso de instalación. Estos datos son tratados de manera totalmente anónima y con fines única y exclusivamente científicos.
				Estos datos nos ayudan a estudiar el comportamiento de las personas en movilidad. Puedes modificar la configuración de tu dispositivo móvil para evitar que la página web almacene tu localización. 
				Por ejemplo, si tienes un móvil Android, puedes configurar esta opción tal y como se describe <a href="http://support.google.com/android/bin/answer.py?hl=es&answer=1722193" target="_blank">aquí</a>. 
				</p>
				
				
			</div>
			<br>
			<div data-role="footer" data-position="fixed">
				<div align="center" style="background-color:black"> 
					<div>
						<a href="http://it3lab.tourgune.org" target="_blank" data-role="none" ><img src="<%=domain%>/resources/images/it3lab_RGB_transparente_ajustada.png" width="124" height="35" style="margin-top:12px;"></a>
						<a href="http://www.tourgune.org" target="_blank" data-role="none" ><img src="<%=domain%>/resources/images/cictourgune_rgb_peque.png" width="199" height="35" style="padding:6px;"></a>
					</div> 
					<br>
				</div>
     		</div>
		</div>		 
		
	</body>	
</html>