var posts;
var index=0;
var max = 10;

$(document).ready(function(){
//	$('#filebrow').change(function() {
//		   var reader = new FileReader();
//		   reader.onload = function(e) {
//		   //this next line separates url from data
//		   var iurl = e.target.result.substr(e.target.result.indexOf(",") + 1, e.target.result.length);
//		   console.log(iurl);
//		   var clientId = "74133e4f7e93563";               
//		   $.ajax({
//		    url: "https://api.imgur.com/3/upload",
//		    type: "POST",
//		    datatype: "json",
//		    data: {
//		    'image': iurl,
//		    'type': 'base64'
//		    },
//		    success: fdone,//calling function which displays url
//		    error: function(){alert("failed")},
//		    beforeSend: function (xhr) {
//		        xhr.setRequestHeader("Authorization", "Client-ID " + clientId);
//		    }
//		});
//		};
//		 reader.readAsDataURL(this.files[0]);
//		});
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
	        url: "/admin/api/recommend/saveImg",
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
		url: "/admin/api/recommend/add?playlist_id="+$('#pl_id').val(),
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

