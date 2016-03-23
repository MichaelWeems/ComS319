////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions


/////////////////////////////////////////////////////////////////////////////////
//	startLoopStaff
//
//		Begins a calling loop to the server to get a randomized 
//		staff to display in the designated canvas
//
function startLoopStaff( key, beat, beatcount, iteration, maxIteration ) {
	
	var queryString = { 'key' : key, 'beat' : beat, 'beatcount' : beatcount, 'iteration' : iteration,
		'maxIteration' : maxIteration}; 

    phpcall_buildStaff(queryString);
	
}

/////////////////////////////////////////////////////////////////////////////////
//	loopStaff
//
//		continues the calling loop to the server to get a 
//		randomized staff to display in the designated canvas
//
function loopStaff(iteration) {

	var queryString = { 'iteration' : iteration}; 

    phpcall_buildStaff(queryString);

}

/////////////////////////////////////////////////////////////////////////////////
//	phpcall_buildStaff
//
//		Using AJAX, call the php script 'buildStaff.php' to build a staff.
//		Receives the staff and sends it to the designated callback for
//		processing 
//
function phpcall_buildStaff(queryString) {
	$.ajax( 
        { 
            type: 'POST', 
            url: 'src/php/buildStaff.php', 
            data: queryString, 
            success: function(data){ 
                phpcall_buildStaff_callback(data);
            } 
        } 
    ); 	
}

/////////////////////////////////////////////////////////////////////////////////
//	phpcall_buildStaff_callback
//
//		Processes the response from the 'buildStaff.php' script. 
//		Will do these things:
//			- parse the data into a javascript Staff object.
//			- check if the loop is complete.
//			- draw the staff.
//			- call to get another staff from the server.
//
function phpcall_buildStaff_callback(data) {
	// put result data into obj
	var obj = jQuery.parseJSON(data); 
	
	// Create a staff object from the contents of obj
	//var staves = parseStaves(obj);
	
	// Check if the loop is done
//	if (staves.checkLoopComplete() /* && user defined quit request */ ) {
//		// Clean up the canvas
//		// ....
//		return;
//	}
	
	// Draw the staff
	drawStaff(obj);
	
	// call the function again, this time with the timestamp we just got from server.php 
	//loopStaff(staves.iteration); 
}

/////////////////////////////////////////////////////////////////////////////////
//	drawStaff
//
//		takes the data present in the staff object argument and
//		displays it on the designated canvas
//
//		**	Utilizes the Vexflow library to accomplish this **
//
function drawStaff(staff) {

	var canvas = $("#sightread-canvas")[0];
	var renderer = new Vex.Flow.Renderer(canvas,
	Vex.Flow.Renderer.Backends.CANVAS);

	var ctx = renderer.getContext();
	var treble = new Vex.Flow.Stave(10, 0, 500);
	var bass = new Vex.Flow.Stave(10, 150, 500, 'bass');

	// Add a treble clef
	treble.addClef("treble");
	bass.addClef("bass");
	
	console.log("time signature: " + staff['timesignature']);
	treble.addTimeSignature(staff['timesignature']);
	bass.addTimeSignature(staff['timesignature']);
	
	console.log("key signature: " + staff['keysignature']);
	treble.addKeySignature(staff['keysignature']);
	bass.addKeySignature(staff['keysignature']);
	
	treble.setContext(ctx).draw();
	bass.setContext(ctx).draw();

	var treble_notes = [];
	for (var i = 0; i < staff['treble'].length; i++){
		treble_notes[i] = new Vex.Flow.StaveNote({
			keys: staff['treble'][i], duration: staff['duration'],
			clef: "treble"});
	}
	
	var bass_notes = [];
	for (var i = 0; i < staff['bass'].length; i++){
		bass_notes[i] = new Vex.Flow.StaveNote({
			keys: staff['bass'][i], duration: staff['duration'],
			clef: "bass"});
	}
	
	// Helper function to justify and draw a 4/4 voice
	Vex.Flow.Formatter.FormatAndDraw(ctx, treble, treble_notes);
	Vex.Flow.Formatter.FormatAndDraw(ctx, bass, bass_notes);

}
	
/////////////////////////////////////////////////////////////////////////////////
//	parseStaves
//
//		Parses the data received from the 'buildStaff.php' script into
//		a javascript Staves object (contains 2 Staff objects).
//	
function parseStaves(obj) {

	var staff = JSON.parse(obj);
	
	return staff;
}

/*	
/////////////////////////////////////////////////////////////////////////////////
//	checkLoopComplete
//
//		Checks if the user has requested to quit or if the maximum
//		iteration limit has been reached
//	
function checkLoopComplete(iteration, maxIteration) {
	if ( iteration >= maxIteration ) {
		return true;
	}
	//else if (user input to quit loop) {
	// do something
	//}
	else {
		return false;
	}
}
*/