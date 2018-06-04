//var usernamePage = "Marko";
var sharing_url = window.location.href;
//var chatPage = document.querySelector('#chat-page');
//var usernameForm = document.querySelector('#usernameForm');
//var messageForm = document.querySelector('#messageForm');
//var messageInput = document.querySelector('#message');
//var messageArea = document.querySelector('#messageArea');
//var connectingElement = document.querySelector('.connecting');
$(document).ready(function() {
	ytp();
	if (vid_id!=-1) {
		opts = {
				duration : videoDto.duration,
				title : videoDto.description,
				id : videoDto.ytId,
				videoQuote : videoDto.quote,
				off : videoDto.offset,
				vid_id:videoDto.id
		}
		addVideoToDivManual(opts);
	}
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
		window.location.href = "/music/autoplay/"+plName;
	});
	$("#randomSelect").click(function(){
		window.location.href = "/music/manualplay/vid?vid="+newList[Math.floor(Math.random()*newList.length)].ytId+"&plName="+plName+"&filter="+$("#searchBox").val().replace(/&/g , "aanndd");
	});
	$("#searchBox").keyup(function() {
		onkeyupsearch();
	});
	$("#fbshare").click(function(){
		fbshare();
	});
	
});

function fbshare() {
	window.open("https://www.facebook.com/sharer/sharer.php?u="+escape(window.location.href)+"&t=Rolling Music", '', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');return false;
}
function afterAppendedNewSong() {
//	sharing_url = location.pathname+"/vid?vid="+id+"$plName="+plName+"&filter="+$("#searchBox").val().replace(/&/g , "aanndd");
	history.pushState(null, null, sharing_url);
}
function refreshSongsSelection() {
	$(".vidstoplay").contextmenu(function(e) {
		e.preventDefault();
	  if ($(this).css("background-color")=="rgba(0, 0, 0, 0)") $(this).css("background-color","rgba(0, 255, 0, 0.2)")
		else $(this).css("background-color","rgba(0, 0, 0, 0)")
	});
}
function checkWhatSongsToPlay() {
	var before = "";
	for (var i = 0;i<$(".vidstoplay").length;i++) {
		before = $("#searchBox").val()!=""?$("#searchBox").val()+",":"";
		if ($($(".vidstoplay")[i]).css("background-color")=="rgba(0, 255, 0, 0.2)") 
			$("#searchBox").val(before+$($(".vidstoplay")[i]).text());
	}
}
function updateSelection() {
	checkWhatSongsToPlay();
	onkeyupsearch();
}
function deleteSelections() {
	$("#searchBox").val("");
	onkeyupsearch();
}
function onkeyupsearch() {
	$("#songs").children().remove();
	var tekst = $("#searchBox").val();
	filter = tekst;
	newList = getFilteredSongs();
	
	for (var i = 0;i<newList.length;i++) { 
	$("#songs").append(
					'<li class="vidstoplay" id = "vid_'+i+'"><div class="media"><div class="media-left image-left-custom"><img alt="No Image" src="https://i.ytimg.com/vi/'
					+ newList[i].ytId
					+ '/hqdefault.jpg"></div><div class="media-body">'
					+ newList[i].description
					+ '</div></div></li>');
	$("#vid_"+i).click(function(){
		var ind = parseInt(this.id.split("_")[1]);
		for (var i = 0;i<newList.length;i++) {
			if (newList[i].ytId===newList[ind].ytId) {
				chosenVid = newList[i];
				currentSongIndex = i;
				break;
			}
		}
		var options = {
				duration : newList[ind].duration,
				title : newList[ind].description,
				id : newList[ind].ytId,
				videoQuote : newList[ind].quote,
				off : newList[ind].offset
		}
		goToVideo(options);
	});
	
	}
	refreshSongsSelection();
	var totalSongsTitle = newList.length!==myVideos.length?newList.length+"/"+myVideos.length:"ALL";
	$("#totalSongs").text(totalSongsTitle);	
}
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
//	shareDetails.url = "http://radiorolling.com/music/manualplay/vid?vid="+opt.id+"&plName="+urlName;
//	shareDetails.description = "Song : "+opt.title;
//	shareDetails.image = "https://i.ytimg.com/vi/"+opt.id+"/hqdefault.jpg";
//	document.title = opt.title;
//	addVideoToDivAfterFinishedManual(opt);
//	window.location.href = "/music/manualplay/vid?vid="+opt.id+"&plName="+plName+"&filter="+$("#searchBox").val().replace(/&/g , "aanndd");
	
	sharing_url = "/music/manualplay/vid?vid="+opt.id+"&plName="+plName+"&filter="+$("#searchBox").val().replace(/&/g , "aanndd");
	afterAppendedNewSong();
	refreshSongsSelection();
	$('.singleleft_inner').scrollTop($('.singleleft_inner').scrollTop() + $('#vid_'+currentSongIndex).position().top - $('#vid_'+currentSongIndex).height());
	$('.vidstoplay').css("font-weight","normal");
	$('#vid_'+currentSongIndex).css("font-weight","bold")
	addVideoToDivAfterFinishedManual(opt);
}
var currentSongIndex = -1;
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
		$("#searchBox").val(filter.replace(/aanndd/g , "&"));
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
		currentSongIndex = 0;
		shareDetails.url = "/music/manualplay/vid?vid="+current.ytId+"&plName="+urlName;
		shareDetails.description = "Song : "+current.description;
		shareDetails.image = "https://i.ytimg.com/vi/"+current.ytId+"/hqdefault.jpg";
		addVideoToDivManual(opts);
	}
	else {
		var chosenVid = {};
		for (var i = 0;i<newList.length;i++) {
			if (newList[i].ytId===vid_id) {
				chosenVid = newList[i];
				currentSongIndex = i;
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
	//addVideoToDivManual(opts);
	
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
			for (var i = 0;i<newList.length;i++) {
				if (newList[i].ytId===sortedList[ind].ytId) {
					chosenVid = newList[i];
					currentSongIndex = i;
					break;
				}
			}
			goToVideo(options);
		});
		
		if (i<sortedList.length-1) currentSong += sortedList[i].duration * 1000;
	}
	refreshSongsSelection();
	$('.singleleft_inner').scrollTop($('.singleleft_inner').scrollTop() + $('#vid_'+currentSongIndex).position().top - $('#vid_'+currentSongIndex).height());
	$('.vidstoplay').css("font-weight","normal");
	$('#vid_'+currentSongIndex).css("font-weight","bold")
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
			"http://www.youtube.com/embed/" + options.id + "?start="+options.off
					+ "&showinfo=0&controls=1&autoplay=1&enablejsapi=1&html5=1");
	$(".song_title").text(options.title);
	$("#videoQuote").text(""+options.videoQuote+"");
	document.title = options.title;
	onYouTubeIframeAPIReady();
	window.scrollTo(0,0);
}

function addVideoToDiv(options) {
	var offset = parseInt(options.id.split("?")[1].split("=")[1]) + parseInt(options.off);
	$("#video_frame").remove();
	$(".single_post_content")
			.append(
					'<div id="video_frame" class="embed-responsive embed-responsive-16by9"><iframe id="existing-iframe-example" class="embed-responsive-item" src="http://www.youtube.com/embed/'
					+ options.id.split("?")[0] + "?start="+offset
							+ '&rel=1&amp;&showinfo=0&autoplay=1&controls=1&enablejsapi=1&html5=1" frameborder="0" allowfullscreen></div>')
	$(".song_title").text(options.title);
	$("#videoQuote").text(""+options.videoQuote+"");
	ytp();
}

function addVideoToDivManual(options) {
	$("#video_frame").remove();
	$(".single_post_content")
			.append(
					'<div id="video_frame" class="embed-responsive embed-responsive-16by9"><iframe id="existing-iframe-example" class="embed-responsive-item" src="http://www.youtube.com/embed/'
					+ options.id + '?start='+options.off
							+ '&rel=1&amp;&showinfo=0&autoplay=1&controls=1&enablejsapi=1&html5=1" frameborder="0" allowfullscreen></div>')
	$(".song_title").text(options.title);
	$("#videoQuote").text(""+options.videoQuote+"");
	
}

var player;
function ytp() {
	var tag = document.createElement('script');
	tag.id = 'iframe-demo';
	tag.src = 'http://www.youtube.com/iframe_api';
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
    if(event.data === 0) {          
    	currentSongIndex++;
    	if (currentSongIndex >= newList.length) currentSongIndex = 0;
    	var video = newList[currentSongIndex];
    	goToVideo(
		{
			duration : video.duration,
    		title : video.description,
    		id : video.ytId,
    		videoQuote : video.quote,
    		off : 0
    	});
    	$('.singleleft_inner').scrollTop($('.singleleft_inner').scrollTop() + $('#vid_'+currentSongIndex).position().top - $('#vid_'+currentSongIndex).height());
    	$('.vidstoplay').css("font-weight","normal");
    	$('#vid_'+currentSongIndex).css("font-weight","bold")
    	
    }
}

function onPlayerReady(event) {
	event.target.playVideo();
}