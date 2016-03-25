////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers

$(document).ready(function() {
    
    get_user();
    get_appselectors();
    
	$('#logout').click(function() {
        logout();
    });

}); // end of document ready function

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

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