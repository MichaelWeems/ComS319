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
    
    $('#password').keypress(function(e) {
        if(e.which == 13) {
            login();
        }
    });
    
    $('#username').focus();

    $("#login").click (function () {
        login();
	});

}); // end of document ready function


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

/////////////////////////////////////////////////////////////////////////////////
//	login
//
//		Makes a call to login.php with the user supplied
//      parameters
//
function login(){
    var login = {'username': $('#username').val(),'password': $('#password').val()};
    var script = "src/php/login.php";
    var func = login_callback;

    console.log("Sending Login info to server");
    ajax(login, script, func);
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
            window.location.assign("admin.html");
        }
        else if ( response.user == 'user' ){
            window.location.assign("wall.html");
            console.log("after changing location");
        }
        else if ( response.user == 'invalid' ){
            alert("Invalid username or password");
        }
    }
    else {alert("Server error: please try again");}
}