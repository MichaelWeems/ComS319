////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers

/**************************************************************************************************************
 *  On page load Functions
 */

/////////////////////////////////////////////////////////////////////////////////
//	document.ready
//
//		queries the server for info/html and sets handlers for the returned data
//
$(document).ready(function() {
    
    get_user();
    
    $('#username_box').click(function(){
        window.location.assign("profile.html"); 
    });
    
    
	$('#logout').click(function() {
        logout();
    });

}); // end of document ready function

