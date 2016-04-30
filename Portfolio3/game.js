var Game = require('crtrdg-gameloop');
var Keyboard = require('crtrdg-keyboard');
var Mouse = require('crtrdg-mouse');
var Player = require('./player');

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
  throw new Error("Something went badly wrong!");
});

game.on('resume', function(){
  console.log('oh, yeah. resuming.')
})

game.on('update', function(interval){
  console.log('updating.');
//  if (player.exists && player.boundingBox.intersects(enemy.boundingBox)){
//    console.log("oh, shit@!!!!!")
//  }
});

var player = new Player({
  position: { x: 10, y: 10 },
  size: { x: 10, y: 10 },
  velocity: { x: 0, y: 0 },
  speed: 3,
  friction: 0.9,
  gravity: 9.8,
  color: '#fff'
});

player.addTo(game);

player.on('update', function(interval){
  this.keyboardInput(keyboard);
  
  this.move(this.velocity);
  this.velocity.x *= this.friction;
  if ( this.verticalFlightTime != 0 ) {
    console.log("this.gravity* verticalflighttime / 1000: " + this.gravity*(this.verticalFlightTime / 150));
    this.velocity.y = -this.speed - 1 + this.gravity*(this.verticalFlightTime / 150);
  }
  this.checkBoundaries();
});

player.on('draw', function(draw){
  draw.fillStyle = this.color;
  draw.fillRect(this.position.x, this.position.y, this.size.x, this.size.y);
});
