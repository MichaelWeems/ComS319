<?php

session_start();
$file = file_get_contents("posts.txt");

if ( array_key_exists('username', $_REQUEST) ) {
	$_SESSION['username'] = $_REQUEST['username'];
}

if($file == ''){
	echo "<h1>sorry, no posts to display at this time</h1>";
}
else{
	$posts = json_decode($file);
	$_SESSION['posts']=$posts;
	$html = '<table class="table"><tr class="titleRow"><th class="trow" align="left">Title</th><th class="message" align="left">Message</th><th class="time" align="left">Time</th><th class="edit" align="left"></th><th class="like" align="left"></th></tr>';
	$i = count($posts)-1;
	$posts = array_reverse($posts);
	
	foreach($posts as $post) {
		$html .= '<tr class="postRow"><td class="title">'.$post->title.'</td><td class="message">'.$post->message.'</td><td class="time">'.$post->time.'</td><td class="edit"><a onclick="edit('.$i.')"><img src="images/edit.png" alt="edit" style="width:15px;height:15px;"></a></td><td class="like"><a onclick="like('.$i.')"><img src="images/like.ico" alt="like" style="width:15px;height:15px;"></a>'.count($post->likes).'</td></tr>';
		$i -= 1;
	}
	$html .= '</table>';
	echo $html;
}


?>