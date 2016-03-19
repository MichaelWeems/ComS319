<?php
class Book {

	private $bookId;
	private $copyId;
	private $title;
	private $author;

	function __construct($id, $type) {
	
		if ($type == "copy") {
			$this->copyId = $id;
			$this->set_copydetails();
		} else {
			$this->bookId = $id;
			$this->set_bookdetails();
		}
	}
	
	public function set_title($btitle) {
		$this->title = $btitle;
	}	
	
	public function set_author($bauthor) {
		$this->author = $bauthor;
	}
	
	public function set_id($id) {
		$this->bookId = $id;
	}

	public function get_id() {
		return $this->bookId;
	}  
	
	public function set_copyId($id) {
		$this->copyId = $id;
	}

	public function get_copyId() {
		return $this->copyId;
	}  

	public function get_title() {
		return $this->title;
	}  

	public function get_author() {
		return $this->author;
	}
	
	public function set_copydetails() {
		include 'connection.php';
		$sql = "select bookId from Group8_bookcopy where copyId = '".$this->copyId."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->set_id($row["bookId"]);
		}
		
		$sql = "select bookTitle, author from Group8_books where bookId = '".$this->bookId."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->set_title($row["bookTitle"]);
			$this->set_author($row["author"]);
		}
		
		include 'close_connection.php';
	}
	
	public function set_bookdetails() {
		include 'connection.php';
		$sql = "select bookTitle, author from Group8_books where bookId = '".$this->bookId."';";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->set_title($row["bookTitle"]);
			$this->set_author($row["author"]);
		}
		include 'close_connection.php';
	}
  
	//this is for receiving the book details
	public function get_bookDetails_array(){
		$arr = array("copyId" => $this->copyId, "bookId" => $this->bookId,"title" => $this->title,"author" => $this->author);
		return $arr;
	}
	
	public function delete(){
		include 'connection.php';
		$sql = "delete from Group8_shelves where copyId = '".$this->copyId."';";
		if (!$conn->query($sql)){
			die("Invalid query: ".$conn->error);
		}
		
		$sql = "delete from Group8_bookcopy where copyId = '".$this->copyId."';";
		if (!$conn->query($sql)){
			die("Invalid query: ".$conn->error);
		}
		include 'close_connection.php';
	}

}

?>