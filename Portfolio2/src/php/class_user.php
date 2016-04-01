<?php
include 'class_friend.php';

class User {

	private $username;
    private $friends;
    private $posts;
    private $pic;

	function __construct($name) {
		$this->username = $name;
        $this->set_friends();
        $this->set_posts();
        $this->set_pic();
	}
    
    public function set_posts() {
		include 'connection.php';
		
		$this->posts = array();
		$sql = "select postId from Group8_posts where username = '".$this->get_username()."' order by postId desc;";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->posts[$row["postId"]] = new Post($row["postId"]);
		};
        
		include 'connection_close.php';
	}
    
    public function set_pic(){
        include 'connection.php';
		
		$sql = "select picPath from Group8_users where username = '".$this->get_username()."';";
		$res = $conn->query($sql);
        while($row = $res->fetch_assoc()){
			$this->pic = $row["picPath"];
		};
        
		include 'connection_close.php';
    }
    
    
    public function get_pic(){
        return $this->pic;
    }
    public function get_posts(){
        return $this->posts;
    }
    
    
    // returns an array of posts from the user and their friends
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
            $sql = "select postId from Group8_posts where username = '".$this->get_username()."' OR username = '".$friend."' order by postId desc;";
            $res = $conn->query($sql);
            while($row = $res->fetch_assoc()){
                $posts[$row["postId"]] = new Post($row["postId"]);
            };
        }
        
        return $posts;
		include 'connection_close.php';
	}
    
    public function set_friends() {
        include 'connection.php';
		$sql = "select friend from Group8_friends where username = '".$this->username."';";
		$res = $conn->query($sql);
		$arr = array();
		while($row = $res->fetch_assoc()){
            array_push($arr, new Friend($row["friend"]) );
		};
        $this->friends = $arr;
        include 'connection_close.php';
	}

    public function get_friends() {
        return $this->friends;
	}

	public function get_username() {
		return $this->username;
	}
    
    public function getFriend_pics(){
        include 'connection.php';
		$sql = "select picPath from Group8_users where username in (select username from Group8_friends where friend = '".$this->username."');";
		$res = $conn->query($sql);
		$arr = array();
		while($row = $res->fetch_assoc()){
            array_push($arr, $row["picPath"]);
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
    
    public function createPost($title, $text) {
		include 'connection.php';
        
        $sql = "insert into Group8_posts(title, text, username)values('".$title."', '".$text."', '".$this->get_username()."');";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
        
        $sql = "select max(postId) from Group8_posts where username = '".$this->username."' and title = '".$title."';";
		$res = $conn->query($sql);
        $postId = '';
		while($row = $res->fetch_assoc()){
            $postId = $row['max(postId)'];
            $this->posts[$postId] = new Post($postId);
            
		};
        include 'connection_close.php';
        return $this->posts[$postId];
        
		
	}
	
	public function deletePost(){
		include 'connection.php';
        
		$sql = "delete from Group8_posts where postId = '".$this->postId."';";
		if (!$conn->query($sql)){
			die("Invalid query: ".$conn->error);
		}
		
		include 'connection_close.php';
	}
    
    public function get_search_users($str){
        $sql = "select username from Group8_users where username like '".$str."%';";
        $arr = array();
        include 'connection.php';
        
        $res = $conn->query($sql);
        while($row = $res->fetch_assoc()){
            array_push($arr,new Friend($row["username"]) );
        }
        
        include 'connection_close.php';
        return $arr;
    }
}
?>