//var usernamePage = "Marko";
//var chatPage = document.querySelector('#chat-page');
//var usernameForm = document.querySelector('#usernameForm');
//var messageForm = document.querySelector('#messageForm');
//var messageInput = document.querySelector('#message');
//var messageArea = document.querySelector('#messageArea');
//var connectingElement = document.querySelector('.connecting');
$(document).ready(function() {
	connect();
	appendPLists();
	if (playlist_id == 287) {
		$("body").css("cursor","url('../../images/darko.cur'), auto");
	}
	
	if (playlist_id == 281) {
		$("body").css("cursor","url('../../images/pavel.cur'), auto");
	}
});
function appendPLists() {
	FetchData(
			{
				url : "http://rolling.nevreme.com/public/api/playlist/"
			},
			function(res) {
				for (var i = 0; i < res.length; i++) {
					$("#plists")
							.append(
									'<li><div class="media"><a class="media-left" href="http://rolling.nevreme.com/home/'
											+ res[i].name
											+ '"> <img alt="No Image" src="'
											+ res[i].image
											+ '"></a><div class="media-body"><a class="glyphicon glyphicon-menu-left" href="http://rolling.nevreme.com/home/'
											+ res[i].name
											+ '">'
											+ res[i].name
											+ '</a></div></div></li>')
				}
			});
}
var stompClient = null;
var username = null;

var colors = [ '#2196F3', '#32c787', '#00BCD4', '#ff5652', '#ffc107',
		'#ff85af', '#FF9800', '#39bbb0' ];

function connect() {
	username = 'mare';// document.querySelector('#name').value.trim();

	if (username) {

		var socket = new SockJS(ws + '/ws');
		stompClient = Stomp.over(socket);

		stompClient.connect({}, onConnected, onError);
	}
}

function onConnected() {
	stompClient.subscribe(ws + '/channel/public/' + playlist_id,
			onMessageReceived);
	getVideo();
	getPlaylist();
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
	FetchData({
		url : appRoot + "/public/nowPlaying/" + playlist_id
	}, afterVideoRequested);
}

function getPlaylist() {
	// todo add playlists logic
	FetchData({
		url : appRoot + "/public/api/playlist/" + playlist_id
	}, afterPlaylistRequested);
}

var myVideos;
function afterPlaylistRequested(pl) {
	myVideos = pl.videos;
	$("#playlistName").text(""+pl.name);
	$("#songs").children().remove();
	myVideos.sort(function(a, b) {
		return a.index_num - b.index_num;
	});
	
	var sortedList = [];
	var current;
	for (var i = 0; i < myVideos.length; i++) {
		if (myVideos[i].state == 0) {
			current = myVideos[i];
			break;
		}
	}
	if (current) sortedList.push(current);
	for (var i = current.index_num;i<myVideos.length;i++) {
		sortedList.push(myVideos[i]);
	}
	for (var i = 0;i<current.index_num-1;i++) {
		sortedList.push(myVideos[i]);
	}
	for (var i = 0; i < sortedList.length; i++) {
		var background = sortedList[i].state == "1" ? "black" : "green";
		$("#songs").append(
				"<a target='_blank' href='http://youtube.com/watch?v="
						+ sortedList[i].ytId + "'><li><span style='color:"
						+ background + "'>"
						+ sortedList[i].description + "</span></li></a>")
	}
}
function sendMessage() {

	var requestMessageDto = {
		requestType : '1'
	};

	stompClient.send(ws + "/app/radio.nowplaying", {}, JSON
			.stringify(requestMessageDto));
}

function onMessageReceived(payload) {
	var data = JSON.parse(payload.body);
	var opt = {
		duration : data.videoDuration,
		title : data.videoDescription,
		id : data.videoUrl,
		videoQuote : data.videoQuote
	};
	document.title = data.videoDescription;
	addVideoToDivAfterFinished(opt);
}

function afterVideoRequested(payload) {
	var p = payload.videoUrl.split("start=");
	var time = Math.round(((Date.now() - timerStart) / 1000)) + parseInt(p[1]);
	var url = p[0] + "start=" + time;
	var opt = {
		duration : payload.videoDuration,
		title : payload.videoDescription,
		id : url,
		videoQuote : payload.videoQuote
	};
	document.title = payload.videoDescription;
	addVideoToDiv(opt);
}

function addVideoToDivAfterFinished(options) {
	$(".embed-responsive-item").attr(
			"src",
			"//www.youtube.com/embed/" + options.id
					+ "&showinfo=0&controls=0&enablejsapi=1&html5=1");
	$(".song_title").text(options.title);
	getPlaylist();
	$("#videoQuote").text("\""+options.videoQuote+"\"");
}

function addVideoToDiv(options) {
	$("#video_frame").remove();
	$(".single_post_content")
			.append(
					'<div id="video_frame" class="embed-responsive embed-responsive-16by9"><iframe id="existing-iframe-example" class="embed-responsive-item" src="//www.youtube.com/embed/'
							+ options.id
							+ '&rel=0&amp;&showinfo=0&controls=0&enablejsapi=1&html5=1" frameborder="0" allowfullscreen></div>')
	$(".song_title").text(options.title);
	$("#videoQuote").text("\""+options.videoQuote+"\"");
	ytp();
}
var player;
function ytp() {
	var tag = document.createElement('script');
	tag.id = 'iframe-demo';
	tag.src = 'https://www.youtube.com/iframe_api';
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}

function onYouTubeIframeAPIReady() {
	player = new YT.Player('existing-iframe-example', {
		events : {
			'onReady' : onPlayerReady
		}
	});
}
function onPlayerReady(event) {
	player.playVideo();
}
