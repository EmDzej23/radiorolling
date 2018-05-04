
$(document).ready(function() {
	initPlaylist();
	connect();
	$('#convert-table').click(function() {
		var table = $('table').tableToJSON(); 
		console.log(table);
		alert(JSON.stringify(table));
		PostData({
			url : "/public/api/video/updatePlaylist?pl="+playlist_id,
			data : JSON.stringify(table)
		}, playlistInserted, onError);
	});
	$('#shuffle-playlist').click(function() {
		shuffleVideos();
	});
	
	document.oncontextmenu = function() {return false;};
	appendAllPls();
});
var stompClient = null;
function initPlaylist() {
	fetchSongs();
}
function appendAllPls() {
	FetchData({
		url : "/public/api/playlist/"
	}, function(response){
		var list = [];
		for (var i = 0;i<response.length;i++) {
			var className = "btn-default";
			if (response[i].id===playlist_id) {
				className = "btn-primary";
			}
			$("body").prepend("<a href='/admin/setupp?id="+response[i].id+"'><div class='btn "+className+"'>"+response[i].name+"</div></a>");
		}
	},
	function(response) {
		alert("ERROR");
		console.log(response);
	});
}
function connect() {
	var socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, onConnected, onError);
}

function onConnected() {
	stompClient.subscribe('/channel/public/' + playlist_id,
			onMessageReceived);
}

function onMessageReceived(payload) {
	console.log(payload);
	initPlaylist();
}

function afterPlaylistRequested(pl) {
	myVideos = pl.videos;
	//TODO
}

function playlistInserted(response) {
	console.log(response);
}
function fetchSongs() {
	FetchData({
		url : "/public/api/playlist/"+playlist_id
	}, setSongs);
}

var songs;

function setSongs(response) {
	songs = response.videos;
	songs.sort(function(a, b) {
		return a.index_num - b.index_num;
	})
	for (var i = 0;i<songs.length;i++) {
		delete songs[i].offset;
		delete songs[i].dailyRecommend;
		delete songs[i].plName;
		delete songs[i].tags;
	}
	$("#table_div").children().remove();
	$("#table_div").append(MakeResponsiveDHTable(songs).html);
	tableEvents();
	
}
function shuffleVideos() {
	var videos = $('table').tableToJSON();
	videos = shuffle(videos);
	$("#table_div").children().remove();
	$("#table_div").append(MakeResponsiveDHTable(videos).html);
	tableEvents();
	$("table tbody").children().each(function(index) {
		$(this).find('td:last').prev("td").prev("td").html(index + 1)
	});
}
function shuffle(array) {
  var currentIndex = array.length, temporaryValue, randomIndex;

  // While there remain elements to shuffle...
  while (0 !== currentIndex) {

    // Pick a remaining element...
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;

    // And swap it with the current element.
    temporaryValue = array[currentIndex];
    array[currentIndex] = array[randomIndex];
    array[randomIndex] = temporaryValue;
  }

  return array;
}
function tableEvents() {
	$( "table tbody" ).on('click', 'tr', function(e){
	    if(e.ctrlKey || e.metaKey){
	        $(this).toggleClass('selected');
	    }else{
	        $(this).addClass('selected').siblings().removeClass('selected');
	    }
	}).sortable( {
	  helper: function(e, item){
	        if(!item.hasClass('selected')){
	           item.addClass('selected').siblings().removeClass('selected');
	        }
	        
	        var elements = item.parent().children('.selected').clone();
	        item.data('multidrag', elements).siblings('.selected').remove();
	        
	        var helper = $('<tr/>');
	        return helper.append(elements);
	    },
	    stop: function(e, ui){
	        var elements = ui.item.data('multidrag');
	        ui.item.after(elements).remove();
	        $(this).children().each(function(index) {
				$(this).find('td:last').prev("td").prev("td").html(index + 1)
			});
	    }
	});
	$("table tbody").sortable({
		update : function(event, ui) {
			
		}
	});
	$('table tr').mousedown(function(event) {
		event.preventDefault();
		if (event.button==2) {
			$("#title_vid").text($(this).find('td:first').next("td").text());
			var m = this;
			$( "#dialog-message" ).dialog({
			      buttons: {
			        Ok: function() {
			        	if ($(m).find('td:last').prev("td").text()=="0") {
			        		$(".pom_text").remove();
			        		$("#title_vid").text("Ne mozes da brises ovu, ona ide sad..");
			        		return;
			        	}
			        	PostData({
		        			  url:"/public/api/video/deleteVideo?pl="+playlist_id,
		        		      data:JSON.stringify({id:$(m).find('td:first').text(),
		        		      index_num:$(m).find('td:last').prev("td").prev("td").text(),
		        		      started:"",
		        		      state:"",
		        		      description:"",
		        		      ytId:"",
		        		      duration:""})},
	        		      function(re){
		        		      fetchSongs();
		        		      $('#dialog-message').dialog('close');
	        		      });
			        }
			      }
			});
		}
    });
}
function isVideoAvailable(idUrl) {
	var ytkey = 'AIzaSyAohq-i1Ee_8ei5PLKyMkHYqX9KUDV9Gq8';
	$.ajax({
		cache : false,
		data : $.extend({
			key : ytkey,
			part : 'snippet,contentDetails,statistics'
		}, {
			id : idUrl
		}),
		dataType : 'json',
		type : 'GET',
		timeout : 5000,
		fields : "items(id,contentDetails,statistics,snippet(title))",
		url : 'https://www.googleapis.com/youtube/v3/videos'
	}).done(function(data) {
		if (data.items.length==0) {
			return false;
		}
		if (data.items[0].contentDetails.licensedContent) {
			return false;
		}
		else {
			return true;
		}
	});
}
function saveSong() {
	PostData({
		url : "/public/api/video/newVideo?pl="+playlist_id,
		data : JSON.stringify({
			id : "",
			index_num : "",
			started : "",
			state : "",
			description : $("#title").val(),
			ytId : $("#url").val(),
			duration : $("#duration").val(),
			quote: $("#quote").val(),
			offset:$("#offset").val()
		})
	}, songInserted, onError);
}

function songInserted(response) {
	alert("Uspesno si sacuvao pesmu!");
	fetchSongs();
}

function onError(err) {
	console.log(err);
	alert("Nesto nije u redu, pesma nije sacuvana!");
}

function getVideoDetails() {
	var ytkey = 'AIzaSyAohq-i1Ee_8ei5PLKyMkHYqX9KUDV9Gq8';
	$.ajax({
		cache : false,
		data : $.extend({
			key : ytkey,
			part : 'snippet,contentDetails,statistics'
		}, {
			id : $("#url").val()
		}),
		dataType : 'json',
		type : 'GET',
		timeout : 5000,
		fields : "items(id,contentDetails,statistics,snippet(title))",
		url : 'https://www.googleapis.com/youtube/v3/videos'
	}).done(function(data) {
		var e = false;
		if (data.items.length==0) {
			alert("Ovaj id ne postoji na youtube-u!")
			return;
			
		}
		for (var i = 0;i<songs.length;i++) {
			if (songs[i].ytId==data.items[0].id) {
				e = true;
				break;
			}
		}
		if (e) {
			alert("Ne mozes da uneses ovaj video, postoji vec!")
			return;
			
		}
		if (data.items[0].contentDetails.licensedContent) {
			alert("Ne mozes da uneses ovaj video, zasticen je autorskim pravima!")
		}
		else {
		populateFields({
			title : data.items[0].snippet.title,
			duration : YTDurationToSeconds(data.items[0].contentDetails.duration)
		})
		}
	});
}
function populateFields(options) {
	$("#title").val(options.title);
	$("#duration").val(options.duration);
}
function YTDurationToSeconds(duration) {
	var match = duration.match(/PT(\d+H)?(\d+M)?(\d+S)?/)

	var hours = ((parseInt(match[1]) || 0) !== 0) ? parseInt(match[1]) : 0;
	var minutes = ((parseInt(match[2]) || 0) !== 0) ? parseInt(match[2]) : 0;
	var seconds = ((parseInt(match[3]) || 0) !== 0) ? parseInt(match[3]) : 0;
	var total = hours * 60 * 60 + minutes * 60 + seconds;
	return total;
}
function gun() {
	AppendInfoModal("Uputstvo","1. Nađite video na youtube-u <br> 2. Iskopirajte id koji sledi posle <i style='background-color:green;color:white'>www.youtube.com/watch?v=</i> u url-u svakog YouTube videa (sadrzi 11 karaktera) <br> 3. Nalepite taj id na polje Youtube id: na ovoj stranici. <br> 4. Pritisnite dugme Popuni ostala polja. <br> 5. Ukoliko je video zaštićen autorskim pravima ili već postoji, izaberite drugi video na YouTube - u. <br> 6. Hvala što doprinosite da RadioRolling bude bolji!","info");
}
