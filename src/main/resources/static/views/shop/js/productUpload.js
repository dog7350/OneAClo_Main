let oEditors = []

createEditor = () => {
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "productContent",
        sSkinURI: "/common/smart/SmartEditor2Skin.html",
        htParams: {
            bUseVerticalResizer : false,
            bUseModeChanger : false
        },
        fCreator: "createSEditor2"
    });
}

const bChanged = () => {
    const bC = document.getElementById("bC");
    const mC = document.getElementById("mC");
    const sC = document.getElementById("sC");

    if (bC.value != "#") {
        mC.disabled = false;
        $("#mC option").remove();
        mC.options.add(addOption("========================================", "#"));

        if (bC.value == "상의") {
            mC.options.add(addOption("니트/스웨터", "니트/스웨터"));
            mC.options.add(addOption("후드", "후드"));
            mC.options.add(addOption("긴소매", "긴소매"));
            mC.options.add(addOption("반소매", "반소매"));
        } else if (bC.value == "하의") {
            mC.options.add(addOption("팬츠", "팬츠"));
            mC.options.add(addOption("슬랙스", "슬랙스"));
            mC.options.add(addOption("레깅스", "레깅스"));
        } else if (bC.value == "신발") {
            mC.options.add(addOption("구두", "구두"));
            mC.options.add(addOption("운동화", "운동화"));
            mC.options.add(addOption("샌들", "샌들"));
            mC.options.add(addOption("슬리퍼", "슬리퍼"));
        } else if (bC.value == "아우터") {
            mC.options.add(addOption("고어텍스자켓", "고어텍스자켓"));
            mC.options.add(addOption("패딩자켓", "패딩자켓"));
        } else if (bC.value == "원피스") {
            mC.options.add(addOption("원피스", "원피스"));
        } else if (bC.value == "스커트") {
            mC.options.add(addOption("스커트", "스커트"));
        }
    } else {
        mC.options[0].selected = true;
        mC.disabled = true;
        sC.options[0].selected = true;
        sC.disabled = true;
    }
}

const addOption = (text, value) => {
    const option = document.createElement("option");
    option.text = text;
    option.value = value;

    return option;
}

const mChanged = () => {
    const mC = document.getElementById("mC");
    const sC = document.getElementById("sC");

    if (mC.value != "#") {
        sC.disabled = false;
    } else {
        sC.options[0].selected = true;
        sC.disabled = true;
    }
}

const submitProduct = () => {
    oEditors.getById["productContent"].exec("UPDATE_CONTENTS_FIELD", []);
    const title = document.getElementById("productTitle").value;
    const content = document.getElementById("productContent").value;
    const bC = document.getElementById("bC").value;
    const mC = document.getElementById("mC").value;
    const sC = document.getElementById("sC").value;
    const price = document.getElementById("price").value;
    const img = document.getElementById("productImg").value;

    if (title == "") {
        alert("제목을 입력하세요.");
    } else if (content == "<p>&nbsp;</p>") {
        alert("내용을 입력하세요.");
        oEditors.getById["content"].exec("FOCUS");
    } else if (bC == "#") {
        alert("대분류를 선택하세요.");
    } else if (mC == "#") {
        alert("중분류를 선택하세요.");
    } else if (sC == "#") {
        alert("소분류를 선택하세요.");
    } else if (price == "" || price < 100) {
        alert("가격을 입력하세요. (최소 100 원 이상)");
    } else if (img == "") {
        alert("상품 대표 이미지를 등록하세요.");
    } else {
        document.getElementById("productForm").submit();
    }
}

window.onload = () => {
    createEditor();
}