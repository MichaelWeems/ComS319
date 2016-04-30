var Game = require('crtrdg-gameloop');
var Keyboard = require('crtrdg-keyboard');
var Mouse = require('crtrdg-mouse');
var Player = require('./player');
var Finish = require('./finish');
var Platform = require('./platform');

var pause = false;

var game = new Game({
  canvasId: 'game',
  width: 800,
  height: 400,
  backgroundColor: '#ff1f1f'
});

var keyboard = new Keyboard(game);

keyboard.on('keydown', function(keyCode){
  if (keyCode === 'P'){
    if (game.ticker.paused === true){
      game.resume();
    } else {
      game.pause();
    }
  }
});

game.on('pause', function(){
  console.log('oooooh, paused.');
  pause = true;
});

game.on('resume', function(){
  console.log('oh, yeah. resuming.')
  pause = false;
})

game.on('update', function(interval){
  console.log('updating.');
    
  if (player.exists){
      // check victory condition
      if (player.exists && player.boundingBox.intersects(finish.boundingBox)){
        console.log("Well Done, you've finished the level!");
        $('#finishMessage').css('visibility', 'visible');
      }
      
      // check if player is on a platform or the ground
      player.checkGround();

      // Check if player is colliding with platforms
      if (player.boundingBox.intersects(platform.boundingBox)){

        console.log("player position: x:" + player.position.x + " y:" + player.position.y + "\nplatform position: x:" + platform.position.x + " y:" + platform.position.y);
    //    if (player.position.y < platform.position.y){
    //        console.log("ABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\nABOVE\n")
    //        player.position.y = platform.position.y - player.size.y;
    //        player.velocity.y = player.speed;
    //        player.jump = false;
    //        player.verticalFlightTime = 0;
    //        onGround = true;
    //    }
    //    else if (player.position.y > platform.position.y){
    //        console.log("BELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\nBELOW\n")
    //        player.position.y = platform.position.y + platform.size.y + 3;
    //        player.velocity.y = 0;
    //    }
    //    else if (player.position.x > platform.position.x){
    //        player.position.x = platform.position.x + platform.size.x + 3;
    //        player.velocity.x = 0;
    //    }
    //    else if (player.position.x < platform.position.x){
    //        player.position.x = platform.position.x - player.size.x - 3;
    //        player.velocity.x = 0;
    //    }
        checkPlayerPlatformCollision(player, platform);
      }

      if (!player.onGround){
        player.verticalFlightTime++;
        console.log("verticalflighttime: " + player.verticalFlightTime + "\nposition: " + player.position.y + "\nvelocity: " + player.velocity.y);
      }
  }
    
  
});

function checkPlayerPlatformCollision(player, platform){
    
  if (player.position.y >= platform.position.y - player.size.y && player.position.y < platform.position.y + platform.size.y - player.size.y){
    if ( !checkXCollision(player, platform) ){
      player.velocity.y = 0;
      player.verticalFlightTime = 0;
      player.onGround = true;
      player.position.y = platform.position.y - player.size.y;
      player.velocity.initialY = 0;
    }
  }
  else if (player.position.y <= platform.position.y + platform.size.y && player.position.y + player.size.y > platform.position.y + platform.size.y){
    if ( !checkXCollision(player, platform) ){
      player.velocity.y = 0;
      player.velocity.initialY = 0;
      player.position.y = platform.position.y + platform.size.y + 1;
      player.onGround = false;
    }  
  }
}

function checkXCollision(player, platform){
   
  if (player.position.x >= platform.position.x - player.size.x && player.position.x < platform.position.x + platform.size.x - player.size.x){
    if ( Math.abs( (player.position.y + player.size.y) - platform.position.y ) < Math.abs( (player.position.x + player.size.x) - platform.position.x ) ) { return false;}
    if ( Math.abs( player.position.y - ( platform.position.y + platform.size.y ) ) < Math.abs( (player.position.x + player.size.x) - platform.position.x ) ) { return false;}
   
    player.velocity.x = 0;
    player.position.x = platform.position.x - player.size.x;
	return true;
  }
  else if (player.position.x <= platform.position.x + platform.size.x && player.position.x + player.size.x > platform.position.x + platform.size.x){
    if ( Math.abs( (player.position.y + player.size.y) - platform.position.y ) < Math.abs( player.position.x - (platform.position.x + platform.size.x) ) ) { return false;}
    if ( Math.abs( player.position.y - ( platform.position.y + platform.size.y ) ) < Math.abs( player.position.x - (platform.position.x + platform.size.x) ) ) { return false;}
	
	player.velocity.x = 0;
    player.position.x = platform.position.x + platform.size.x;
	return true;
  }
    
        
  return false;
}

var player = new Player({
  position: { x: 10, y: 390 },
  size: { x: 10, y: 10 },
  velocity: { x: 0, y: 0 },
  speed: 3.5,
  friction: 0.9,
  gravity: 12,
  color: '#fff'
});

player.addTo(game);

player.on('update', function(interval){
  this.keyboardInput(keyboard);
  
  this.move(this.velocity);
  this.velocity.x *= this.friction;
  if ( this.verticalFlightTime != 0 ) {
    console.log("this.gravity* verticalflighttime / 1000: " + this.gravity*(this.verticalFlightTime / 150));
    var speed = -this.speed;
    if (this.velocity.initialY != -this.speed){speed = this.velocity.initialY;}
    this.velocity.y = speed - 1 + this.gravity*(this.verticalFlightTime / 150);
  }
  this.checkBoundaries();
});

player.on('draw', function(draw){
  draw.fillStyle = this.color;
  draw.fillRect(this.position.x, this.position.y, this.size.x, this.size.y);
});

var finish = new Finish({
  position: { x: 700, y: 300 },
  size: { x: 30, y: 30 },
  color: '#000'
});

finish.addTo(game);

finish.on('draw', function(draw){
  draw.fillStyle = this.color;
  draw.fillRect(this.position.x, this.position.y, this.size.x, this.size.y);
});

var platform = new Platform({
  position: { x: 300, y: 300 },
  size: { x: 100, y: 75 },
  color: '#0000FF'
});

platform.addTo(game);

platform.on('draw', function(draw){
  draw.fillStyle = this.color;
  draw.fillRect(this.position.x, this.position.y, this.size.x, this.size.y);
});