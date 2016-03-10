<?php
session_start();
date_default_timezone_set('America/Chicago');
$time = new DateTime();

//if ( $_REQUEST['op'] == 'add' ) { // add a post to the server
if ( array_key_exists('index', $_REQUEST) ) {
	$posts = $_SESSION['posts'];
	$i = $_REQUEST['index'];
	
	$reqMess = $_REQUEST["message"];
	
	$posts[$i]->message = $reqMess;
	$posts[$i]->time = $time->format('H:i');
	
	$toFile = json_encode($posts);
	file_put_contents('posts.txt', $toFile);
}

else if (isset($_SESSION['posts'])) {
	$posts = $_SESSION['posts'];
	array_push($posts, array('title' => $_POST['title'], 'message' => $_POST['message'], 'time' => $time->format('H:i')));
	$toFile = json_encode($posts);
	file_put_contents('posts.txt', $toFile);
}
else {
	$toFile = array('title' => $_POST['title'], 'message' => $_POST['message'], 'time' => $time->format('H:i'));
	file_put_contents('posts.txt', json_encode($toFile));
}


?>