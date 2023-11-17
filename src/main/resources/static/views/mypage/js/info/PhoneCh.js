function FormSubmit() {
    const NewPhone = document.getElementById("InputPhone").value;
    if(NewPhone == null || NewPhone.length < 11 || !NewPhone.startsWith("010-")) {
        alert("전화 번호 양식에 맞지 않습니다.");
    }else {
        document.forms['PhoneForm'].submit();
        setTimeout(function () {
            alert("전화 번호가 성공적으로 변경되었습니다.");
            opener.location.replace("/UpdateSuccess");
            window.close();
        },10);
    }
}