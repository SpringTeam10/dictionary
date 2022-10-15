let data;
let curScore;
let curOrder = 0;

function execSearch() {
    $("#tbody-box").empty();
    curOrder = 0;
    let category = $("#category option:selected").val()
    let keyword = $('#input-word').val();

    if (keyword === '') {
        alert('검색어를 입력해주세요');
        $('#input-word').focus();
        return;
    }

    $.ajax({
        type: 'GET',
        traditional : true,
        url : `/search/wiki?keyword=${keyword}&category=${category}`,
        success: function (response) {
            $(".more").show();
            data = response['data'];
            curScore = data[0].score;

            getMoreDataByScore()
        }
    });
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

function addHTML(item) {
    return `<tr class="word-word">
                    <td><img class="image" referrerpolicy="no-referrer" src=${item.img_url}></td>
                    <td><a href=${item.detail_url}>${item.keyword}</a></td>
                    <td>${item.contents}</td>
                    <td>${item.classification}</td>
                </tr>`
}