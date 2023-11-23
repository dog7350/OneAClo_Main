function login() {
    const userid = document.getElementById("InputId").value;
    const userpw = document.getElementById("InputPw").value;
    if(userid !=="") {
        if(userpw === "") {
            alert("비밀번호를 입력해주세요")
        }else {
            document.forms['loginform'].submit();
        }
    }else {
        alert("아이디를 입력해주세요");
    }

}

