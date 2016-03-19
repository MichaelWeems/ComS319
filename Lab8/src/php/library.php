<?php
include 'shelf.php';

class Library {

	private $shelves;
	private $maxShelves;
	private $bookList;

	function __construct() {
		include 'shelf_constants.php';
		
		$this->maxShelves = $shelfCount;
		$this->set_shelves();
		$this->set_bookList();
	}
	
	public function set_shelves() {
		include 'connection.php';
		
		$this->shelves = array();
		$sql = "select distinct shelfId from Group8_shelves";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->shelves[$row["shelfId"]] = new Shelf($row["shelfId"]);
		};
		
		include 'close_connection.php';
	}
	
	public function set_bookList() {
		include 'connection.php';
		
		$this->bookList = array();
		$sql = "select bookId from Group8_books";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			$this->bookList[$row["bookId"]] = new Book($row["bookId"], "book");
		};
		
		include 'close_connection.php';
	}
	
	public function get_shelves_tableview($user){
	
		$html  = '<table id="shelves-display">';
		$html .= '<tr id="shelf-ids">';
		for ($i=1; $i <= count($this->shelves); $i++){
			$html .= '<th>Shelf '.$i.'</th>';
		}
		$html .= '</tr>';
		
		
		for ($i=0; $i < $this->shelves[1]->get_capacity(); $i++){
			$html .= '<tr class="shelf-row">';
			for ($j=1; $j <= count($this->shelves); $j++){
				$html .= '<td><table class="table-book">';
				if(!isset($this->shelves[$j]->get_copies()[$i])){
					$html .= '<td></td><td></td>';
				}
				else {
					$id = $this->shelves[$j]->get_copies()[$i]->get_copyId();
					$html .= '<tr><th class="book-cell">'.$this->shelves[$j]->get_copies()[$i]->get_title().'</th>';
					$html .= '<td class="details-cell"><a class="book-details-button" onclick="get_details('.$id.')">Get Details</a></td>';
					if ($user == 'librarian'){
						$html .= '<td class="delete-cell"><a class="book-delete-button" onclick="delete_book('.$id.')">delete</a></td>';
					}
				}
				$html .= '</tr></table></td>';
			}
			$html .= '</tr>';
		}
		$html .= '</table>';
		echo $html;
	}
	
	public function get_bookList() {
		return $this->bookList;
	}
	
	public function get_copyID_from_title($title) {
		include 'connection.php';
		
		$sql = "select min(copyId) from Group8_shelves where copyId in(select copyId from Group8_bookcopy where bookId in(select bookId from Group8_books where bookTitle = '".$title."'))";
		$res = $conn->query($sql);
		while($row = $res->fetch_assoc()){
			return $row["min(copyId)"];
		}
		
		include 'close_connection.php';
	}
  
	public function borrowBook($name, $copyId){
		include 'connection.php';
		
		$sql  = "delete from Group8_shelves where copyId = '".$copyId."'; ";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
		date_default_timezone_set('America/Chicago');
		$date = date('Y-m-d');
		$date = date_create($date);
		date_add($date,date_interval_create_from_date_string("7 days")); // give the student one week in borrow time
		$sql = "insert into Group8_loanhistory(username, copyId, duedate)values('".$name."', '".$copyId."', '".date_format($date,"Y-m-d")."');";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
		include 'close_connection.php';
	}
	
	public function delBook($copyId, $shelfId){
		include 'connection.php';
		$sql  = "delete from Group8_bookcopy where copyId = '".$copyId."';";
		$sql .= "delete from Group8_shelves where copyId = '".$copyId."';";
		$res = $conn->query($sql);
		include 'close_connection.php';
	}
	
	public function delBook_allcopies($bookId){
		include 'connection.php';
		$sql  = "delete from Group8_bookcopy where bookId = '".$bookId."';";
		$sql .= "delete from Group8_shelves where bookId = '".$bookId."';";
		/*
			Also need to delete from loanhistory
		
		*/
		
		$res = $conn->query($sql);
		include 'close_connection.php';
	}
	
	public function addCopy($copyId){
		foreach ($this->shelves as $shelf){
			if ($shelf->addCopy($copyId) == "success"){
				break;
			}
		}
	}
	
	public function addBook_existing($id){
		include 'connection.php';
		// add a copy to the copies table
		//echo "\n\n\nInserting into bookcopy: ".$id."\n\n\n\n";
		$sql = "insert into Group8_bookcopy(bookId)values(".$id.")";
		if (!$conn->query($sql)){
			die('Invalid Query: '.$conn->error);
		}
		// copy to a shelf
		$sql = "select max(copyId) from Group8_bookcopy";
		$res = $conn->query($sql);
		$copyid = "";
		while($row = $res->fetch_assoc()){
			$copyid = $row['max(copyId)'];
		}
		
		include 'close_connection.php';
		$this->addCopy($copyid);
	}
	
	public function addBook($title, $author){
		include 'connection.php';
		$sql = "select bookTitle, bookId from Group8_books where bookTitle = '".$title."';";
		$res = $conn->query($sql);
		$copy = false;
		$id = "";
		while($row = $res->fetch_assoc()){
			if ($row['bookTitle'] == $title) {
				$copy = true;
				$id = $row['bookId'];
			}
		};
		
		// new book
		if (!$copy) {
			// add to the book in the books table
			$sql = "insert into Group8_books(bookTitle, author)values('".$title."', '".$author."')";
			$res = $conn->query($sql);
			
			$sql = "select bookId from Group8_books where bookTitle = '".$title."';";
			$res = $conn->query($sql);
			$row = $res->fetch_assoc();
			$id = $row["bookId"];
			
		} 
		include 'close_connection.php';
		addBook_existing($id);
	}
	
}

?>