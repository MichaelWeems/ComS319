<?php
include 'connection.php';
session_start();

$json['username'] = $_SESSION["username"];
$sql = "select picPath from Group8_users where username = '".$_SESSION["username"]."';";
$res = $conn->query($sql);
while($row = $res->fetch_assoc()){
    $json['picPath'] = $row["picPath"];
}
echo json_encode($json);
	
include 'connection_close.php';
?>
