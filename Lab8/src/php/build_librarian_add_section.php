<?php
session_start();
include 'library.php';

function build_div($arr, &$build){
	foreach ($arr as $book){
		
		$build .= '<div class="existingBook-add">';
		$build .= '<a>'.$book->get_title().' - </a>&nbsp';
		$build .= '<a>'.$book->get_author().' - </a>&nbsp';
		$build .= '<a>'.$book->get_id().'</a><br>';
		//echo "\nBook: ".$book->get_title();
		//echo " / Author: ".$book->get_author();
		$id = $book->get_id();
		
		$build .= '<a class="loan-return-button" onclick="addbook_existing('.$id.')">Add new Copy</a>';
		$build .= '</div><br><br>';
	}
	return $build;
}

$lib = new Library();
$books = $lib->get_bookList();

$html = '<h3>Add a copy of an existing book</h3>';
build_div($books, $html);

echo $html;
?>