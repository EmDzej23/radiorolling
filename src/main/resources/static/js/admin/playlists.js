$(document).ready(function() {
	fetchSongs();
	$('#convert-table').click(function() {
		var table = $('table').tableToJSON(); 
		console.log(table);
		alert(JSON.stringify(table));
		PostData({
			url : appRoot + "/public/api/video/updatePlaylist",
			data : JSON.stringify(table)
		}, playlistInserted, onError);
	});
	document.oncontextmenu = function() {return false;};
});
function playlistInserted(response) {
	console.log(response);
}
function fetchSongs() {
	FetchData({
		url : appRoot + "/public/api/video/"
	}, setSongs);
}

var songs;

function setSongs(response) {
	songs = response;
	songs.sort(function(a, b) {
		return a.index_num - b.index_num;
	})
	$("#table_div").children().remove();
	$("#table_div").append(MakeResponsiveDHTable(songs).html);
	tableEvents();
	
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
				$(this).find('td:last').prev("td").html(index + 1)
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
			        	if ($(m).find('td:last').text()=="0") {
			        		$(".pom_text").remove();
			        		$("#title_vid").text("Ne mozes da brises ovu, ona ide sad..");
			        		return;
			        	}
			        	PostData({
		        			  url:"http://localhost:8080/public/api/video/deleteVideo",
		        		      data:JSON.stringify({id:$(m).find('td:first').text(),
		        		      index_num:"1",
		        		      started:"",
		        		      state:"",
		        		      description:"",
		        		      ytId:"",
		        		      duration:""})},
	        		      function(re){
		        		      conole.log(re);
		        		      fetchSongs();
	        		      });
			        }
			      }
			});
		}
    });
}
function saveSong() {
	PostData({
		url : appRoot + "/public/api/video/newVideo",
		data : JSON.stringify({
			id : "",
			index_num : "",
			started : "",
			state : "",
			description : $("#title").val(),
			ytId : $("#url").val(),
			duration : $("#duration").val()
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
		populateFields({
			title : data.items[0].snippet.title,
			duration : YTDurationToSeconds(data.items[0].contentDetails.duration)
		})
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
