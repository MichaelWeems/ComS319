
var io = require('socket.io').listen(5000);

var clients = [];
var messages = [];
var users = [];
var count = 0;

io.sockets.on('connection', function(socket) {
    var username = "Place Holder";
    
    //push new socket into clients
    
    //on 'login' updates all clients with new name, also new client
    socket.on('login', function(data){
      console.log(clients.length);
      users.push(data);
      clients.push(socket);
      clients.forEach(function(client){
        client.emit('update', users);
      });
      
      socket.emit('post', messages);
      
      socket.on('disconnect', function(){
        console.log("Closing...");
        var index = clients.indexOf(socket);
        clients.splice(index, 1);
        users.splice(index, 1);
        clients.forEach(function(client){
            client.emit('update', users);
        });
      });
        
      socket.on('edit', function(data){
        messages[data.index] = data;
        socket.emit('post', messages);
      });
    });
    
    //posts new message to all clients
    socket.on('message', function(data) {
      console.log(data.title +" "+ data.mess +" "+ data.name +" "+  count);
      var post = {title:data.title, mess:data.mess, name:data.name, index:count++};
      messages.push(post);
      clients.forEach(function(client) {
        client.emit('post', messages);
      }); 
    }); 
    
    
});