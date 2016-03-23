////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers

$(document).ready(function() {

	$(".mat-input").focus(function() {
      $(this).parent().addClass("is-active is-completed");
    });

    $(".mat-input").focusout(function() {
      if ($(this).val() === "")
        $(this).parent().removeClass("is-completed");
      $(this).parent().removeClass("is-active");
    });
    
    $('select').material_select();

    $(".img-container").click (function () {
        var login = {'username': $('#username').val(),'password': $('#password').val()};
        var script = "src/php/login.php";
        var func = login_callback;
        
        console.log("Sending Login info to server");
        ajax(login, script, func);
	});

	$("#signup").click (function () {
		$.post("logout.php", {}, function(data) {
				console.log(data);
			 $("#includedContent").load("login.html"); 
		});
	});

}); // end of document ready function

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

/////////////////////////////////////////////////////////////////////////////////
//	ajax
//
//		calls the designated php script with the given data. 
//		on return it will call the given callback function
//
function ajax(parameters, script, func){
    $.ajax({
        type: 'GET',
        url: script,
        data: parameters,
        success: function(data){func(data);}
    });
}

//////////////////////////////////////////////////////////////////////////////////////////////////////
/// Callback functions

/////////////////////////////////////////////////////////////////////////////////
//	login_callback
//
//		Parses server response to login attempt
//      If successful will load the main application page
//      If not successful will prompt the user to try again
//
function login_callback(data){
    console.log("Received Response from server: Login");
    var response = JSON.parse(data);
    console.log("\tResponse: " + response.user);
    
    if (typeof response.user != 'undefined'){
        if ( response.user == 'admin' ){
            window.location.href = "app.html";
        }
        else if ( response.user == 'user' ){
            window.location.href = "app.html";
        }
        else if ( response.user == 'invalid' ){
            alert("Invalid username or password");
        }
    }
    else {alert("Server error: please try again");}
}