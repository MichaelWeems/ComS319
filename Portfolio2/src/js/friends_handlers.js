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
    
	$('#logout').click(function() {
        logout();
    });
    $('#search').keypress(function(e) {
        if(e.which == 13) {
            e.preventDefault();
            search();
        }
    });
    $('.name-tag').click(function(){
        profile_relocate( $(this).html() );
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
    if(obj != false){
        $('.wall').empty();
        $('.wall').append(obj);
    }
}

