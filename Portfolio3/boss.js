var inherits = require('inherits');
var aabb = require('aabb-2d');
var Entity = require('crtrdg-entity');


module.exports = Boss;
inherits(Boss, Entity);

function Boss(options){
  
  this.position = { 
    x: options.position.x, 
    y: options.position.y 
  };
  
  this.size = {
    x: options.size.x,
    y: options.size.y
  };
  
  this.velocity = {
    x: options.velocity.x,
    y: options.velocity.y
  };
  
  this.health = 30;
  
  this.boundingBox = aabb([this.position.x, this.position.y], [this.size.x, this.size.y]);
  
  var count = 0;
  this.on('update', function(interval){
    count++;
    this.boundingBox = aabb([this.position.x, this.position.y], [this.size.x, this.size.y]);
    
    if (this.position.x <= 0) {
      this.position.x = 25;
    }
    else if (this.position.x >= 1000){
      this.position.x = 975;
    }
    
    if (this.position.y <= 0){
      this.position.y = 25;
    }
    if (this.position.y >= 475){
      this.position.y = 525;
    }
    
    this.position.x += this.velocity.x;
    this.position.y += this.velocity.y;
    
    np = Math.floor((Math.random() * 2) + 1);
    if (np == 2){np = -1;}

    rand = Math.floor((Math.random() * 5) + 1);
    rand *= np;
    
    if(count%20 == 0){
      this.velocity.y *= rand;
    }
    if (count%30 == 0){
      this.velocity.y = 1;
    }
    if(count%60 == 0){
      count = 0;
      this.velocity.x *= -1.001;
      this.velocity.y = -1.5
    }
    this.checkBoundaries();
  });
  
  Boss.prototype.checkBoundaries = function(){
    if(this.position.x <= 0 || this.position.x >= this.game.width - this.size.x){
      this.velocity.x *= -1;
    }
  }
  
  this.color = options.color;
};