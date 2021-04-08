var roomName = window.location.href.replace(window.location.href.substr(0, window.location.href.indexOf("room/")+5), '');
createButton();
loadRoom(roomName);

function createButton() {
    document.getElementById("button").innerHTML = '<button onclick=\'switchLamp(roomName)\'>Click!</button>';
}
function loadRoom(roomName) {
    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }
    httpRequest.open('GET', "/therooms_war/getroom?roomName="+roomName);

    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                var response = httpRequest.response;
                if(response.lamp === true){
                    document.getElementById("lamp").innerText = "Lamp is on.";
                }
                else document.getElementById("lamp").innerText = "Lamp is off.";
                document.getElementById("roomName").innerHTML = response.name;
            }
        } else {
            console.log('Something went wrong..!!');
        }
    }
}

setInterval(function () {
    loadRoom(roomName)
}, 2000);

function switchLamp(roomName) {
    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        console.log('Unable to create XMLHTTP instance');
        return false;
    }
    httpRequest.open('GET', "/therooms_war/switch?roomName="+roomName);

    httpRequest.responseType = 'json';
    httpRequest.send();
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                loadRoom(roomName);
            }
        }
    }
}