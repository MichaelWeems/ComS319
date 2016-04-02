<?php
include 'connection.php';

$ret = array();

if ($_GET['op'] == 'question'){
    $sql = "select securityquestion from Group8_users where username = '"
    .$_GET["username"]."';";
    
    $res = $conn->query($sql);
    $ret['question'] = 'invalid';
    while($row = $res->fetch_assoc()){
        if( !is_null($row["securityquestion"]) ){
            $ret['question'] = $row['securityquestion'];
            $ret['username'] = $_GET['username'];
        }
    }
}
else if ($_GET['op'] == 'answer'){
    $sql = "select securityanswer, admin from Group8_users where username = '"
    .$_GET["username"]."';";
    
    $res = $conn->query($sql);
    $ret['user'] = 'invalid';
    while($row = $res->fetch_assoc()){
        if( isset($row["securityanswer"]) ){
            if ( $row["securityanswer"] == $_GET["answer"] ){
                if ($row['admin']  == "true" ) {
                    $ret['user'] = 'admin';
                }
                else {
                    $ret['user'] = 'user';
                }
            }
        }
    }
}

echo json_encode($ret);
include 'connection_close.php';
?>