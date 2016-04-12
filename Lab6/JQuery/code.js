/**
 * 
 */
$(document).ready(function() {
	
	var str = "";
	var on = true;

	var Bands = {
		str: " is an album by ",
		info:[
		      {name:"The Strokes", work:"Is This It?"},
		      {name:"Portugal. The Man", work:"Waiter: You Voltures!"},
		      {name:"The Beatles", work:"Abbey Road"},
		      {name:"Led Zeppelin", work:"Led Zeppelin"},
		      {name:"Kendrik Lamar", work:"To Pimp a Butterfly"},
		      {name:"Arctic Monkeys", work:"What Ever People Say I Am, That's What I'm Not"}],
		 handler:function(j){
			 return (this.info[j].work + this.str + this.info[j].name);
		 },
		handler2:function(i){
			return this.info[i].work;
		}
	}
	
	var Directors = {
			str : " is a film by ",
			info:[
			      {name:"Steven SpielBerg", work:"Jurassic Park"},
			      {name:"Martin Scorsese", work:"Goodfellas"},
			      {name:"David Fincher", work:"Fight Club"},
			      {name:"Christopher Nolan", work:"The Prestige"},
			      {name:"Quentin Tarantino", work:"Reservoir Dogs"},
			      {name:"Spike Jonze", work:"Adaptation"}
			      ],
	}
	
	var main = Bands.handler2.bind(Bands);
	var madeBy = Bands.handler.bind(Bands);
	
	$('#reset').click(function(){
		location.reload();
	});
	
	$('#bands').click(function(){
		main = Bands.handler2.bind(Bands);
		madeBy = Bands.handler.bind(Bands);
		for(i = 0; i < 6; i++){
			$('.txt:eq('+i+')').text(Bands.info[i].name)
		}
		$('.txt').fadeIn("slow")
	}),
	
	$('#directors').click(function(){
		main = Bands.handler2.bind(Directors);
		madeBy = Bands.handler.bind(Directors);
		for(i = 0; i < 6; i++){
			$('.txt:eq('+i+')').text(Directors.info[i].name)
		}
		$('.txt').fadeIn("slow")
	}),
	
/**
 * 
 */
	
	$('#four').click(function(){
		if(four){
			$('.txt').hide();
			four = false;
			$('#four').val("Show");
		}
		else{
			$('.txt').show();
			four = true;
			$('#four').val("Hide");
		}
		
	});

	$('.txt').mouseover(function() {
		str = $(this).text();
		$(this).text(main( $('.txt').index(this) ));
		$(this).css({'background':'purple', 'color':'white', 'font-weight':'bold', 'font-size':'120%', 'padding':'10px'});
	});

	$('.txt').mouseout(function() {
		$(this).text(str);
		$(this).css({'background':'white', 'color':'black', 'font-weight':'normal', 'font-size':'100%', 'padding':'0px'});
	});
	
	$('.txt').dblclick(function(){
		$(this).fadeOut("slow");
		$('#log').append("<div>"+madeBy($('.txt').index(this)) +"</div>");
	});
	
	$('#log').click(function(){
		$(this).animate({
			width: "80%", opacity: 0.5, marginLeft: "0.6in",  fontSize: "24", backgroundColor: "red"}, 1500 ).animate({
			borderBottomWidth: "5px", borderTopWidth: "5px"}, 2000);
	});
	
});