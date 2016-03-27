<?php

$difficulty = $_GET['difficulty'];

$notes = array('C','D','E','F','G','A','B');
$accidentals = array('x', '#', 'b', 'n');

// begin building the staff
$responseStaff = array();
$treble = array();
$bass = array();
$duration = $_GET['beat'];

if ($difficulty == 'hard'){
    $duration = (string)(intval($duration)*2);
}

for ($i = 0; $i < intval($_GET['beatcount']); $i++) {

	$tgroup = array();
	$bgroup = array();
    
    if ($difficulty == 'easy'){
        
        shuffle($notes);
        $tnote = current($notes)."/5";
        
        shuffle($notes);
        $bnote = current($notes)."/3";

        array_push($tgroup, $tnote);
        array_push($bgroup, $bnote);
        
        // push the notes into the treble and bass clefs
        array_push($treble, $tgroup);
        array_push($bass, $bgroup);
        
    }
    else if ($difficulty == 'medium'){
        
        $count = rand(1,3);
        
        for ($j = 0; $j < $count; $j++){
            shuffle($notes);
            $tnote = current($notes)."/5";

            shuffle($notes);
            $bnote = current($notes)."/3";

            array_push($tgroup, $tnote);
            array_push($bgroup, $bnote);
        }
        // push the notes into the treble and bass clefs
        array_push($treble, $tgroup);
        array_push($bass, $bgroup);
    }
    else if ($difficulty == 'hard'){
        $count = rand(1,3);
        
        $oct = rand(1,2);
        $high = "/5";
        $low = "/3";
        if ($oct == 1){
            $high = "/4";
            $low = "/3";
        }
        
        for ($j = 0; $j<2; $j++){
            $tgroup = array();
	        $bgroup = array();
            
            for ($k = 0; $k < $count; $k++){
                shuffle($notes);
                $tnote = current($notes).$high;
                
                shuffle($notes);
                $bnote = current($notes).$low;

                array_push($tgroup, $tnote);
                array_push($bgroup, $bnote);
            }
            
            // push the notes into the treble and bass clefs
            array_push($treble, $tgroup);
            array_push($bass, $bgroup);
        }
    }
	
	
	
}

$responseStaff['treble'] = $treble;
$responseStaff['bass'] = $bass;
$responseStaff['timesignature'] = $_GET['beatcount']."/".$_GET['beat'];
$responseStaff['duration'] = $duration;

// Encode the staff into json
$json = json_encode($responseStaff);
echo $json;

?>
