////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions


/////////////////////////////////////////////////////////////////////////////////
//	startLoopStaff
//
//		Begins a calling loop to the server to get a randomized 
//		staff to display in the designated canvas
//
function startLoopStaff( clef, key, bpm, notes, iteration, maxIteration ) {
	
	var queryString = {'clef' : clef, 'key' : key, 'bpm' : bpm, 'notes' : notes, 
		'iteration' : iteration, 'maxIteration' : maxIteration}; 

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
            url: '../php/buildStaff.php', 
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
	var staff = parseStaff(obj);
	
	// Check if the loop is done
	if (checkLoopComplete(staff.currIteration)) {
		// Clean up the canvas
		// ....
		return;
	}
	
	// Draw the staff
	drawStaff(staff);
	
	// call the function again, this time with the timestamp we just got from server.php 
	loopStaff(staff.iteration); 
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

	var canvas = $("canvas")[0];
	var renderer = new Vex.Flow.Renderer(canvas,
	Vex.Flow.Renderer.Backends.CANVAS);

	var ctx = renderer.getContext();
	var stave = new Vex.Flow.Stave(10, 0, 500);

	// Add a treble clef
	stave.addClef("treble");
	stave.setContext(ctx).draw();

	var notes = [
	new Vex.Flow.StaveNote(
	{ keys: ["g/4", "b/4", "cb/5", "e/5", "g#/5", "b/5"],
		duration: "h" }).
		addAccidental(0, new Vex.Flow.Accidental("bb")).
		addAccidental(1, new Vex.Flow.Accidental("b")).
		addAccidental(2, new Vex.Flow.Accidental("#")).
		addAccidental(3, new Vex.Flow.Accidental("n")).
		addAccidental(4, new Vex.Flow.Accidental("b")).
		addAccidental(5, new Vex.Flow.Accidental("##")),
		new Vex.Flow.StaveNote({ keys: ["c/4"], duration: "h" })
	];

	// Helper function to justify and draw a 4/4 voice
	Vex.Flow.Formatter.FormatAndDraw(ctx, stave, notes);

}
	
/////////////////////////////////////////////////////////////////////////////////
//	parseStaff
//
//		Parses the data received from the 'buildStaff.php' script into
//		a javascript Staff object.
//	
function parseStaff(obj) {

	var staff = new Staff();
	
	// set all variables for staff object
	
	return staff;
}
	
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