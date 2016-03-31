////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers

$(document).ready(function() {
    
    get_user();
    get_appselectors();
    
    $('#username_box').click(function(){
        console.log('here');
        window.location.assign("profile.html"); 
    });
    
    $('#wall').click(function(){
       wall(); 
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
//	get_appselectors
//
//		Gets apps from the database to display on the wall
//
function get_appselectors(){
    var data = {'op': 'get all apps'};
    var script = 'src/php/handler.php';
    var func = get_appselectors_callback;
    
    console.log('Getting appselector html');
    ajax(data,script,func);
}


/////////////////////////////////////////////////////////////////////////////////
//	set_pageHandlers
//
//		Sets all handlers needed by the page elements
//
function set_pageHandlers(){
    
    $('.app-card').click(function(){
        var appname = $(this).attr('id').substring(4);
        var op = {'op': 'open app', 'appname': appname};
        var script = 'src/php/handler.php';
        var func = openapp_callback;
        
        ajax(op, script, func);
    });

}


//////////////////////////////////////////////////////////////////////////////////////////////////////
/// Callback functions

/////////////////////////////////////////////////////////////////////////////////
//	get_appselectors_callback
//
//		Receives html containing the appselectors and adds it to
//      the page.
//
function get_appselectors_callback(data){
    console.log("Gathered all apps for the page");
    $('.wall').append(data);
    obj = JSON.parse(data);
    console.log(data);
    $('.wall').empty();
    $('.wall').append(obj);
    
    set_pageHandlers();
}

/////////////////////////////////////////////////////////////////////////////////
//	openapp_callback
//
//		Will open the app page. html will be saved as a session variable
//
function openapp_callback(data){
    console.log("About to open the app");
    window.location = "app.html";
}