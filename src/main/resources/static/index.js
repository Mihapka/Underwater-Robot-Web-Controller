
    let stompClient = null;

    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        }
        else {
            $("#conversation").hide();
        }
        $("#greetings").html("");
    }

    function disconnect() {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");

    }

    function connect() {
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/showSensor', function (message) {
                showSensor(JSON.parse(message.body));
            });
        });
    }

    function showSensor(message) {

        $("#showPitch").text(message.pitch);
        $("#showRoll").text(message.roll);
        $("#showHeading").text(message.heading);
        $("#showDepth").text(message.depth);
        $("#showTemperature").text(message.temperature);
        $("#showCpuTemp").text(message.cpuTemp);
    }

    function sendGamepad(gamepad) {

//            stompClient.send("/welcome", {}, JSON.stringify({'name': name}));
            stompClient.send("/app/sendGamepad", {}, JSON.stringify({'button': button}));
        }

    $(function () {
                $("form").on('submit', function (e) {e.preventDefault();});
                $( "#connect" ).click(function() { connect(); });
                $( "#disconnect" ).click(function() { disconnect(); });
                $( "#requestSensor" ).click(function() { requestSensor(); });
            });


    window.addEventListener("gamepadconnected", function(event) {
            let gp = navigator.getGamepads ? navigator.getGamepads() : (navigator.webkitGetGamepads ? navigator.webkitGetGamepads : []);
            console.log(gp[0]);
            updateGamepad();
    });

    window.addEventListener("gamepaddisconnected", function(event) {
        console.log("Gamepad disconnected from index %d: %s",
        event.gamepad.index, event.gamepad.id);
    });

    function ifButtonPressed(buttons){
        buttons.forEach(function(button, i, buttons){
            if(button.pressed){
                console.log("Pushet button #: " + i + " " + buttons[i].pressed);
            }
        });
    }

    function prepareAxes(axes){
        let rightAxe = [];
        for(let i = 0; i < axes.length; i++){
            if(axes[i].toFixed(5) != 0.00392){
                rightAxe[i] = axes[i].toFixed(5);
            }
        }
        console.log(rightAxe);
    }

    function updateGamepad(){
        let gp = navigator.getGamepads ? navigator.getGamepads() : (navigator.webkitGetGamepads ? navigator.webkitGetGamepads : []);
        let buttons = gp[0].buttons;
        let axes = [gp[0].axes[0], gp[0].axes[1], gp[0].axes[2], gp[0].axes[5]];
        prepareAxes(axes);
        ifButtonPressed(buttons);
        setTimeout(updateGamepad, 100);
    }

    setTimeout(updateGamepad, 100);

