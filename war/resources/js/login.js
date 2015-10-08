$(document).on("tap", "#login-cancel-submit", function(){
	
	$.mobile.changePage(domain()+"/index", {transition: "none", reloadPage: true}); 
	
    return false;  
}); 

$(document).on("tap", "#forgot-password", function(event){
	$.mobile.changePage(domain()+'/forgotPassword', { transition: "none", reloadPage: true});

}); 
