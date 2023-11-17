function PhoneCh() {
    window.open("/PhoneCh","pop","width=700,height=500,left=950,top=500")
}

function NewNick() {
    window.open("/NickCh","pop","width=700,height=500,left=950,top=500")
}

function ProfileCh() {
    window.open("/ProfileCh","pop","width=900,height=600,left=950,top=500")
}

function MemberDel() {
    if (confirm("정말로 탈퇴하시겠습니까?") === true){
        alert("이용해 주셔셔 감사합니다.");
        location.href="/UserDel";
    }else{
        return;
    }
}