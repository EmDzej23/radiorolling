//var usernamePage = "Marko";
//var chatPage = document.querySelector('#chat-page');
//var usernameForm = document.querySelector('#usernameForm');
//var messageForm = document.querySelector('#messageForm');
//var messageInput = document.querySelector('#message');
//var messageArea = document.querySelector('#messageArea');
//var connectingElement = document.querySelector('.connecting');
$(document).ready(function() {
	$("#randomSelect").click(function(){
		window.location.href = "/music/manualplay/vid?vid="+newList[Math.floor(Math.random()*newList.length)].ytId+"&plName="+plName+"&filter="+$("#searchBox").val();
	});
	var opts = {
			duration : theVid.duration,
			title : theVid.description,
			id : theVid.ytId,
			videoQuote : theVid.quote,
			off : theVid.offset,
			vid_id:theVid.id
	}
	addVideoToDivManual(opts);
	
});
var shareDetails={};
function appendPLists() {
	FetchData(
			{
				//todo: add playlist_type
				url : "/public/api/playlist/t?type=1"
			},
			function(res) {
				for (var i = 0; i < res.length; i++) {
					$("#plists")
							.append(
									'<li><div class="media"><a class="media-left" href="/music/manualplay/'
											+ res[i].name
											+ '"> <img alt="No Image" src="'
											+ res[i].image
											+ '"></a><div class="media-body"><a class="glyphicon glyphicon-menu-left" href="/music/manualplay/'
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
//	shareDetails.url = "/music/manualplay/vid?vid="+opt.id+"&plName="+urlName;
//	shareDetails.description = "Song : "+opt.title;
//	shareDetails.image = "https://i.ytimg.com/vi/"+opt.id+"/hqdefault.jpg";
//	document.title = opt.title;
//	addVideoToDivAfterFinishedManual(opt);
	window.location.href = "/music/manualplay/vid?vid="+opt.id+"&plName="+plName+"&filter="+$("#searchBox").val();
}

function getPlaylist() {
	// todo add playlists logic
	FetchData({
		url : "/public/api/playlist/" + playlist_id
	}, afterPlaylistRequested);
}

function getFilteredSongs() {
	newList = [];
	var filters = [];
	if (filter.indexOf(",")>-1) {
		filters = filter.split(",");
	}
	else {
		filters.push(filter);
	}
	for (var i = 0;i<myVideos.length;i++) {
		var isItContained = false;
		for (var j = 0;j<filters.length;j++) {
			if (myVideos[i].description.toUpperCase().indexOf(filters[j].toUpperCase())>-1) {
				isItContained = true;
			}
		}
		if (isItContained) {
			newList.push(myVideos[i]);
		}
	}
	return newList;
}

var myVideos;
function afterPlaylistRequested(pl) {
	myVideos = pl.videos;
	newList = myVideos;
	if (filter!=="") {
		newList = getFilteredSongs();
		$("#searchBox").val(filter);
	}
	var totalSongsTitle = newList.length!==myVideos.length?newList.length+"/"+myVideos.length:"ALL";
	$("#totalSongs").text(totalSongsTitle);	
	shareDetails.title = "Rolling Music ("+pl.name+")";
	
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
	newList.sort(function(a, b) {
		return a.index_num - b.index_num;
	});
	
	var sortedList = [];
	var current = newList[0];
	
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
		shareDetails.url = "/music/manualplay/vid?vid="+current.ytId+"&plName="+urlName;
		shareDetails.description = "Song : "+current.description;
		shareDetails.image = "https://i.ytimg.com/vi/"+current.ytId+"/hqdefault.jpg";
	}
	else {
		var chosenVid = {};
		for (var i = 0;i<newList.length;i++) {
			if (newList[i].ytId===vid_id) {
				chosenVid = newList[i];
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
		shareDetails.url = "/music/manualplay/vid?vid="+chosenVid.ytId+"&plName="+urlName;
		shareDetails.description = "Song : "+chosenVid.description;
		shareDetails.image = "https://i.ytimg.com/vi/"+chosenVid.ytId+"/hqdefault.jpg";
	}
	addVideoToDivManual(opts);
	sortedList = newList;
//	else {
//		if (current) sortedList.push(current);
//		lastStartedTime = current.started - new Date().getTime() + current.duration * 1000;
//		for (var i = current.index_num;i<newList.length;i++) {
//			sortedList.push(newList[i]);
//		}
//		for (var i = 0;i<current.index_num-1;i++) {
//			sortedList.push(newList[i]);
//		}
//	}
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
			"//www.youtube.com/embed/" + options.id + "?start="+options.off
					+ "&showinfo=0&controls=1&autoplay=1&enablejsapi=1&html5=1");
	$(".song_title").text(options.title);
	$("#videoQuote").text(""+options.videoQuote+"");
	document.title = options.title;
	onYouTubeIframeAPIReady();
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
					+ options.id + '?start='+options.off
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
			'onReady' : onPlayerReady,
			'onStateChange' : onPlayerStateChange
		}
	});
}
var newList=[];
function onPlayerStateChange(event) {        
	var video = newList[Math.floor(Math.random()*newList.length)];
    if(event.data === 0) {          
    	addVideoToDivAfterFinishedManual(
		{
			duration : video.duration,
    		title : video.description,
    		id : video.ytId,
    		videoQuote : video.quote,
    		off : 0
    	});
    }
}

function onPlayerReady(event) {
	player.playVideo();
}