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
    change.innerHTML = '<form action="/board/p/CmtModify" method="post" name="CmtModifyForm" id="ModifyForm" target="CmtModifyTarget">\n' +
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
    focus();
}
function CmtModifySave() {
    const content = document.getElementById("ModifyContent").value;
    if(content === "") {
        alert("수정 내용을 입력해주세요");
    }else {
        document.forms['CmtModifyForm'].submit();
        setTimeout(function (){
            $('#CmtBody').load(location.href+' #CmtBody');
        },10)
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

function BoardReport() {
    const bno = document.getElementById("Bno").value;

    $.ajax({
        url: "/board/p/BoardReport",
        type: "get",
        data: {bno:bno},
        success:()=> {
            alert("신고가 접수 되었습니다!!!")
        },error:()=> {
            alert("문제 발생");
        }
    });
}

function BoardReply() {
    const bno = document.getElementById("Bno").value;
    location.href="/board/p/BoardReply?bno="+bno;
}

function CmtComment() {
    const target = event.target
    const num = target.getAttribute("data-num");

    const Id = document.getElementById("CmtWriter"+num).value;
    const bno = document.getElementById("Bno").value;
    const cno = document.getElementById("CmtCno"+num).value;
    const step = document.getElementById("CmtStep"+num).value;
    const cnogroup = document.getElementById("CmtGroup"+num).value;

    const change = document.querySelector("#CmtComment" + num);
    change.innerHTML = '<form class="ms-4" action="/board/p/CmtComment" method="post" name="CCmtForm" id="CommentForm" target="CmtTarget">\n' +
        '<div class="input-group-text bg-body">\n' +
        '<input type="hidden" name="cno" value='+ cno +'>\n' +
        '<input type="hidden" name="bno" value=' + bno + '>\n' +
        '<input type="hidden" name="step" value=' + step + '>\n' +
        '<input type="hidden" name="cnogroup" value=' + cnogroup + '>\n' +
        '<input type="text" class="fs-4 border-0 bg-white FocusLine" name="writer" id="UserId" value=' + Id + ' readonly>\n' +
        '</div>\n' +
        '<div class="input-group">\n' +
        '<textarea id="CCmtContent" class="form-control col-sm-5" name="content" rows="5" maxlength="100" placeholder="댓글을 입력해주세요"></textarea>\n' +
        '</div>\n' +
        '<button type="button" class="btn btn-secondary float-lg-end" onclick="CommentCancel()">취소</button>\n' +
        '<button type="button" class="btn btn-secondary float-lg-end" onclick="CommentSave()">작성</button>\n' +
        '</form>';
    }
function CommentCancel() {
    $('#CommentForm').remove();
}
function CommentSave() {
   const content = document.getElementById("CCmtContent").value;
    if(content === "") {
        alert("내용을 입력해주세요");
    }else {
        document.forms['CCmtForm'].submit();
        setTimeout(function (){
            $('#CmtBody').load(location.href+' #CmtBody');
        },10)
    }
}

