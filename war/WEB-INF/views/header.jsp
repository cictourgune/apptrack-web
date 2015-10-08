<%
String domain="/egistour-web";
String version="2.0";
%>
 
<title>eGIStour</title> 

<meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1"> 


<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script> 
<script src="<%=domain%>/resources/js/defaults.js?v=<%=version%>"></script> 
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>

<link rel="stylesheet" href="<%=domain%>/resources/css/xpressio_040.lite.css" />   
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>
  
<!-- FLOTCHARTS-->
<script src="<%=domain%>/resources/js/plugin/flot/jquery.flot.js"></script> 
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="/js/flot/excanvas.min.js"></script><![endif]--> 
<script src="<%=domain%>/resources/js/plugin/flot/jquery.flot.js"></script>
<script src="<%=domain%>/resources/js/plugin/flot/jquery.flot.time.js"></script>
<script src="<%=domain%>/resources/js/plugin/flot/jquery.flot.axislabels.js"></script>
<script src="<%=domain%>/resources/js/plugin/flot/jquery.flot.symbol.js"></script>  
  

<script type="text/javascript" src="https://www.google.com/jsapi"></script> 
 <script src="<%=domain%>/resources/js/plugin/jquery.json-2.2.min.js?v=<%=version%>"></script> 
<script src="<%=domain%>/resources/js/plugin/json2.js?v=<%=version%>"></script> 
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=visualization,places"></script>

<link rel="stylesheet" href="<%=domain%>/resources/css/styles.css" />


<!-- PRODUCTION-->
<!-- <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCqc2iIoJMk8AuvMzHKpvZFyZ4jYUEbOik&sensor=true">
</script>      -->
 
						  
<script charset="UTF-8" src="<%=domain%>/resources/js/index.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/user-registro.js?v=<%=version%>"></script>  
<script charset="UTF-8" src="<%=domain%>/resources/js/dashboard.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/logout.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/login.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/utils.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/registro-application.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/duplicar-application.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/registro-visor.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/mapa.js?v=<%=version%>"></script>  
<script charset="UTF-8" src="<%=domain%>/resources/js/integer.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/date.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/option.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/decimal.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/informacion.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/defaults.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/vista.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/comparador.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/reports.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/recover-password.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/informes.js?v=<%=version%>"></script>
<%-- <script charset="UTF-8" src="<%=domain%>/resources/js/mapaAnalysis.js?v=<%=version%>"></script>    --%>

<script type="text/javascript">
	  google.load('visualization', '1.0', {'packages':['corechart']});
	  google.load('visualization', '1', {'packages':['annotatedtimeline']});
      var script = '<script type="text/javascript" src="/egistour-web/resources/js/plugin/markerclusterer';
      if (document.location.search.indexOf('compiled') !== -1) {
        script += '_compiled';
      }
      script += '.js"><' + '/script>';
      document.write(script);
</script> 

<style>
  .ui-page { background: white;}
</style>  
 	 