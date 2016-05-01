var Game = require('crtrdg-gameloop');
var Keyboard = require('crtrdg-keyboard');
var Mouse = require('crtrdg-mouse');
var Player = require('./player');
var Finish = require('./finish');
var Platform = require('./platform');
var Pitfall = require('./pitfall');

var levels = 
{
  level1: {
    player: {
      position: { x: 10, y: 390 },
      size: { x: 10, y: 10 },
      velocity: { x: 0, y: 0 },
      speed: 3.5,
      friction: 0.9,
      gravity: 12,
      color: '#fff'
    },
    finish: [{
        position: { x: 700, y: 300 },
        size: { x: 30, y: 30 },
        color: '#000'
    }],
    platform: [{
        position: { x: 300, y: 300 },
        size: { x: 100, y: 75 },
        color: '#0000FF'
    }],
    pitfall: [{
        position: { x: 600, y: 395 },
        size: { x: 100, y: 5 },
        color: '#FF0000'
    }]
  },
  level2: {
    player: {
      position: { x: 10, y: 390 },
      size: { x: 50, y: 50 },
      velocity: { x: 0, y: 0 },
      speed: 3.5,
      friction: 0.9,
      gravity: 12,
      color: '#fff'
    },
    finish: [{
        position: { x: 500, y: 100 },
        size: { x: 100, y: 100 },
        color: '#000'
        },
        { position: { x: 0, y: 100 },
          size: { x: 100, y: 150 },
          color: '#000'
        }],
    platform: [{
        position: { x: 300, y: 300 },
        size: { x: 100, y: 75 },
        color: '#0000FF'
    }],
    pitfall: [
        {
          position: { x: 600, y: 395 },
          size: { x: 100, y: 5 },
          color: '#FF0000'
        },
        {
          position: { x: 100, y: 395 },
          size: { x: 100, y: 5 },
          color: '#FF0000'
        }]
  }    
};

var pause = false;
var time = 0;
var scoreTimer = null;
var deathTimer = null;

var player = null;
var platforms = [];
var finishes = [];
var pitfalls = [];

var game = new Game({
  canvasId: 'game',
  width: 800,
  height: 400,
  backgroundColor: '#1fff1f'
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

scoreTimer = setInterval(incrementTimer, 1);

function incrementTimer(){
    time++;
    $('#scoreTimer').html(time + " milliseconds");
}

game.on('update', function(interval){
  console.log('updating.');
    
  $('#level1').on('click', function(){
   loadNewLevel("level1"); 
    });

    $('#level2').on('click', function(){
       loadNewLevel("level2"); 
    });
    

  if (player.exists){
      // check victory condition
      for (i=0;i<finishes.length;i++){
        if (player.boundingBox.intersects(finishes[i].boundingBox)){
          console.log("Well Done, you've finished the level!");
          $('#finishMessage').css('visibility', 'visible');
          clearInterval(scoreTimer);
          $('#scoreTimer').html('Your final time was: ' + time + ' milliseconds');
        }
      }
      
      
      // check if player is on a platform or the ground
      player.checkGround();

      // Check if player is colliding with platforms
      for (i=0;i<platforms.length;i++){
          
        if (player.boundingBox.intersects(platforms[i].boundingBox)){
          console.log("player position: x:" + player.position.x + " y:" + player.position.y + "\nplatform position: x:" + platforms[i].position.x + " y:" + platforms[i].position.y);
          checkPlayerPlatformCollision(player, platforms[i]);
        }
      }
      
      for (i=0;i<pitfalls.length;i++){
        // Check if the player is colliding with pitfalls
        if (player.boundingBox.intersects(pitfalls[i].boundingBox)){
          $('#Dead').css('visibility', 'visible');
          deathTimer = setTimeout(function(){$('#Dead').css('visibility', 'hidden');}, 2000)

          player.position.x = player.startingposition.x;
          player.position.y = player.startingposition.y;
        }
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

loadNewLevel("level1");



function loadNewLevel(level){
//    delete require.cache[require.resolve('./player.js')];
//    delete require.cache[require.resolve('./platform.js')];
//    delete require.cache[require.resolve('./pitfall.js')];
//    delete require.cache[require.resolve('./finish.js')];
    
    player = null;
    platforms = [];
    pitfalls = [];
    finishes = [];
    
    
    if (level in levels){
        lev = levels[level];
        
        player = new Player({
          position: lev.player.position,
          size: lev.player.size,
          velocity: lev.player.velocity,
          speed: lev.player.speed,
          friction: lev.player.friction,
          gravity: lev.player.gravity,
          color: lev.player.color
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
        
        for(i=0; i<lev.finish.length; i++){

            finish = new Finish({
              position: lev.finish[i].position,
              size: lev.finish[i].size,
              color: lev.finish[i].color
            });

            finish.addTo(game);

            finish.on('draw', function(draw){
              draw.fillStyle = this.color;
              draw.fillRect(this.position.x, this.position.y, this.size.x, this.size.y);
            });
            
            finishes.push(finish);
        }

         for(i=0; i<lev.platform.length; i++){
            
            platform = new Platform({
              position: lev.platform[i].position,
              size: lev.platform[i].size,
              color: lev.platform[i].color
            });

            platform.addTo(game);

            platform.on('draw', function(draw){
              draw.fillStyle = this.color;
              draw.fillRect(this.position.x, this.position.y, this.size.x, this.size.y);
            });
            
            platforms.push(platform);
        }

         for(i=0; i<lev.pitfall.length; i++){
            pitfall = new Pitfall({
              position: lev.pitfall[i].position,
              size: lev.pitfall[i].size,
              color: lev.pitfall[i].color
            });

            pitfall.addTo(game);

            pitfall.on('draw', function(draw){
              draw.fillStyle = this.color;
              draw.fillRect(this.position.x, this.position.y, this.size.x, this.size.y);
            });
            
            pitfalls.push(pitfall);
        }
    }
}