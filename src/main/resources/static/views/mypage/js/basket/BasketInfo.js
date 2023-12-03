var general;
window.onload = () => {
    document.getElementById("general").innerHTML = document.getElementById("totalPrice").innerText;

}
function countAdd  () {
    const t = event.target
    const num = t.getAttribute("data-plus");

    const span = document.getElementById("count"+num);
    let price = parseInt(document.getElementById("price"+num).innerText);
    let count = parseInt(span.innerText);
    span.innerHTML = count + 1;

    document.getElementById("totalPrice"+num).innerHTML = price * (count + 1);
}

function countSub (){
    const t = event.target
    const num = t.getAttribute("data-minus");

    const span = document.getElementById("count"+num);
    let price = parseInt(document.getElementById("price"+num).innerText);
    let count = parseInt(span.innerText);

    if (count > 1) {
        span.innerHTML = count - 1;
        document.getElementById("totalPrice"+num).innerHTML = price * (count - 1);

    } else {
        alert("1개 이하로 주문할 수 없습니다.");
    }
}

function CookieDel () {
    const t = event.target
    const pno = t.getAttribute("data-index")
    $.ajax({
        url:"/mypage/p/CartDel",
        type:"get",
        data:{pno:pno},
        success : () => {
            alert("해당 목록이 삭제되었습니다.")
            setTimeout(function () {
                $('#Reset').load(location.href=" #Reset");
            },100)
        },error : () => {
            alert("문제발생")
        }
    })
}