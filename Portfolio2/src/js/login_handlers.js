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

    $("#forgot").click (function () {
        forgot_password();
	});
    
}); // end of document ready function


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

/////////////////////////////////////////////////////////////////////////////////
//	forgot_password
//
//		Prompts for security question and answer
//
function forgot_password(){
    if ($('#username').val() != ""){
        var data = {'op': 'question', 'username': $('#username').val()};
        var script = "src/php/forgot_password.php";
        var func = forgot_password_callback;

        console.log("Sending username to server");
        ajax(data, script, func);
    } else {
        alert("Fill out the username field before hitting 'forgot your password'");
    }
}

/////////////////////////////////////////////////////////////////////////////////
//	send_answer
//
//		sends security answer to server for validation
//
function send_answer(ans, username){
    var data = {'op': 'answer', 'answer': ans, 'username': username};
    var script = "src/php/forgot_password.php";
    var func = login_callback;

    console.log("Sending security answer to server");
    ajax(data, script, func);
}

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
//	forgot_password_callback
//
//		prompts the user with their security question
//
function forgot_password_callback(data){
    console.log("Received Response from server: forgot password");
    console.log(data);
    var response = JSON.parse(data);
    
    if (response.question != 'undefined'){
        if ( response.question == 'invalid'){
            alert("You do not have a security question set, sorry... :(");
        }
        else{
            var answer = prompt(response.question);
            send_answer(answer, response.username);
        }
    }
    else {alert("Server error: please try again");}
}

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