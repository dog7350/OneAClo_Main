function CheckPassword() {
    const Origin = document.getElementById("InputPassword").value;
    const Change = document.getElementById("InputChangePw").value;
    const Ck = document.getElementById("InputChangePwCk").value;
    if(Origin === "" || Change === "" || Ck === "") {
        alert("비밀번호를 입력해주세요");
    }else if(Change !== Ck) {
        alert("새로운 비밀번호가 일치하지 않습니다");
    }else {
        $.ajax({
            url : "/PasswordCk",
            type : "get",
            data : {Origin:Origin,Change:Change,Ck:Ck},
            dataType : "text",
            success : (data) => {
                if(data === "success") {
                    document.forms['PasswordForm'].submit();
                    alert("비밀번호가 변경되었습니다 다시 로그인해주세요!!!");
                }else {
                    alert("잘못된 기존 비밀 번호입니다.")
                }
            },error : () => {    
                alert("실패")
            }
        })
    }


}

