$(document).ready(function() {
	$(".song_title").text(dto.name);
	appendAnswers();
});

function like() {

}

function addAnswer() {
	PostData(
			{
				url : "http://radiorolling.com/public/api/question/addAnswer?id=1&username=MJ23",
				data : JSON.stringify({
					text : "Chuck Berry - Promised Land"
				})
			}, function(r) {
				console.log("Success")
			}, function(r) {
				console.log(r)
			});
}

function appendAnswers() {
	$(".single_post_content").children().remove();
	for (var i = 0; i < dto.answers.length; i++) {
		$(".single_post_content")
				.append(
						'<div class="row"> <div class="col-md-12"> <div class="panel panel-white post panel-shadow"> <div class="post-heading"> <div class="pull-left meta"> <div class="title h5"> <a href="#"><b>'
								+ dto.answers[i].visitor.username
								+ '</b></a> </div> <h6 class="text-muted time">'+formatDate(dto.answers[i].date)+'</h6> </div> </div> <div class="post-description"> <p>'
								+ dto.answers[i].text
								+ '</p> <div class="stats"> <a class="btn btn-default stat-item" href="#"> <i class="fa fa-thumbs-up icon"></i>'
								+ dto.answers[i].upVotes
								+ ' </a> <a class="btn btn-default stat-item" href="#"> <i class="fa fa-thumbs-down icon"></i>'
								+ dto.answers[i].downVotes
								+ ' </a> </div> </div> </div> </div> </div>');
	}
}

function formatDate(date) {
	var days = ['Nedelja', 'Ponedeljak', 'Utorak', 'Sreda', 'Četvrtak', 'Petak', 'Subota'];
	var months = ['januar','februar','mart','april','maj','jun','jul','avgust','septembar','oktobar','novembar','decembar']
	var d = new Date(date);
	var dayName = days[d.getDay()];
	var ttdate = dayName + " " + d.getDate() +". "+months[d.getMonth()] + " " +d.getFullYear()+". "+d.getHours() + ":"+d.getMinutes()+":"+d.getSeconds();
	return ttdate;
}