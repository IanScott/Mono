jQuery(document).ready(function () {

    $('.player').mousedown(ssd);
    $('.player').mouseup(ssu);
    $('#player1').mousemove(ssm);
    $('#player2').mousemove(ssm);

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

    socket.on('p1move', function  (data) {
        $("#player1").css("top", data+"px");;
    });

    jQuery('#chat_box').keypress(function (event) {
        if (event.which == 13) {
            socket.emit('chat', {message: jQuery('#chat_box').val()});
            jQuery('#chat_box').val('');
        }
/*
    $('#player1').mousemove(function(event){
            socket.emit('p1move', y1);
    });

*/
    });
});

/*
var oldY = null;
var moving = null;
var y1 = 150;
var y2 = 150;




function ssd(event){
    this.style.backgroundColor = "yellow";
    moving =true;
    oldY = event.screenY;
}


function ssu(){
    this.style.backgroundColor = "blue";
    moving = false;
}

function ssm(event){

    if(moving && oldY!==null){
        var dy = event.screenY - oldY;

        y1 = y1 + dy;
        if(y1<0){y1=1}
        if(y1>300){y1=299}

        $(this).css("top", y1+"px");
    }
    oldY = event.screenY;
}
function ssm2(event){

    if(moving && oldY!==null){
        var dy = event.screenY - oldY;

        y2 = y2 + dy;
        if(y2<0){y2=1}
        if(y2>300){y2=299}

        $(this).css("top", y2+"px");
    }
    oldY = event.screenY;
}

*/
