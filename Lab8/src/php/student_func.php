<?php
session_start();
include 'student.php';

$student = new Student($_SESSION['username']);

$op = $_GET['op'];
if ($op == "return"){
	$student->return_book($_GET['copyId']);
}
else if ($op == "borrow") {
	$lib = new Library();
	$copyId = $lib->get_copyID_from_title($_GET['title']);
	if ( is_null($copyId) ){
		exit("Found None");
	}
	$lib->borrowBook($_SESSION['username'], $copyId);
	echo "The book is borrowed";
}
else if ($op == "shelves"){
	$lib = new Library();
	$html = $lib->get_shelves_tableview('student');
	echo $html;
}
else if ($op == "details"){
	
}
?>