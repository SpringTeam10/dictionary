let keywordCheck;

function execSearch() {
    let category = $("#category").val()
    let keyword = $('#input-word').val();
    if(keywordCheck != keyword){
        $("#tbody-box").empty();
        keywordCheck = keyword
    }
    keywordCheck = keyword;
    if (keyword == '') {
        alert('검색어를 입력해주세요');
        $('#input-word').focus();
        return;
    }
    $.ajax({
        type: 'GET',
        traditional : true,
        url : `/search/wiki?keyword=${keyword}`,
        data : {'category' : category},
        success: function (response) {
            $(".more").show();
            for (let i = 0; i < response['data'].length; i++) {
                let item = response['data'][i];
                let tempHtml = addHTML(item);
                $("#tbody-box").append(tempHtml);
            }
        }
    });
}

function addHTML(item) {
    return `<tr class="word-word">
                    <td><img class="image" src=${item.image_url}></td>
                    <td><a href=${item.detail_url}>${item.keyword}</a></td>
                    <td>${item.contents}</td>
                </tr>`
}