function readURL(input) {
    var file = input.files[0] //파일에 대한 정보
    if(file != "") {
        var reader = new FileReader();
        reader.readAsDataURL(file); // 읽고
        reader.onload = function(e) {
            $('#preview').attr('src',e.target.result);
        }
    }
}
function FileCk() {
    document.forms['FileForm'].submit();
    setTimeout(function () {
        alert("프로필이 성공적으로 변경되었습니다.");
        opener.location.replace("/UpdateSuccess");
        window.close();
    },10);
}

function DelPic() {
    $("#FileChange").val("");
    $("#preview").attr('src',"");
}