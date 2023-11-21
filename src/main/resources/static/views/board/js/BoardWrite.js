function readURL(input) {
    var file = input.files[0] //파일에 대한 정보
    if(file != "") {
        var reader = new FileReader();
        reader.readAsDataURL(file); // 읽고
        reader.onload = function(e) {
            $('#InputName').val(file.name)
        }
    }
}

function BoardCk() {
    const title = document.getElementById("InputTitle").value;
    const content = document.getElementById("InputContent").value;
    if(title !== "") {
        if(content === "") {
            alert("내용을 입력해 주세요");
            return false;
        }else {
            return true;
        }
    }else {
        alert("제목을 입력해 주세요");
        return false;
    }
}