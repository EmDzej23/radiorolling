//var usernamePage = "Marko";
//var chatPage = document.querySelector('#chat-page');
//var usernameForm = document.querySelector('#usernameForm');
//var messageForm = document.querySelector('#messageForm');
//var messageInput = document.querySelector('#message');
//var messageArea = document.querySelector('#messageArea');
//var connectingElement = document.querySelector('.connecting');
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
	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, onConnected, function(r){console.log("error "+r)});
	
	showNewCards();
	
	FetchData({
		url : "/public/api/question/active/ac?lidl=1"
	}, function(r){console.log("Success");$("#questcont").append('<a id="question" class="logo" href="/question?id='+r.id+'"><b>'+r.name+'</b> <span id="quest">Rolling pitanje'+
			'</span></a><a href="/qiestion?id='+r.id+'"</a>');console.log(r);$("#allcont").show('slow');$("#ldr").hide('slow');}, function(r) {console.log(r)});
});
//window.onload=function() {
//	$(".box_wrapper").css("display","block");
//	$(".loader").css("display","none");
//}

function onConnected() {
	stompClient.subscribe('/channel/public/1',
			function(r){var data = JSON.parse(r.body);$("#nr").text("");$("#nr").append('<b>NOW ROLLING </b><i style="font-size:24px" class="fa">&#xf04b;</i><br>'+data.videoDescription)});
}
function openInNewTab(url) {
	window.open(url,'_blank');
}
var start = 0;
var max = 4;
function showNewCards() {
	$("#btnload").css("display","none");
	FetchData({url:"/public/api/card/range?start="+start+"&max="+max},
			function(s){
				for (var i = 0;i<s.length;i++){
					var html = '<a href="'+s[i].link+'"><div class="col-lg-3 col-md-12 mb-3" id="content2"><div class="card card-cascade wider"><div class="view overlay"><img alt="wider" class="img-fluid" id="songImg" src="'+s[i].img+'"><a href="#!"><div class="mask rgba-white-slight waves-effect waves-light"></div></a></div><div class="card-body text-center"><h4 class="card-title"><strong id="dSongTitle">'+s[i].title+'</strong></h4><h5 class="indigo-text"><strong id="dSongDesc">'+s[i].description+'</strong></h5><p class="card-text" id="dSongPl">'+s[i].playlistName+'</p></div></div></div></a>';
					$("#other").append(html);
					$("#btnload").css("display","block");
				}
				if (s.length<4) {
					$("#btnload").css("display","none");
				}
				else {
					start+=max;
				}
			}
	);
	
}

