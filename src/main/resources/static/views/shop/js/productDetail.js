window.onload = () => {
    convertStr();
    document.getElementById("totalPrice").innerHTML = document.getElementById("price").innerText;
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
    }
}