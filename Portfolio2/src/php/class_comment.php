<?php
class Comment {

	private $commentId;
    private $postId;
	private $text;
	private $username;

	function __construct($commentId) {
        $this->commentId = $commentId;
        $this->postId = "";
        $this->text = "";
        $this->username = "";
        
        $this->setvars_fromDB();
	}
    
    public function setvars_fromDB(){
        include 'connection.php';
		
        $sql = "select postId, text, username from Group8_comments where commentId = '".$this->commentId."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->set_postId($row["postId"]);
			$this->set_text($row["text"]);
            $this->set_username($row["username"]);
        }
        
        include 'connection_close.php';
    }
    
	public function set_postId($postId) {
		$this->postId = $postId;
	}	
	
	public function set_text($text) {
		$this->text = $text;
	}
	
	public function set_username($username) {
		$this->username = $username;
	}
    

	public function get_commentId() {
		return $this->commentId;
	}  
	
	public function get_postId() {
		return $this->postId;
	}

	public function get_text() {
		return $this->text;
	}  

	public function get_username() {
		return $this->username;
	}

    
	
}
?>