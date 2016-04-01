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

function get_friends_callback(data){
    var obj = JSON.parse(data);
    console.log(data);
    //console.log(data);
    if(obj != false){
        $('.wall').empty();
        $('.wall').append(obj);
    }
    
    $('.friend-load').click(function(){
        console.log($(this).html());
        load_profile($(this).html());
    });
    
}