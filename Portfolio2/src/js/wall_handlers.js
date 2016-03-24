////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers

$(document).ready(function() {
    
    get_user();
    get_wallPosts();
    
	$('#logout').click(function() {
        logout();
    });

}); // end of document ready function

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

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

//////////////////////////////////////////////////////////////////////////////////////////////////////
/// Callback functions

/////////////////////////////////////////////////////////////////////////////////
//	get_wallPosts_callback
//
//		Receives html containing the wall posts and adds it to
//      the wall.
//
function get_wallPosts_callback(data){
    console.log("Gathered all posts for the wall");
    obj = JSON.parse(data);
    //console.log(data);
    $('.wall').empty();
    $('.wall').append(obj);
}
