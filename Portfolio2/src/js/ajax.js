/////////////////////////////////////////////////////////////////////////////////
//	ajax
//
//		calls the designated php script with the given data. 
//		on return it will call the given callback function
//
function ajax(parameters, script, func){
    $.ajax({
        type: 'GET',
        url: script,
        data: parameters,
        success: function(data){func(data);}
    });
}

/////////////////////////////////////////////////////////////////////////////////
//	logout
//
//		logs the user out, invalidates their session
//
function logout(){
    var data = "";
    var script = 'src/php/logout.php';
    var func = logout_callback;
    
    console.log('Logging the user out');
    ajax(data,script,func);
}

/////////////////////////////////////////////////////////////////////////////////
//	logout_callback
//
//		Receives logout verification from server, reloads login page
//
function logout_callback(data){
    console.log("Successfully logged out");
    window.location.assign("index.html");
}

/////////////////////////////////////////////////////////////////////////////////
//	get_user
//
//		Gets the username from the session
//
function get_user(){
    var data = "";
    var script = 'src/php/get_user.php';
    var func = get_user_callback;
    
    console.log('Getting the username');
    ajax(data,script,func);
}

/////////////////////////////////////////////////////////////////////////////////
//	get_user_callback
//
//		Receives the currently logged in user's username
//
function get_user_callback(data){
    console.log("Successfully obtained username");
    obj = JSON.parse(data);
    $('#username').html(obj.username);
    //this is added to get the profile picture
    $('#userPic').attr('src', obj.picPath);
}

