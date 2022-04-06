
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
    $(function () {
        $("form").on('submit', function (e) {e.preventDefault();});
        $( "#connect" ).click(function() { connect(); });
        $( "#disconnect" ).click(function() { disconnect(); });
        $( "#requestSensor" ).click(function() { requestSensor(); });
    });

    function sendName() {
        stompClient.send("/app/hello", {}, "привет");
    }

    function sendName() {
        stompClient.send("/app/hello", {}, JSON.stringify({'name': "qqq"}));
    };

    function sendGamepad(rightAxe) {
//      если задействован хотя бы один стик
//      собираем оъбект парсим в json и отправляем
    let q = rightAxe.some(axe => axe != 0)
        if(q){
            let gamepadObject = {
                leftStickX: rightAxe[0],
                leftStickY: rightAxe[1],
                rightStickX: rightAxe[2],
                rightStickY: rightAxe[3]
        };
            let json = JSON.stringify(gamepadObject);
            console.log(json);
            stompClient.send("/app/sendAxes", {}, json);
        }
    }

    window.addEventListener("gamepadconnected", function(event) {
            let gp = navigator.getGamepads ? navigator.getGamepads() : (navigator.webkitGetGamepads ? navigator.webkitGetGamepads : []);
            console.log(gp[0]);
            updateGamepad();
    });

    window.addEventListener("gamepaddisconnected", function(event) {
        console.log("Gamepad disconnected from index %d: %s",
        event.gamepad.index, event.gamepad.id);
    });

    function prepareAxes(axes){
        let rightAxe = [];
        for(let i = 0; i < axes.length; i++){
            if(axes[i].toFixed(5) != 0.00392){
                rightAxe[i] = axes[i].toFixed(5);
            }
            else{rightAxe[i] = 0.0}
        }
        return rightAxe;
        console.log(rightAxe);
    }

    function updateGamepad(){
        let gp = navigator.getGamepads ? navigator.getGamepads() : (navigator.webkitGetGamepads ? navigator.webkitGetGamepads : []);
        let axes = [gp[0].axes[0], gp[0].axes[1], gp[0].axes[2], gp[0].axes[5]];
//        console.log("полученные стики: ");
//        console.log(axes);
        sendGamepad(prepareAxes(axes));
        setTimeout(updateGamepad, 100);
    }

    setTimeout(updateGamepad, 100);

