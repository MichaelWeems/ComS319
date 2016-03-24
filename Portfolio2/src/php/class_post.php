<?php
class Post {

	private $postId;
	private $title;
	private $text;
	private $data;
    private $image;
    private $comments;

	function __construct($id) {
        $this->postId = $id;
        $this->title = "";
        $this->text = "";
        $this->data = "";
        $this->image = "";
        
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
    
    

	public function get_id() {
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
	
    
    
    
	
}
?>