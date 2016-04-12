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
$loginSQL = "insert into Group8_users(username, password, email, phone, librarian, firstname, lastname)"
    ."values('".$_GET["username"]."', '".$pass."', '".$_GET["email"]."', '".$_GET["phone"]."', '"
	.$_GET["librarian"]."', '".$_GET["firstname"]."', '".$_GET["lastname"]."');";

$conn->query($loginSQL);

$loginSQL = "select librarian from Group8_users where username = '"
    .$_GET["username"]."';";

$res = $conn->query($loginSQL);
	
$ret = "";
while($row = $res->fetch_assoc()){
	if($_GET["librarian"] == "true"){
		$ret .= 'librarian';
	}else{
		$ret .= 'student';
	}
}
echo json_encode($ret);
	
include 'close_connection.php';

?>
