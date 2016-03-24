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
    $('.wall').append(data);
    obj = JSON.parse(data);
    //console.log(data);
    $('.wall').empty();
    $('.wall').append(obj);
    
    $('.expander').click(function(){
        
        if ($(this).parent().parent().parent().attr('class') == 'card') {
            $(this).parent().parent().parent().removeClass('card');
            $(this).parent().parent().parent().addClass('card large');
            $(this).parent().parent().parent().parent().css('z-index', '10');
        }
        else {
            $(this).parent().parent().parent().removeClass('card large');
            $(this).parent().parent().parent().addClass('card');
            $(this).parent().parent().parent().parent().css('z-index', '1');
        }
        
    });
}
