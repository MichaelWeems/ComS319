<?php

class User {

	private $username;
    private $friends;
    private $posts;

	function __construct($name) {
		$this->username = $name;
        $this->set_friends();
        $this->set_posts();
	}
    
    public function set_posts() {
		include 'connection.php';
		
		$this->posts = array();
		$sql = "select postId from Group8_userposts where username = '".$this->get_username()."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->posts[$row["postId"]] = new Post($row["postId"]);
		};
        
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
        
        $sql = "insert into Group8_posts(title, text, data)values('".$title."', '".$text."', '".$data."');";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
        
        $sql = "select max(postId) from Group8_posts";
		$res = $conn->query($sql);
		$postId = "";
		while($row = $res->fetch_assoc()){
			$postId = $row['max(postId)'];
		}
        
        $sql = "insert into Group8_userposts(username, postId)values('".$this->get_username()."', '".$postId."');";
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
		
		$sql = "delete from Group8_userposts where postId = '".$this->postId."';";
		if (!$conn->query($sql)){
			die("Invalid query: ".$conn->error);
		}
        
		include 'connection_close.php';
	}
}
?>