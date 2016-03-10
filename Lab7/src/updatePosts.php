<?php
session_start();
date_default_timezone_set('America/Chicago');
$time = new DateTime();

echo 'username: '.$_SESSION['username'];

if ( array_key_exists('like', $_REQUEST) ) {
	$user = $_SESSION['username'];
	$posts = $_SESSION['posts'];
	$i = $_REQUEST['index'];
	
	$index = array_search($user, $posts[$i]->likes);
	if ( is_int($index) ) {  // unlike a post
		unset($posts[$i]->likes[$index]);
	}
	else {  // like a post
		array_push($posts[$i]->likes, $user);
	}
	$toFile = json_encode($posts);
	file_put_contents('posts.txt', $toFile);
}
else if ( array_key_exists('index', $_REQUEST) ) {
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
	array_push($posts, array('title' => $_POST['title'], 'message' => $_POST['message'], 'time' => $time->format('H:i'), 'likes' => []));
	$toFile = json_encode($posts);
	file_put_contents('posts.txt', $toFile);
}
else {
	$toFile = array('title' => $_POST['title'], 'message' => $_POST['message'], 'time' => $time->format('H:i'), 'likes' => []);
	file_put_contents('posts.txt', json_encode($toFile));
}


?>