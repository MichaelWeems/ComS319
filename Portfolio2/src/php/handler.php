<?php
session_start();
include 'class_post.php';
include 'class_user.php';
include 'class_applist.php';

/********************************************************************************************************
 *  Build app select html
 */

/*
 *  Builds the HTML to house a list of app selectors. 
 *  Will be appended to a div when received by the 
 *  javascript handler. Creates a card container to 
 *  host the app name and description for each app.
 */
function build_appSelectors($apps){
    $html  = '';
    foreach($apps as $app){
        $html .= '<div class="card-app-wrapper">';
        $html .=    '<div class="card app-card" id="card'.$app->get_name().'">';
        $html .=        '<div class="card-image waves-effect waves-block waves-light">';
        $html .=            '<div class="img-container"></div>';
        $html .=        '</div>';
        $html .=        '<div class="card-content">';
        $html .=            '<span class="card-title activator grey-text text-darken-4">'.$app->get_name();
        $html .=            '</span><br>';
        $html .=            '<p><div style="float:left">'.$app->get_description().'</div></p>';
        $html .=        '</div>';
        $html .= '</div></div>';
    }
    return $html;
}



/********************************************************************************************************
 *  Build wall post html
 */

/*
 *  Builds the HTML to house a reply field to add a new comment. 
 */
function build_replyField(&$html, $postId){
    
    $html .= '<div style="height:15%">';
    $html .= '<div class="row"><form class="col s12"><div class="row"><div class="input-field col s12">';
    $html .= '<textarea id="reply'.$postId.'" class="materialize-textarea"></textarea>';
    $html .= '<label for="reply'.$postId.'">Reply</label>';
    $html .= '</div></div></form></div>';
}

/*
 *  Builds the HTML to house a comment.
 */
function build_one_comment(&$html, $comment){
    $html .=    '<div class="row" style="margin-top:0px; margin-bottom:0px; padding-top: -25px; padding-bottom: -25px">';
    $html .=        '<div class="col s5 m12">';
    $html .=            '<div class="card-panel">';
    $html .=                '<span><a id="commentusername'.$comment->get_commentId().'">'.$comment->get_username().'</a><br><a id="commenttext'.$comment->get_commentId().'">'.$comment->get_text().'</a></span>';
    $html .=   '</div></div></div>';
}

/*
 *  Builds the HTML to house a list of comments. Each comment
 *  section holds the comments and an input field to add another
 *  comment.
 */
function build_comments(&$html, $comments, $postId){
    
    $html .= '<div style="height:85%">';
    $html .=    '<div id="comments'.$postId.'">';
    foreach ($comments as $comment){
        build_one_comment($html, $comment);
    }
    
    $html .= '</div>';
    build_replyField($html, $postId);
    $html .= '</div>';
}

/*
 *  Builds the HTML to house a list of posts. Will be appended to
 *  a div when received by the javascript handler.
 *  Creates a card container to host the title, text, and comments
 *  of each given post.
 */
function build_posts($posts, $user){
    $html  = '';
    foreach($posts as $post){
        $html .= '<div class="card-wall-wrapper">';
        $html .=    '<div class="card wall-card" id="card'.$post->get_postId().'">';
        $html .=        '<div class="card-image waves-effect waves-block waves-light">';
        $html .=            '<div class="img-container"></div>';
        $html .=        '</div>';
        $html .=        '<div class="card-content">';
        $html .=            '<span class="card-title activator grey-text text-darken-4">'.$post->get_title();
        $html .=            '<i class="material-icons right expander">more_vert</i></span><br>';
        $html .=            '<span style="float:right"><a class="likebutton" onclick="like('.$post->get_postId().')">';
        $html .=            '<img src="src/img/like.png" alt="like" style="width:16px;height:16px;"></a>';
        $html .=            '<a id="likecount'.$post->get_postId().'" style="font-size:20px">'.count($post->get_likes()).'</a>';
        $html .=            '<div id="like'.$post->get_postId().'" style="font-size:10px">';
        if (isset($post->get_likes()[$user])) {
            $html .= 'You like this';
        }
        $html .=            '</div></span>';
        $html .=            '<p><div style="float:left">'.$post->get_text().'</div></p>';
        $html .=        '</div>';
        $html .=        '<div class="card-reveal" id="reveal'.$post->get_postId().'">';
        $html .=            '<span class="card-title grey-text text-darken-4">Comments<i class="material-icons right expander">close</i></span>';
        
        $comments = $post->get_comments();
        build_comments($html, $comments, $post->get_postId());
        
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
    echo json_encode(build_posts($posts, $user->get_username()));
}
else if ($op == "get user posts"){
    // needs username
    $posts = $user->get_posts();
	echo json_encode(build_posts($posts,$user->get_username()));
}
else if ($op == "write comment"){
	// need postId, text
    $postId = $_GET['postId'];
    $text = $_GET['text'];
    $post = new Post($postId);
    $post->write_comment($user->get_username(), $text);
    $comment = $post->get_lastCommentByUser($user->get_username());
    $html = '';
    build_one_comment($html, $comment);
    $ret = array('html' => $html, 
                 'postId' => $post->get_postId(),
                 'username' => $user->get_username());
    echo json_encode($ret);
}
else if ($op == "like"){
    $postId = $_GET['postId'];
    $post = new Post($postId);
    $ret = array();
    $ret['like'] = $post->like($user->get_username());
    $ret['postId'] = $postId;
    $ret['likecount'] = count($post->get_likes());
    echo json_encode($ret);
}
else if ($op == "get all apps") {
    $applist = new AppList();
    echo json_encode(build_appSelectors($applist->get_applist()));
}
else if ($op == "open app"){
    $app = new App($_GET['appname']);
    $_SESSION['html'] = file_get_contents($app->get_html_location());
    $_SESSION['scripts'] = file_get_contents($app->get_htmlscripts_location());
    var_dump($_SESSION);
}
else if ($op == "get app") {
    $json = array();
    $json['html'] = $_SESSION['html'];
    $json['scripts'] = $_SESSION['scripts'];
    echo json_encode($json);
}
?>