////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers and Initialization

$(document).ready(function() {
    
    $('#logo-container').text('Sight Reading Trainer');

    $('select').material_select();
    
    var timer = null;

    $("#start").click(function() {
        startloop();
    });
    
    $("#end").click(function() {
        endloop();
    });
    
    
    function startloop() {
        var beatcount = parseInt($("#beatcount select option:selected").text());
        var tempo = parseInt($('input[type=range]').val());
        var time = (beatcount * (60 / tempo)) * 1000;
        console.log('Time: ' + time);
        
        if (timer != null){
            clearInterval(timer);
        }
        loop();
        timer= setInterval(loop, time);
    }
    
    function endloop() {
        clearInterval(timer);
        var canvas = $("#sightread-canvas")[0];
        var context = canvas.getContext('2d');
        context.clearRect(0, 0, canvas.width, canvas.height);
    }
    
    function loop(){
        var difficulty = $('form input[type=radio]:checked').attr('id');
        var beat = $("#beat select option:selected").text();
        var beatcount = $("#beatcount select option:selected").text();
        loopStaff(difficulty, beat, beatcount);
    }
        
}); // end of document ready function