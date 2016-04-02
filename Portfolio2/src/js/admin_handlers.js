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
    
    $('#posts').click(function(){
        get_wallPosts();
    });
    
    $('#users').click(function(){
        build_user_wall();
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
    var data = {'op': 'get all user posts'};
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
    $('.wall').append(obj.dropdown+obj.posts);
    
    $('.dropdown-button').dropdown();
    set_wallHandlers();
}

function build_user_wall(){

    $('.wall').empty();
    get_users();

}

function get_users(){
    var data = {'op':'get users'};
    var script = "src/php/handler.php";
    var func = get_users_callback;
    ajax(data,script,func);
}

function get_users_callback(data){
    obj = JSON.parse(data);
   
    $('.wall').append(obj.dropdown);
    $('.wall').append(obj.cards);
    
    console.log(obj.dropdown);
    $('.dropdown-button').dropdown();
    
    $('.friend-load').click(function(){
        console.log( $(this).html() );
        password_prompt( $(this).html() );
    });
    
    $('.user-cards').click(function(){
        console.log($(this).html());
        delete_account($(this).html());
    });
    
}

function delete_account(username){
    data = {'op':"delete account", 'name':username};
    script = "src/php/handler.php";
    func = build_user_wall;
    ajax(data,script,func);
}



function delete_post(postId){
    data = {'op':"delete post", 'postId':postId};
    script = "src/php/handler.php";
    func = get_wallPosts;
    ajax(data,script,func);
}

function password_prompt(name){
    console.log("prompt should show");
    data = {'op':"password prompt", 'name':name};
    script = "src/php/handler.php";
    func = prompt_callback;
    ajax(data,script,func);
}

function prompt_callback(data){
    var obj = JSON.parse(data);
    $('.wall').append(obj.prompt);
    console.log(obj.prompt);
    
    $('#submit-change').click(function(){
        change_password();
    });
    
    function change_password(){
        data = {'op':"password change", 'pass':$('#pass').val(), 'conf':$('#conf').val(), 'name':obj.name};
        script = "src/php/handler.php";
        func = change_password_callback;
        console.log(data);
        ajax(data,script,func);
    }
    
    
}

    
function change_password_callback(data){
    $('#change-pass').removeClass('unhidden');
    $('#change-pass').addClass('hidden');
    console.log(data);
}




