let oEditors = []

createEditor = () => {
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "mailContent",
        sSkinURI: "/common/smart/SmartEditor2Skin.html",
        htParams: {
            bUseVerticalResizer : false,
            bUseModeChanger : false
        },
        fCreator: "createSEditor2"
    });
}

submitForm = () => {
    oEditors.getById["mailContent"].exec("UPDATE_CONTENTS_FIELD", []);
    let title = document.getElementById("mailTitle").value;
    let content = document.getElementById("mailContent").value;

    if (title == "") {
        alert("제목을 입력하세요.");
    } else if(content == "<p>&nbsp;</p>") {
        alert("내용을 입력하세요.");
        oEditors.getById["content"].exec("FOCUS");
    } else {
        document.getElementById("mailForm").submit();
    }
}

window.onload = () => {
    createEditor();
}