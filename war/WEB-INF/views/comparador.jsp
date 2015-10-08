<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/views/header.jsp"%>
</head>

<body>
  
	 <div id="comparar-page"  data-role="page" data-dialog="true" data-close-btn="right" data-corners="false"   data-overlay-theme="a"> 
			<div data-role="header">
				<h1>Comparar consultas</h1> 
			</div> 
			<div data-role="content"> 
 


			<div class="row">
				<div class="col6">
 
					<ul data-role="listview" data-inset="true" data-theme="a"
						data-dividertheme="a">
						<li data-role="list-divider" style="text-align: center"><h1>Mapa fijado</h1></li>
						<li><div id="map_canvas1"
									style="height: 500px; width:100%"></div> 
						</li>

					</ul>
				</div>

				<div class="col6">
					<ul data-role="listview" data-inset="true" data-theme="a"
						data-dividertheme="a">
						<li data-role="list-divider" style="text-align: center"><h1>Mapa actual</h1></li>
						<li><div id="map_canvas2"
									style="height: 500px; width:100%"></div> 
						</li>

					</ul>
				</div>



			</div>
		</div>
	</div>
</body>
</html>
