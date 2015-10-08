<!DOCTYPE html>
<html>
<head>

<%@include file="/WEB-INF/views/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

</head>
<body>

	<div id="dashboard-page" data-role="page">
	
	
		<div data-role="header">
		 	<%@include file="/WEB-INF/views/pageheader.jsp"%>  
		</div>  
		
		
		<!-- MENU  -->
		<div data-role="panel" id="leftmenu" data-position="left"
			data-display="overlay" data-theme="a">
			<ul data-role="listview" style="margin-top:37px">
				<li data-icon="false" data-theme="b"><a href="#" id="menu_gestion">Configuración</a></li>  
				<li data-icon="false" ><a href="#" id="menu_mapas" action="mapaDashboard">Mapas</a></li>  
				<li data-icon="false" ><a href="#" id="menu_informes" action="informeDashboard">Consultas</a></li>  
				<li data-icon="false" ><a href="#" id="menu_informes_2" action="informeDashboard_2">Informes</a></li>  
				<li data-icon="false">
				<a id="info" href="#" action="info">Token desarrollador</a> 
				</li> 
				 <li data-icon="false">
					<a id="docu" action="docu">Documentación</a> 
				</li> 
				<li data-icon="false"><a id="salir">Salir</a></li>
			</ul> 
		</div>


		<div class="ui-content" style="margin-bottom:40px;">
			<div class="row">
				<div class="col3">

					<!-- Listado de aplicaciones -->
					<ul id="aplicaciones_creadas" data-role="listview"
						class="aplicaciones_list" data-split-icon="gear" data-inset="true"
						data-divider-theme="a" data-scroll="true">
						<li data-role="list-divider">Aplicaciones</li>
						<li id="li_botones">
							<div data-role="controlgroup" data-type="horizontal"
								align="center">
								<a href="<%=domain%>/private/aplicacion/nueva"
									id="newApplication" data-role="button" data-icon="plus"
									data-iconpos="left" data-inline="true" data-mini="true">Crear
								</a> <a id="eliminar_app" data-role="button" data-mini="true"
									action="eliminar-app" data-rel="popup"
									data-position-to="window" href="#popupDeleteApp"
									data-icon="delete" class="ui-disabled">Borrar</a> <a
									href="<%=domain%>/private/aplicacion/duplicar" id="duplicar"
									data-role="button" action="duplicar" data-mini="true"
									data-icon="recycle" class="ui-disabled">Duplicar </a>
							</div>
						</li>
						<c:forEach items="${listaAplicaciones}" var="aplicaciones">
							<li id="li_${aplicaciones.idaplicacion}"><a
								id="${aplicaciones.idaplicacion}"
								value="${aplicaciones.nombreaplicacion}" action='edit' href="#">
									<h3 class="nombreApplicacion"
										data-val="${aplicaciones.nombreaplicacion}">${aplicaciones.nombreaplicacion}</h3>
									<p>${aplicaciones.descripcion}</p>
							</a></li>
						</c:forEach>
					</ul>
				</div>

				<div class="col9">

					<ul id="nombre_desc" data-role="listview" data-theme="d"
						class="nombre_desc_list" data-split-icon="gear" data-inset="true"
						data-divider-theme="a">



						<li>
							<ul id="nombre_desc" data-role="listview" data-theme="d"
								class="nombre_desc_list" data-split-icon="gear"
								data-inset="true" data-divider-theme="a"> 
 
								<li id="li_botones_nom_desc">
									<div>
										<label for="nombre_aplicacion">Nombre: </label> <input
											type="text" name="nombre_aplicacion"
											id="id_nombre_aplicacion" value="${nombreAplicacion}"
											readonly="readonly" /> </br>
									</div>
									<div>
										<label for="descripcion_aplicacion">Descripcion: </label>
										<textarea name="descripcion_aplicacion" rows="3"
											id="id_descripcion_aplicacion" style="height: 100px;"
											value="${descripcionAplicacion}" readonly="readonly"> </textarea>
									</div>
									<div>
										<label for="token_aplicacion">Token de app: </label> <input
											type="text" name="token_aplicacion" id="id_token_aplicacion"
											value="${tokenAplicacion}" readonly="readonly" />
									</div>


									<div class="row">

										<div class="col6">

											<div data-role="controlgroup" data-type="horizontal"
												align="center">

												<!-- 		<a href="#" id="mostrar_mapa_dashboard" data-role="button"
											action="mapaDashboard" data-mini="true" data-icon="search"
											class="ui-disabled"> Mapa</a>  
										<a href="#"
											id="mostrar_informe" data-role="button"
											action="informeDashboard" data-mini="true" data-icon="grid"
											class="ui-disabled"> Informes</a> -->
												<a href="#" id="newVisor" action="nuevo-visor"
													data-role="button" data-icon="plus" data-iconpos="left"
													data-inline="true" data-mini="true" class="ui-disabled">Crear visor</a>
											</div>
										</div>

										<div class="col6">
											<div data-role="controlgroup" data-type="horizontal"
												align="center">

												<!-- 		<a href="#" id="mostrar_mapa_dashboard" data-role="button"
											action="mapaDashboard" data-mini="true" data-icon="search"
											class="ui-disabled"> Mapa</a>  
										<a href="#"
											id="mostrar_informe" data-role="button"
											action="informeDashboard" data-mini="true" data-icon="grid"
											class="ui-disabled"> Informes</a> -->

												<a href="#" id="nuevavar" data-role="button"
													data-icon="plus" data-iconpos="left" data-mini="true"
													data-inline="true" class="ui-disabled">Crear variable:</a>
												<select name="tipo" id="tipovar" class="select_type"
													data-native-menu="false" data-mini="true">
													<option value="int">entero</option>
													<option value="mul_option">opción</option>
													<option value="date">fecha</option>
													<option value="decimal">decimal</option>
												</select>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col6">
											<!-- Visores de la aplicacion -->
											<ul id="visores_creados" data-role="listview"
												class="visores_list" data-inset="true"
												data-divider-theme="a">
												<li id="li_botones_visores" data-role="list-divider">Visores</li>
											</ul>
										</div>
										<div class="col6">
											<!-- Variables de aplicación -->
											<ul id="variables_creadas" data-role="listview"
												class="variables_list" data-inset="true"
												data-divider-theme="a">
												<li id="li_botones_variables" data-role="list-divider">Variables</li>
											</ul>
										</div>
									</div>
								</li>
							</ul>

						</li>

					</ul>

				</div>



			</div>


		</div>







		<!-- 	POPUPS		 -->
 			
 			
<div data-role="popup" id="popupDeleteApp"   data-overlay-theme="b"  data-dismissible="false">
	<a href="#" data-rel="back" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right">Close</a>
    <div data-role="header" data-theme="a">
    <h1>Eliminar aplicación</h1>
    </div>
    <div role="main" class="ui-content">
        <h2 class="ui-title">Se va a eliminar la app seleccionada</h2>
	    <p class="margin_top_35">¿Quieres continuar?</p>  
	    
	   <div class="row"> 
	   <div class="col6">
				   <a id="confirmacion-eliminacion-ok" data-role="button" data-rel="back" data-corners="false" class="no-shadow">Si</a>       
			</div>	
			<div  class="col6"> 
				<a  data-role="button" data-rel="back" data-corners="false">No</a>  
			</div>
				
		</div> 
    </div>
</div>





<div data-role="popup" id="popupDeleteVisor"   data-overlay-theme="b"  data-dismissible="false">
	<a href="#" data-rel="back" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right">Close</a>
    <div data-role="header" data-theme="a">
    <h1>Eliminar visor</h1>
    </div>
    <div role="main" class="ui-content">
        <h2 class="ui-title">Se va a eliminar el visor seleccionado</h2>
	    <p class="margin_top_35">¿Quieres continuar?</p>  
	    
	   <div class="row"> 
	   	<div class="col6">
				   <a action="delete-visor" data-role="button" data-rel="back" data-corners="false" class="no-shadow">Si</a>       
			</div>	
			<div  class="col6"> 
				<a  data-role="button" data-rel="back" data-corners="false">No</a>  
			</div>
			
		</div> 
    </div>
</div>
 			
 			
<div data-role="popup" id="popupDeleteVar"   data-overlay-theme="b"  data-dismissible="false">
	<a href="#" data-rel="back" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right">Close</a>
    <div data-role="header" data-theme="a">
    <h1>Eliminar variable</h1>
    </div>
    <div role="main" class="ui-content">
        <h2 class="ui-title">Se va a eliminar la variable seleccionada</h2>
	    <p class="margin_top_35">¿Quieres continuar?</p>  
	    
	   <div class="row"> 
	   	<div class="col6">
				   <a action="delete-var" data-role="button" data-rel="back" data-corners="false" class="no-shadow">Si</a>       
			</div>	
			<div  class="col6"> 
				<a  data-role="button" data-rel="back" data-corners="false">No</a>  
			</div>
			
		</div> 
    </div>
</div>
 			
 			
 			
 			
 			
		
		
		</div>
 
 

</body>
</html>