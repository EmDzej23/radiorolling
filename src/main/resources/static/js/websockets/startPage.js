//var usernamePage = "Marko";

//var chatPage = document.querySelector('#chat-page');
//var usernameForm = document.querySelector('#usernameForm');
//var messageForm = document.querySelector('#messageForm');
//var messageInput = document.querySelector('#message');
//var messageArea = document.querySelector('#messageArea');
//var connectingElement = document.querySelector('.connecting');

var players = [];
function createPlayer(id) {
	var player1 = new YT.Player(id, {
		events : {
			'onReady' : onPlayerReady,
			'onStateChange': onPlayerStateChange
		}
	});
	player1.elementId = id;
	player1.isPlaying = 0;
	return player1;
}
function updatePlayer(id) {
	for (var i = 0;i<players.length;i++) {
		if (players[i].elementId === id) {
			var ispl = players[i].isPlaying;
			players.splice(i, 1);
			var player = new YT.Player(id, {
				events : {
					'onReady' : onPlayerReady,
					'onStateChange': onPlayerStateChange
				}
			});
			player.elementId = id;
			player.isPlaying = ispl;
			players.push(player);
			break;
		}
	}
}
function onPlayerStateChange(event) {
	console.log("change");
	console.log(event);
	if (event.data===1) {
		
		for (var i = 0;i<players.length;i++) {
			if (players[i].elementId!=event.target.elementId) {
				players[i].pauseVideo();
				players[i].isPlaying = 0;
			}
			else {
				players[i].isPlaying = 1;
			}
		}
	}
}
function onPlayerReady(event) {
	console.log("ready");
	if (event.target.isPlaying === 1) {
		event.target.playVideo();
	}
}
function ytp() {
	var tag = document.createElement('script');
	tag.id = 'iframe-demo';
	tag.src = 'https://www.youtube.com/iframe_api';
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}

var contentText;
var contentMusic;
var contentVideo;
var contentRecommended;
var musicPlaylists;
var videoPlaylists;
var textPlaylists;
var recommendPlaylists;
var musicUrl = "/music/autoplay/Rolling";
var videoUrl = "/video/autoplay/Inserti Iz Filmova";
var textUrl = "/text/Poezija";
var recommendUrl = "/recommend/Knjige";
var stompClient = null;
$(document).ready(function() {
	ytp();
	FetchData({
		url : "/public/nowPlaying/1"
	}, function(r){$("#nr").text("");$("#nr").append('<b>NOW ROLLING </b><i style="font-size:24px" class="fa">&#xf04b;</i><br>'+r.videoDescription)});
	$("#textContent").click(function(){
		openInNewTab(textUrl);
	});
	$("#musicContent").click(function(){
		openInNewTab(musicUrl);
	});
	$("#videoContent").click(function(){
		openInNewTab(videoUrl);
	});
	$("#recContent").click(function(){
		openInNewTab(recommendUrl);
	});
	
	$("#scrolling1").click(function(){
		openInNewTab("/music/scrolling");
	});
	$("#scrolling2").click(function(){
		openInNewTab("/sport/scrolling");
	});
	$("#scrolling3").click(function(){
		openInNewTab("/movies/scrolling");
	});
	$("#gotoradio").click(function(){
		openInNewTab("/music/autoplay/Rolling");
	});
	
	
	
	$("#ytsendbtn").click(function(){
		suggestLink();
	});
	
	
	$("#content1").click(function(){
//		window.location.href = "/text/t?text="+contentText;
		openInNewTab("/text/t?text="+contentText);
	});
	$("#content2").click(function(){
//		window.location.href = "/music/manualplay/vid?vid="+contentMusic;
		openInNewTab("/music/manualplay/vid?vid="+contentMusic);
	});
	$("#content3").click(function(){
//		window.location.href = "http://radiorolling.com/video/manualplay/vid?vid="+contentVideo;
		openInNewTab("/video/manualplay/vid?vid="+contentVideo);
	});
	$("#content4").click(function(){
//		window.location.href = "http://radiorolling.com/recommend/r?id="+contentRecommended;
		openInNewTab("/recommend/r?id="+contentRecommended);
	});
	FetchData({
		url : "/public/api/playlist/lazy?type=1"
	}, function(response){
		musicPlaylists = response.sort(function(a, b){return a.id-b.id});
		var $dropdown = $("#dropdownMusic");
		$.each(musicPlaylists, function() {
		    $dropdown.append($("<option />").val(this.name).text(this.name));
		});
		$('#dropdownMusic').on('change', function() {
			musicUrl = "/music/autoplay/"+this.value;
		});
	},
	function(response) {
		console.log(response);
	});
	FetchData({
		url : "/public/api/playlist/lazy?type=2"
	}, function(response){
		videoPlaylists = response.sort(function(a, b){return a.id-b.id});
		var $dropdown = $("#dropdownVideo");
		$.each(videoPlaylists, function() {
		    $dropdown.append($("<option />").val(this.name).text(this.name));
		});
		$('#dropdownVideo').on('change', function() {
			videoUrl = "/video/autoplay/"+this.value;
		});
	},
	function(response) {
		console.log(response);
	});
	FetchData({
		url : "/public/api/playlist/lazy?type=3"
	}, function(response){
		textPlaylists = response.sort(function(a, b){return a.id-b.id});
		var $dropdown = $("#dropdownTekst");
		$.each(textPlaylists, function() {
		    $dropdown.append($("<option />").val(this.name).text(this.name));
		});
		$('#dropdownTekst').on('change', function() {
			textUrl = "/text/"+this.value;
		});
	},
	function(response) {
		console.log(response);
	});
	FetchData({
		url : "/public/api/playlist/lazy?type=4"
	}, function(response){
		recommendPlaylists = response.sort(function(a, b){return a.id-b.id});
		var $dropdown = $("#dropdownRec");
		$.each(recommendPlaylists, function() {
		    $dropdown.append($("<option />").val(this.name).text(this.name));
		});
		$('#dropdownRec').on('change', function() {
			recommendUrl = "/recommend/"+this.value;
		});
	},
	function(response) {
		console.log(response);
	});
	FetchData({
		url : "/public/api/video/getRecommendedVideo?type=1"
	}, function(response){
		contentMusic = response.ytId+"&plName="+response.plName+"&filter";
		var img = "https://i.ytimg.com/vi/"+response.ytId+"/hqdefault.jpg"
		var description = response.description;
		$("#songImg").attr("src",img);
		$("#dSongDesc").text(description);
		$("#dSongPl").text(response.plName)
	},
	function(response) {
		console.log(response);
	});
	
	FetchData({
		url : "/public/api/video/getRecommendedVideo?type=2"
	}, function(response){
		contentVideo = response.ytId+"&plName="+response.plName;
		var img = "https://i.ytimg.com/vi/"+response.ytId+"/hqdefault.jpg";
		var description = response.description;
		$("#videoImg").attr("src",img);
		$("#dVideoDesc").text(description);
		$("#dVideoPl").text(response.plName)
		
	},
	function(response) {
		console.log(response);
	});
	
	FetchData({
		url : "/public/api/video/getRecommendedVideo?type=3"
	}, function(response){
		contentText = response.id+"&plName="+response.plName;
		var img = response.ytId;
		var description = response.description;
		$("#textImg").attr("src",img);
		$("#dTextDesc").text(description);
		$("#dTextPl").text(response.plName)
	},
	function(response) {
		console.log(response);
	});
	
	FetchData({
		url : "/public/api/video/getRecommendedVideo?type=4"
	}, function(response){
		contentRecommended = response.id+"&plName="+response.plName;
		var img = response.ytId;
		var description = response.description;
		$("#recommendImg").attr("src",img);
		$("#dRecName").text(description);
		$("#dRecPl").text(response.plName)
	},
	function(response) {
		console.log(response);
	});
	FetchData({
		url : "/public/api/video/getRecommendedVideo?type=5"
	}, function(response){
		$(".quoteOfTheDay").text("");
		$(".quoteOfTheDay").children().remove();		
		$(".quoteOfTheDay").append("CITAT DANA<br><b>"+response.description+":</b><br>\""+response.quote+"\"");
	},
	function(response) {
		console.log(response);
	});
	
	
	showNewCards();
	showNewPapers();
	//showPlaylists();
	
//	FetchData({
//		url : "/public/api/question/active/ac?lidl=1"
//	}, function(r){console.log("Success");$("#questcont").append('<a id="question" class="logo" href="/question?id='+r.id+'"><b>'+r.name+'</b> <span id="quest">Rolling pitanje'+
//			'</span></a><a href="/qiestion?id='+r.id+'"</a>');console.log(r);$("#allcont").show('slow');$("#ldr").hide('slow');}, function(r) {console.log(r)});
});
//window.onload=function() {
//	$(".box_wrapper").css("display","block");
//	$(".loader").css("display","none");
//}
var currentId="";
function onYouTubeIframeAPIReady() {
	if (!subscribed) {
		var socket = new SockJS('/ws');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, onConnected, function(r){console.log("error "+r)});
	}
	else {
		updatePlayer(currentId);
	}
}
function onConnected() {
	showPlaylists();
	
}
function openInNewTab(url) {
	window.open(url,'_blank');
}
var start = 0;
var max = 4;
function showNewCards() {
	$("#btnload").css("display","none");
	$("#load1").css("display","block");
	FetchData({url:"/public/api/card/range?start="+start+"&max="+max},
			function(s){
				for (var i = 0;i<s.length;i++){
					var html = '<a href="'+s[i].link+'"><div class="col-lg-3 col-md-12 mb-3" id="content2"><div class="card card-cascade wider"><div class="view overlay centerimage"><img alt="wider" class="img-fluid fitimage" id="songImg" src="'+s[i].img+'"><a href="#!"><div class="mask rgba-white-slight waves-effect waves-light"></div></a></div><div class="card-body text-center"><h4 class="card-title"><strong id="dSongTitle">'+s[i].title+'</strong></h4><h5 class="indigo-text"><strong id="dSongDesc">'+s[i].description+'</strong></h5><p class="card-text" id="dSongPl">'+s[i].playlistName+'</p></div></div></div></a>';
					$("#other").append(html);
					$("#btnload").css("display","block");
					$("#load1").css("display","none");
				}
				if (s.length<4) {
					$("#btnload").css("display","none");
					$("#load1").css("display","none");
				}
				else {
					start+=max;
				}
			}
	);
	
}
var pstart=0;
var pmax=3;
function showNewPapers() {
	$("#paperload").css("display","none");
	$("#load2").css("display","block");
	FetchData({url:"/public/api/paper/range?p=14456&start="+pstart+"&max="+pmax},
			function(s){
				for (var i = 0;i<s.length;i++){
					var html = '<a href="/paper?id='+s[i].id+'"><div class="col-lg-4 col-md-12 mb-3"> <div class="card card-cascade wider"> <div class="view overlay" style="text-align: center;"> <img alt="wider" class="img-fluid" src="'+s[i].ytId+'" style="max-height: 250px;"> </div> </div></a> </div>';
					$("#papers").append(html);
					$("#paperload").css("display","block");
					$("#load2").css("display","none");
				}
				if (s.length<3) {
					$("#paperload").css("display","none");
					$("#load2").css("display","none");
				}
				else {
					pstart+=pmax;
				}
				$("#allcont").show('slow');$("#ldr").hide('slow');
			}
	);
}
var subscribed = false;
function showPlaylists() {
	FetchData({url:"/public/nowPlaying/all"},
			function(s){
				s = s.sort(function(a, b){return a.id-b.id});
				
				for (var i = 0;i<s.length;i++){
					if (!subscribed) {
						stompClient.subscribe('/channel/public/'+s[i].plId,
							function(r){var data = JSON.parse(r.body);updateImageAndSong(data.plName.split(" ")[0],data.videoUrl.split('?')[0],data.videoDescription,data)});
					}
					console.log("-----");
					console.log(s[i]);
					console.log("-----");
					var html = '<div class="card col-lg-2 col-md-3 mb-3 ml-4"><div class="card card-cascade wider" style="pointer-events: none;"><div class="view overlay" style="background-color:black"><div id="player" margin-top class="embed-responsive embed-responsive-16by9" style="height:50%;"> <div id="placeholder" style="top:0px;left:0px;width: 100%; height:100%;" class="embed-responsive-item"><iframe allowfullscreen scrolling="no" id="songImg_'+s[i].videoQuote.split(" ")[0]+'" src="http://m.radiorolling.com/" style="position: relative;left: 0px;height:100%; width: 100%; top: 0px;" frameborder="0" width="100%" height="100%"></iframe> </div> </div></div></div><div style="text-align:center;" class="mask rgba-white-slight waves-effect waves-light"><div class="" style="text-align:center"><div class="btn fa fa-play btn-primary" id="songImg_'+s[i].videoQuote.split(" ")[0]+'_btn" onclick="playSong(\'#songImg_'+s[i].videoQuote.split(" ")[0]+'\',\''+s[i].videoUrl+'\')"></div></div><div class="card-body text-center"><h4 class="card-title"><strong id="dSongTitle" style="font-size:0.65em;">'+s[i].videoQuote+'</strong></h4><p class="card-text" style="font-size: small;" id="dSongPl_'+s[i].videoQuote.split(" ")[0]+'">'+s[i].videoDescription+'</p></div><a href="http://radiorolling.com/music/autoplay/'+s[i].videoQuote+'"><button class="btn btn-sm btn-primary">Sve pesme</button></a></div></div>';
					$("#nowrolling").append(html);
					players.push({elementId:"#songImg_"+s[i].videoQuote.split(" ")[0],song:s[i].videoUrl,playing:0,ts:new Date().getTime()});
				}
				subscribed = true;
			}
	);
}
function playSong(id,song,socket) {
	if ($(id+"_btn").hasClass("fa-pause") && socket === undefined) {
		$(id+"_btn").removeClass("fa-pause");
		$(id+"_btn").addClass("fa-play");
		pauseSong(id,song);
		return;
	}
	for (var i = 0;i<players.length;i++) {
		pauseSong(players[i].elementId);
		$(players[i].elementId+"_btn").removeClass("fa-pause");
		$(players[i].elementId+"_btn").addClass("fa-play");
		var offset;
		if (players[i].elementId===id) {
			players[i].playing = 1;
			offset = (new Date().getTime() - players[i].ts)/1000;
			$(players[i].elementId+"_btn").removeClass("fa-play");
			$(players[i].elementId+"_btn").addClass("fa-pause");
		}
		else {
			players[i].playing = 0;
		}
	}
	offset+=parseInt(song.split("?start=")[1]);
	var frame = top.document.querySelector(id);
	frame["contentWindow"].postMessage(song.split("?start=")[0]+"?start="+offset, '*');
}
function pauseSong(id,song) {
	if (song!==undefined) {
		for (var i = 0;i<players.length;i++) {
			if (players[i].elementId===id) {
				players[i].playing = 0;
			}
		}
	}
	
	var frame = top.document.querySelector(id);
	frame["contentWindow"].postMessage("stopit" ,'*');
}
function updateImageAndSong(plName,img,song,data) {
//	$("#songImg_"+plName).attr("src","http://www.youtube.com/embed/"+data.videoUrl);
	$("#dSongPl_"+plName).text(song);
	currentId = "#songImg_"+plName;
	var gone = false;
	for (var i = 0;i<players.length;i++) {
		if (players[i].elementId === currentId) {
			$(currentId+"_btn").attr('onclick','playSong(\''+currentId+'\',\''+data.videoUrl+'\')');
			players[i].ts = new Date().getTime();
		}
		
		if (players[i].elementId === currentId && players[i].playing === 1) {
			players[i].playing = 1;
			players[i].ts = new Date().getTime();
			playSong(currentId,data.videoUrl,true);
//			var frame = top.document.querySelector(currentId);
//			frame["contentWindow"].postMessage(videoUrl,'*');
			break;
		}
//		if (players[i].id === currentId && players[i].playing === 0) {
//			pauseSong(currentId);
//			$(currentId+"_btn").removeClass("fa-pause");
//			$(currentId+"_btn").addClass("fa-play");
//			break;
//		}
	}
//	ytp();
	//updatePlayer("songImg_"+plName);
}

function suggestLink() {
	if ($("#ytlink").val()==="") {
		alert("Unesi link!!!");
		return;
	}
	PostData({
		url : "/public/api/suggestions/newLink",
		data : JSON.stringify({link:$("#ytlink").val(),linkType:1})
	}, function(r){alert("Hvala!!!");$("#ytlink").val("");$("#ytlink").text("")}, function(r) {alert("Nešto nije u redu, probaj opet!!!");});
}

