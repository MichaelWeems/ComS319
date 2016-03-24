<?php
include 'class_post.php';
include 'class_user.php';
session_start();
function build_wallPosts($posts){
    
    $html  = '';
    foreach($posts as $post){
        $html .= '<div class="card-wall-wrapper">';
        $html .=    '<div class="card" style="width:100%; height:100%">';
        $html .=        '<div class="card-image waves-effect waves-block waves-light">';
        $html .=            '<div class="img-container"></div>';
        $html .=        '</div>';
        $html .=        '<div class="card-content">';
        $html .=            '<span class="card-title activator grey-text text-darken-4">'.$post->get_title();
        $html .=            '<i class="material-icons right">more_vert</i></span>';
        $html .=        '<p><a href="#">'.$post->get_text().'</a></p>';
        $html .=        '</div>';
        $html .=        '<div class="card-reveal">';
        $html .=            '<span class="card-title grey-text text-darken-4">Card Title<i class="material-icons right">close</i></span>';
        $html .=            '<p>Pandas!</p>';
        $html .= '</div></div></div>';
    }
    return $html;
}

function build_profilePosts($user){
    
    
}

$user = new User($_SESSION['username']);

$op = $_GET['op'];
if ($op == "create post"){
    // needs username
	$user->createPost($_GET['title'], $_GET['text'], $_GET['data']);
    
}
else if ($op == "get all posts") {
    // needs username
    $posts = $user->get_posts();
    echo json_encode(build_wallPosts($posts));
}
else if ($op == "get user posts"){
    // needs username
	echo json_encode(build_profilePosts());
}
else if ($op == "write comment"){
	// need postId, username
}
?>