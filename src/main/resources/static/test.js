var socket = new SockJS('websocket');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/topic/test1', function (greeting) {
        console.log(greeting);
    });
    stompClient.subscribe('/topic/greetings', function (greeting) {
        console.log(greeting);
    });
});
function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/app/test", {}, JSON.stringify({'name': $("#name").val()}));
}
