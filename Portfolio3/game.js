var Game = require('crtrdg-gameloop');
var Keyboard = require('crtrdg-keyboard');
var Mouse = require('crtrdg-mouse');
var Player = require('./player');
var Finish = require('./finish');
var Platform = require('./platform');
var Pitfall = require('./pitfall');
var Enemy = require('./enemy');
var Bullet = require('./bullet');

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
        },
        {
          position: { x: 200, y: 325 },
          size: { x: 500, y: 20 },
          color: '#0000FF'
        }
    ],
    pitfall: [{
        position: { x: 600, y: 395 },
        size: { x: 100, y: 5 },
        color: '#FF0000'
    }],
    enemy : [{
        position: {x: 100, y: 250},
        size: {x: 20, y: 20},
        velocity: {x: 3, y: 3},
        color: '#3e2470'
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
        }],
    enemy : [{
        position: {x: 500, y: 100},
        size: {x: 50, y: 50},
        velocity: {x: 1, y: 1},
        color: '#3e2470'
    }]
  }    
};

var time = 0;
var scoreTimer = null;
var deathTimer = null;

var game = null;
var keyboard = null;
var player = null;
var platforms = [];
var finishes = [];
var pitfalls = [];
var enemies = [];

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

function removeLevel(){
  for (i=0; i<game.entities.length; i++){
    game.entities[i].remove();
  }
  player.remove();
  for (i=0; i<platforms.length; i++){
      platforms[i].remove();
  }
  for (i=0; i<pitfalls.length; i++){
      pitfalls[i].remove();
  }
  for (i=0; i<finishes.length; i++){
      finishes[i].remove();
  }
  for (i=0; i<enemies.length; i++){
      enemies[i].remove();
  }
  clearInterval(inter);
  clearInterval(scoreTimer);
  scoreTimer = null;
  time = 0;
  
  player = null;
  keyboard = null;
  platforms = [];
  pitfalls = [];
  finishes = [];
  enemies = [];
  
  game.context.clearRect(0,0,game.width, game.height);
  game = null;
}

function incrementTimer(){
  time+=5;
  $('#scoreTimer').html(time + " milliseconds");
}

function loadNewLevel(level){
           
    game = new Game({
      canvasId: 'game',
      width: 800,
      height: 400,
      backgroundColor: '#1fff1f'
    });

    keyboard = new Keyboard(game);

    keyboard.on('keydown', function(keyCode){
      if (keyCode === 'P'){
        if (game.ticker.paused === true){
          game.resume();
        } else {
          game.pause();
        }
      }
        
      if (keyCode === '1'){
        removeLevel();
        loadNewLevel('level1');
      }
      if (keyCode === '2'){
        removeLevel();
        loadNewLevel('level2');
      }
        
    });
  
    mouse = new Mouse(game);
  
    mouse.on('click', function(location){
      var bull = new Bullet({
        position: { 
          x: player.position.x, 
          y: player.position.y
        },
        target: { 
          x: location.x, 
          y: location.y 
        }
      });
      bull.addTo(game);
      game.on('update', function(interval){
        enemies.forEach(function(enemy){
          if(bull.boundingBox.intersects(enemy.boundingBox)){
            bull.remove();
            enemy.remove();
            clearInterval(inter);
          }
        })
      })
      
    })

    game.on('pause', function(){
      console.log('oooooh, paused.');
    });

    game.on('resume', function(){
      console.log('oh, yeah. resuming.')
    })
    
    scoreTimer = setInterval(incrementTimer, 1);

    game.on('update', function(interval){
      console.log('updating.');

      if (player.exists){
          // check victory condition
          for (i=0;i<finishes.length;i++){
            if (finishes[i].exists){
              if (player.boundingBox.intersects(finishes[i].boundingBox)){
                console.log("Well Done, you've finished the level!");
                $('#finishMessage').css('visibility', 'visible');
                clearInterval(scoreTimer);
                $('#scoreTimer').html('Your final time was: ' + time + ' milliseconds');
                player.speed = 0;
                game.pause();
              }
            }
          }


          // check if player is on a platform or the ground
          player.checkGround();

          // Check if player is colliding with platforms
          for (i=0;i<platforms.length;i++){
            if (platforms[i].exists){
              if (player.boundingBox.intersects(platforms[i].boundingBox)){
                console.log("player position: x:" + player.position.x + " y:" + player.position.y + "\nplatform position: x:" + platforms[i].position.x + " y:" + platforms[i].position.y);
                checkPlayerPlatformCollision(player, platforms[i]);
              }
            }
          }

          for (i=0;i<pitfalls.length;i++){
            if (pitfalls[i].exists){
              // Check if the player is colliding with pitfalls
              if (player.boundingBox.intersects(pitfalls[i].boundingBox)){
                deathMessage("Get Analed! You fell into a pit!");

              }
            }
          }

          if (!player.onGround){
            player.verticalFlightTime++;
            console.log("verticalflighttime: " + player.verticalFlightTime + "\nposition: " + player.position.y + "\nvelocity: " + player.velocity.y);
          }

          for (i=0; i<enemies.length;i++){
            if (enemies[i].exists){
              if(player.boundingBox.intersects(enemies[i].boundingBox)){
                deathMessage("Get Analed! You got raped by an enemy!");
              }
            }
          }

      }

    });
    
    
    
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
        
        for (i=0; i<lev.enemy.length; i++){
            enemy = new Enemy({
                position: lev.enemy[i].position,
                size: lev.enemy[i].size,
                velocity: lev.enemy[i].velocity,
                color: lev.enemy[i].color
            });

            enemy.addTo(game);

            enemy.on('draw', function(draw){
              draw.fillStyle = this.color;
              draw.fillRect(this.position.x, this.position.y, this.size.x, this.size.y);
            });
            
            enemies.push(enemy);
        }
    }
}

var inter = window.setInterval(shoot, 1000);

function shoot(){
  for (i=0; i<enemies.length; i++){
      // randomize bullet spread
      np = Math.floor((Math.random() * 2) + 1);
      if (np == 2){np = -1;}
      
      rand = Math.floor((Math.random() * 100) + 1);
      rand *= np;
      
      bullet = new Bullet({
        target: {x: player.position.x + rand, y: player.position.y + rand},
        position: {x: enemies[i].position.x, y: enemies[i].position.y}
      });

      bullet.addTo(game);

      bullet.on('draw', function(draw){
        draw.fillStyle = this.color;
        draw.fillRect(this.position.x, this.position.y, this.size.x, this.size.y);
      });

      game.on('update', function(interval){
        if(bullet.boundingBox.intersects(player.boundingBox)){
          bullet.remove();
          deathMessage("Get Analed! You got shot like a Bitch!");
        }
      });
  }
}

function deathMessage(message){
  $('#Dead').css('visibility', 'visible');
  $('#Dead').html(message);
  deathTimer = setTimeout(function(){$('#Dead').css('visibility', 'hidden');}, 2000)

  player.position.x = player.startingposition.x;
  player.position.y = player.startingposition.y;
}