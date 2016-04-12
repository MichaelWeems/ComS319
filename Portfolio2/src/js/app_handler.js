////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers

$(document).ready(function() {
    
    get_user();
    get_app();
    
    $('#username_box').click(function(){
        load_profile($('#username').html());
    });
    
    $('#wall').click(function(){
       wall(); 
    });
    
    $('#appselect').click(function(){
       appselect(); 
    });
    
	$('#logout').click(function() {
        logout();
    });

}); // end of document ready function

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

/////////////////////////////////////////////////////////////////////////////////
//	wall
//
//		loads the wall page
//
function wall(){
    window.location.assign("wall.html");
}

/////////////////////////////////////////////////////////////////////////////////
//	appselect
//
//		loads the appselect page
//
function appselect(){
    window.location.assign("appselect.html");
}

/////////////////////////////////////////////////////////////////////////////////
//	get_app
//
//		Gets html for the app and injects it into the page
//
function get_app(){
    var data = {'op': 'get app'};
    var script = 'src/php/handler.php';
    var func = get_app_callback;
    
    console.log('Getting appselector html');
    ajax(data,script,func);
}

//////////////////////////////////////////////////////////////////////////////////////////////////////
/// Callback functions

/////////////////////////////////////////////////////////////////////////////////
//	get_app_callback
//
//		Receives html containing the app and adds it to
//      the page.
//
function get_app_callback(data){
    console.log("Got app html for the page");
    console.log(data);
    var obj = JSON.parse(data);
    $('.app').append(obj.html);
    $('#append-scripts').append(obj.scripts);
}