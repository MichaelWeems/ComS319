<?php
session_start();
include 'library.php';

$op = $_GET['op'];
$lib = new Library();

if ($op == "add"){
	$lib->addBook($_GET['title'], $_GET['author']);
}
else if ($op == "add_existing"){
	$lib->addBook_existing($_GET['id']);
}
else if ($op == "delete") {
	$book = new Book($_GET['copyId'], 'copy');
	$book->delete();
}
else if ($op == "shelves") {
	$lib = new Library();
	$html = $lib->get_shelves_tableview('librarian');
	echo $html;
}
?>