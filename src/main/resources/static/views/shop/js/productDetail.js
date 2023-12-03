window.onload = () => {
    convertStr();
    document.getElementById("totalPrice").innerHTML = document.getElementById("price").innerText;
    const url = new URL(window.location);
    const urlParams = url.searchParams;
    var location = document.querySelector('#review').offsetTop;
    if(urlParams.get('page')) {
        scrollTo({top:location});
    }
}
const buy = (pno) => {
    const price = document.getElementById("price").innerText;
    const count = document.getElementById("count").innerText;

    if (confirm("구매하시겠습니까?")) {
        location.href="/order/buyPage?pno=" + pno + "&count=" + count;
    } else {
        alert("취소");
    }
}

const deleteProduct = (pno) => {
    if (confirm("삭제하시겠습니까?")) {
        location.href="/admin/productDelete?pno=" + pno;
    } else {
        alert("취소");
    }
}

const countAdd = () => {
    const span = document.getElementById("count");
    let price = parseInt(document.getElementById("price").innerText);
    let count = parseInt(span.innerText);
    span.innerHTML = count + 1;

    document.getElementById("totalPrice").innerHTML = price * (count + 1);
}

const countSub = () => {
    const span = document.getElementById("count");
    let price = parseInt(document.getElementById("price").innerText);
    let count = parseInt(span.innerText);
    if (count > 1) {
        span.innerHTML = count - 1;
        document.getElementById("totalPrice").innerHTML = price * (count - 1);
    } else {
        alert("1개 이하로 주문할 수 없습니다.");
    }
}

const convertStr = () => {
    let str = document.getElementById("contentDiv").innerText;

    str = str.replaceAll("&lt;", "<");
    str = str.replaceAll("&gt;", ">");

    str = str.replaceAll("\"", "");
    str = str.replaceAll("&quot;", "");
    str = str.replaceAll("&#34;", "");
    str = str.replaceAll("le=", "le=\"");
    str = str.replaceAll(";>", ";\">");

    document.getElementById("contentDiv").innerHTML = str;
}

function ReviewOn() {
    document.getElementById("ReviewCon").classList.remove('visually-hidden');
}
function ReviewOff() {
    document.getElementById("ReviewCon").classList.add('visually-hidden');
}

function ReviewSave() {
    const content = document.getElementById("InputContent").value;
    if(content === "") {
        alert("내용을 입력해 주세요")
    }else {
        document.forms['ReviewForm'].submit();
        setTimeout(function () {
            $('#CmtList').load(location.href=" #CmtList");
        },100)
    }
}
function PageMove() {
    const target = event.target
    const num = target.getAttribute("data-num");
    const FormObj = document.querySelector("#CmtSetUp")
    FormObj.innerHTML += "<input type='hidden' name='page' value="+num+">"
    FormObj.submit();
}

function CmtDel() {
    const pno = document.getElementById("PNO").value;
    const target = event.target
    const cno = target.getAttribute("data-num");
    if (confirm("삭제하시겠습니까?")) {
        $.ajax({
            url:"/shop/CmtDel",
            type:"get",
            data:{pno:pno,cno:cno},
            error : () => {
                alert("문제발생")
            }
        })
        setTimeout(function (){
            $('#CmtList').load(location.href+' #CmtList');
        },100)
    } else {
        alert("취소");
    }
}
var data;
function CmtModify() {
    const pno = document.getElementById("PNO").value;
    const target = event.target
    const cno = target.getAttribute("data-num");
    const content = document.getElementById("content"+cno).value;
    data = $('#Origin'+cno).hide();
    const change = document.querySelector("#CmtModify" + cno);
    change.innerHTML = '<form class="ms-4" action="/shop/CmtModify" method="post" id="CmtModify" name="ModifyForm" target="ModifyTarget">\n' +
        '<div class="form-control">\n' +
        '<br>\n' +
        '<hr>\n' +
        '<br>\n' +
        '<input type="hidden" name="cno" value='+ cno +'>\n' +
        '<input type="hidden" name="pno" value=' + pno + '>\n' +
        '<div>\n' +
        '<textarea class="input-group" name="ccontent" rows="5" id="ModifyContent" maxlength="100" placeholder="댓글을 입력해주세요">'+content+'</textarea>\n' +
        '</div>\n' +
        '<label class="form-check-label" for="secretCk">비밀글</label>\n' +
        '<input class="form-check-input" type="checkbox" id="secretCk" name="secret" value="s">\n' +
        '<button type="button" class="btn btn-secondary float-lg-end" th:id="Cancel+${list.cno}" onclick="CommentCancel()">취소</button>\n' +
        '<button type="button" class="btn btn-secondary float-lg-end" onclick="CommentSave()">등록</button>\n' +
        '</form>\n' +
        '<br>\n' +
        '<hr>\n' +
        '<br>\n' +
        '</div>';
}
function CommentCancel() {
    setTimeout(function () {
        $('#CmtList').load(location.href=" #CmtList");
    },100)
}

function CommentSave() {
    const content = document.getElementById("ModifyContent").value;
    if(content === "") {
        alert("수정 내용을 입력해주세요");
    }else {
        document.forms['ModifyForm'].submit();
        setTimeout(function (){
            $('#CmtList').load(location.href+' #CmtList');
        },100)
    }
}

function BasketSend() {
    const pno = document.getElementById("PNO").value;
    const price = document.getElementById("price").innerText;
    const count = document.getElementById("count").innerText;

    $.ajax({
        url:"/mypage/p/AddBasket",
        type: "get",
        data:{pno:pno,price:price,count:count},
        success : () => {
            alert("장바구니에 추가 되었습니다.")
        },
        error : () => {
            alert("문제발생")
        }
    })
}