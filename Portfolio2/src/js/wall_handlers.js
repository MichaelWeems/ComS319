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
    get_wallPosts();
    
    $('#username_box').click(function(){
        load_profile($('#username').html());
    });

	$('#logout').click(function() {
        logout();
    });

}); // end of document ready function


/////////////////////////////////////////////////////////////////////////////////
//	get_wallPosts
//
//		Gets posts from the database to display on the wall
//
function get_wallPosts(){
    var data = {'op': 'get all posts'};
    var script = 'src/php/handler.php';
    var func = get_wallPosts_callback;
    
    console.log('Getting wall post html');
    ajax(data,script,func);
}

/*********************************************************************************************
 *  set post handlers
 */

/////////////////////////////////////////////////////////////////////////////////
//	set_wallHandlers
//
//		Sets all handlers needed by the wall elements
//
function set_wallHandlers(){
    set_allPostHandlers();
    set_createPostHandlers();
}

/*********************************************************************************************
 *  Callback functions
 */

/////////////////////////////////////////////////////////////////////////////////
//	get_wallPosts_callback
//
//		Receives html containing the wall posts and adds it to
//      the wall.
//
function get_wallPosts_callback(data){
    console.log("Gathered all posts for the wall");
    //$('.wall').append(data); // for debugging
    //console.log("data: " + data);
    obj = JSON.parse(data);
    //console.log(data);    // for debugging
    $('.wall').empty();
    $('.wall').append(obj);
    
    set_wallHandlers();
}