function login() {
    const userid = document.getElementById("InputId").value;
    $.ajax({
        url : "/jwtcreate",
        data : {UserId : userid},
        type : "post",
        dataType : "text",
        success : (data) => {
            document.forms['loginform'].submit();
            },error : () => {
            alert("실패!!!")
        }
    })

}

