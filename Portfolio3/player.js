var inherits = require('inherits');
var aabb = require('aabb-2d');
var Entity = require('crtrdg-entity');


module.exports = Player;
inherits(Player, Entity);

function Player(options){
  this.position = { 
    x: options.position.x, 
    y: options.position.y 
  };
    
  this.startingposition = {
    x: options.position.x,
    y: options.position.y
  };
  
  this.onGround = false;
  this.falling = false;

  this.size = {
    x: options.size.x,
    y: options.size.y
  };

  this.velocity = {
    x: options.velocity.x,
    y: options.velocity.y,
    initialY: options.velocity.y
  };
    
  this.boundingBox = aabb([this.position.x, this.position.y], [this.size.x, this.size.y]);

  this.on('update', function(interval){
    this.boundingBox = aabb([this.position.x, this.position.y], [this.size.x, this.size.y]);
  });
  
  this.speed = options.speed;
  this.verticalFlightTime = 0;
  this.friction = options.friction;
  this.gravity = options.gravity;
  this.color = options.color;
}

Player.prototype.move = function(velocity){
  this.position.x += velocity.x;
  this.position.y += velocity.y;
};

Player.prototype.checkBoundaries = function(){
  if (this.position.x <= 0){
    this.position.x = 0;
  }

  if (this.position.x >= this.game.width - this.size.x){
    this.position.x = this.game.width - this.size.x;
  }

  if (this.position.y <= 0){
    this.position.y = 0;
  }
  
  this.checkGround();
  
};

Player.prototype.checkGround = function (){
  if (this.position.y >= this.game.height - this.size.y){
    if (!this.onGround){
      this.onGround = true;
	  this.verticalFlightTime = 0;
      this.velocity.y = 0.1;
    }
    this.position.y = this.game.height - this.size.y;
  }
  else {
      this.onGround = false;
  } 
      
}

Player.prototype.keyboardInput = function(keyboard){
    
  if ('A' in keyboard.keysDown){
    this.velocity.x = -this.speed;
  }

  if ('D' in keyboard.keysDown){
    this.velocity.x = this.speed;
  }

  if ('W' in keyboard.keysDown){
    if (this.onGround){
      this.velocity.y = -this.speed;
      this.velocity.initialY = -this.speed;
      this.onGround = false;
    }
  }

  if ('S' in keyboard.keysDown){
    if (this.onGround){
      this.velocity.y = this.speed;
    }
  }
};