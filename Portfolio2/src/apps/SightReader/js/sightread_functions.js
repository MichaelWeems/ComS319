////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//// Functions

/////////////////////////////////////////////////////////////////////////////////
//  LoopStaff
//
//		Begins a calling loop to the server to get a randomized 
//		staff to display in the designated canvas
//
function loopStaff( difficulty, beat, beatcount ) {
	
	var queryString = { 'beat' : beat, 'beatcount' : beatcount, 'difficulty' : difficulty}; 
    
    var script = 'src/apps/SightReader/php/buildStaff.php';
    var func = phpcall_buildStaff_callback;
    ajax(queryString,script,func);
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
            type: 'GET', 
            url: 'src/apps/SightReader/php/buildStaff.php', 
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
    //$('.canvas-area').html(data);
	var obj = jQuery.parseJSON(data); 
	
	drawStaff(obj);
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
    var context = canvas.getContext('2d');
    context.clearRect(0, 0, canvas.width, canvas.height);
	
    var renderer = new Vex.Flow.Renderer(canvas,
	Vex.Flow.Renderer.Backends.CANVAS);

	var ctx = renderer.getContext();
	var treble = new Vex.Flow.Stave(10, 50, 500);
	var bass = new Vex.Flow.Stave(10, 200, 500, 'bass');

	// Add clef designators
	treble.addClef("treble");
	bass.addClef("bass");
    
    // wipe the canvas clean
    
    
	//console.log("time signature: " + staff['timesignature']);
	treble.addTimeSignature(staff['timesignature']);
	bass.addTimeSignature(staff['timesignature']);
	
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