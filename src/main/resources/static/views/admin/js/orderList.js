const falseMsg = (msg) => {
    alert(msg);
    return false;
}

const statusBtn = (status, ono) => {
    let statusGroup = $('input[name=statusGroup]:checked').val();

    if (statusGroup == undefined) return falseMsg("상태를 선택하세요.");
    else if (status == statusGroup) return falseMsg("현재와 같은 상태입니다.");

    const data = {
        ono : ono,
        status : statusGroup
    }

    if (confirm("상태를 반영하시겠습니까?")) {
        fetch("/order/statusChange", {
            method: "POST",
            body: JSON.stringify(data),
            headers: {"Content-Type": "Application/JSON"}
        }).then(function (res) {
            location.reload();
        });
    }
    else alert("취소");
}

const UserStatusChange = (ono, status) => {
    if (confirm("상태를 반영하시겠습니까?")) {
        location.href='/order/userStatus?ono=' + ono + "&status=" + status;
    } else alert("취소");
}

const cancelPay = (ouid, price) => {
    if (confirm("환불하시겠습니까?")) {
        const msg = prompt("사유를 적어주십시오.");

        if (msg != "") {
            location.href="/cancelPay?uid=" + ouid + "&price=" + price + "&msg=" + msg;
        } else alert("취소");
    } else alert("취소");
}