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

    $(".mat-input").focus(function() {
      $(this).parent().addClass("is-active is-completed");
    });

    $(".mat-input").focusout(function() {
      if ($(this).val() === "")
        $(this).parent().removeClass("is-completed");
      $(this).parent().removeClass("is-active");
    });
    
    $('select').material_select();

    get_user();
    
	$('#logout').click(function() {
        logout();
    });
    
    $('#username_box').click(function(){
        load_profile( $('#username').html() );
    });
    
    $('#editBTN').click(function(){
        edit_info();
    });
    
    $('#delete').click(function(){
        var con = confirm("Are you sure you want to delete your account?\nAll your information will be deleted.");
        if(con == true){
            delete_account();
        }
    });
    
    var form = document.forms.namedItem("submit-image");
    form.addEventListener('submit', function(ev) {

      var oOutput = document.querySelector(".wall"),
          oData = new FormData(form);

      var oReq = new XMLHttpRequest();
      oReq.open("POST", "src/php/upload_image.php", true);
      oReq.onload = function(oEvent) {
        if (oReq.status == 200) {
            console.log(this.responseText);
            edit_profilepic(this.responseText);
        } else {
          oOutput.innerHTML = "Error " + oReq.status + " occurred when trying to upload your file.<br \/>";
        }
      };

      oReq.send(oData);
      ev.preventDefault();
    }, false);

}); // end of document ready function

function edit_info(){
    data = {'op':"edit info", 'question':$('#question').val(), 'answer':$('#answer').val(),
    'profilePic':$('#proPic').val(), 'password':$('#pass').val(),
    'confirmation':$('#conPass').val()}
    script = "src/php/handler.php";
    func = back_to_wall;
    ajax(data,script,func);
}

function edit_profilepic(path){
    data = {'op':"edit profile pic", 'profilePic':path}
    script = "src/php/handler.php";
    func = back_to_wall;
    ajax(data,script,func);
}

function back_to_wall(data){
    //console.log(data);
    window.location.assign("wall.html");
}

function delete_account(){
    data = {'op':"delete account", 'name':$('#username').html()};
    script = "src/php/handler.php";
    func = logging_out;
    ajax(data,script,func);
}

function logging_out(data){
    console.log(data);
    //logout();
}

