function NickCk() {
    document.forms['NickForm'].submit();
    setTimeout(function () {
        alert("별명이 성공적으로 변경되었습니다.");
        opener.location.replace("/mypage/UpdateSuccess");
        window.close();
    },10);
}