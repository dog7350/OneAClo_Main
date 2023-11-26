var data
var number
function BackList() {
    location.href="/board/list";
}

function reg() {
    const Content = document.getElementById("AreaContent").value;
    console.log(Content)
    if(Content === "") {
        alert("댓글을 입력해 주세요");
    }else {
        document.forms['CmtForm'].submit();
    }
}

function CmtModify() {

    const target = event.target
    const num = target.getAttribute("data-num");

    const Id = document.getElementById("UserId").value;
    const cno = document.getElementById("CmtCno"+num).value;
    const bno = document.getElementById("Bno").value;
    const content = document.getElementById(cno).value;

    data = $('#CmtContent'+num).hide();

    const change = document.querySelector("#CmtModify"+num);
    change.innerHTML = '<form action="/board/p/CmtModify" method="post" name="CmtModifyForm" id="ModifyForm">\n' +
        '<div class="input-group-text bg-body">\n' +
        '<input type="hidden" name="cno" value='+cno+'>\n' +
        '<input type="hidden" name="bno" value='+bno+'>\n' +
        '<input type="text" class="fs-4 border-0 bg-white FocusLine" name="writer" id="UserId" value='+Id+' readonly>\n' +
        '</div>\n' +
        '<div class="input-group">\n' +
        '<textarea id="ModifyContent" class="form-control col-sm-5" name="content" rows="5" maxlength="100" placeholder="댓글을 입력해주세요">'+content+'</textarea>\n' +
        '</div>\n' +
        '<button type="button" class="btn btn-secondary float-lg-end" onclick="CmtReset()">취소</button>\n' +
        '<button type="button" class="btn btn-secondary float-lg-end" onclick="CmtModifySave()">수정</button>\n' +
        '</form>';
}
function CmtModifySave() {
    const content = document.getElementById("ModifyContent").value;
    if(content === "") {
        alert("수정 내용을 입력해주세요");
    }else {
        document.forms['CmtModifyForm'].submit();
    }
}

function CmtReset() {
    $('#ModifyForm').remove();
    data.show();
}

function ModifyForm() {
    const bno = document.getElementById("Bno").value;
    location.href="/board/p/BoardModify?bno="+bno;
}

function BoardDel() {
    const modal = new bootstrap.Modal(document.querySelector("#BoardModal"));
    modal.show();
}

function ModalDel() {
    const bno = document.getElementById("Bno").value;
    location.href="/board/p/BoardDel?bno="+bno;
}

function CmtDel() {
    const modal = new bootstrap.Modal(document.querySelector("#CmtModal"));
    const target = event.target
    number = target.getAttribute("data-num2");
    modal.show();
}

function ModalCmtDel() {
    const bno = document.getElementById("Bno").value;
    const cno = document.getElementById("CmtCno"+number).value;
    location.href="/board/p/BoardCmtDel?cno="+cno+"&bno="+bno;
}