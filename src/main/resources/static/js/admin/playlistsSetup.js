$(document).ready(function () {
    appendAllPls();
});

function appendAllPls() {
    FetchData({
            url: "/public/api/playlist/user"
        }, function (response) {
            var list = [];
            for (var i = 0; i < response.length; i++) {
                var className = "btn-default";
                $("body").prepend("<a href='/admin/setupp?id=" + response[i].id + "'><div class='btn " + className + "'>" + response[i].name + "</div></a>");
            }
        },
        function (response) {
            alert("ERROR");
            console.log(response);
        });
}
