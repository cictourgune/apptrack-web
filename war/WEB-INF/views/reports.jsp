 
<!DOCTYPE html>
<html>
<head>

<%@include file="/WEB-INF/views/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
</head>

<body>

	<div id="informe-page" data-role="page">
 
	  <div data-role="header">
		 	<%@include file="/WEB-INF/views/pageheader.jsp"%>  
		</div>  
		
		
				 		 	 		 	 
<sec:authorize access="hasRole('ROLE_REGISTERED')">

<!-- MENU  -->
		<div data-role="panel" id="leftmenu" data-position="left"
			data-display="overlay" data-theme="a">
			<ul data-role="listview" style="margin-top:37px">
				<li data-icon="false"><a href="#" id="menu_gestion">Configuración</a></li>  
				<li data-icon="false" ><a href="#" id="menu_mapas" action="mapaDashboard">Mapas</a></li>  
				<li data-icon="false"  data-theme="b"><a href="#" id="menu_informes" action="informeDashboard">Consultas</a></li>  
					<li data-icon="false" ><a href="#" id="menu_informes_2" action="informeDashboard_2">Informes</a></li>  
				<li data-icon="false">
				<a id="info" href="#" action="info">Token desarrollador</a> 
				</li> 
				 <li data-icon="false">
					<a id="docu" action="docu">Documentación</a> 
				</li> 
				<li data-icon="false"><a
					href="<%=domain%>/j_spring_security_logout">Salir</a></li>
			</ul> 
		</div>
 
</sec:authorize> 

<sec:authorize access="hasRole('ROLE_VISOR')">

		<div data-role="panel" id="leftmenu" data-position="left"
			data-display="overlay" data-theme="a">
			<ul data-role="listview" style="margin-top:37px">
				<li data-icon="false" ><a href="#" id="menu_mapas_visor" action="mapaDashboardVisor">Mapa</a></li>  
				<li data-icon="false" data-theme="b"><a href="#" id="menu_informes_visor"  action="informeDashboardVisor">Consultas</a></li>  
					<li data-icon="false" ><a href="#" id="menu_informes_2" action="informeDashboard_2">Informes</a></li>  
				<li data-icon="false"><a
					href="<%=domain%>/j_spring_security_logout">Salir</a></li>
			</ul> 
		</div>
		
 
</sec:authorize> 

		
			<div class="ui-content" style="margin-bottom:40px;">
			<div class="row">
			
			
			<sec:authorize access="hasRole('ROLE_REGISTERED')">
			
				<div class="col3">

					<!-- Listado de aplicaciones -->
					<ul id="aplicaciones_creadas" data-role="listview"
						class="aplicaciones_list" data-split-icon="gear" data-inset="true"
						data-divider-theme="a" data-scroll="true">
						<li data-role="list-divider">Aplicaciones</li>
						<li id="li_botones">
							<div data-role="controlgroup" data-type="horizontal"
								align="center">
								 
							</div>
						</li>
						<c:forEach items="${listaAplicaciones}" var="aplicaciones">
							<li id="li_${aplicaciones.idaplicacion}"><a
								id="${aplicaciones.idaplicacion}"
								value="${aplicaciones.nombreaplicacion}" action='selectAppReport' href="#">
									<h3 class="nombreApplicacion"
										data-val="${aplicaciones.nombreaplicacion}">${aplicaciones.nombreaplicacion}</h3>
									<p>${aplicaciones.descripcion}</p>
							</a></li>
						</c:forEach>
					</ul>
				</div> 
				<div class="col7">
			</sec:authorize>
			
			<sec:authorize access="hasRole('ROLE_VISOR')">
			<div class="col10">
			</sec:authorize>
				
				
				
				<ul data-role="listview"  data-inset="true" data-divider-theme="a" data-theme="d"> 
						<li data-role="list-divider">${nombreAplicacion}</li>
						 
						<li>
							<div id="informe" style="height: 600px"></div>
						</li>  
					</ul> 
					
					
				</div>
				<div class="col2">
				
				
					<ul data-role="listview"  data-inset="true" data-divider-theme="a" > 
						<li data-role="list-divider">Rango de fechas</li>
						<li>
							<div data-role="fieldcontain">
								<label for="fecha_desde_est" style='padding-top: 5px;'><b>Desde:</b>
								</label> <input type='date' name="fecha_desde_est" id="fecha_desde_est"
									data-role='datebox' data-tipo='3'
									placeholder="Ejemplo: 2013-02-27"> </input>
							</div>

							<div data-role="fieldcontain">
								<label for="fecha_hasta_est" style='padding-top: 5px;'><b>Hasta:</b>
								</label> <input type='date' name="fecha_hasta_est" id="fecha_hasta_est"
									data-role='datebox' data-tipo='3'
									placeholder="Ejemplo: 2013-02-27"> </input>
							</div>
						</li>  
					</ul> 
					<ul data-role="listview"  data-inset="true" data-divider-theme="a" > 
						<li data-role="list-divider">Variables</li>
						<li>
							 <fieldset data-role="controlgroup">
						      	<c:forEach items="${listaVariables}" var="variables">
							        <input type="radio" name="radio-choice" id="radio_${variables.idvariable}" value="${variables.idvariable}" checked="checked" class="radio" namevar="${variables.nombrevariable}" tipovar="${variables.idtipo}"/>
							        <label for="radio_${variables.idvariable}" id="radio_${variables.idvariable}"><b>${variables.nombrevariable}</b></label>
							    </c:forEach>
							    <input type="radio" name="radio-choice" id="radio_muestras" value="muestras" class="radio" namevar="muestras" tipovar="tipo_muestras" data-theme="a"/>
							    <label for="radio_muestras" id="radio_muestras"><b>Resumen muestras</b></label>
						      </fieldset> 
						      <div>
									<a href='#' data-role='button' data-icon='search'
									action='consultar-estadisticas' data-theme="b">BUSCAR</a>
							  </div>
							  <div id='filtro-estadisticas-error' data-role="content"
									style="text-align: center;"></div>
						</li>
					</ul> 
				
				</div> 
				
				</div> 
		</div>


	</div>
</body>
</html>