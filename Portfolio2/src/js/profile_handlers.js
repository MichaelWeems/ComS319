////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers

$(document).ready(function() {
    
    get_user();
    get_profilePosts();
    //get_profileImages();
    
   $('#username_box').click(function(){
        load_profile($('#username').html());
    });
    
    $('#logout').click(function() {
        logout();
    });

}); // end of document ready function

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

/////////////////////////////////////////////////////////////////////////////////
//	set_wallHandlers
//
//		Sets all handlers needed by the wall elements
//
function set_profilePosts_handlers(loggedin){
    set_allPostHandlers();
    set_createPostHandlers(loggedin);
}

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

/*********************************************************************************************
 *  set post handlers
 */

/////////////////////////////////////////////////////////////////////////////////
//	set_createPostHandlers
//
//		Sets all handlers needed by the create post elements
//
function set_createPostHandlers(loggedin){
    console.log("logged in: " + loggedin);
    if (loggedin) {
        $('.fab').html('<i class="material-icons">add</i>');

        $('.fab').click(function(){

            if ($('#create-post').hasClass("hidden")){
                $('#create-post').removeClass('hidden');
                $('#create-post').addClass('unhidden');
            }
            else if ($('#create-post').hasClass("unhidden")){
                $('#create-post').removeClass('unhidden');
                $('#create-post').addClass('hidden');
            }

        });

        // create post inputs
        $(".mat-input").focus(function() {
          $(this).parent().addClass("is-active is-completed");
        });

        $(".mat-input").focusout(function() {
          if ($(this).val() === "")
            $(this).parent().removeClass("is-completed");
          $(this).parent().removeClass("is-active");
        });

        $('#post-text').keypress(function(e) {
            if(e.which == 13) {
                submitPost();
                $("#fab").trigger( "click" );
            }
        });

        $("#submit-post").click(function () {
            submitPost();
            $("#fab").trigger( "click" );
        });
    }
    else {
        $('.fab').hide();
        $('#create-post-container').hide();
    }
}

/////////////////////////////////////////////////////////////////////////////////
//	set_postHandlers
//
//		Sets all handlers needed by the given post ids.
//      Called after a user submits a post
//
function set_postHandlers(expanderid, commentexpanderid, replyid){
    
    $('#' + expanderid).click(function(){
         set_expanderHandler($(this));
    });
    
    $('#' + commentexpanderid).click(function(){
         set_expanderHandler($(this));
    });
    
    $('#' + replyid).trigger('autoresize');
    $('#' + replyid).keypress(function(e){
        set_replyfieldHandler($(this), e);
    });
}

/////////////////////////////////////////////////////////////////////////////////
//	set_allPostHandlers
//
//		Sets all handlers needed by the post elements.
//      Called after receiving all the current posts from the database
//
function set_allPostHandlers(){
    
    $('.expander').click(function(){
        set_expanderHandler($(this));
    });
    
    $('.materialize-textarea').trigger('autoresize');
    $('.materialize-textarea').keypress(function(e){
        set_replyfieldHandler($(this), e);
    });
}


// Helper for setting the post handlers
function set_expanderHandler(ele){
    
    var img = false;
    
    if (ele.attr('id').includes('postimg')){
        img = true;
    }
    
    if (ele.parent().parent().parent().attr('class') == 'card wall-card z-depth-2') {
        $('.expander').each(function(){
            $(this).parent().parent().parent().removeClass('large z-depth-4');
            $(this).parent().parent().parent().addClass('wall-card z-depth-2');
            $(this).parent().parent().parent().parent().css('z-index', '50');
        });
        $('img').each(function(){
            $(this).removeClass('large-image');
        });
                               
        ele.parent().parent().parent().removeClass('wall-card z-depth-2');
        ele.parent().parent().parent().addClass('large z-depth-4');
        ele.parent().parent().parent().parent().css('z-index', '100');
        if (img){
             ele.addClass('large-image');
        }
    }
    else {
        ele.parent().parent().parent().removeClass('large z-depth-4');
        ele.parent().parent().parent().addClass('wall-card z-depth-2');
        ele.parent().parent().parent().parent().css('z-index', '50');
        if (img){
            ele.removeClass('large-image');
        }
    }
}

// Helper for setting the post handlers
function set_replyfieldHandler(ele, e){
    if (e.which == 13){
        console.log('here');
        if (ele.val() != ''){
            var data = prep_addComment(ele);
            var script = 'src/php/handler.php';
            var func = addComment_callback;
            ajax(data, script, func);
        }
    }
}

/*********************************************************************************************
 *  Sending data to the server
 */

/////////////////////////////////////////////////////////////////////////////////
//	submitPost
//
//		Submits a new post to the database
//
function submitPost(){
    var data = { 'op': 'create post', 'title': $('#post-title').val(),'text': $('#post-text').val()};
    var script = "src/php/handler.php";
    var func = submitPost_callback;

    console.log("Creating a new post");
    ajax(data, script, func);
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
//	get_profilePosts_callback
//
//		Receives html containing the wall posts and adds it to
//      the wall.
//
function get_profilePosts_callback(data){
    console.log("Gathered all posts for the profile");
    //$('.wall').html(data);
    obj = JSON.parse(data);
    $('.wall').html(obj.html);
    
    console.log("returned loggedin: " + obj.loggedin);
    
    set_profilePosts_handlers(obj.loggedin);
}

/////////////////////////////////////////////////////////////////////////////////
//	submitPost_callback
//
//		adds the post to the wall
//
function submitPost_callback(data){
    console.log("Added a new Post");
    //$('.wall').html(data);  // for debugging
    //console.log(data);  // for debugging
    obj = JSON.parse(data);
    $('#posts').prepend(obj.html);
    
    var scrollview = $('#posts');
    var height = scrollview[0].scrollHeight;
    scrollview.scrollTop(height);
    
    $('#post-title').val('');
    $('#post-text').val('');
    
    set_postHandlers(obj.expanderid, obj.commentexpanderid, obj.replyid);
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