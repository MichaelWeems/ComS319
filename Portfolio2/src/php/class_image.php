<?php

class Image {

	private $imageId;
	private $title;
    private $path;
    

	function __construct($id) {
        $this->imageId = $id;
        $this->title = "";
        $this->path = "";
        
        $this->setvars_fromDB();
	}
    
    public function setvars_fromDB(){
        include 'connection.php';
		
        $sql = "select title, path from Group8_images where imageId = '".$this->imageId."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->set_title($row["title"]);
			$this->set_path($row["path"]);
        }

        include 'connection_close.php';
    }
    
	public function set_title($btitle) {
		$this->title = $btitle;
	}	
	
	public function set_path($path) {
		$this->path = $path;
	}
	
    

	public function get_imageId() {
		return $this->imageId;
	}  
	
	public function get_title() {
		return $this->title;
	}

	public function get_path() {
		return $this->path;
	}
	
    
}
?>