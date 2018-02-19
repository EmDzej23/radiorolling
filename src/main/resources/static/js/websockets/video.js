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
	setInterval(updateRollingTime, 1000);
	$("#fb_share_btn").click(function(){
		shareOverrideOGMeta(shareDetails.url, shareDetails.title, shareDetails.description, shareDetails.image)
	});
	$("#manualplaySelect").click(function(){
		window.location.href = "http://radiorolling.com/video/manualplay/"+plName;
	});
});
var plName;
var shareDetails={};
function appendPLists() {
	FetchData(
			{
				//todo: add playlist_type
				url : "http://radiorolling.com/public/api/playlist/t?type=2"
			},
			function(res) {
				for (var i = 0; i < res.length; i++) {
					$("#plists")
							.append(
									'<li><div class="media"><a class="media-left" href="http://radiorolling.com/video/autoplay/'
											+ res[i].name
											+ '"> <img alt="No Image" src="'
											+ res[i].image
											+ '"></a><div class="media-body"><a class="glyphicon glyphicon-menu-left" href="http://radiorolling.com/video/autoplay/'
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
	
	shareDetails.title = "Rolling Video ("+pl.name+")";
	var pUrl = [];
	var urlName = "";
	pUrl = pl.name.split(" ");
	for (var i = 0;i<pUrl.length;i++) {
		if (i===(pUrl.length-1)) urlName += pUrl[i];
		else urlName += pUrl[i] + "%20";
	}
	plName = urlName;
	shareDetails.url = "http://radiorolling.com/video/autoplay/"+urlName;
//	$("#tv_kanal_slika").attr("src",pl.image);
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
	shareDetails.description = "Video : "+current.description;
	shareDetails.image = "https://i.ytimg.com/vi/"+current.ytId+"/hqdefault.jpg";
	if (current) sortedList.push(current);
	lastStartedTime = current.started - new Date().getTime() + current.duration * 1000;
	for (var i = current.index_num;i<myVideos.length;i++) {
		sortedList.push(myVideos[i]);
	}
	for (var i = 0;i<current.index_num-1;i++) {
		sortedList.push(myVideos[i]);
	}
	var currentSong = sortedList[0].started;
	for (var i = 0; i < sortedList.length; i++) {
		var background = sortedList[i].state == "1" ? "black" : "green";
		var zero = new Date(currentSong).getMinutes() < 10 ? "0" : "";
		var zeroH = new Date(currentSong).getHours() < 10 ? "0" : "";
		$("#songs").append(
				"<li><span style='color:gray'>"+zeroH+new Date(currentSong).getHours()+":"+zero+new Date(currentSong).getMinutes()+"</span><span style='color:"
						+ background + "'>"
						+" "+sortedList[i].description + "</span></li>");
		if (i<sortedList.length-1) currentSong += sortedList[i].duration * 1000;
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
		videoQuote : data.videoQuote,
		off : data.videoOffset
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
		videoQuote : payload.videoQuote,
		off : payload.videoOffset
	};
	document.title = payload.videoDescription;
	addVideoToDiv(opt);
}

function addVideoToDivAfterFinished(options) {
	var offset = parseInt(options.id.split("?")[1].split("=")[1]) + parseInt(options.off);
	$(".embed-responsive-item").attr(
			"src",
			"//www.youtube.com/embed/" + options.id.split("?")[0] + "?start="+offset
					+ "&showinfo=0&controls=0&enablejsapi=1&html5=1");
	$(".song_title").text(options.title);
	getPlaylist();
	$("#videoQuote").text(""+options.videoQuote+"");
}

function addVideoToDiv(options) {
	var offset = parseInt(options.id.split("?")[1].split("=")[1]) + parseInt(options.off);
	$("#video_frame").remove();
	$(".single_post_content")
			.append(
					'<div id="video_frame" class="embed-responsive embed-responsive-16by9"><iframe id="existing-iframe-example" class="embed-responsive-item" src="//www.youtube.com/embed/'
					+ options.id.split("?")[0] + "?start="+offset
							+ '&rel=0&amp;&showinfo=0&controls=0&enablejsapi=1&html5=1" frameborder="0" allowfullscreen></div>')
	$(".song_title").text(options.title);
	$("#videoQuote").text(""+options.videoQuote+"");
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
