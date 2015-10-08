
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@include file="/WEB-INF/views/header.jsp"%>
</head>

<body>

	<div id="mapa-page" data-role="page" data-dom-cache="true"> 
	
		<div data-role="header">
			<%@include file="/WEB-INF/views/pageheader.jsp"%> 
		</div>  
		
		<!-- menu ROL DEVELOPER -->
		<div data-role="panel" id="leftmenu" data-position="left"
			data-display="overlay" data-theme="a">
			<ul data-role="listview" style="margin-top:37px">
				<li data-icon="false"><a id="menu_gestion">Configuración</a></li>  
				<li data-icon="false"  data-theme="b"><a href="#" id="menu_mapas" action="mapaDashboard">Mapas</a></li>  
				<li data-icon="false" ><a href="#" id="menu_informes"  action="informeDashboard">Consultas</a></li>  
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
		
		
		




		
			
		<div data-role="popup" id="popupFijado">
				<p>Mapa almacenado en memoria
				<p>
			</div>
			<div data-role="popup" id="popupFijadoEliminar">
				<p>Mapa eliminado de memoria
				<p>
			</div>

			<div data-role="popup" id="popupBasic">
				<p>Posición del mapa almacenada
				<p>
			</div>

			<div data-role="popup" id="popupDelete">
				<p>Puntos de la aplicación eliminados
				<p>
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
								 
							</div>
						</li>
						<c:forEach items="${listaAplicaciones}" var="aplicaciones">
							<li id="li_${aplicaciones.idaplicacion}" ><a
								id="${aplicaciones.idaplicacion}"
								value="${aplicaciones.nombreaplicacion}" action='selectAppMap' href="#">
									<h3 class="nombreApplicacion"
										data-val="${aplicaciones.nombreaplicacion}">${aplicaciones.nombreaplicacion}</h3>
									<p>${aplicaciones.descripcion}</p>
							</a></li>
						</c:forEach>
					</ul>
				</div>
 		 	 
 		 	
 				<div class="col7">
 				
 					<ul data-role="listview" data-inset="true" data-theme="d" data-divider-theme="a"> 
							<li data-role="list-divider">
								${nombreAplicacion} : Número de usuarios <span id="mapa_num_usuarios"> ${numUsuarios}</span> 
							</li>
							<li>
							 
<!-- 								<div class="row-fluid" style='padding-bottom: 2px;'>
										<div class="span2" style='padding-top: 10px;'>
											<a href='#' data-role='button'  data-icon='search' data-mini="true"	action='localiza_mapa'>Buscar</a>
										</div>
										<div class="span10"   style='padding-top: 13px;' >
											<label for="ir_mapa" style='padding-top: 5px;' data-mini="true" ><b>Buscar localización:</b> </label>
											<input data-mini="true"  type='text' name="ir_mapa" id="ir_mapa" placeholder="Calle, Numero, Ciudad ... -"> </input>
										</div> 
								</div> -->
								<div class="ui-body ui-body-d">
										<div class="row">										 
											<div class="col6" data-role="controlgroup" data-type="horizontal" align="center">
												<a href="#" action="store_location" data-role="button" data-mini="true">Fijar</a> 
												<a href="#" action="delete_points" data-role="button" data-mini="true">Eliminar puntos</a> 
								 			</div>
								 			 
								 			<div class="col6" data-role="controlgroup" data-type="horizontal" align="left"> 
								 			 	<a id="id_fijar" href="#" action="fijar" data-role="button" data-mini="true">Fijar para comparar</a> 
												<a id="id_comparar" href="#" action="comparar" data-role="button" data-mini="true">Comparar</a> 
												<a id="id_eliminarfijado" href="#" action="delete_fijado" data-role="button" data-mini="true">Eliminar fijado</a> 
								 			</div> 
								 			<!-- <div class="col2" data-role="controlgroup" data-type="horizontal" align="left"> 
								 			 	 <a href="#" id="mostrar_informe" data-role="button" action="informe" data-mini="true"
													data-icon="grid"> Informes</a>   
								 			</div>  -->
								 		</div> 
								</div>  
								<div data-role="navbar" style="margin-top: 10px">
									<ul>
										<li><a action="heatmap" id="id_heatmap" href="#" >Heat
												Map</a></li>
										<li><a action="cluster" id="id_cluster" href="#">Clusters</a>
										</li>
									</ul>
								</div>
								<div id="panel">
								      <input id="target" type="text" placeholder="Ciudad, calle,..." data-mini="true">
								</div>
								<div id="map_canvas" style="height: 700px"></div>
							</li> 
					</ul>  
 				
 				
 				
 				
 				</div>
 				<div class="col2">
 				
 				
 										<ul data-role="listview" data-inset="true" data-theme="d" data-divider-theme="a" > 
							<li data-role="list-divider">
								Fecha y hora
							</li>
							<li>
							    <div> 
									<div data-role="fieldcontain">
										<label for="fecha_desde" style='padding-top: 5px; font-size:small'><b>Desde:</b>
										</label> <input type='date' name="fecha_desde" id="fecha_desde"
											data-role='datebox' data-tipo='3'
											placeholder="Ejemplo: 2013-02-27" data-mini="true"> </input>
									</div> 
									<div data-role="fieldcontain">
										<label for="fecha_desde" style='padding-top: 5px;; font-size:small'><b>Hasta:</b>
										</label> <input type='date' name="fecha_hasta" id="fecha_hasta"
											data-role='datebox' data-tipo='3'
											placeholder="Ejemplo: 2013-02-27" data-mini="true"> </input>
									</div> 
									<div class="row-fluid" style='margin-top:5px'>
										<div class="span3" style='margin-top:5px; font-size:small'>
										 <b>Horas:</b> 
										</div>
										<div class="span9" align="center" > 
											<!-- <div name="hour_slider" id="hour_slider" style='padding-top: 8px;'> </div>  -->
											<div id="hour_slider" data-role="rangeslider">
												<input type="range" name="hora-desde" id="hora-desde" min="1"
													max="23" step="1" value="0" data-mini="true"> 
												<input type="range"
													name="hora-hasta" id="hora-hasta" min="2" max="24" step="1"
													value="24" data-mini="true">
											</div>
											
										</div> 
									</div> 
								</div>
							</li>
						</ul>	
						<ul data-role="listview" data-inset="true" data-theme="d" data-divider-theme="a" > 
							<li data-role="list-divider">
								Filtrado de variables
							</li>
							<li> 
							
								<div id="idlistvariables" data-role="collapsibleset" data-mini="true">
									<c:forEach items="${listaVariables}" var="variables">
	
										<div data-role='collapsible'
											id="collapsible_${variables.idvariable}"
											data-content-theme='c'>
	
											<h3 id="h_${variables.idvariable}"
												data-varname='${variables.nombrevariable}' data-active='0'>
												<span style='color: grey;'>${variables.nombrevariable}</span>
											</h3>
											
											<div>
												<c:if test="${variables.idtipo == 1}">
													<div style='height: 50px; padding-top: 40px; width: 100%;'>
														<div data-role="rangeslider" id="${variables.idvariable}" class="resize"
															data-tipo='1' data-max="${variables.max}"
															data-min="${variables.min}">
															<input type="range" id="${variables.idvariable}_inicio" step="1" data-mini="true" min="${variables.min}" max ="${variables.max}" value="${variables.min}"> 
															<input type="range" id="${variables.idvariable}_fin" step="1" data-mini="true" min="${variables.min}" max ="${variables.max}" value="${variables.max}">
															
														</div>
													</div> 
												</c:if>
	
										<c:if test="${variables.idtipo == 2}">
	
													<c:if test="${variables.multiopcion==true}">
														<select name="${variables.nombrevariable}"
															id="${variables.idvariable}" class='select_type'
															data-native-menu='false' data-mini='true' data-tipo='2'
															multiple='multiple' style='width: 100%;'
															  > 
													</c:if>
	
													<c:if test="${variables.multiopcion==false}">
														<select name="${variables.nombrevariable}"
															id="${variables.idvariable}" class='select_type'
															data-native-menu='false' data-mini='true' data-tipo='2'
															style='width: 100%;'
																 > 
													</c:if>
	
													<c:forEach items="${variables.opciones}" var="opciones">
														<option value="${opciones.nombreopcion}">
															${opciones.nombreopcion}</option>
	
													</c:forEach>
													</select>
	
	
												</c:if>
	
												<c:if test="${variables.idtipo == 3}">
													<input type='date' name="${variables.nombrevariable}"
														id="${variables.idvariable}" data-role='datebox'
														min="${variables.fechadesde}" max="${variables.fechahasta}"
														data-tipo='3' style='width: 100%;'></input>
												</c:if>
	
												<c:if test="${variables.idtipo == 4}">
													<div style='height: 50px; padding-top: 40px; width: 100%;'>
														<div data-role="rangeslider" id="${variables.idvariable}" class="resize"
															data-tipo='4' data-max="${variables.max}"
															data-min="${variables.min}">
															<input type="range" id="${variables.idvariable}_inicio" step="0.1" data-mini="true" min="${variables.min}" max ="${variables.max}" value="${variables.min}"> 
															<input type="range" id="${variables.idvariable}_fin" step="0.1" data-mini="true" min="${variables.min}" max ="${variables.max}" value="${variables.max}">
														</div>
													</div>
												</c:if> 
												
												<div style="padding-left:5px">
													<select name='flip-1' id='flip${variables.idvariable}'
														class='flip' data-role='slider' data-mini='true' >
														<option value='off'>Off</option>
														<option value='on'>On</option>
													</select> 
												</div>
												
	
											</div>
										</div>
									</c:forEach>
									<div>
										<a href='#' data-role='button' data-icon='search' data-theme="b"
											action='consultar'>BUSCAR</a>
									</div>
		
									<div id='filtro-error' data-role="content"
										style="text-align: center;"></div>
								</div>								 
							</li> 
						</ul> 
					</div>
 			</div>  			 
		</div>
	</div>
</body>
</html>