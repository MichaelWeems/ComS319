<?php

class User {

	private $username;
    private $friends;
    private $posts;
    private $images;
    private $pic;

	function __construct($name) {
		$this->username = $name;
        $this->set_friends();
        $this->set_posts();
        $this->set_images();
        $this->set_pic();
	}
    
    public function set_posts() {
		include 'connection.php';
		
		$this->posts = array();
		$sql = "select postId from Group8_posts where username = '".$this->get_username()."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->posts[$row["postId"]] = new Post($row["postId"]);
		};
        
		include 'connection_close.php';
	}
    
    public function set_images(){
        include 'connection.php';
        
        $this->images = array();
		$sql = "select imageId from Group8_images where username = '".$this->get_username()."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->images[$row["imageId"]] = new Image($row["imageId"]);
		};
        include 'connection_close.php';
    }
    
    public function set_pic(){
        include 'connection.php';
		
		$sql = "select picPath from Group8_users where username = '".$this->get_username()."';";
		$res = $conn->query($sql);
		$row = $res->fetch_assoc();
        $this->pic = $row["picPath"];
        
		include 'connection_close.php';
    }
    
    public function get_images(){
        return $this->images;
    }
    
    public function get_pic(){
        return $this->pic;
    }
    public function get_posts(){
        return $this->posts;
    }
    
    
    
    public function get_allPosts() {
		include 'connection.php';
		
		$posts = array();
        $friends = array();
		$sql = "select friend from Group8_friends where username = '".$this->get_username()."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			array_push($friends, $row["friend"]);
		};
        
        foreach ($friends as $friend){
            $sql = "select postId from Group8_posts where username = '".$this->get_username()."' OR username = '".$friend."';";
            $res = $conn->query($sql);
            while($row = $res->fetch_assoc()){
                $posts[$row["postId"]] = new Post($row["postId"]);
            };
        }
        
        return $posts;
		include 'connection_close.php';
	}
    
    public function get_allImages(){
        include 'connection.php';
        
        $images = array();
        $friends = array();
		$sql = "select friend from Group8_friends where username = '".$this->get_username()."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			array_push($friends, $row["friend"]);
		};
        
        foreach ($friends as $friend){
            $sql = "select imageId from Group8_images where username = '".$this->get_username()."' OR username = '".$friend."';";
            $res = $conn->query($sql);
            while($row = $res->fetch_assoc()){
                $posts[$row["imageId"]] = new Image($row["imageId"]);
            };
        }
        
        return $images;
        
        include 'connection_close.php';
    }
    
    public function set_friends() {
		include 'connection.php';
		
		$this->friends = array();
		$sql = "select friend from Group8_friends where username = '".$this->get_username()."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			array_push($this->friends, $row["friend"]);
		};
		
		include 'connection_close.php';
	}

	public function get_username() {
		return $this->username;
	}

	// returns an array of the books loaned out to this student
	public function getFriends_array() {
        include 'connection.php';
		$sql = "select friend from Group8_friends where username = '".$this->username."';";
		$res = $con->query($sql);
		$arr = array();
		while($row = $res->fetch_assoc()){
            array_push($arr, $row["friend"]);
		};
        include 'connection_close.php';
		return $arr;
	}
	
	public function addFriend($friend){
		include 'connection.php';

        $sql = "insert into Group8_friend(username, friend)values('".$this->username."', '".$friend."');";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
		include 'connection_close.php';
	}
    
    public function createPost($title, $text, $data) {
		include 'connection.php';
        
        $sql = "insert into Group8_posts(title, text, data, username)values('".$title."', '".$text."', '".$data."', '".$this->get_username()."');";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
        
		include 'connection_close.php';
	}
	
	public function deletePost(){
		include 'connection.php';
        
		$sql = "delete from Group8_posts where postId = '".$this->postId."';";
		if (!$conn->query($sql)){
			die("Invalid query: ".$conn->error);
		}
		
		include 'connection_close.php';
	}
}
?>