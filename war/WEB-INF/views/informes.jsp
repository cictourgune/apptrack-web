 
<!DOCTYPE html>
<html>
<head>

<%@include file="/WEB-INF/views/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
</head>

<body>

	<div id="informes-page" data-role="page">
 
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
				<li data-icon="false"  ><a href="#" id="menu_informes" action="informeDashboard">Consultas</a></li>  
					<li data-icon="false" data-theme="b"><a href="#" id="menu_informes_2" action="informeDashboard_2">Informes</a></li>  
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
				<li data-icon="false" ><a href="#" id="menu_informes_visor"  action="informeDashboardVisor">Consultas</a></li>  
					<li data-icon="false" data-theme="b"><a href="#" id="menu_informes_2" action="informeDashboard_2">Informes</a></li>  
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
										value="${aplicaciones.nombreaplicacion}" action='selectAppInforme' href="#">
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
							<li data-role="list-divider">Informe</li>
							 
							<li>
								<div id="informe"  style="text-align:center; margin: 0 auto" >
									<h1 id="informe_periodico" style="font-size: 2em; color:#0174DF" ></h1>
									</br></br>
									<div id="tabla_resumen" style="visibility: hidden;">
										<table class="tb1" align="center" border="1">
										    <tr>
										        <td id="usuarios_tot" width="50%"></td>
										        <td id="usuarios_rec" width="50%"></td>
										    </tr>
										    <tr>
										        <td id="visitantes_nuevos" width="50%"></td>
										        <td id="visitantes_no_destino" width="50%"></td>
										    </tr>
										 </table>
									 </div>
									 </br></br>
									 <h2 id="h2_usuarios_nuevos_recurrentes" style="visibility: hidden;">Distribución entre usuarios nuevos y usuarios recurrentes</h2>
									 </br>
								  	 <div id="estudio_usuarios_nuevos_recurrentes" style="width:100%;height:300px;margin: 0 auto" class="ui-grid-b"></div>
								  	 </br></br>
								  	 <h2 id="h2_dias_del_mes" style="visibility: hidden;">Estudio temporal (días del mes)</h2>
								  	 <p id="p_dias_del_mes" style="visibility: hidden;">Puntos de localización obtenidos y número de usuarios de la app <br/> Indica los días de mayor actividad por parte de los visitantes</p>
								  	 </br>
								  	 <div id="estudio_temporal_mes_usuarios" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br>
								  	 <div id="estudio_temporal_mes_puntos" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br>
								  	 <div id="estudio_temporal_mes" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br></br>
								  	 <h2 id="h2_dias_semana" style="visibility: hidden;">Estudio temporal (días de la semana)</h2>
								  	 <p id="p_dias_semana" style="visibility: hidden;">Puntos de localización obtenidos y número de usuarios de la app <br/> Indica los días de mayor actividad por parte de los visitantes</p>
								  	 </br>
								  	 <div id="estudio_temporal_semana_usuarios" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br>
								  	 <div id="estudio_temporal_semana_puntos" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br>
								  	 <div id="estudio_temporal_semana" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br></br>
								  	 <h2 id="h2_horas" style="visibility: hidden;">Estudio temporal (horas)</h2>
								  	 <p id="p_horas" style="visibility: hidden;">Puntos de localización obtenidos y número de usuarios de la app <br/> Indica los días de mayor actividad por parte de los visitantes</p>
								  	 </br>
								  	 <div id="estudio_temporal_hora_usuarios" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br>
								  	 <div id="estudio_temporal_hora_puntos" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br>
								  	 <div id="estudio_temporal_hora" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br></br>
								  	 <h2 id="h2_ranking_municipios" style="visibility: hidden;">Ranking de municipios visitados</h2>
								  	 </br>
								  	 <div id="ranking_municipios" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br></br>
								  	 <h2 id="h2_ranking_comunidades" style="visibility: hidden;">Ranking de visitas por comunidad de origen</h2>
								  	 </br>
								  	 <div id="ranking_comunidades" style="width:100%;height:300px;margin: 0 auto"></div>
								  	 </br></br>
								  	 <h2 id="h2_ranking_descargas" style="visibility: hidden;">Ranking de descargas de la aplicación por comunidad</h2>
								  	 </br>
								  	 <div id="ranking_descargas" style="width:100%;height:300px;margin: 0 auto"></div>
								</div>
							</li>  
						</ul> 
						
					</div>
					<div class="col2">
					
					
						<ul data-role="listview" class="aplicaciones_list" data-split-icon="gear" data-inset="true"
							data-divider-theme="a" data-scroll="true" > 
							<li data-role="list-divider">Año</li>
							
							<li>
								
									<div data-role="fieldcontain">
										<fieldset data-role="controlgroup">
											<div id="year_radio"></div>
										    	<!-- <input type="radio" name="radio-choice" id="radio-choice-2" value="2010" checked="checked" />
										     	<label for="radio-choice-2">2010</label>  -->
										     
										     	 
										</fieldset>
									</div> 
									    
							</li>  
						</ul> 
						
						 <!--<input type="radio" name="radio-choice" id="radio-choice-1" value="2014"/>
										     	<label for="radio-choice-1">2014</label>
										
										     	<input type="radio" name="radio-choice" id="radio-choice-2" value="2015" checked="checked" />
										     	<label for="radio-choice-2">2015</label>-->
						
						<!-- <ul data-role="listview"  data-inset="true" data-divider-theme="a" >  -->
						<ul id="listado_meses"  data-role="listview" class="aplicaciones_list" data-split-icon="gear" data-inset="true"
							data-divider-theme="a" data-scroll="true" >
							
							<li data-role="list-divider">Mes</li>
						
									<li id="li_ENERO"><a href="#" id="ENERO" action="mostrar_report" mes="01" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Enero</a></li>
									<li id="li_FEBRERO"><a href="#" id="FEBRERO" action="mostrar_report" mes="02" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Febrero</a></li>
									<li id="li_MARZO"><a href="#" id="MARZO" action="mostrar_report" mes="03" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Marzo</a></li>
									<li id="li_ABRIL"><a href="#" id="ABRIL" action="mostrar_report" mes="04" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Abril</a></li>
									<li id="li_MAYO"><a href="#" id="MAYO" action="mostrar_report" mes="05" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Mayo</a></li>
									<li id="li_JUNIO"><a href="#" id="JUNIO" action="mostrar_report" mes="06" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Junio</a></li>
									<li id="li_JULIO"><a href="#" id="JULIO" action="mostrar_report" mes="07" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Julio</a></li>
									<li id="li_AGOSTO"><a href="#" id="AGOSTO" action="mostrar_report" mes="08" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Agosto</a></li>
									<li id="li_SEPTIEMBRE"><a href="#" id="SEPTIEMBRE" action="mostrar_report" mes="09" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Septiembre</a></li>
									<li id="li_OCTUBRE"><a href="#" id="OCTUBRE" action="mostrar_report" mes="10" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Octubre</a></li>
									<li id="li_NOVIEMBRE"><a href="#" id="NOVIEMRBE" action="mostrar_report" mes="11" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Noviembre</a></li>
									<li id="li_DICIEMBRE"><a href="#" id="DICIEMBRE" action="mostrar_report" mes="12" class="ui-btn ui-btn-inline ui-btn-icon-left ui-icon-carat-l">Diciembre</a></li>
						</ul> 
					
					
					</div>

				
				</div> 
		</div>


	</div>
</body>
</html>