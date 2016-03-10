<?php
var_dump($_GLOBALS);
session_start();
date_default_timezone_set('America/Chicago');
$time = new DateTime();

// edit a post on the server
if ( isset($_GET['post_Index']) ) {
	$posts = $_SESSION['posts'];
	$i = $_GET['i'];
	
	$posts[$i]->"title" = $_GET["title"];
	$posts[$i]->"message" = $_GET["message"];
	
	
	$posts[$i]->"time" = $time->format('H:i');
	
	$toFile = json_encode($posts);
	file_put_contents("posts.txt", $toFile);


}
else if ( isset($_SESSION['posts']) ) { // add a post to the server
	$posts = $_SESSION['posts'];
	array_push($posts, array('title' => $_POST['title'], 'message' => $_POST['message'], 'time' => $time->format('H:i')));
	$toFile = json_encode($posts);
	file_put_contents('posts.txt', $toFile);
}
else { // initialize the post array on the server
	$toFile = array('title' => $_POST['title'], 'message' => $_POST['message'], 'time' => $time->format('H:i'));
	file_put_contents('posts.txt', json_encode($toFile));
}
?>