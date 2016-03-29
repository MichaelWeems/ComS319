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
    
    $('#appselect').click(function(){
       appselect(); 
    });
    
	$('#logout').click(function() {
        logout();
    });

}); // end of document ready function

/////////////////////////////////////////////////////////////////////////////////
//	appselect
//
//		loads the appselect page
//
function appselect(){
    window.location.assign("appselect.html");
}

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
//	set_createPostHandlers
//
//		Sets all handlers needed by the create post elements
//
function set_createPostHandlers(){
    
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
    if (ele.parent().parent().parent().attr('class') == 'card wall-card z-depth-2') {
        $('.expander').each(function(){
            ele.parent().parent().parent().removeClass('large z-depth-4');
            ele.parent().parent().parent().addClass('wall-card z-depth-2');
            ele.parent().parent().parent().parent().css('z-index', '50');
        });
        ele.parent().parent().parent().removeClass('wall-card z-depth-2');
        ele.parent().parent().parent().addClass('large z-depth-4');
        ele.parent().parent().parent().parent().css('z-index', '100');
    }
    else {
        ele.parent().parent().parent().removeClass('large z-depth-4');
        ele.parent().parent().parent().addClass('wall-card z-depth-2');
        ele.parent().parent().parent().parent().css('z-index', '50');
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
    obj = JSON.parse(data);
    //console.log(data);    // for debugging
    $('.wall').empty();
    $('.wall').append(obj);
    
    set_wallHandlers();
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
    $('#posts').append(obj.html);
    
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