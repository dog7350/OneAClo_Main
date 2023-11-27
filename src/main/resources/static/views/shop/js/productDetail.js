window.onload = () => {
    convertStr();

    document.getElementById("totalPrice").innerHTML = document.getElementById("price").innerText;
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