<?php
session_start();
include 'connection.php';
$_SESSION["username"] = $_GET["username"];

$loginSQL = "select password, admin from Group8_users where username = '"
    .$_GET["username"]."';";

$res = $conn->query($loginSQL);
$pass = md5($_GET["password"]);

$ret = array('user' => 'invalid');
while($row = $res->fetch_assoc()){
    if($row["password"] == $pass){
        
        if($row["admin"] == "true"){
            $ret['user'] = 'admin';
        }else{
            $ret['user'] = 'user';
        }
        
    }
}

echo json_encode($ret);
include 'close_connection.php';
?>