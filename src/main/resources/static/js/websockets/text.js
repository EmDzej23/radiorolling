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
	$("#searchBox").keyup(function() {
		$("#songs").children().remove();
		var tekst = $("#searchBox").val();
		filter = tekst;
		newList = getFilteredSongs();
		
		for (var i = 0;i<newList.length;i++) { 
			$("#songs").append(
					'<li class="vidstoplay" id = "vid_'+i+'"><div class="media"><div class="media-left image-left-custom"><img alt="No Image" src="'
					+ newList[i].ytId
					+ '"></div><div class="media-body">'
					+ newList[i].description
					+ '</div></div></li>');
		$("#vid_"+i).click(function(){
			var ind = parseInt(this.id.split("_")[1]);
			var options = {
					duration : newList[ind].duration,
					title : newList[ind].description,
					id : newList[ind].id,
					videoQuote : newList[ind].quote,
					off : newList[ind].offset
			}
			goToVideo(options);
		});
		}
		});
});
var shareDetails={};
var newList = [];
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
function appendPLists() {
	FetchData(
			{
				//todo: add playlist_type
				url : "/public/api/playlist/t?type=3"
			},
			function(res) {
				for (var i = 0; i < res.length; i++) {
					$("#plists")
							.append(
									'<li><div class="media"><a class="media-left" href="/text/'
											+ res[i].name
											+ '"> <img alt="No Image" src="'
											+ res[i].image
											+ '"></a><div class="media-body"><a class="glyphicon glyphicon-menu-left" href="/text/'
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
	window.location.href = "/text/t?text="+opt.id+"&plName="+plName;
}

function getPlaylist() {
	// todo add playlists logic
	FetchData({
		url : "/public/api/playlist/" + playlist_id
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
				id : current.id,
				videoQuote : current.quote,
				off : current.offset,
				vid_id:current.id,
				started:current.started
		}
		shareDetails.url = "/text/t?text="+current.id+"&plName="+urlName;
		shareDetails.description = "Naslov : "+current.description;
		//TODO:Add image to video
		shareDetails.image = current.image
	}
	else {
		var chosenVid = {};
		for (var i = 0;i<myVideos.length;i++) {
			if (myVideos[i].id===vid_id) {
				chosenVid = myVideos[i];
				break;
			}
			
		}
		opts = {
				duration : chosenVid.duration,
				title : chosenVid.description,
				id : chosenVid.id,
				videoQuote : chosenVid.quote,
				off : chosenVid.offset,
				started: chosenVid.started
		}
		shareDetails.url = "/text/t?text="+chosenVid.id+"&plName="+urlName;
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
				+ sortedList[i].ytId
				+ '"></div><div class="media-body">'
				+ sortedList[i].description
				+ '</div></div></li>');
		$("#vid_"+i).click(function(){
			var ind = parseInt(this.id.split("_")[1]);
			var options = {
					duration : sortedList[ind].duration,
					title : sortedList[ind].description,
					id : sortedList[ind].id,
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
	.append(options.videoQuote)
}

function addVideoToDivManual(options) {
	$("#video_frame").remove();
	$(".single_post_content")
			.append(options.videoQuote)
	$(".song_title").text(options.title);
	$(".song_title").append('<span id="startedDate" class="media-body" style="font-size: small;">'+formatDate(options.started)+'</span>');
}

function formatDate(date) {
	var days = ['Nedelja', 'Ponedeljak', 'Utorak', 'Sreda', 'Četvrtak', 'Petak', 'Subota'];
	var months = ['Januar','Februar','Mart','April','Maj','Jun','Jul','Avgust','Septembar','Oktobar','Novembar','Decembar']
	var d = new Date(date);
	var dayName = days[d.getDay()];
	var ttdate = dayName + " " + d.getDate() +". "+months[d.getMonth()] + " " +d.getFullYear()+". "+d.getHours() + ":"+d.getMinutes()+":"+d.getSeconds();
	return ttdate;
}