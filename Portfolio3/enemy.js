var inherits = require('inherits');
var aabb = require('aabb-2d');
var Entity = require('crtrdg-entity');


module.exports = Enemy;
inherits(Enemy, Entity);

function Enemy(options){
  
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
  
  

  this.boundingBox = aabb([this.position.x, this.position.y], [this.size.x, this.size.y]);
  
  
  var count = 0;
  this.on('update', function(interval){
    count++;
    this.boundingBox = aabb([this.position.x, this.position.y], [this.size.x, this.size.y]);
    this.position.x += this.velocity.x;
    this.position.y += this.velocity.y;
    if(count%20 == 0){
      count = 0;
      this.velocity.y *= -1;
    }
    this.checkBoundaries();
    
    
  });
  
  Enemy.prototype.checkBoundaries = function(){
    if(this.position.x <= 0 || this.position.x >= this.game.width - this.size.x){
      this.velocity.x *= -1;
    }
  }
  
  this.color = options.color;
};