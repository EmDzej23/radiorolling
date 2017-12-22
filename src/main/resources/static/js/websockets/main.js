//var usernamePage = "Marko";
//var chatPage = document.querySelector('#chat-page');
//var usernameForm = document.querySelector('#usernameForm');
//var messageForm = document.querySelector('#messageForm');
//var messageInput = document.querySelector('#message');
//var messageArea = document.querySelector('#messageArea');
//var connectingElement = document.querySelector('.connecting');
$(document).ready(function() {
	connect();
	
});
var stompClient = null;
var username = null;

var colors = [ '#2196F3', '#32c787', '#00BCD4', '#ff5652', '#ffc107',
		'#ff85af', '#FF9800', '#39bbb0' ];

function connect() {
	username = 'mare';// document.querySelector('#name').value.trim();

	if (username) {

		var socket = new SockJS('/ws');
		stompClient = Stomp.over(socket);

		stompClient.connect({}, onConnected, onError);
	}
}

function onConnected() {
	stompClient.subscribe('/channel/public', onMessageReceived);
	getVideo();
	// sendMessage();
	// Tell your username to the server
	// stompClient.send("/app/chat.addUser",
	// {},
	// JSON.stringify({sender: username, type: 'JOIN'})
	// )

	// connectingElement.classList.add('hidden');
}

function onError(error) {
}

function getVideo() {
	var requestMessageDto = {
		requestType : '1'
	};
	FetchData({url:"http://localhost:8080/public/nowPlaying/"},afterVideoRequested);
}

function sendMessage() {

	var requestMessageDto = {
		requestType : '1'
	};

	stompClient.send("/app/radio.nowplaying", {}, JSON
			.stringify(requestMessageDto));
}

function onMessageReceived(payload) {
	var data = JSON.parse(payload.body);
	var opt = {
		duration : data.videoDuration,
		title : data.videoDescription,
		id : data.videoUrl
	};
	addVideoToDiv(opt);
}

function afterVideoRequested(payload) {
	var p = payload.videoUrl.split("start=");
	var time = Math.round(((Date.now() - timerStart)/1000)) + parseInt(p[1]);
	var url = p[0]+"start="+time;
	var opt = {
		duration : payload.videoDuration,
		title : payload.videoDescription,
		id : url
	};
	
	addVideoToDiv(opt);
}

function addVideoToDiv(options) {
	$("#video_frame").remove();
	$(".single_post_content")
			.append(
					'<div id="video_frame" style="pointer-events: none;" class="embed-responsive embed-responsive-16by9"><iframe class="embed-responsive-item" src="//www.youtube.com/embed/'
							+ options.id
							+ '&rel=0&amp;&autoplay=1&showinfo=0&controls=0" frameborder="0" allowfullscreen></div>')
	$(".song_title").text(options.title);
}
