$(document).ready(function() {
            var ytkey = 'AIzaSyAohq-i1Ee_8ei5PLKyMkHYqX9KUDV9Gq8';
            addVideoToDiv({title:vidDescription,duration:vidDuration,id:videoUrl});
            //getVideoDetails("LrMLt9bMd_I");
            function getVideoDetails(id){
                $.ajax({
                    cache: false,
                    data: $.extend({
                        key: ytkey,
                        part: 'snippet,contentDetails,statistics'
                    }, {id: id}),
                    dataType: 'json',
                    type: 'GET',
                    timeout: 5000,
                    fields: "items(id,contentDetails,statistics,snippet(title))",
                    url: 'https://www.googleapis.com/youtube/v3/videos'
                })
                .done(function(data) {
					console.log("ytytyt");
					console.log(data);
					console.log("Title: "+data.items[0].snippet.title);
					console.log("Duration: "+YTDurationToSeconds(data.items[0].contentDetails.duration)+" milliseconds");
					console.log("ytytyt");
					addVideoToDiv({title:data.items[0].snippet.title,duration:YTDurationToSeconds(data.items[0].contentDetails.duration),id:data.items[0].id});
                });
            }

            function YTDurationToSeconds(duration) {
                var match = duration.match(/PT(\d+H)?(\d+M)?(\d+S)?/)

                var hours = ((parseInt(match[1]) || 0) !== 0)?parseInt(match[1]):0;
                var minutes = ((parseInt(match[2]) || 0) !== 0)?parseInt(match[2]):0;
                var seconds = ((parseInt(match[3]) || 0) !== 0)?parseInt(match[3]):0;
                var total = hours*60*60*1000 + minutes*60*1000 + seconds*1000;
                return total;
            }

              
        });

function addVideoToDiv(options) {
	$(".single_post_content").append('<iframe width="100%" height="100%" src="//www.youtube.com/embed/'+options.id+'&rel=0&amp;&autoplay=1&showinfo=0&controls=0" frameborder="0" allowfullscreen>')
	$(".song_title").text(options.title);
}