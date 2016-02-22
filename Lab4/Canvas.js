/**
 * 
 */
function Drawing(contxt){
	
	x += down;
	y += right;
	right = 5*Math.sin(Math.PI*turn/2);
	down = 5*Math.cos(Math.PI*turn/2);
	contxt.fillRect(x, y, 5, 5);
//	alert("("+right+", "+down+")");
}
function startTimer(){
	Drawing(contxt);
}


function leftTurn(contxt){
	turn = (turn+3)%4;
	startTimer();
}

function rightTurn(contxt){
	turn = (turn+1)%4;
	startTimer();
}