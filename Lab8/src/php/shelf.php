<?php

include 'book.php';

class Shelf {

	private $shelfId;
	private $capacity;
	private $currentSize;
	private $copies;

	function __construct($id) {
		include 'shelf_constants.php';
		
		$this->shelfId = $id;
		$this->capacity = $shelfSize;
		$this->currentSize = 0;
		$this->copies = array();
		$this->set_details();
	}
	
	public function set_id($id) {
		$this->shelfId = $id;
	}	
	
	public function add_to_copies($copy) {
		array_push($this->copies, $copy);
	}
	
	public function get_shelfId() {
		return $this->shelfId;
	}  
	
	public function get_copies() {
		return $this->copies;
	}
	
	public function get_capacity() {
		return $this->capacity;
	}
	
	public function set_details() {
		include 'connection.php';
		
		$sql = "select copyId from Group8_shelves where shelfId = '".$this->shelfId."';";
		$res = $conn->query($sql);
		$row = $res->fetch_assoc();
		while($row = $res->fetch_assoc()){
			$this->add_to_copies( new Book($row["copyId"], "copy") );
			$this->currentSize += 1;
		}
		include 'close_connection.php';
	}
	
	public function addCopy($copyId) {
		if ($this->currentSize >= $this->capacity){
			return "shelf full";
		}
		include 'connection.php';
		$sql = "insert into Group8_shelves(shelfId, copyId)values(".$this->shelfId.", ".$copyId.");";
		$res = $conn->query($sql);
		$this->currentSize += 1;
		
		include 'close_connection.php';
		return "success";
	}
	
	public function deleteBook($copyId){
		if ($this->currentSize == 0){
			return "Shelf empty";
		}
		
		include 'close_connection.php';
		$sql = "delete from Group8_shelves where copyId = '".$copyId."';";
		$res = $conn->query($sql);
		$this->currentSize -= 1;
		include 'connection.php';
	}
}
?>