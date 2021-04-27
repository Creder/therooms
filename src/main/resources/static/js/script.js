createButton();
loadRoomLamp();

function createButton() {
  document.getElementById(
      "button").innerHTML = "<button onclick='switchLamp()'>Click!</button>";
}

function loadRoomLamp() {
  httpRequest = new XMLHttpRequest();

  if (!httpRequest) {
    console.log('Unable to create XMLHTTP instance');
    return false;
  }
  httpRequest.open('GET', window.location.href + "/getLamp");

  httpRequest.responseType = 'json';
  httpRequest.send();
  httpRequest.onreadystatechange = function () {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
      if (httpRequest.status === 200) {
        var response = httpRequest.response;
        if (response === true) {
          document.getElementById("lamp").innerText = "Lamp is on.";
        } else {
          document.getElementById("lamp").innerText = "Lamp is off.";
        }
      } else {
        console.log('Something went wrong..!!');
      }
    }
  }
}

setInterval(function () {
  loadRoomLamp()
}, 2000);

function switchLamp() {
  httpRequest = new XMLHttpRequest();

  if (!httpRequest) {
    console.log('Unable to create XMLHTTP instance');
    return false;
  }
  httpRequest.open('GET', window.location.href + "/switch");

  httpRequest.responseType = 'json';
  httpRequest.send();
  httpRequest.onreadystatechange = function () {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
      if (httpRequest.status === 200) {
        var response = httpRequest.response;
        if (response === 0) {
          document.getElementById("message").innerText = "Lamp switched";
        } else {
          document.getElementById("message").innerText = "Error";
        }

        loadRoomLamp();
      }
    }
  }
}