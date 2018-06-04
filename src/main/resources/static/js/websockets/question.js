$(document).ready(function() {
	$(".song_title").children().remove();
	$(".song_title").append(quest.name+'<br><span id="startedDate"'+
			'class="media-body" style="font-size: small;">'+quest.answers.length+' odgovora</span>');
	
	$("#tit23").append('<a href="#">Odgovor na pitanje: <b>"'+quest.name+
	'"</b></a>');
	
	$("#answerName").val(user.username);
	
	appendAnswers(quest);
	appendQuestions();
	$("#sortByDate").attr("disabled", "disabled");
	$("#sortByDown").removeAttr("disabled");
	$("#sortByUp").removeAttr("disabled");
	$("#sortByDate").click(function(){
		compare = compareDate;
		$("#sortByDate").attr("disabled", "disabled");
		$("#sortByDown").removeAttr("disabled");
		$("#sortByUp").removeAttr("disabled");
		$(".single_post_content").children().remove();
		quest.answers.sort(compare);
		appendAnswers(quest);
	});
	$("#sortByDown").click(function(){
		compare = compareDown;	
		$("#sortByDate").removeAttr("disabled");
		$("#sortByDown").attr("disabled", "disabled");
		$("#sortByUp").removeAttr("disabled");
		$(".single_post_content").children().remove();
		quest.answers.sort(compare);
		appendAnswers(quest);
	});
	$("#sortByUp").click(function(){
		compare = compareUp;
		$("#sortByDate").removeAttr("disabled");
		$("#sortByDown").removeAttr("disabled");
		$("#sortByUp").attr("disabled", "disabled");
		$(".single_post_content").children().remove();
		quest.answers.sort(compare);
		appendAnswers(quest);
	});
});

var compare = compareDate;

function like(id,type) {
	PostData({
		url : "/public/api/question/like?id="+id+"&type="+type
	}, function(r){
		console.log("Success");
		console.log(r);
		replaceAnswer(r,user.id);
	}, function(r) {
		console.log("Error");
//		replaceAnswer(r,user.id);
	});
}
function appendAnswer(answer, id) {
	var liked = false;
	var colorLiked = "";
	var colorDown = "";
	if (answer.votes!=null) {
		for (var j = 0;j<answer.votes.length;j++) {
			if (answer.votes[j].visitorId===id) {
				liked = true;
				if (!answer.votes[j].vote) {
					colorDown = "red"
				}
				else {
					colorLiked = "green";
				}
				break;
			}
		}
	}
	var username = answer.visitor.username==null?"Gost":answer.visitor.username;
	var html = liked?'<div id="likeswrapper_'+answer.id+'" class="stats"> <i style="color:'+colorLiked+'" class="fa fa-thumbs-up icon"></i>&nbsp&nbsp'
			+ answer.upVotes
			+ ' &nbsp&nbsp&nbsp <i style="color:'+colorDown+'" class="fa fa-thumbs-down icon"></i>&nbsp&nbsp'
			+ answer.downVotes+'<i onclick="shareToFb('+answer.id+')" style="color:" class="btn fa fa-share icon">Deli na FB</i>'
			+ ' </div>':'<div id="likeswrapper_'+answer.id+'" class="stats"> <button onclick="like('+answer.id+',true)" class="btn btn-default stat-item"><i id="ansL'+answer.id+'" class="fa fa-thumbs-up icon"></i><span id="avl'+answer.id+'">'
				+ answer.upVotes + '</span>'
				+ ' </button> <button onclick="like('+answer.id+',false)" class="btn btn-default stat-item" href="#"> <i id="ansD'+answer.id+'" class="fa fa-thumbs-down icon"></i><span id="avd'+answer.id+'">'
				+ answer.downVotes + '</span>'
				+ ' </button><i onclick="shareToFb('+answer.id+')" style="color:" class="btn fa fa-share icon">Deli na FB</i> </div>';
			console.log(liked);
	$(".single_post_content")
			.append(
					'<div class="row"> <div class="col-md-12"> <div class="panel panel-white post panel-shadow"> <div class="post-heading"> <div class="pull-left meta"> <div class="title h5"> <a href="#"><b>'
							+ username
							+ '</b></a> </div> <h6 class="text-muted time">'+formatDate(answer.date)+'</h6> </div> </div> <div class="post-description"> <p style="margin: 3px;">'
							+ answer.text
							+ '</p> '+html+' </div> </div> </div> </div>');
}
function replaceAnswer(answer,id) {
	debugger;
	var liked = false;
	var colorLiked = "";
	var colorDown = "";
	if (answer.votes!=null) {
		for (var j = 0;j<answer.votes.length;j++) {
			if (answer.votes[j].visitorId===id) {
				liked = true;
				if (!answer.votes[j].vote) {
					colorDown = "red"
				}
				else {
					colorLiked = "green";
				}
				break;
			}
		}
	}
	$("#likeswrapper_"+answer.id).children().remove();
	
	var username = answer.visitor.username==null?"Gost":answer.visitor.username;
	var html = liked?'<i style="color:'+colorLiked+'" class="fa fa-thumbs-up icon"></i>&nbsp&nbsp'
			+ answer.upVotes
			+ ' &nbsp&nbsp&nbsp <i style="color:'+colorDown+'" class="fa fa-thumbs-down icon"></i>&nbsp&nbsp'
			+ answer.downVotes+'<i onclick="shareToFb('+answer.id+')" style="color:" class="btn fa fa-share icon">Deli na FB</i>'
			:'<button onclick="like('+answer.id+',true)" class="btn btn-default stat-item"><i id="ansL'+answer.id+'" class="fa fa-thumbs-up icon"></i><span id="avl'+answer.id+'">'
				+ answer.upVotes + '</span>'
				+ ' </button> <button onclick="like('+answer.id+',false)" class="btn btn-default stat-item" href="#"> <i id="ansD'+answer.id+'" class="fa fa-thumbs-down icon"></i><span id="avd'+answer.id+'">'
				+ answer.downVotes + '</span>'
				+ ' </button><i onclick="mj('+answer.id+')" style="color:" class="btn fa fa-share icon">Deli na FB</i>';
			console.log(liked);
			$("#likeswrapper_"+answer.id).append(html);
//	$(".single_post_content")
//			.append(
//					'<div class="row"> <div class="col-md-12"> <div class="panel panel-white post panel-shadow"> <div class="post-heading"> <div class="pull-left meta"> <div class="title h5"> <a href="#"><b>'
//							+ username
//							+ '</b></a> </div> <h6 class="text-muted time">'+formatDate(answer.date)+'</h6> </div> </div> <div class="post-description"> <p>'
//							+ answer.text
//							+ '</p> '+html+' </div> </div> </div> </div>');
}
function appendAnswers(dto) {
	var id = user.id;
	dto.answers.sort(compare);
	$(".single_post_content").children().remove();
	for (var i = 0; i < dto.answers.length; i++) {
		appendAnswer(dto.answers[i], id);
//		var liked = false;
//		var colorLiked = "";
//		var colorDown = "";
//		for (var j = 0;j<dto.answers[i].votes.length;j++) {
//			if (dto.answers[i].votes[j].visitorId===id) {
//				liked = true;
//				if (!dto.answers[i].votes[j].vote) {
//					colorDown = "red"
//				}
//				else {
//					colorLiked = "green";
//				}
//				break;
//			}
//		}
//		
//		var username = dto.answers[i].visitor.username==null?"Gost":dto.answers[i].visitor.username;
//		var html = liked?'<div id="likeswrapper_'+dto.answers[i].id+'" class="stats"> <i style="color:'+colorLiked+'" class="fa fa-thumbs-up icon"></i>&nbsp&nbsp'
//				+ dto.answers[i].upVotes
//				+ ' &nbsp&nbsp&nbsp <i style="color:'+colorDown+'" class="fa fa-thumbs-down icon"></i>&nbsp&nbsp'
//				+ dto.answers[i].downVotes
//				+ ' </div>':'<div class="stats"> <button onclick="like('+dto.answers[i].id+',true)" class="btn btn-default stat-item"><i id="ansL'+dto.answers[i].id+'" class="fa fa-thumbs-up icon"></i>'
//					+ dto.answers[i].upVotes
//					+ ' </button> <button onclick="like('+dto.answers[i].id+',true)" class="btn btn-default stat-item" href="#"> <i id="ansD'+dto.answers[i].id+'" class="fa fa-thumbs-down icon"></i>'
//					+ dto.answers[i].downVotes
//					+ ' </button> </div>';
//				console.log(liked);
//		$(".single_post_content")
//				.append(
//						'<div class="row"> <div class="col-md-12"> <div class="panel panel-white post panel-shadow"> <div class="post-heading"> <div class="pull-left meta"> <div class="title h5"> <a href="#"><b>'
//								+ username
//								+ '</b></a> </div> <h6 class="text-muted time">'+formatDate(dto.answers[i].date)+'</h6> </div> </div> <div class="post-description"> <p>'
//								+ dto.answers[i].text
//								+ '</p> '+html+' </div> </div> </div> </div>');
	}
}

function answerTheQuestion() {
	var username = $("#answerName").val();
	var answerText = $("#answerText").val();
	if (answerText.indexOf("<")>-1) {
		alert("Ne unosi <");
		return;
	}
	if (answerText==="") {
		alert("Prazan ti je odgovor");
		return;
	}
	
	if (username==="") {
		alert("Prazan ti je username");
		return;
	}
	var teks = $("#answerLink").val()!==""&&$("#answerLink").val().indexOf("http")>-1?'<a target="_blank" href='+$("#answerLink").val()+'>'+answerText+'</a>':answerText;
	PostData({
		url : "/public/api/question/addAnswer?id="+questionId+"&username="+username,
		data : JSON.stringify({text:teks,html:answerText})
	}, function(r){console.log(r);if(r.length==0) {alert("Username vec postoji, izaberi drugi...");return;}quest.answers=r;quest.answers.sort(compare);appendAnswers({answers:r});window.scrollTo(0,0);$("#answerText").val("");$("#answerLink").val("");}, function(r) {console.log(r);});
}
function compareDate(b,a) {
	  if (a.date < b.date)
	    return -1;
	  if (a.date > b.date)
	    return 1;
	  return 0;
	}
function compareUp(b,a) {
	  if (a.upVotes - a.downVotes < b.upVotes - b.downVotes)
	    return -1;
	  if (a.upVotes - a.downVotes> b.upVotes - b.downVotes)
	    return 1;
	  return 0;
	}
function compareDown(b,a) {
	  if (a.downVotes - a.upVotes < b.downVotes - b.upVotes)
	    return -1;
	  if (a.downVotes- a.upVotes > b.downVotes- b.upVotes)
	    return 1;
	  return 0;
	}
function shareToFb(id) {
	window.open("https://www.facebook.com/sharer/sharer.php?u="+escape("http://radiorolling.com/answer?id="+id)+"&t=Rolling Music", '', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=300');
}
function appendQuestions() {
	FetchData(
			{
				//todo: add playlist_type
				url : "/public/api/question/"
			},
			function(res) {
				for (var i = 0; i < res.length; i++) {
					$("#plists")
							.append(
									'<li><div class="media"><a class="media-left" href="/question?id='
											+ res[i].id
											+ '"> <img alt="" src="'
											+ res[i].image
											+ '"></a><div class="media-body"><a class="glyphicon glyphicon-menu-left" href="/question?id='
											+ res[i].id
											+ '">'
											+ res[i].name
											+ '</a></div></div></li>')
				}
			});
}

function formatDate(date) {
	var days = ['Nedelja', 'Ponedeljak', 'Utorak', 'Sreda', 'Četvrtak', 'Petak', 'Subota'];
	var months = ['januar','februar','mart','april','maj','jun','jul','avgust','septembar','oktobar','novembar','decembar']
	var d = new Date(date);
	var dayName = days[d.getDay()];
	var ttdate = dayName + " " + d.getDate() +". "+months[d.getMonth()] + " " +d.getFullYear()+". "+d.getHours() + ":"+d.getMinutes()+":"+d.getSeconds();
	return ttdate;
}