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
});
var shareDetails={};
function appendPLists() {
	FetchData(
			{
				//todo: add playlist_type
				url : "http://localhost:8081/public/api/playlist/t?type=3"
			},
			function(res) {
				for (var i = 0; i < res.length; i++) {
					$("#plists")
							.append(
									'<li><div class="media"><a class="media-left" href="http://localhost:8081/video/manualplay/'
											+ res[i].name
											+ '"> <img alt="No Image" src="'
											+ res[i].image
											+ '"></a><div class="media-body"><a class="glyphicon glyphicon-menu-left" href="http://localhost:8081/video/manualplay/'
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
	var pUrl = [];
	var urlName = "";
	pUrl = plName.split(" ");
	for (var i = 0;i<pUrl.length;i++) {
		if (i===(pUrl.length-1)) urlName += pUrl[i];
		else urlName += pUrl[i] + "%20";
	}
	shareDetails.url = "http://localhost:8081/video/manualplay/vid?vid="+opt.id+"&plName="+urlName;
	shareDetails.description = "Video : "+opt.title;
	shareDetails.image = "https://i.ytimg.com/vi/"+opt.id+"/hqdefault.jpg";
	document.title = opt.title;
	addVideoToDivAfterFinishedManual(opt);
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
	
	shareDetails.title = "Rolling Tekstovi ("+pl.name+")";
	
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
		shareDetails.url = "http://localhost:8081/text/t?id="+current.ytId+"&plName="+urlName;
		shareDetails.description = "Naslov : "+current.description;
		//TODO:Add image to video
		shareDetails.image = current.image
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
		shareDetails.url = "http://localhost:8081/text/t?id="+chosenVid.ytId+"&plName="+urlName;
		shareDetails.description = "Video : "+chosenVid.description;
		shareDetails.image = chosenVid.image;
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
				'<li class="vidstoplay" id = "vid_'+i+'"><div class="media"><div class="media-left image-left-custom"><img alt="No Image" src="'
				+ sortedList[i].image
				+ '></div><div class="media-body">'
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

function addVideoToDivAfterFinishedManual(options) {
	$("#video_frame").remove();
	$(".song_title").text(options.title);
	$(".single_post_content")
	.append(options.id)
}

function addVideoToDivManual(options) {
	$("#video_frame").remove();
	$(".single_post_content")
			.append(options.id)
	$(".song_title").text(options.title);
}
