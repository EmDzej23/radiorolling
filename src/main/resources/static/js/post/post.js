var posts;
var index=0;
var max = 10;

$(document).ready(function(){

	$.ajax({
		  url: 'https://api.github.com/emojis'
		}).then(function(data) {
		  window.emojis = Object.keys(data);
		  window.emojiUrls = data; 
		});;

		$("#summernote").summernote({
		  callbacks: {
		    onKeyup: function(e) {
		      $("#preview").text($('#summernote').summernote('code'));
		    }
		  },
		  placeholder: 'Napravi post',
		  hint: {
		    match: /:([\-+\w]+)$/,
		    search: function (keyword, callback) {
		      callback($.grep(emojis, function (item) {
		        return item.indexOf(keyword)  === 0;
		      }));
		    },
		    template: function (item) {
		      var content = emojiUrls[item];
		      return '<img src="' + content + '" width="20" /> :' + item + ':';
		    },
		    content: function (item) {
		      var url = emojiUrls[item];
		      if (url) {
		        return $('<img />').attr('src', url).css('width', 20)[0];
		      }
		      return '';
		    }
		  }
		});
	$("#btnUpload").on("click",function(){
		var form = $('#fileUploadForm')[0];

		// Create an FormData object
	    var data = new FormData(form);

		// disabled the submit button
	    $("#btnUpload").prop("disabled", true);

	    $.ajax({
	        type: "POST",
	        enctype: 'multipart/form-data',
	        url: "/admin/api/post/saveImg",
	        data: data,
	        processData: false,
	        contentType: false,
	        cache: false,
//	        timeout: 600000,
	        success: function (data) {
	            console.log("SUCCESS : ", data);
	            $("#btnSubmit").prop("disabled", false);
	            fdone(data);
	        },
	        error: function (e) {
	            console.log("ERROR : ", e);
	            $("#btnSubmit").prop("disabled", false);

	        }
	    });
	});
	FetchData({url:"/public/api/template/"},function(s){$(".note-codable").val((s[0].hTemplate))})
    
});
function sendPostData() {
	var videoDto = {};
	videoDto.description = $('#title').val();
	videoDto.quote = $('#preview').text();
	videoDto.started = new Date();
	
	var tags = $("#tags").val().split(",");
	var tagsToSend = [];
	for (var i = 0;i<tags.length;i++) {
		tagsToSend.push({id:null,name:tags[i]});
	}
	videoDto.tags = tagsToSend;
	
	var imagePost = $($("img")[0]).attr("src");
	videoDto.ytId = imagePost;
	PostData({
		url: "/admin/api/post/add?playlist_id="+$('#pl_id').val(),
		data: JSON.stringify(videoDto)
	},writeData,function(response) {
		console.log("error");
		console.log(response);
	});
}
function writeData(data) {
	console.log(data);
	index += 10;
	//append posts
}


function appendHtml(){
	$("#preview").text(editor.getValue());
}

function fdone(data)
{
   $("#url").val(data);    
}


function addFact() {
	var text = $("#fact_text").val();
	var id = $("#fact_id").val();
	var pic = $("#fact_pic").val();
	var html = '<h4 style="text-align: center;"><img style="width: 1200px;" src="'+pic+'"><br></h4><h4 style="text-align: center;"><span class="col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-lg-6 col-lg-offset-3" style="text-align: center;"><font color="#000000">'+text+'</font></span></h4><h4 style="text-align: center;"><span class="col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-lg-6 col-lg-offset-3" style="text-align: center;"><div style="text-align: center;"><br></div></span></h4>';
	
	
	var videoDto = {};
	videoDto.description = $('#title').val();
	videoDto.quote = html;
	videoDto.started = new Date();
	
	var tags = $("#tags").val().split(",");
	var tagsToSend = [];
	for (var i = 0;i<tags.length;i++) {
		tagsToSend.push({id:null,name:tags[i]});
	}
	videoDto.tags = tagsToSend;
	
	var imagePost = pic;
	videoDto.ytId = imagePost;
	PostData({
		url: "/admin/api/post/add?playlist_id="+$('#pl_id').val(),
		data: JSON.stringify(videoDto)
	},writeData,function(response) {
		console.log("error");
		console.log(response);
	});
}
