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
    
    $("#signup").click (function () {
        signup();
	});
	
}); // end of document ready function

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

/////////////////////////////////////////////////////////////////////////////////
//	signup
//
//		Makes a call to signup.php with the user supplied
//      parameters
//
function signup(){
    var login = {'username': $('#username').val(),'password': $('#password').val(), 'admin': $('#admin').is(':checked')};
    var script = "src/php/signup.php";
    var func = signup_callback;

    console.log("Sending Signup info to server");
    ajax(login, script, func);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////
/// Callback functions

/////////////////////////////////////////////////////////////////////////////////
//	signup_callback
//
//		Parses server response to signup attempt
//      If successful will load the main application page
//      If not successful will prompt the user to try again
//
function signup_callback(data){
    console.log("Received Response from server: Signup");
    var response = JSON.parse(data);
    console.log("\tResponse: " + response.user);
    
    if (typeof response.user != 'undefined'){
        if ( response.user == 'admin' ){
            window.location.assign("admin.html");
        }
        else if ( response.user == 'user' ){
            window.location.assign("wall.html");
        }
        else if ( response.user == 'invalid' ){
            alert("Invalid username or password");
        }
    }
    else {alert("Server error: please try again");}
}