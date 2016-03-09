<?php
sleep(1);
$ret = false;
$file = fopen("users.txt","r");

while(!feof($file)){
  if ($temp = fgets($file)) {
	//var_dump($temp);
	if (confirmLogin($temp, $_GET["username"], $_GET["password"])) {
		$ret = true;
		break;
	}
  }
}

fclose($file);

echo json_encode($ret);

function confirmLogin($line, $user, $pass) {

	$retr = false;
	$vars = explode(",", $line);
	
	if ($vars[0] == $user) {
		if ($vars[1] == $pass) {
			$retr = true;
		}
	}
	return $retr;
}

?>
