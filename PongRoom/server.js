var io = require('socket.io'),
  connect = require('connect');

var app = connect().use(connect.static('public')).listen(3000);
var chat_room = io.listen(app);

var i =0;
var i1 =0;
var i2 =0;
var y1 = 150;
var y2 = 150;
var by = 190;
var bx = 440;
var scorep1 =0;
var scorep2= 0;


var myobject = {message: 'Welcome to the chat room!', 
		yp1: y1, 
		yp2 :y2,
		yb:by,
		xb:bx};

var myobject1 = {yp1: y1, 
		yp2 :y2,
		yb:by,
		xb:bx};


chat_room.sockets.on('connection', function (socket) {
	
  socket.emit('entrance', myobject);

  socket.on('disconnect', function  () {
    chat_room.sockets.emit('exit', {message: 'A chatter has disconnected.'});
  });

  socket.on('chat', function  (data) {
    chat_room.sockets.emit('chat', {message: '# ' + data.message});
  });

socket.on('reset', function  (data) {
    restart();
	scorep1=0;
	scorep2 =0;
  });

socket.on('p1move', function  (data) {
	y1 = y1 + data.delta;
        if(y1<0){y1=1}
        if(y1>300){y1=299}
	
	
    chat_room.sockets.emit('p1move',{yp1:y1, yp2:y2,yb:by,xb:bx});
	
  });

socket.on('p2move', function  (data) {
	
	y2 = y2 + data.delta;
        if(y2<0){y2=1}
        if(y2>300){y2=299}
	
    chat_room.sockets.emit('p2move', {yp1:y1, yp2:y2,yb:by,xb:bx});
	
  });

  chat_room.sockets.emit('entrance', {message: 'A new chatter is online.'});
});


var ballspeed = setInterval(ball, 5);

function ball(){
bmove();
chat_room.sockets.emit("ballmove", {yb:by,xb:bx});
}
setInterval(ball1, 100);

function ball1(){
i2++;
if(i2%20==0){
chat_room.sockets.emit("ballmove", {yb:by,xb:bx});}
}


var direction = 1;
var fieldWidth = 900;
var ballRadius = 10;
var playerWidth=10;



function bmove(){
   check();
   pmove();
}

function check(){
    if(bx<=0){player2point()}
    if(bx<=0||bx>=fieldWidth-(2*ballRadius)){player1point()}
    if(bx<=ballRadius+playerWidth&&by<=y1+100&&by>=y1-(2*ballRadius)){changeDirectionhit()}
    if(bx>=fieldWidth-(2*ballRadius)-playerWidth-10&&by<=(y2+100)&&by>=(y2-(2*ballRadius))){changeDirectionhit()}
    if(by<=0||by>=380){changeDirectionv()}
}

function pmove(){
    switch(direction){
        case 1:
            bx=bx-1;
            break;
        case 2:
            bx=bx-1;
            by=by-1;
            break;
        case 3:
            by=by-1;
            break;
        case 4:
            bx=bx+1;
            by=by-1;
            break;
        case 5:
            bx=bx+1;
            break;
        case 6:
            bx=bx+1;
            by=by+1;
            break;
        case 7:
            by=by+1;
            break;
        default:
            bx=bx-1;
            by=by+1;
            break;
    }

}

function player1point(){
    restart();
	scorep1++;
	chat_room.sockets.emit('score',{p1:scorep1, p2:scorep2});
	
}
function player2point(){
    restart();
	scorep2++;
	chat_room.sockets.emit('score',{p1:scorep1, p2:scorep2});
	
}

function changeDirectionv(){
    direction = (direction+2)%8;
    pmove();
    pmove();
    if(direction==7){direction=direction+1}
    if(direction==3){direction=direction+1}
    pmove();
}

    function changeDirectionhit(){
     direction = (direction+4)%8;
     pmove();
     pmove();
     var random = parseInt((Math.random()*3)+7);
     direction = (direction+random)%8;
        pmove();
	
	
}
function restart(){
    by = 190;
    bx = 440;
    var random= Math.random();
    if(random<0.5){direction=1}
    else{direction=5;}
}
