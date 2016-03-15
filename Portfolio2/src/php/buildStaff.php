<?php

////////////////////////////////////////////////////
//	This section will be from the sql database //
// Temp using php for usability in testing     //

// List all major keys here (may or may not add minor keys)
$C = array('C','D','E','F','G','A','B');
$C['key']='C';

// Place all the keys into an array
$keys = array($C);

$requestedKey = null;

// Check for the given key
foreach($keys as $key){
	if ($key['key'] == $_REQUEST['key'] ) {	// find the key we want
		$requestedKey = $key;
		break;
	}
}



// end section from the sql database //
///////////////////////////////////////////

// begin building the staff
$responseStaff = array();
$treble = array();
$bass = array();

for ($i = 0; $i < intval($_REQUEST['beatcount']); $i++) {

	// grab random elements from the key array and place them into the return array
	
	// account for difficulty with if statements?
	
	// create notes (only one for each clef for now)
	$tgroup = array();
	$bgroup = array();
	
	$tnote =  $requestedKey[$i]."/5";
	$bnote =  $requestedKey[$i]."/3";
	
	array_push($tgroup, $tnote);
	array_push($bgroup, $bnote);
	
	// push the notes into the treble and bass clefs
	array_push($treble, $tgroup);
	array_push($bass, $bgroup);
	
}

$responseStaff['treble'] = $treble;
$responseStaff['bass'] = $bass;
$responseStaff['keysignature'] = $requestedKey[0];
$responseStaff['timesignature'] = $_REQUEST['beatcount']."/".$_REQUEST['beat'];
$responseStaff['duration'] = $_REQUEST['beat'];
$responseStaff['iterations'] = $_REQUEST['iteration'];

// Encode the staff into json
$json = json_encode($responseStaff);
echo $json;

?>
