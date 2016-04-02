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
    get_friends();
    
    $('#username_box').click(function(){
        console.log($('#username').html());
        load_profile( $('#username').html() );
    });
    
	$('#logout').click(function() {
        logout();
    });
    
    $('#search').keypress(function(e) {
        if(e.which == 13) {
            e.preventDefault();
            search();
        }
    });
    
    $('.card-title').click(function(){
        console.log( $(this).html() );
    });
    
   

}); // end of document ready function


function search(){
    var data = {'op':'search users', 'string':$('#search').val()};
    var script = "src/php/handler.php";
    var func = get_friends_callback;
    console.log($('#search').val());
    ajax(data,script,func);
}


function get_friends(){
    var data = {'op':'get friends'};
    var script = "src/php/handler.php";
    var func = get_friends_callback;
    
    ajax(data,script,func);
}

function add_friend(name){
    var data = {'op':'add friend', 'name': name};
    var script = "src/php/handler.php";
    var func = add_friend_callback;
    
    ajax(data,script,func);
}

function remove_friend(name){
    var data = {'op':'remove friend', 'name': name};
    var script = "src/php/handler.php";
    var func = add_friend_callback;
    
    ajax(data,script,func);
}

function get_friends_callback(data){
    var obj = JSON.parse(data);
    //console.log(data);
    if(obj != false){
        $('.wall').empty();
        $('.wall').append(obj);
    }
    
    $('.friend-load').click(function(){
        console.log($(this).attr('id').substring(4));
        load_profile($(this).attr('id').substring(4));
    });
    
    $('.friend-add').click(function(){
        console.log($(this).attr('id').substring(3));
        add_friend($(this).attr('id').substring(3));
    });
    
    $('.friend-remove').click(function(){
        console.log($(this).attr('id').substring(3));
        remove_friend($(this).attr('id').substring(3));
    });
    
    set_allFriendsHandlers();
}


/////////////////////////////////////////////////////////////////////////////////
//	set_allFriendsHandlers
//
//		Sets all handlers needed by the friend card elements.
//      Called after receiving all the current friends from the database
//
function set_allFriendsHandlers(){
    
    $('.expander').click(function(){
        set_expanderHandler($(this));
    });
    
}

// Helper for setting the friend handlers
function set_expanderHandler(ele){
    
    if (ele.parent().parent().parent().hasClass('wall-card')) {
        $('.expander').each(function(){
            $(this).parent().parent().parent().removeClass('large z-depth-4');
            $(this).parent().parent().parent().addClass('wall-card z-depth-2');
            $(this).parent().parent().parent().parent().css('z-index', '50');
            $(this).removeClass('large-image');
        });
                               
        ele.parent().parent().parent().removeClass('wall-card z-depth-2');
        ele.parent().parent().parent().addClass('large z-depth-4');
        ele.parent().parent().parent().parent().css('z-index', '100');
        ele.addClass('large-image');
    }
    else {
        ele.parent().parent().parent().removeClass('large z-depth-4');
        ele.parent().parent().parent().addClass('wall-card z-depth-2');
        ele.parent().parent().parent().parent().css('z-index', '50');
        ele.removeClass('large-image');
    }
}


function add_friend_callback(data){
    console.log(data);
    var obj = JSON.parse(data);
    $('#' + obj).remove();
    get_friends();
}