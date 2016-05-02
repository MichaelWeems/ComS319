var inherits = require('inherits');
var aabb = require('aabb-2d');
var Entity = require('crtrdg-entity');


module.exports = Token;
inherits(Token, Entity);

function Token(options){
  this.position = { 
    x: options.position.x, 
    y: options.position.y 
  };
  
  this.size = {
    x: options.size.x,
    y: options.size.y
  };
  
  this.type = options.type;

  this.boundingBox = aabb([this.position.x, this.position.y], [this.size.x, this.size.y]);

  this.on('update', function(interval){
    this.boundingBox = aabb([this.position.x, this.position.y], [this.size.x, this.size.y]);
  });
  
  
  if(this.type == 1){
    //check point
    this.color = '#fff000';
  }
  else if(this.type == 2){
    //bullet activation
    this.color = '#771155';
  }
  else if(this.type == 3){
    //remove all enemies
    this.color = '#f0550f';
  }
  else if(this.type == 3){
    
    this.color = '#ff00ff';
  }
  
};