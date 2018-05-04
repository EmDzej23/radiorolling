var posts;
var postToShow;
var user = "Anonimus";
var userId = "2";
var userImg = 'https://app.viima.com/static/media/user_profiles/user-icon.png';
function getLogedUser() {
	FetchData({
		url : "/admin/api/post/getUser"
	}, function(response) {
		user = response.name;
		userId = response.id;
		userImg = response.image!=undefined&&response.image!="null"?response.image:'https://app.viima.com/static/media/user_profiles/user-icon.png';;
		console.log(user);
		commentsLogic();
	},function(error) {
		console.log("error");
		console.log(error);
		commentsLogic();
	});
}
function getPPosts() {
	FetchData({
		url : "/public/post/popularStories"
	}, addDataToPost);
}
function getSPosts() {
	FetchData({
		url : "/public/post/latestSO"
	}, addArticles);
}
function addArticles(response) {
	appendPopularPosts(response);
}
function addDataToPost(response) {
	posts = response;
	post = posts[posts.length - 1];
	appendRecentPosts();
//	appendComments();
	
	getLogedUser();
	
}
function appendPost(p) {
	postToShow = p;
	$(".post_title").text(p.title);
	$(".author_name").text("");
	$(".author_name").append("<i class='fa fa-user'></i>"+p.author_name);
	$(".post_date").text("");
	$(".post_date").append(
			"<i class='fa fa-clock-o'></i>"
					+ new Date(p.date).toString("dd.MM.yyyy. HH:mm:ss"));
	$(".single_post_content").children().remove();
	$(".single_post_content").append(p.post_html);
}
function appendRecentPosts() {
	for (var i = 0; i < posts.length; i++) {
		$(".recentpost_nav").append(
				'<li>'+
				'<div class="media">'+
				'<a class="media-left" href="'+appRoot+'/p/'+posts[i].id+'"> <img alt="" src="'+(posts[i].image==undefined?"http://www.rockcreekdothan.com/Common/images/jquery/galleria/image-not-found.png":posts[i].image.url)+'">'+
				'</a>'+
				'<div class="media-body">'+
					'<a class="glyphicon glyphicon-menu-right" href="'+appRoot+'/p/'+posts[i].id+'">'+posts[i].title+'</a>'+
				'</div>'+
			'</div>'+
		'</li>')
	}
}
function redirectToPost(id) {
	window.location.href = "'+appRoot+'/p/"+id;
}
function appendPopularPosts(pposts) {
	for (var i = 0; i < pposts.length; i++) {
		$("#popular_posts").append('<li>'+
				'<div class="media">'+
				'<a class="media-left" href = "'+appRoot+'/p/'+pposts[i].id+'"> <img alt="" src="'+(pposts[i].image==undefined?"http://www.rockcreekdothan.com/Common/images/jquery/galleria/image-not-found.png":pposts[i].image.url)+'">'+
				'</a>'+
				'<div class="media-body">'+
					'<a class="glyphicon glyphicon-menu-left" href="'+appRoot+'/p/'+pposts[i].id+'">'+pposts[i].title+'</a>'+
				'</div>'+
			'</div>'+
		'</li>')
	}
}

function commentsLogic() {
	var comments = [];
	var comms;
	if (!postToShow || !postToShow.comments) comms=[];
	else comms = postToShow.comments;
	for (var i = 0;i<comms.length;i++) {
		comments.push({id:comms[i].id,content:comms[i].text,fullname:comms[i].author})
	}
	$('#comments-list').comments({
		youText:user,
		enableReplying: user!="Anonimus",
		readOnly: user=="Anonimus",
		postCommentOnEnter: true,
		roundProfilePictures: true,	
		enableEditing: false,
		enableUpvoting: false,
		enableHashtags: true,
		profilePictureURL : userImg,
		fieldMappings: {
		    id: 'id',
		    parent: 'comment_id',
		    content: 'text',
		    fullname: 'youText',
		    profilePictureURL:'profilePictureURL',
		    upvoteCount: 'upvoteCount',
		    userHasUpvoted: 'userHasUpvoted'
		},
		getComments: function(success, error) {
			success(postToShow.comments);
	    },
	    postComment: function(commentJSON, success, error) {
	    	delete commentJSON.modified;
	    	delete commentJSON.profile_picture_url;
	    	delete commentJSON.created_by_current_user;
	    	delete commentJSON.userHasUpvoted;
	    	delete commentJSON.upvoteCount;
	    	delete commentJSON.id;
	    	delete commentJSON.author;
	    	delete commentJSON.pings;
	    	delete commentJSON.username;
	    	delete commentJSON.profilePictureURL;
	    	delete commentJSON.created;
	    	delete commentJSON.fullname;
	    	delete commentJSON.youText;
	    	if (commentJSON.comment_id==null) delete commentJSON.comment_id;
	    	commentJSON.comment_author = userId;
	    	var d = new Date();
	    	commentJSON.comm_date = new Date().toString("yyyy-MM-dd HH:mm:ss");
			commentJSON.post_id = postToShow.id;
			commentJSON.upvote_count = 0;
			commentJSON.user_has_upvoted = 0;
			commentJSON.author_id = userId;
			commentJSON.created = new Date().toString("yyyy-MM-ddTHH:mm:ss");
			var data = JSON.stringify(commentJSON);
			commentJSON.youText = user;
			commentJSON.comment_author = userId;
	    	console.log(JSON.stringify(commentJSON));
	    	
	        $.ajax({
	            type: 'POST',
	            contentType: "application/json",
	            url: appRoot+'/public/api/comment/addComment/'+postToShow.id,
	            dataType: "json",
	            data: data,
	            success: function() {
	            	delete commentJSON.author_id;
	    			delete commentJSON.created;
	            	commentJSON.profilePictureURL = userImg;
	            	commentJSON.youText = user;
	            	commentJSON.upvoteCount = 0;
	            	commentJSON.comment_author = userId;
	            	commentJSON.created = new Date();
	                success(commentJSON)
	            },
	            error: function(error) {
	            	console.log(error);
	            }
	        });
	    },
	    upvoteComment: function(commentJSON, success, error) {
	        var commentURL = '/basketballstats/api/comments/' + commentJSON.id;
	        var upvotesURL = commentURL + '/vote/';
	        if(commentJSON.voted) {
	            $.ajax({
	                type: 'POST',
	                contentType: "application/json",
	                url: upvotesURL,
	                success: function(comment) {
	                	console.log(comment);
	                    success(comment)
	                },
	                error: error
	            });
	        }
	        
	    }
	});
}
