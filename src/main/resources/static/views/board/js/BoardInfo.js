function BackList() {
    history.back();
}

function reg() {
    const con = document.getElementsByName("content").value;
    if(con === "") {
        alert("댓글을 입력해 주세요")
    }else {
        document.forms['CmtForm'].submit();
    }

}