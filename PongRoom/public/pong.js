
jQuery(document).ready(function () {
	var log_chat_message = function  (message, type) {
		var li = jQuery('<li />').text(message);
		
		if (type === 'system') {
			li.css({'font-weight': 'bold'});
		} else if (type === 'leave') {
			li.css({'font-weight': 'bold', 'color': '#F00'});
		}
				
		jQuery('#chat_log').append(li);
	};

	var socket = io.connect('http://84.82.145.170:3000');

	socket.on('entrance', function  (data) {
		log_chat_message(data.message, 'system');
	});

	socket.on('exit', function  (data) {
		log_chat_message(data.message, 'leave');
	});

	socket.on('chat', function  (data) {
		log_chat_message(data.message, 'normal');
	});

	jQuery('#chat_box').keypress(function (event) {
		if (event.which == 13) {
			socket.emit('chat', {message: jQuery('#chat_box').val()});
			jQuery('#chat_box').val('');
		}
	});
});





var player1;
var player2;
var y =150;
var y1=150;
var by = 190;
var bx = 440;
var direction = 1;
var fieldWidth = 900;
var ballRadius = 10;
var playerWidth=10;
var lengthPlayer1=100;
var speed = 2;
var p1score=0;
var p2score=0;

setInterval(bmove, 10);
setInterval(mPlayer2, 30);


window.onload = function () {
    player1 = $("player1");
    player1.focus();
    player1.onkeydown = move;
    player2=$("player2");

    $("field").style.width=fieldWidth+"px";
    $("player1").style.width=playerWidth+"px";
    $("player2").style.width=playerWidth+"px";
    $("ball").style.width=(2*ballRadius)+ "px";
    $("ball").style.height=(2*ballRadius)+"px";

};



function move (event){
    switch (event.keyCode){
        case 38:
            if(y>0)
            y=y-10;
            player1.style.top = y +"px";
            break;
        case 40:
            if(y<300)
            y=y+10;
            player1.style.top = y +"px";
            break;
    }

}
function mPlayer2(){

    if((y1+50)<(by)){move1(1);}
    else if (y1+50>by+8){move1(0);}
}
function move1 (x){
    switch (x){
        case 0:

            if(y1>0)
                y1=y1-10;
            player2.style.top = y1 +"px";
            break;
        case 1:

            if(y1<300)
                y1=y1+10;
            player2.style.top = y1 +"px";
            break;
    }

}
function bmove(){
var i = speed;
    while(i>0){
    check();
   pmove();
    i--;}

}
function check(){
    if(bx<=0){player2point()}
    if(bx<=0||bx>=fieldWidth-(2*ballRadius)){player1point()}
    if(bx<=ballRadius+playerWidth&&by<=y+100&&by>=y-(2*ballRadius)){changeDirectionhit()}
    if(bx>=fieldWidth-(2*ballRadius)-playerWidth-10&&by<=(y1+100)&&by>=(y1-(2*ballRadius))){changeDirectionhit()}
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


    $("ball").style.top = by +"px";
    $("ball").style.left = bx +"px";
}

function player1point(){
    p1score++;
    $("score").innerHTML=("Score: "+p1score +" - "+p2score);
    restart();
}
function player2point(){
    p2score++;
    $("score").innerHTML=("Score: "+p1score+" - "+p2score);
    restart();
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
    $("ball").style.top = by +"px";
    $("ball").style.left = bx +"px";
    var random= Math.random();
    if(random<0.5){direction=1}
    else{direction=5;}
}