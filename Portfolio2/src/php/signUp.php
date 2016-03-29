<?php

include 'connection.php';

session_start();
$_SESSION["username"] = $_GET["username"];

$loginSQL = "select username from Group8_users where username = '"
    .$_GET["username"]."';";

$res = $conn->query($loginSQL);
while($row = $res->fetch_assoc()){
	if ($row['username'] == $_GET["username"]){
		$err = json_encode("username taken");
		exit($err);
	}
}

$pass = md5($_GET["password"]);
$loginSQL = "insert into Group8_users(username, password, admin)"
    ."values('".$_GET["username"]."', '".$pass."', '".$_GET["admin"]."');";

$conn->query($loginSQL);

$loginSQL = "select admin from Group8_users where username = '"
    .$_GET["username"]."';";

$res = $conn->query($loginSQL);
	
$ret = array("user" => "invalid");
while($row = $res->fetch_assoc()){
	if($_GET["admin"] == "true"){
		$ret["user"] = 'admin';
	}else{
		$ret["user"] = 'user';
	}
}
echo json_encode($ret);
	
include 'connection_close.php';

?>
