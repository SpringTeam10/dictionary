let data;
let curScore;
let curOrder = 0;

function execSearch() {
    let keyword = $('#input-word').val();
    $("#tbody-box").empty();
    curOrder = 0;

    $.ajax({
        type: 'GET',
        traditional : true,
        url : `/search/kordict?keyword=${keyword}`,
        success: function (response) {
                data = response;
                curScore = data[0].score;
                getMoreDataByScore()
                $(".more").show();
        }
    });
}

function addHTML(item) {
    return `<tr class="word-word">
                    <td>${item.word}</td>
                    <td>${item.meaning}</td>
                    <td>${item.pronunciation}</td>
                    <td>${item.example}</td>
                    <td>${item.classification}</td>
                </tr>`
}
function getMoreDataByScore(){
    while(1){
        let item = data[curOrder];
        let itemScore = item.score;
        if (itemScore === curScore){
            let tempHtml = addHTML(item);
            $("#tbody-box").append(tempHtml);
            curOrder += 1;
        } else {
            curScore = itemScore
            break
        }
    }
}
