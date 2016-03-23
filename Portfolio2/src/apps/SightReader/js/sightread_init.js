////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Handlers and Initialization

$(document).ready(function() {

    $(".app").load("src/pages/sightreader_app.html",
    function() {
        $(".mat-input").focus(function() {
            $(this).parent().addClass("is-active is-completed");
        });

        $(".mat-input").focusout(function() {
          if ($(this).val() === "")
            $(this).parent().removeClass("is-completed");
          $(this).parent().removeClass("is-active");
        });

        $('select').material_select();

        $("#start").click(function() {
            startLoopStaff('C', '4', '4', '0', '1');
        });
        
        
    }); 
}); // end of document ready function