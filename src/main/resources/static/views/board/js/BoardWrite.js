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
    const modal = new bootstrap.Modal(document.querySelector(".modal"));

    if(title !== "") {
        if(content === "") {
            alert("내용을 입력해주세요");
        }else {
            modal.show();
        }
    }else {
        alert("제목을 입력해주세요");
    }
}

function ModalSave() {
    document.forms['SaveForm'].submit();
}