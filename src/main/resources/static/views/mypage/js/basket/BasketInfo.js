
window.onload = () => {
    const TotalList = document.getElementsByClassName("NodeValue")
    let result = 0;
    for (var i = 0; i < TotalList.length; i++) {
        result += parseInt(TotalList.item(i).innerText);
    }
    document.getElementById("general").innerText = result;
    $('#TotalPrice').val(result);

}
function countAdd  () {
    const t = event.target
    const num = t.getAttribute("data-plus");

    const span = document.getElementById("count"+num);
    let price = parseInt(document.getElementById("price"+num).innerText);
    let count = parseInt(span.innerText);
    span.innerHTML = count + 1;
    let total = parseInt(document.getElementById("general").innerText)
        document.getElementById("totalPrice"+num).innerHTML = price * (count + 1);
        $('#InputCount'+num).val(count+1);
    if(document.getElementById("box"+num).checked) {
        document.getElementById("general").innerHTML = total + price;
        $('#TotalPrice').val(total + price);
    }
}
function countSub (){
    const t = event.target
    const num = t.getAttribute("data-minus");

    const span = document.getElementById("count"+num);
    let price = parseInt(document.getElementById("price"+num).innerText);
    let count = parseInt(span.innerText);
    let total = parseInt(document.getElementById("general").innerText)

    if (count > 1  ) {
        span.innerHTML = count - 1;
        document.getElementById("totalPrice"+num).innerHTML = price * (count - 1);
        $('#InputCount'+num).val(count-1);
        if(document.getElementById("box"+num).checked) {
            document.getElementById("general").innerHTML = total - price;
            $('#TotalPrice').val(total - price);
        }
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

const AddBox = (e) => {
    const t = event.target
    const num = t.getAttribute("data-box");
    const span = document.getElementById("count"+num);

    let price = parseInt(document.getElementById("price"+num).innerText);
    let count = parseInt(span.innerText);
    let total = parseInt(document.getElementById("general").innerText)

    if(e.checked) {
        document.getElementById("general").innerText = total + price*count
        $('#TotalPrice').val(total + price*count);
    }else {
        document.getElementById("general").innerText = total - price*count
        $('#TotalPrice').val(total - price*count);
    }
}

async function ProductBuy() {
    const list = document.getElementsByClassName("ArrayPno");
    for(var i=0;i<list.length;i++) {
        setTimeout(function (i) {
            document.forms['CartForm'+list.item(i).value].submit();
        },i)
    }
}
