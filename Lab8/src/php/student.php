<?php
include 'library.php';

class Student {

	private $username;

	function __construct($name) {
		$this->username = $name;
	}

	public function get_username() {
		return $this->username;
	}

	// returns an array of the books loaned out to this student
	public function loanHistory() {
		$sql = "select username, copyId, duedate, returned from Group8_loanHistory where username = '".$this->username."';";
		$res = $con->query($sql);
		$arr = array();
		while($row = $res->fetch_assoc()){
			$usr_row = array("username" => $row["username"], "copyId" => $row["copyId"], "duedate" => $row["duedate"], "returned" => $row["returned"]);
			array_push($arr, $usr_row);
		};
		return $arr;
	}
	
	public function loanHistory_detailed(){
		include 'connection.php';

		$sql = "select copyId, returned, duedate from Group8_loanhistory where username = '"
			.$this->get_username()."';";
		
		$response_loans = $conn->query($sql);
		$loans = array();
		$copyIds_query = array();
		while($row = $response_loans->fetch_assoc()){
			$query_bookcopy = "select bookId from Group8_bookcopy where copyId = '"
				.$row['copyId']."';";
			array_push($copyIds_query, array($query_bookcopy, array('copyId' => $row['copyId'], 'returned' => $row['returned'], 'duedate' => $row['duedate'])));
		}
		
		//var_dump($copyIds_query);
		$bookIds_query = array();		
		foreach ($copyIds_query as $query){
			$response_bookcopy = $conn->query($query[0]);
			while($bookcopy = $response_bookcopy->fetch_assoc()){
				$query_books = "select bookId, bookTitle, author from Group8_books where bookId = '"
					.$bookcopy['bookId']."';";
					array_push($bookIds_query, array($query_books,$query[1]));
			}
		}
		//var_dump($bookIds_query);
		foreach ($bookIds_query as $query){
			$response_books = $conn->query($query[0]);
			while($book = $response_books->fetch_assoc()){
				array_push($loans, array('copyId' => $query[1]['copyId'], 'returned' => $query[1]['returned'], 'duedate' => $query[1]['duedate'],
					'bookId' => $book['bookId'], 'title' => $book['bookTitle'], 'author' => $book['author']));
			}
		}
		
		include 'close_connection.php';
		
		return $loans;
	}
	
	public function return_book($copyId){
		include 'connection.php';
		$lib = new Library();
		$lib->addCopy($copyId);
		
		date_default_timezone_set('America/Chicago');
		$date = date('Y-m-d');
		$sql = "UPDATE Group8_loanHistory SET returned='".$date."' WHERE copyId = '".$copyId."' and username = '".$this->username."'";
		//$sql = "delete from Group8_loanHistory where copyId = '".$copyId."' and username = '".$this->username."' and returned is null";
		if ($conn->query($sql) === false){
			die('Invalid query: '.$conn->error);
		}
		include 'close_connection.php';
	}
}
?>