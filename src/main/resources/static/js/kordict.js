let korDictId = null;
let keywordCheck;
let checkList = [];

function execSearch() {
    let keyword = $('#input-word').val();
    if(keywordCheck != keyword){
        keywordCheck = keyword
        $("#tbody-box").empty();
        korDictId = null;
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
        url : `/search/kordict?keyword=${keyword}`,
        data : {'korDictId':korDictId,'checkId' : checkList},
        success: function (response) {
            $(".more").show();
            korDictId = response['data'].at(-1).id;
            checkList = [];
            for(i=0;i<response['data'].length;i++){
                checkList.push(response['data'][i].id);
            }

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
                    <td>${item.word}</td>
                    <td>${item.meaning}</td>
                    <td>${item.pronunciation}</td>
                    <td>${item.example}</td>
                    <td>${item.classification}</td>
                </tr>`
}