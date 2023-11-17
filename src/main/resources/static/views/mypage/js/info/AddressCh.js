function daumPost() {
    new daum.Postcode({
        oncomplete: function(data) {
            const zipcode = document.getElementById("InputZipcode");
            const address = document.getElementById("InputAddress");
            const detail = document.getElementById("InputDetail");
            let addr;
            if(data.userSelectedType === 'R') {
                addr = data.roadAddress;
            }else {
                addr = data.jibunAddress;
            }
            zipcode.value = data.zonecode;
            address.value = addr;
            detail.focus();
        }
    }).open();
}

function AddrCk() {
    const zipcode = document.getElementById("InputZipcode").value;
    const address = document.getElementById("InputAddress").value;
    const detail = document.getElementById("InputDetail").value;
    if(zipcode === "" || address === "" || detail === "") {
        alert("주소를 잘못 입력하셨습니다.");
    }else {
        document.forms['NewAddrForm'].submit();
    }
}