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
function build_password_change($name){
    
    $html = '<div id="change-pass" class="create-post z-depth-5" style="height:60%">';
    $html .=    '<div id="post-image-creater" class="card" style="width:250px; height:100%;">';
    
    $html .=    '<ul class="tabs tab-profile blue" style="width:100%;">';
    $html .=        '<li class="tab">';
    $html .=            '<a href="#passChange" class="waves-effect waves-dark white-text">Change Password</a>';
    $html .=        '</li>';
    $html .=    '</ul>';
    
    $html .=    '<div id="passChange" class="tab-content">';
    $html .=        '<div class="input-field">';
    $html .=            '<div class="mat-div">';
    $html .=                '<textarea id="pass" class="mat-input"></textarea>';
    $html .=                '<label for="pass" class="mat-label">New Password</label>';
    $html .=            '</div><div class="mat-div">';
    $html .=                '<textarea id="conf" class="mat-input"></textarea>';
    $html .=                '<label for="conf" class="mat-label indigo-text">Confirm Password</label>';
    $html .=            '</div>';
    $html .=            '<a id="submit-change" class="btn-large" style="float:left">Submit</a>';
    $html .=        '</div>';
    $html .=    '</div>';
    
    $html .= '</div>';
    $html .= '</div>';

    
    return $html;
}
 
function build_createPost(&$html){
    $html .= '<a id="fab" class="btn-floating btn-large waves-effect waves-light blue fab z-depth-2"></a>';
    
    $html .= '<div id="create-post" class="create-post hidden z-depth-5" style="height:60%">';
    $html .=    '<div id="post-image-creater" class="card" style="width:100%; height:100%;">';
    
    $html .=    '<ul class="tabs tab-profile blue" style="width:100%;">';
    $html .=        '<li class="tab">';
    $html .=            '<a href="#textpost" class="waves-effect waves-dark white-text">Text Post</a>';
    $html .=        '</li>';
    $html .=        '<li class="tab">';
    $html .=            '<a href="#imagepost" class="waves-effect waves-dark white-text">Image Post</a>';
    $html .=        '</li>';
    $html .=    '</ul>';
    
    $html .=    '<div id="textpost" class="tab-content">';
    $html .=        '<div class="input-field">';
    $html .=            '<div class="mat-div">';
    $html .=                '<textarea id="post-title" class="mat-input"></textarea>';
    $html .=                '<label for="post-title" class="mat-label">Title</label>';
    $html .=            '</div><div class="mat-div">';
    $html .=                '<textarea id="post-text" class="mat-input"></textarea>';
    $html .=                '<label for="post-text "class="mat-label indigo-text">Text</label>';
    $html .=            '</div>';
    $html .=            '<a id="submit-post" class="btn-large" style="float:left">Submit</a>';
    $html .=        '</div>';
    $html .=    '</div>';
    
    $html .=    '<div id="imagepost" class="tab-content">';
    $html .=        '<form id="submit-image">';
    $html .=        '<div class="input-field">';
    $html .=            '<div class="mat-div">';
    $html .=                '<input type="text" id="post-title-image" class="mat-input">';
    $html .=                '<label for="post-title-image" class="mat-label">Title</label>';
    $html .=            '</div><div class="mat-div">';
    $html .=                '<input type="text" id="post-text-image" class="mat-input">';
    $html .=                '<label for="post-text-image" class="mat-label indigo-text">Text</label>';
    $html .=            '</div>';
    $html .=            '<div class="file-field input-field" style="width:60%; float:left">';
    $html .=                '<div class="btn-large">';
    $html .=                    '<span>File</span>';
    $html .=                    '<input type="file" name="fileToUpload" id="fileToUpload" required>';
    $html .=                '</div>';
    $html .=                '<div class="file-path-wrapper" style="width:60%">';
    $html .=                    '<input id="file-validator" class="file-path validate" type="text">';
    $html .=                '</div>';
    $html .=            '</div>';
    $html .=            '<input id="submit-post-image" class="btn-large" type="submit" style="float:left">';
    $html .=        '</div>';
    $html .=        '</form>';
    $html .=    '</div>';
    
    
    $html .= '</div>';
    $html .= '</div>';
    
        /*
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
    
        */

                    
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
    $html .=                '<span><div id="commentusername'.$comment->get_commentId().'">'.$comment->get_username();
    $html .=                    '</div><div id="commenttext'.$comment->get_commentId().'">'.$comment->get_text().'</div></span>';
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

function build_image_wallpost(&$html, $post, $user){
    $html .=    '<div class="card-image waves-effect waves-block waves-light">';
    $html .=        '<div class="img-container">';
    $html .=            '<img id="imageexpander'.$post->get_postId().'" class="expander" src='.$post->get_postPath();
    $html .=                ' width="280" height="180">';
    $html .=            '<span class="card-title">'.$post->get_title().'</span>';
    $html .=        '</div>';
    $html .=    '</div>';
}

function build_text_wallpost(&$html, $post, $user){
    $html .=        '<div class="card-content">';
    $html .=            '<span class="card-title activator indigo-text text-darken-4"><div id="expander';
    $html .=                $post->get_postId().'"style="width:70%; overflow:hidden" class="expander left">';
    $html .=                '<h6 class="" style="margin-bottom:0px;">'.$post->get_username().'</h6>';
    $html .=                '<h5 style="margin-top:1%">'.$post->get_title().'</h5></div>';
    $html .=                '<i class="material-icons right">more_vert</i></span><br>';
    $html .=            '<span style="float:right"><a class="likebutton" onclick="like('.$post->get_postId().')">';
    $html .=                '<img src=src/img/like.png alt="like" style="width:16px;height:16px;"></a>';
    $html .=                '<a id="likecount'.$post->get_postId().'" style="font-size:20px"';
    $html .=                ' class="grey-text text-darken-4">'.count($post->get_likes()).'</a></span>';
    $html .=            '<span style="float:right"><div id="like'.$post->get_postId().'" style="font-size:10px">';
    if (isset($post->get_likes()[$user])) {
        $html .= 'You like this';
    }
    $html .=            '</div></span>';
    $html .=            '<div style="clear:both"></div>';
    $html .=            '<span style="clear:both">'.$post->get_text().'</span>';
    $html .=        '</div>';
}

/*
 *Builds the html for an individual post
 */
function build_post(&$html, $post, $user){
    $html .= '<div class="card-wall-wrapper">';
    $html .=    '<div class="card wall-card z-depth-2" id="card'.$post->get_postId().'">';
    
    if ( !is_null($post->get_postPath()) ){
        build_image_wallpost($html, $post, $user);
    }

    build_text_wallpost($html, $post, $user);
    
    $html .=        '<div class="card-reveal" id="reveal'.$post->get_postId().'">';
    $html .=            '<span class="card-title grey-text text-darken-4"><h6 id="commentexpander';
    $html .=                $post->get_postId().'" class="expander left">Comments</h6>';
    $html .=                    '<i class="material-icons right">close</i></span>';

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

function build_image_post(&$html, $image, $title){
    $html .= '<div class="col s4 m3">';
    $html .= '<div class="card">';
    $html .= '<div class="card-image">';
    $html .= '<img src='.$image.' width="280" height="180">';
    $html .= '<span class="card-title">'.$title.'</span>';
    $html .= '</div>';
    $html .= '</div>';
    $html .= '</div>';
}

function build_profilePic($pic, $user){
    $html = '<img src=src/img/ztwild_pic.jpg alt class="circle responsive-img';
    $html .= 'valign profile-image" width="50" height="50"></img>';
}

function build_friend_card(&$html, $friend){
    
    $html .= '<div id="'.$friend->get_username().'" class="card-wall-wrapper" style="width:200px; height:200px">';
    $html .= '<div class="card wall-card z-depth-2 indigo lighten-2">';
    $html .=    '<div class="card-image waves-effect waves-block waves-light" style="height:100%; width:100%">';
    $html .=        '<div class="img-container">';
    $html .=            '<img id="imageexpander'.$friend->get_username().'" class="expander" src='.$friend->get_pic();
    $html .=                ' width="280" height="180">';
    $html .=            '<span class="card-title white-text indigo" style="width:100%; padding-top:0px;';
    $html .=                ' padding-bottom:0px">'.$friend->get_username().'</span>';
    $html .=        '</div>';
    $html .=    '</div>';
    
    
    $html .=        '<div class="card-content">';
    
                        // View friend profile field
    $html .=            '<span id="view'.$friend->get_username().'" class="card-title friend-load';
    $html .=                    ' hoverhand indigo-text text-darken-4 left" style="height:100%;width:40%;">';
    $html .=                '<div overflow:hidden">';
    $html .=                    '<h4 style="margin-bottom:0px;">View Profile</h4>';
    $html .=                '</div>';
    $html .=                '</span>';
    
    $add = 'add';   // check if the logged in user is friends with this person
    $currentuser = new User($_SESSION['username']);
    if ($currentuser->has_friend($friend->get_username())){
        $add = 'remove';
    }
        
                        // Add friend field
    $html .=            '<span id="add'.$friend->get_username().'" class="card-title';
    $html .=                    ' friend-'.$add;
    $html .=                    ' hoverhand indigo-text text-darken-4 right" style="height:100%;width:40%;>';
    $html .=                '<div overflow:hidden">';
    $html .=                    '<h4 style="margin-bottom:0px;">'.$add.' Friend</h4>';
    $html .=                '</div>';
    $html .=                '</span>';
    $html .=        '</div>';
    $html .= '</div>';
}

function build_friend_cards($html, $friends){
    $html = '<div class="cards row" style="height:100%">';
    foreach($friends as $friend){
        build_friend_card($html, $friend);
    }
    $html .= '</div>';
    return $html;
}

$user = new User($_SESSION['username']);

$op = $_GET['op'];
if ($op == "create post"){  // creates a post
    // needs post title and text
    $post;
    if ( isset($_GET['file']) ){
        $post = $user->createPost_image($_GET['title'], $_GET['text'], $_GET['file']);
    }
    else {
        $post = $user->createPost($_GET['title'], $_GET['text']);
    }
    $html = '';
    build_post($html, $post, $user->get_username());
    $json = array('html' => $html, 
                  'expanderid' => 'expander'.$post->get_postId(), 
                  'commentexpanderid' => 'commentexpander'.$post->get_postId(),
                  'imageexpander' => 'imageexpander'.$post->get_postId(),
                  'replyid' => 'reply'.$post->get_postId());
    echo json_encode($json);
}
else if($op == "get user images"){  // gets all images from users posts
    $posts = $user->get_allPosts();
    $html = '';
    foreach($posts as $post){
        build_image_post($html, $post->get_postPath(), $post->get_title());
    }
    $json = array('html' => $html);
    echo json_encode($json);
}
else if($op == "get all images"){   // gets all images from all friends posts
    $posts = $user->get_posts();
    $html = '';
    foreach($posts as $post){
        build_image_post($html, $post->get_postPath(), $post->get_title());
    }
    $json = array('html' => $html);
    echo json_encode($json);
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
else if ($op == "load profile"){
    $_SESSION['name'] = $_GET['name'];
}
else if ($op == "get profile header"){
    $user = new User($_SESSION['name']);
    $img = '<img src='.$user->get_pic().' height=64px width=64px>';
    $name = $user->get_username();
    $json = array();
    $json['img'] = $img;
    $json['username'] = $name;
    echo json_encode($json);
}
else if ($op == "get user posts"){  // gets only the current user's posts
    // needs username
    $user_profile = new User($_SESSION['name']);
    $posts = $user_profile->get_posts();
    $html = build_posts($posts, $user_profile->get_username());
    build_createPost($html);
    $loggedin = $user_profile->get_username() == $user->get_username();
    $json = array();
    $json['html'] = $html;
    $json['loggedin'] = $loggedin;
    echo json_encode($json);
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
else if($op == "get friends"){
    $html = "";
    $json = build_friend_cards($html, $user->get_friends());
    echo json_encode($json);
}
else if($op == "add friend"){
    $user->addFriend($_GET['name']);
    echo json_encode($_GET['name']);
}
else if($op == "remove friend"){
    $user->removeFriend($_GET['name']);
    echo json_encode($_GET['name']);
}
else if($op == "search users"){
    $html = "";
    if(preg_match('/\w+/', $_GET['string'])){
        $allUsers = $user->get_search_users($_GET['string']);
        $json = build_friend_cards($html, $allUsers);
        echo json_encode($json);
    }
    else{
        echo 'false';
    }
}
else if($op == "edit info"){
    include 'connection.php';
    if(preg_match('/\w[\w*\s*]\?{0,1}/', $_GET['question'])){
        $sql = "update Group8_users set securityquestion='".$_GET['question']."' where username = '".$user->get_username()."';";
        $res = $conn->query($sql);
        echo "accepted question";
    }
    if(preg_match('/\S+/', $_GET['answer'])){
        $sql = "update Group8_users set securityanswer='".$_GET['answer']."' where username = '".$user->get_username()."';";
        $res = $conn->query($sql);
        echo "accepted answer";
    }
    if(preg_match('/\S+/', $_GET['profilePic'])){
        $sql = "update Group8_users set picPath='src/img/".$_GET['profilePic']."' where username = '".$user->get_username()."';";
        $res = $conn->query($sql);
        echo "profile picture";
    }
    if(preg_match('/[\w\d]+/', $_GET['password']) && $_GET['password'] == $_GET['confirmation']){
       $pass = md5($_GET['password']);
       $sql = "update Group8_users set Password='".$pass."' where username = '";
       $sql .= $user->get_username()."';";
       $res = $conn->query($sql);
       echo "password change";
    }
    include 'close_connection.php';
}
else if ($op == "edit profile pic"){
    include 'connection.php';
    $sql = "update Group8_users set picPath='".$_GET['profilePic']."' where username = '".$user->get_username()."';";
    $res = $conn->query($sql);
    include 'connection_close.php';
}
else if($op == "delete account"){
    $name = $_GET['name'];
    $start = "SET SQL_SAFE_UPDATES = 0;";
    $end = "SET SQL_SAFE_UPDATES = 1;";
    $like = "delete from Group8_likes where username = '".$name."';";
    $comment = "delete from Group8_comments where username = '".$name."';";
    $posts = "delete from Group8_posts where username = '".$name."';";
    $friends = "delete from Group8_friends where username = '".$name."' or friend = '".$name."';";
    $user = "delete from Group8_users where username = '".$name."';";
    include 'connection.php';
    $res = $conn->query($start);
    $res = $conn->query($like);
    $res = $conn->query($comment);
    $res = $conn->query($posts);
    $res = $conn->query($friends);
    $res = $conn->query($user);
    $res = $conn->query($end);
    include 'connection_close.php';
    echo $start;
    echo $like;
    echo $comment;
    echo $posts;
    echo $user;
    echo $end;
}
//Admin handlers
else if($op == "get all user posts"){
    $dropbox = "<a class='dropdown-button btn' href='#' data-activates='dropdown1'>Delete Post</a>";
    $dropbox .= "<ul id='dropdown1' class='dropdown-content'>";
    include 'connection.php';
    $sql = "select postId from Group8_posts order by postId desc";
    $arr = array();
    $res = $conn->query($sql);
    while($row = $res->fetch_assoc()){
        $post = new Post($row["postId"]);
        $arr[$row["postId"]] = $post;
        $dropbox .= "<li><a href='#!' onclick='delete_post(".$post->get_postId().")'>";
        $dropbox .= $post->get_title()."</a></li>";
    };
    $dropbox .= "</ul>";
    $html['dropdown'] = $dropbox;
    $html['posts'] = build_posts($arr, $user->get_username());
    build_createPost($html['posts']);
    include 'connection_close.php';
    echo json_encode($html);
    
}
else if($op == "get users"){
    $html = "";
    $dropbox = "<a class='dropdown-button btn' href='#' data-activates='dropdown1'>Delete User</a>";
    $dropbox .= "<ul id='dropdown1' class='dropdown-content'>";
    include 'connection.php';
    $sql = "select username from Group8_users where admin = 'false'";
    $arr = array();
    $res = $conn->query($sql);
    while($row = $res->fetch_assoc()){
        $person = new Friend($row["username"]);
        $arr[$row["username"]] = $person;
        //$dropbox .= "<li><a href='#!'>";
        $dropbox .= "<li><a href='#!' class='user-cards'>";
        $dropbox .= $person->get_username()."</a></li>";
    };
    $dropbox .= "</ul>";
    $html['dropdown'] = $dropbox;
    $html['cards'] = build_friend_cards($html, $arr);
    
    include 'connection_close.php';
    echo json_encode($html);
}

else if($op == "delete post"){
    $postId = $_GET['postId'];
    $start = "SET SQL_SAFE_UPDATES = 0;";
    $end = "SET SQL_SAFE_UPDATES = 1;";
    $like = "delete from Group8_likes where postId = ".$postId.";";
    $comment = "delete from Group8_comments where postId = ".$postId.";";
    $posts = "delete from Group8_posts where postId = ".$postId.";";
    
    include 'connection.php';
    $res = $conn->query($start);
    $res = $conn->query($like);
    $res = $conn->query($comment);
    $res = $conn->query($posts);
    $res = $conn->query($end);
    include 'connection_close.php';
    
}

else if($op == "password prompt"){
    $html['prompt'] = build_password_change($_GET['name']);
    $html['name'] = $_GET['name'];
    echo json_encode($html);
    
}

else if($op == "password change"){
    include 'connection.php';
    if(preg_match('/[\w\d]+/', $_GET['pass']) && $_GET['pass'] == $_GET['conf']){
       $pass = md5($_GET['pass']);
       $sql = "update Group8_users set Password='".$pass."' where username = '";
       $sql .= $_GET['name']."';";
       $res = $conn->query($sql);
       echo "password change";
    }
    
    include 'connection_close.php';
}





?>