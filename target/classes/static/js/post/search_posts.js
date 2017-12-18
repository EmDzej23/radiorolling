var start = 0;
var max = 3;
var posts = [];
var globalSearchUrl="";
function appendPosts(response) {
	$("#loader_button").remove();
	posts = response;
	
	for (var i = 0; i < posts.length; i++) {
		var im = posts[i].image==null?"http://www.rockcreekdothan.com/Common/images/jquery/galleria/image-not-found.png":posts[i].image.url;
		$("#posts_container")
				.append(
						'<div class="single_archive wow fadeInDown animated" style="visibility: visible; animation-name: fadeInDown;">'
								+ '<a href="'+appRoot+"/p/"+posts[i].id+'"><img alt="" src="'+im+'"></a> <a class="read_more" href="single_page.html">Read More <i class="fa fa-angle-double-right"></i></a>'
								+ '<div class="singlearcive_article">'
								+ '<h2>'
								+ '<a href="'+appRoot+"/p/"+posts[i].id+'">'+posts[i].title+'</a>'
								+ '</h2>'
								+ '<a class="author_name" href="#"><i class="fa fa-user"></i>'+posts[i].author_name+'</a> <a class="post_date" href="#"><i class="fa  fa-clock-o"></i>'+new Date(posts[i].date).toString("dd.MM.yyyy. HH:mm:ss")+'</a>'
								+ '</div>' + '</div>');
	}
	
	
	
	if (posts.length == 0) {
		if (start==0) {
			$("#posts_container").children().remove();
			$("#posts_container").append("<h3 style='text-align: center;'>Trenutno nema postova za takvu pretragu</h3>");
		}
		else {
			$("#posts_container").append("<h3 style='text-align: center;'>Nema vise postova.</h3>");
		}
		start = 0;
		max = 3;
		return;
	}
	
	if (posts.length > 0 && posts.length < max) {
		$("#posts_container").append("<h3 style='text-align: center;'>Nema vise postova</h3>");
		start = posts.length;
		return;
	}
	
	if (posts.length == max)  {
		$("#posts_container").append('<button id="loader_button" class="btn btn-primary" style="text-align: center;">Ucitaj jos</button>');
		refreshEvent();
	}
}

function fetchPosts(url) {
	var endPoint = "";
	if (!url) {
		endPoint = appRoot+"/public/post/all/"+start+"/"+max+"/";
	}
	else {
		endPoint = url;
	}
	FetchData({
		url: endPoint
	},appendPosts,function(err){console.log(err);});
}


function getPostsForDate() {
	//TODO
}

function getPostsByAuthor(author, type) {
	globalSearchUrl = appRoot+"/public/post/postsByAuthor/"+parseInt(type)+"/"+author;
	fetchPosts(globalSearchUrl+"/"+start+"/"+max+"/");
}

function getAllByType(type) {
	globalSearchUrl = appRoot+"/public/post/all/"+parseInt(type);
	fetchPosts(globalSearchUrl+"/"+start+"/"+max+"/");
}

function getPostsByTitleOrTag() {
	//TODO
}

function refreshEvent() {
	start+=max;
	$("#loader_button").on("click", function(){
		fetchPosts(globalSearchUrl+"/"+start+"/"+max+"/");
	});
}

function setFilterEvents() {
	$("select#author_select").on("change", function(){
		start = 0;
		$("#posts_container").children().remove();
		var type = $("select#type_select").val();
		if (this.value=="Svi") {
			if (type=="-1") {
				fetchPosts();
			}
			else {
				getAllByType(type);
				return;
			}
		}
		else {
			getPostsByAuthor(this.value, type);
		}
	});
	
	$("select#type_select").on("change", function(){
		start = 0;
		$("#posts_container").children().remove();
		var author = $("select#author_select").val();
		if (this.value=="-1") {
			if (author=="Svi") {
				fetchPosts();
				return;
			}
			else {
				getPostsByAuthor(author, -1);
				return;
			}
		}
		else {
			if (author=="Svi") {
				getAllByType(this.value);
				return;
			}
			else {
				getPostsByAuthor(author, this.value);
			}
		}
	});
}