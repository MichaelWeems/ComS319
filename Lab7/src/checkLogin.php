<?php
$ret = false;
$file = file_get_contents('users.txt');

$userpass= explode(",", $file);
for ($j = 0; $j < count($userpass) - 1; $j++ ) {
	
	if (confirmLogin($userpass[$j], $userpass[$j+1], $_GET["username"], $_GET["password"]) ) {
		$ret = true;
		break;
	}
	$j++;
}

fclose($file);

if ( $ret ) {
	if ( isset($_GET['username']) ) {
		$username = $_GET['username'];
		$_SESSION['username'] = $username;
	}
}

echo json_encode($ret);

function confirmLogin($fuser, $fpass, $user, $pass) {

	if ($fuser == $user && $fpass == $pass) {
		return true;
	}
	return false;
}

?>
