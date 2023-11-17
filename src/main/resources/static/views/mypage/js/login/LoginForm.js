function login() {
    const userid = document.getElementById("InputId").value;
    const userpw = document.getElementById("InputPw").value;
    $.ajax({
        url : "/mypage/jwtcreate",
        data : {UserId : userid,UserPw : userpw},
        type : "post",
        dataType : "text",
        success : (data) => {
            document.forms['loginform'].submit();
            },error : () => {
            alert("실패!!!")
        }
    })

}

