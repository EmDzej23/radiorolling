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
	$("#fb_share_btn").click(function(){
		shareOverrideOGMeta(shareDetails.url, shareDetails.title, shareDetails.description, shareDetails.image)
	});
	$("#autoplaySelect").click(function(){
		window.location.href = "http://radiorolling.com/video/autoplay/"+plName;
	});
});
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
									'<li><div class="media"><a class="media-left" href="http://radiorolling.com/video/manualplay/'
											+ res[i].name
											+ '"> <img alt="No Image" src="'
											+ res[i].image
											+ '"></a><div class="media-body"><a class="glyphicon glyphicon-menu-left" href="http://radiorolling.com/video/manualplay/'
											+ res[i].name
											+ '">'
											+ res[i].name
											+ '</a></div></div></li>')
				}
			});
}

var colors = [ '#2196F3', '#32c787', '#00BCD4', '#ff5652', '#ffc107',
		'#ff85af', '#FF9800', '#39bbb0' ];

function connect() {
	getPlaylist();
}

var plName;
function goToVideo(opt) {
//	var pUrl = [];
//	var urlName = "";
//	pUrl = plName.split(" ");
//	for (var i = 0;i<pUrl.length;i++) {
//		if (i===(pUrl.length-1)) urlName += pUrl[i];
//		else urlName += pUrl[i] + "%20";
//	}
//	shareDetails.url = "http://radiorolling.com/video/manualplay/vid?vid="+opt.id+"&plName="+urlName;
//	shareDetails.description = "Video : "+opt.title;
//	shareDetails.image = "https://i.ytimg.com/vi/"+opt.id+"/hqdefault.jpg";
//	document.title = opt.title;
//	addVideoToDivAfterFinishedManual(opt);
	window.location.href = "http://radiorolling.com/video/manualplay/vid?vid="+opt.id+"&plName="+plName;
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
//	$("#tv_kanal_slika").attr("src",pl.image);
	$("#playlistName").text(""+pl.name);
	$("#songs").children().remove();
	myVideos.sort(function(a, b) {
		return a.index_num - b.index_num;
	});
	
	var sortedList = [];
	var current = myVideos[0];
	
	var opts = {};
	if (vid_id===-1) {
		opts = {
				duration : current.duration,
				title : current.description,
				id : current.ytId,
				videoQuote : current.quote,
				off : current.offset,
				vid_id:current.id
		}
		shareDetails.url = "http://radiorolling.com/video/manualplay/vid?vid="+current.ytId+"&plName="+urlName;
		shareDetails.description = "Video : "+current.description;
		shareDetails.image = "https://i.ytimg.com/vi/"+current.ytId+"/hqdefault.jpg";
	}
	else {
		var chosenVid = {};
		for (var i = 0;i<myVideos.length;i++) {
			if (myVideos[i].ytId===vid_id) {
				chosenVid = myVideos[i];
				break;
			}
			
		}
		opts = {
				duration : chosenVid.duration,
				title : chosenVid.description,
				id : chosenVid.ytId,
				videoQuote : chosenVid.quote,
				off : chosenVid.offset,
				vid_id:chosenVid.id
		}
		shareDetails.url = "http://radiorolling.com/video/manualplay/vid?vid="+chosenVid.ytId+"&plName="+urlName;
		shareDetails.description = "Video : "+chosenVid.description;
		shareDetails.image = "https://i.ytimg.com/vi/"+chosenVid.ytId+"/hqdefault.jpg";
	}
	addVideoToDivManual(opts);
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
				'<li class="vidstoplay" id = "vid_'+i+'"><div class="media"><div class="media-left image-left-custom"><img alt="No Image" src="https://i.ytimg.com/vi/'
				+ sortedList[i].ytId
				+ '/hqdefault.jpg"></div><div class="media-body">'
				+ sortedList[i].description
				+ '</div></div></li>');
//		$("#songs").append(
//				"<li class='vidstoplay' id = 'vid_"+i+"'><span>"+ sortedList[i].description + "</span></li>");
		$("#vid_"+i).click(function(){
			var ind = parseInt(this.id.split("_")[1]);
			var options = {
					duration : sortedList[ind].duration,
					title : sortedList[ind].description,
					id : sortedList[ind].ytId,
					videoQuote : sortedList[ind].quote,
					off : sortedList[ind].offset
			}
			goToVideo(options);
		});
		if (i<sortedList.length-1) currentSong += sortedList[i].duration * 1000;
	}
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

function addVideoToDivAfterFinishedManual(options) {
	$(".embed-responsive-item").attr(
			"src",
			"//www.youtube.com/embed/" + options.id + "?start=0"
					+ "&showinfo=0&controls=1&enablejsapi=1&html5=1");
	$(".song_title").text(options.title);
	$("#videoQuote").text(""+options.videoQuote+"");
}

function addVideoToDiv(options) {
	var offset = parseInt(options.id.split("?")[1].split("=")[1]) + parseInt(options.off);
	$("#video_frame").remove();
	$(".single_post_content")
			.append(
					'<div id="video_frame" class="embed-responsive embed-responsive-16by9"><iframe id="existing-iframe-example" class="embed-responsive-item" src="//www.youtube.com/embed/'
					+ options.id.split("?")[0] + "?start="+offset
							+ '&rel=1&amp;&showinfo=0&controls=1&enablejsapi=1&html5=1" frameborder="0" allowfullscreen></div>')
	$(".song_title").text(options.title);
	$("#videoQuote").text(""+options.videoQuote+"");
	ytp();
}

function addVideoToDivManual(options) {
	$("#video_frame").remove();
	$(".single_post_content")
			.append(
					'<div id="video_frame" class="embed-responsive embed-responsive-16by9"><iframe id="existing-iframe-example" class="embed-responsive-item" src="//www.youtube.com/embed/'
					+ options.id + "?start=0"
							+ '&rel=1&amp;&showinfo=0&controls=1&enablejsapi=1&html5=1" frameborder="0" allowfullscreen></div>')
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