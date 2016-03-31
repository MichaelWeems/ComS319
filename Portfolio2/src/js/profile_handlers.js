////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers

$(document).ready(function() {
    
    get_user();
    get_profilePosts();
    get_profileImages();
    
    $('#logout').click(function() {
        logout();
    });

}); // end of document ready function

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

/////////////////////////////////////////////////////////////////////////////////
//	get_profilePosts
//
//		Gets posts from the database to display on the wall
//
function get_profilePosts(){
    var data = {'op': 'get user posts'};
    var script = 'src/php/handler.php';
    var func = get_profilePosts_callback;
    
    console.log('Getting profile post html');
    ajax(data,script,func);
}

//function get_profileImages(){
//    var data = {'op': 'get user images'};
//    var script = 'src/php/handler.php';
//    var func = get_profileImages_callback;
//
//    ajax(data,script,func);
//}

//////////////////////////////////////////////////////////////////////////////////////////////////////
/// Callback functions

/////////////////////////////////////////////////////////////////////////////////
//	get_profilePosts_callback
//
//		Receives html containing the wall posts and adds it to
//      the wall.
//
function get_profilePosts_callback(data){
    console.log("Gathered all posts for the profile");
    //$('main').html(data);
    obj = JSON.parse(data);
    $('.wall').empty();
    $('.wall').append(obj);
}

//function get_profileImages_callback(data){
//    console.log("Gathered images for profile");
//    //$('main').html(data);
//    obj = JSON.parse(data);
//   console.log(obj);
//    $('.prof-wall').empty();
//    $('.prof-wall').append(obj);
//}
