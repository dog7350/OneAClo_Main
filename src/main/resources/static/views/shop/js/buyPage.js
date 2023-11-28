const orequal = (e) => {
    const orderName = document.getElementById("InputOrderName");
    const orderPhone = document.getElementById("InputOrderPhone");
    const orderEmail = document.getElementById("OrderEmail");
    const orderZipcode = document.getElementById("OrderZipCode");
    const orderAddress = document.getElementById("OrderAddress");
    const orderDetailAddr = document.getElementById("OrderDetailAddr");

    const recName = document.getElementById("InputName");
    const recPhone = document.getElementById("InputPhone");
    const recEmail = document.getElementById("InputEmail");
    const recZipcode = document.getElementById("InputZipcode");
    const recAddress = document.getElementById("InputAddress");
    const recDetailAddr = document.getElementById("InputDetailAddr");

    if (e.checked) {
        recName.value = orderName.value;
        recPhone.value = orderPhone.value;
        recEmail.value = orderEmail.value;
        recZipcode.value = orderZipcode.value;
        recAddress.value = orderAddress.value;
        recDetailAddr.value = orderDetailAddr.value;
    } else {
        recName.value = "";
        recPhone.value = "";
        recEmail.value = "";
        recZipcode.value = "";
        recAddress.value = "";
        recDetailAddr.value = "";
    }
}

const falseMsg = (msg) => {
    alert(msg);
    return false;
}

window.onload = () => {
    IMP = window.IMP;
    IMP.init("imp42717518");
}

const buyBtn = () => {
    const pname = document.getElementById("pname").innerText;
    const totalPrice = document.getElementById("InputTotalValue").value;
    const receiver = document.getElementById("InputName");
    const phone = document.getElementById("InputPhone");
    const email = document.getElementById("InputEmail");
    const zipcode = document.getElementById("InputZipcode");
    const address = document.getElementById("InputAddress");
    const detailAddr = document.getElementById("InputDetailAddr");

    if (receiver.value == "") return falseMsg("수령인을 입력하세요.");
    else if (phone.value == "") return falseMsg("수령자 전화번호를 입력하세요.");
    else if (email.value == "") return falseMsg("수령자 이메일을 입력하세요.");
    else if (zipcode.value == "" || address == "") return falseMsg("수령할 주소를 입력하세요.");

    IMP.request_pay({
        //pg : 'kakaopay.TC0ONETIME',

        //pg : 'kcp.AO09C',
        //pg: "mobilians.170622040674",
        pg : 'html5_inicis.INIpayTest',
        pay_method : "card",
        merchant_uid : "merchant_" + new Date().getTime(),
        name : pname,
        amount : totalPrice,
        buyer_email : email,
        buyer_name : receiver,
        buyer_tel : phone,
        buyer_addr : address + ", " + detailAddr,
        buyer_postcode : zipcode,
        m_redirect_url : 'http://www.iamport.kr/mobile/landing'
    }, function (res) {
        // 결제 검증
        $.ajax({
            type: "POST",
            url: "/verifyIamport/" + res.imp_uid
        }).done(function (data) {
            if (res.paid_amount == data.response.amount) {
                alert("결제 완료");
                document.getElementById("orderForm").submit();
            } else {
                alert("결제 실패");
            }
        });
    });
}