<?php
include 'class_comment.php';

class Post {

	private $postId;
	private $title;
	private $text;
	private $data;
    private $image;
    private $comments;
    private $likes;

	function __construct($id) {
        $this->postId = $id;
        $this->title = "";
        $this->text = "";
        $this->data = "";
        $this->image = "";
        $this->comments = array();
        $this->likes = array();
        
        $this->setvars_fromDB();
	}
    
    public function setvars_fromDB(){
        include 'connection.php';
		
        $sql = "select title, text, data from Group8_posts where postId = '".$this->postId."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->set_title($row["title"]);
			$this->set_text($row["text"]);
            $this->set_data($row["data"]);
        }
        
        $sql = "select commentId from Group8_comments where postId = '".$this->postId."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->comments[$row["commentId"]] = new Comment($row["commentId"]);
        }
        
        $sql = "select likeId, username from Group8_likes where postId = '".$this->postId."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->likes[$row["username"]] = $row["likeId"];
        }

        include 'connection_close.php';
    }
    
	public function set_title($btitle) {
		$this->title = $btitle;
	}	
	
	public function set_text($text) {
		$this->text = $text;
	}
	
	public function set_data($data) {
		$this->data = $data;
	}
    
    public function set_image($image) {
		$this->image = $image;
	}
    
    

	public function get_postId() {
		return $this->postId;
	}  
	
	public function get_title() {
		return $this->title;
	}

	public function get_text() {
		return $this->text;
	}  

	public function get_data() {
		return $this->data;
	}  

	public function get_image() {
		return $this->image;
	}
    
    public function get_comments(){
        return $this->comments;
    }
    
    public function get_likes(){
        return $this->likes;
    }
    
    
    public function write_comment($user, $text){
        include 'connection.php';
        $sql = "insert into Group8_comments(username, text, postId)values('".$user."', '".$text."', '".$this->postId."');";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
		include 'connection_close.php';
    }
    
    public function get_lastCommentByUser($user){
        include 'connection.php';
        $sql = "select max(commentId) from Group8_comments where username = '".$user."'";
		$res = $conn->query($sql);
        while($row = $res->fetch_assoc()){
            $comment = new Comment($row['max(commentId)']);
        };
		include 'connection_close.php';
        return $comment;
    }
    
    
    public function like($user){
        
        if (isset($this->likes[$user])){
            $ret = $this->unlike($user);
            return $ret;
        }
        
        include 'connection.php';
        $sql = "insert into Group8_likes(username, postId)values('".$user."', '".$this->postId."');";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
        
        $sql = "select likeId from Group8_likes where username = '".$user."' and postId = '".$this->get_postId()."';";
		$res = $conn->query($sql);
        while($row = $res->fetch_assoc()){
            $this->likes[$user] = $row['likeId'];
        };
            
		include 'connection_close.php';
        
        return 'liked';
    }
    
    public function unlike($user){
        include 'connection.php';
        $sql = "delete from Group8_likes where username = '".$user."' and postId = '".$this->get_postId()."';";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
        unset($this->likes[$user]);
		include 'connection_close.php';
        return 'unliked';
    }
	
    
    
    
	
}
?>