<?php

session_start();
$json = file_get_contents("posts.txt");
if($json == ''){
	echo "<h1>sorry, no posts to display at this time</h1>";
}
else{
	$posts = json_decode($json);
	$_SESSION['posts']=$posts;
	$html = '<table class="table"><tr class="titleRow"><th class="trow">Title</th><th class="message">Message</th><th class="time">Time</th><th></th></tr>';
	$i = count($posts)-1;
	$posts = array_reverse($posts);
	
	foreach($posts as $post) {
		$html .= '<tr class="postRow"><td class="title">'.$post->title.'</td><td class="message">'.$post->message.'</td><td class="time">'.$post->time.'</td><td class="edit"><a onclick="edit('.$i.')">edit</a></td></tr>';
		$i -= 1;
	}
	$html .= '</table>';
	echo $html;
}


?>