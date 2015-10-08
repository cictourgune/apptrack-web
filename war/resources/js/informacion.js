$(document).on("pageshow", "#information-page", function(event){ 
	$("#id_info_dev").val(localStorage.getItem("token"));
});

 

$(document).on("tap", "#docu", function(event){ 
	window.location.href=domain()
	+ '/private/documentacion';
});

