<?php
session_start();
include 'student.php';

function build_div($arr, &$build, $lib){
	foreach ($arr as $loan){
		$build .= '<div class="loan-return">';
		$build .= '<a>'.$loan['title'].'</a>&nbsp';
		$build .= '<a>'.$loan['author'].'</a><br>';
		$build .= '<a>Book ID: '.$loan['bookId'].'</a>&nbsp';
		$build .= '<a>Copy ID: '.$loan['copyId'].'</a><br>';
		$build .= '<a>Due Date: '.$loan['duedate'].'</a>&nbsp';
		if ( is_null($loan['returned']) ){
			if ($lib){
				$build .= '<a>Not yet returned</a>';
			}
			else {
				$build .= '<a class="loan-return-button" onclick="return_book('.$loan['copyId'].')">Return</a>';
			}
		} else {
			$build .= '<br><a>Returned on '.$loan['returned'].'</a>';
		}
		$build .= '</div><br><br>';
	}
	return $build;
}

$name = '';
$lib = false;
if ($_GET['user'] == "student"){
	$name = $_SESSION['username'];
} else {
	$name = $_GET['username'];
	$lib = true;
}


$student = new Student($name);
$loans = $student->loanHistory_detailed();

$to_return = array();
$returned = array();
foreach ($loans as $loan){
	if ( is_null($loan['returned']) ){
		array_push($to_return, $loan);
	} else {
		array_push($returned, $loan);
	}
}

$html = '';
if ($lib){
	$html  .= '<h3>'.$name.'\'s pending loans</h3>';
}
else {
	$html  .= '<h1>Return a Book</h1>';
}

$html  .= '<div style="overflow-y: scroll; height:87%">';
build_div($to_return, $html, $lib);

if ($lib){
	$html  .= '<h3>'.$name.'\'s past loans</h3>';
}
else {
	$html .= '<h2>Books you have returned</h2><br>';
}
build_div($returned, $html, $lib);
$html .= '</div>';

echo $html;
?>