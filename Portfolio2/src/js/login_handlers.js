////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers

$(document).ready(function() {

	hideAll();

	$("#makepost").click(function() {
		showCreatePost();
		$('#Title').focus();
	});

	$('#Message').keydown(function (e){
		if(e.keyCode == 13){
			submitPost();
		}
	})

	$("#submit").click(function() {
		submitPost();
	});

	$("#login").click (function () {
	  
	  req = "username=" + $("#username").val();
	  req += "&password=" + $("#password").val();
	  
	  var username_var = $("#username").val();
	  
	  console.log("checkLogin.php?" + req);
	  $.get("checkLogin.php?" + req, 
			function(data,status) {
				console.log("Login was successful: " + data);
				if ( data == "true" ) {
					$(".loginDiv,.app").toggle();
					$('.createPosts').show();
					console.log("username: " + username_var);
					$.post("viewPosts.php", {username: username_var},
						function (data) {
							console.log(data);
							$(".viewPosts").html(data);
						});
				}
				else {
					alert("Incorrect login information");
					$("#username").val("");
					$("#password").val("");
				}
	   });
	});

	$("#logout").click (function () {
		$.post("logout.php", {}, function(data) {
				console.log(data);
			 $("#includedContent").load("login.html"); 
		});
	});


}); // end of document ready function