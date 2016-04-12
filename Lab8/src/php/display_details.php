<?php
session_start();
include 'book.php';

$copyId = $_GET['copyId'];
$book = new Book($copyId, 'copy');
$arr = $book->get_bookDetails_array();
$json = json_encode($arr);
echo $json;

?>