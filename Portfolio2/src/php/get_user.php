<?php
include 'connection.php';
session_start();

$json['username'] = $_SESSION["username"];
echo json_encode($json);
	
include 'connection_close.php';
?>