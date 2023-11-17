function OutId() {
    const userid = document.getElementById("InputId").value;
    if(userid !== "") {
        $.ajax({
            url : "/mypage/IdCk",
            data : { id : userid},
            type: "get",
            dataType: "json",
            success : (bool) => {
                if(bool) {
                    document.getElementById("IdCk").innerHTML = "중복된 아이디 입니다.";
                    sessionStorage.removeItem("PassId");
                }else {
                    document.getElementById("IdCk").innerHTML = "사용 가능한 아이디 입니다.";
                    sessionStorage.setItem("PassId",userid);
                }
            },error : () => {
                alert("실패!!!");
            }
        });
    }else {
        alert("아이디를 입력해 주세요!!!")
    }
}


function daumPost() {
    new daum.Postcode({
        oncomplete: function(data) {
            const zipcode = document.getElementById("InputZipcode");
            const address = document.getElementById("InputAddress");
            const detail = document.getElementById("InputDetailAddr");
            let addr;
            if(data.userSelectedType === 'R') {
                addr = data.roadAddress;
            }else {
                addr = data.jibunAddress;
            }
            zipcode.value = data.zonecode;
            address.value = addr;
            detail.focus();
        }
    }).open();
}

function EmailCk() {
    const UserMail = document.getElementById("InputEmail").value;
    sessionStorage.setItem("EmailClick","1");
    if(!(UserMail === "")) {
        $.ajax({
            url : "/mypage/EmailGetList",
            data: {email:UserMail},
            type: "get",
            dataType: "json",
            success : (bool) => {
                if(bool) {
                    alert("가입 하신 이메일 주소 입니다!!!")
                }else {
                    window.open("/mypage/EmailCk?email="+UserMail,"pop","width=700,height=700,left=200,top=200");
                }
            },error : () => {
                alert("실패");
            }
        })
    }else {
        alert("이메일을 입력해 주세요!!!");
    }
}

function inputEmail (email) {
    document.getElementById("InputEmail").value = email;
    document.getElementById("InputEmail").readOnly=true;
}

function AgeRange() {
    const age = document.getElementById("InputAge").value
    if(age !== "") {
        if(age > 4 && age < 101) {
            document.getElementById("AgeCk").innerHTML = "가입 가능한 연령 입니다.";
            sessionStorage.setItem("Age",age);
        }else {
            document.getElementById("AgeCk").innerHTML = "가입이 불가능한 연령 입니다.";
            sessionStorage.removeItem("Age");
        }
    }else {
        alert("나이를 입력해 주세요");
    }
}

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

function JoinCkButton() {
    let list = {};
    let arr = $("#MembershipForm").serializeArray();
    arr.forEach(data => {
        list[data.name] = data.value;
    });

    if(list.id===""){
        alert("아이디를 입력해주세요");
        return false;
    }else if(sessionStorage.getItem("PassId")==null || sessionStorage.getItem("PassId")===undefined) {
        alert("중복된 아이디입니다. 다시입력해주세요");
        return false;
    }
    if(list.pw==="") {
        alert("비밀번호를 입력해주세요");
        return false;
    }
    if(list.name==="") {
        alert("이름을 입력해주세요");
        return false;
    }
    if(list.nick==="") {
        alert("별명을 입력해주세요");
        return false;
    }
    if(list.age==="") {
        alert("나이를 입력해주세요");
        return false;
    }else if(sessionStorage.getItem("Age")!==list.age || sessionStorage.getItem("Age")===undefined) {
        alert("나이가 정확하지 않습니다");
        return false;
    }
    if(list.zipcode==="" || list.address==="" || list.detailaddr==="") {
        alert("주소를 입력해주세요");
        return false;
    }
    if(list.phone==="" || list.phone.length < 11 || !list.phone.startsWith("010-")) {
        alert("전화번호를 입력해주세요");
        return false;
    }
    if(list.email==="") {
        alert("이메일을 입력해주세요");
        return false;
    }else if(localStorage.getItem("successEmail") !== list.email || sessionStorage.getItem("EmailClick")===undefined) {
        alert("이메일 인증을 해주세요!!!");
        return false;
    }
    if(list.gender==="") {
        alert("성별을 선택해주세요");
        return false;
    }
}



