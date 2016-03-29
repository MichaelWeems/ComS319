<?php
session_start();
include 'class_post.php';
include 'class_user.php';
include 'class_applist.php';
include 'class_image.php';



/********************************************************************************************************
 *  Build the create post input
 */

/*
 *  Builds the HTML to house a create post input to add a new post. 
 */
function build_createPost(&$html){
    
    $html .= '<a id="fab" class="btn-floating btn-large waves-effect waves-light red fab z-depth-2"><i class="material-icons">add</i></a>';
    
    $html .= '<div id="create-post" class="card create-post hidden z-depth-5">';
    $html .=    '<div class="card-content">';
    $html .=        '<h4>New Post</h4>';
    $html .=        '<div class="mat-div">';
    $html .=            '<label for="post-title" class="mat-label indigo-text">Title</label>';
    $html .=            '<input type="text" name="post-title" id="post-title" class="mat-input">';
    $html .=        '</div>';
    $html .=        '<div class="mat-div">';
    $html .=            '<label for="post-text "class="mat-label indigo-text">Text</label>';
    $html .=            '<input type="text" name="post-text" id="post-text" class="mat-input">';
    $html .=        '</div>';
    $html .=            '<a id="submit-post" class="btn-large waves-effect waves-light indigo">Submit</a>';
    $html .=    '</div>';
    $html .= '</div>';
                    
}


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
        $html .=            '<div class="img-container"><img src=src/img/'.$app->get_name().'.jpg></div>';
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
    $html .=                '<span><div id="commentusername'.$comment->get_commentId().'">'.$comment->get_username().'</div><div id="commenttext'.$comment->get_commentId().'">'.$comment->get_text().'</div></span>';
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
 *Builds the html for an individual post
 */
function build_post(&$html, $post, $user){
    $html .= '<div class="card-wall-wrapper">';
    $html .=    '<div class="card wall-card z-depth-2" id="card'.$post->get_postId().'">';
    $html .=        '<div class="card-image waves-effect waves-block waves-light">';
    $html .=            '<div class="img-container"></div>';
    $html .=        '</div>';
    $html .=        '<div class="card-content">';
    $html .=            '<div></div>';
    $html .=            '<span class="card-title activator indigo-text text-darken-4"><div id="expander'.$post->get_postId().'" style="width:70%; overflow:hidden" class="expander left"><h6 class="" style="margin-bottom:0px;">'.$post->get_username().'</h6><h5 style="margin-top:1%">'.$post->get_title().'</h5></div>';
    $html .=                '<i class="material-icons right">more_vert</i></span><br>';
    $html .=            '<span style="float:right"><a class="likebutton" onclick="like('.$post->get_postId().')">';
    $html .=                '<img src=src/img/like.png alt="like" style="width:16px;height:16px;"></a>';
    $html .=                '<a id="likecount'.$post->get_postId().'" style="font-size:20px" class="grey-text text-darken-4">'.count($post->get_likes()).'</a></span>';
    $html .=            '<span style="float:right"><div id="like'.$post->get_postId().'" style="font-size:10px">';
    if (isset($post->get_likes()[$user])) {
        $html .= 'You like this';
    }
    $html .=            '</div></span>';
    $html .=            '<div style="clear:both"></div>';
    $html .=            '<span style="clear:both">'.$post->get_text().'</span>';
    $html .=        '</div>';
    $html .=        '<div class="card-reveal" id="reveal'.$post->get_postId().'">';
    $html .=            '<span class="card-title grey-text text-darken-4"><h6 id="commentexpander'.$post->get_postId().'" class="expander left">Comments</h6><i class="material-icons right">close</i></span>';

    $comments = $post->get_comments();
    build_comments($html, $comments, $post->get_postId());

    $html .= '</div></div></div></div>';
}

/*
 *  Builds the HTML to house a list of posts. Will be appended to
 *  a div when received by the javascript handler.
 *  Creates a card container to host the title, text, and comments
 *  of each given post.
 */
function build_posts($posts, $user){
    $html  = '<div id="posts" style="height:100%; width:100%">';
    foreach($posts as $post){
        build_post($html, $post, $user);
    }
    $html .= '</div>';
    return $html;
}

function build_image_post($images, $user){
    $html = '';
    var_dump($images);
    foreach($images as $image){
        $html .= '<div class="col s4 m3">';
        $html .= '<div class="card">';
        $html .= '<div class="card-image">';
        $html .= '<img src="'.$image->get_path().'" width="280" height="180">';
        $html .= '<span class="card-title">'.$image->get_title().'</span>';
        $html .= '</div>';
        $html .= '</div>';
        $html .= '</div>';
    }
    return $html;
}

function build_profilePic($pic, $user){
    $html = '<img src="src/img/ztwild_pic.jpg" alt class="circle responsive-img';
    $html .= 'valign profile-image" width="50" height="50"></img>';
}

$user = new User($_SESSION['username']);

$op = $_GET['op'];
if ($op == "create post"){  // creates a post
    // needs post title and text
	$post = $user->createPost($_GET['title'], $_GET['text']);
    $html = '';
    build_post($html, $post, $user->get_username());
    $json = array('html' => $html, 
                  'expanderid' => 'expander'.$post->get_postId(), 
                  'commentexpanderid' => 'commentexpander'.$post->get_postId(),
                  'replyid' => 'reply'.$post->get_postId());
    echo json_encode($json);
}
else if($op == "get user images"){
    $images = $user->get_images();
    var_dump($images);
    echo json_encode(build_image_post($images, $user->get_username()));
}
else if($op == "get all images"){
    $images = $user->get_allImages();
    echo json_encode(build_image_post($images, $user->get_username()));
}
else if($op == "get profile pic"){
    $pic = $user->get_pic();
    echo json_encode(build_profilePic($pic, $user->get_username()));
}
else if ($op == "get all posts") {
    // needs username
    $posts = $user->get_allPosts();
    $html = build_posts($posts, $user->get_username());
    build_createPost($html);
    echo json_encode($html);
}
else if ($op == "get user posts"){  // gets only the current user's posts
    // needs username
    $posts = $user->get_posts();
	echo json_encode(build_posts($posts,$user->get_username()));
}
else if ($op == "write comment"){   // adds a comment to a post
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
else if ($op == "like"){    // adds a like from the current user to the post
    $postId = $_GET['postId'];
    $post = new Post($postId);
    $ret = array();
    $ret['like'] = $post->like($user->get_username());
    $ret['postId'] = $postId;
    $ret['likecount'] = count($post->get_likes());
    echo json_encode($ret);
}
else if ($op == "get all apps") {   // get the html for selecting each app
    $applist = new AppList();
    echo json_encode(build_appSelectors($applist->get_applist()));
}
else if ($op == "open app"){    // saves the requested app info in the session
    $app = new App($_GET['appname']);
    $_SESSION['html'] = file_get_contents($app->get_html_location());
    $_SESSION['scripts'] = file_get_contents($app->get_htmlscripts_location());
}
else if ($op == "get app") {    // Gets the requested app from the session
    $json = array();
    $json['html'] = $_SESSION['html'];
    $json['scripts'] = $_SESSION['scripts'];
    echo json_encode($json);
}
?>