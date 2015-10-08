$(document).on("pageshow", "#index-page", function(event){ 
	$("#login-usuario").val('');
	$("#login-password").val('');
	$('#login-error').text('');  
});


$(document).on("tap", "#entrar", function(event){  
	localStorage.clear();
	$.mobile.changePage(domain()+"/private/dashboard", {reloadPage: true});   
});




