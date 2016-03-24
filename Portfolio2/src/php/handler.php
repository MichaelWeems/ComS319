<?php
include 'class_post.php';
include 'class_user.php';
session_start();

function build_posts($posts){
    $html  = '';
    foreach($posts as $post){
        $html .= '<div class="card-wall-wrapper">';
        $html .=    '<div class="card" id="card'.$post->get_postId().'">';
        $html .=        '<div class="card-image waves-effect waves-block waves-light">';
        $html .=            '<div class="img-container"></div>';
        $html .=        '</div>';
        $html .=        '<div class="card-content">';
        $html .=            '<span class="card-title activator grey-text text-darken-4">'.$post->get_title();
        $html .=            '<i class="material-icons right expander">more_vert</i><a style="float:right">like</a></span>';
        $html .=        '<p><a>'.$post->get_text().'</a></p>';
        $html .=        '</div>';
        $html .=        '<div class="card-reveal" id="reveal'.$post->get_postId().'">';
        $html .=            '<span class="card-title grey-text text-darken-4">Comments<i class="material-icons right expander">close</i></span>';
        
        $html .=            '<div style="height:85%">';
        
        $comments = $post->get_comments();
        foreach ($comments as $comment){
            $html .=    '<div class="row">';
            $html .=        '<div class="col s5 m12">';
            $html .=            '<div class="card-panel">';
            $html .=                '<span"><a id="commentusername'.$comment->get_commentId().'">'.$comment->get_username().'</a><br><a id="commenttext'.$comment->get_commentId().'">'.$comment->get_text().'</a></span>';
            $html .=   '</div></div></div>';
        }
        $html .= '<div style="height:15%">Add Comment textbox here</div>';
        $html .= '</div></div></div></div>';
    }
    
    
    return $html;
    
    
    

}

$user = new User($_SESSION['username']);

$op = $_GET['op'];
if ($op == "create post"){
    // needs username
	$user->createPost($_GET['title'], $_GET['text'], $_GET['data']);
    
}
else if ($op == "get all posts") {
    // needs username
    $posts = $user->get_allPosts();
    echo json_encode(build_posts($posts));
}
else if ($op == "get user posts"){
    // needs username
    $posts = $user->get_posts();
	echo json_encode(build_posts($posts));
}
else if ($op == "write comment"){
	// need postId, username
    $postId = $_GET['postId'];
    $text = $_GET['text'];
    $post = new Post($postId);
    $post->write_comment($user, $postId);
}
?>