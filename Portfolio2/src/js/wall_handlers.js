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

/////////////////////////////////////////////////////////////////////////////////
//	set_wallHandlers
//
//		Sets all handlers needed by the wall elements
//
function set_wallHandlers(){
    
    $('.expander').click(function(){
        
        
        
        if ($(this).parent().parent().parent().attr('class') == 'card') {
            $('.expander').each(function(){
                $(this).parent().parent().parent().removeClass('card large');
                $(this).parent().parent().parent().addClass('card');
                $(this).parent().parent().parent().parent().css('z-index', '1');
            });
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
    
    $('.materialize-textarea').trigger('autoresize');
    $('.materialize-textarea').keypress(function(e){
        if (e.which == 13){
            if ($(this).val() != ''){
                var data = prep_addComment($(this));
                var script = 'src/php/handler.php';
                var func = addComment_callback;
                ajax(data, script, func);
            }
        }
    });
}

/////////////////////////////////////////////////////////////////////////////////
//	prep_addComment
//
//		Gathers the necessary data to save the comment to the database
//
function prep_addComment(inputfield){
    // inputfield id is of format 'reply###'
    // need to strip the 'reply' out
    var postId = inputfield.attr('id').substring(5);
    var text = inputfield.val();
    var data = {'op': 'write comment', 
                'postId': postId,
                'text': text};
    return data;
}


/////////////////////////////////////////////////////////////////////////////////
//	like
//
//		Add or remove this user to or from the list of people liking this post
//
function like(postId){
    var data = {'op': 'like', 'postId': postId};
    var script = "src/php/handler.php";
    var func = like_callback;
    
    ajax(data, script, func);
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
    
    set_wallHandlers();
}

/////////////////////////////////////////////////////////////////////////////////
//	addComment_callback
//
//		adds the comment to the comment list
//
function addComment_callback(data){
    console.log("Added a comment to a post");
    obj = JSON.parse(data);
    $('#comments' + obj.postId ).append(obj.html);
    
    var scrollview = $('#reveal' + obj.postId);
    var height = scrollview[0].scrollHeight;
    scrollview.scrollTop(height);
    
    $('#reply' + obj.postId).val('');
}

/////////////////////////////////////////////////////////////////////////////////
//	like_callback
//
//		emptys the like div and then appends the like html to it 
//
function like_callback(data){
    console.log("Updated the like status on a post");
    obj = JSON.parse(data);
    $('#like' + obj.postId ).empty();
    if ( obj.like == 'liked'){
        console.log('it is liked')
        $('#like' + obj.postId ).append('You like this');
    }
    $('#likecount' + obj.postId).empty();
    $('#likecount' + obj.postId).html(obj.likecount);
}