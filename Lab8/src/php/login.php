<?php
session_start();
include 'connection.php';
$_SESSION["username"] = $_GET["username"];

$loginSQL = "select password, librarian from Group8_users where username = '"
    .$_GET["username"]."';";

$res = $conn->query($loginSQL);
$ret = "";
$pass = md5($_GET["password"]);

while($row = $res->fetch_assoc()){
    if($row["password"] == $pass && $row["librarian"] == $_GET["librarian"]){
        if($_GET["librarian"] == "true"){
            $ret .= 'librarian';
        }else{
            $ret .= 'student';
        }
    }else{
        $ret .= "invalid";
    }
}

echo json_encode($ret);
include 'close_connection.php';
?>