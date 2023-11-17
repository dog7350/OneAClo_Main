function PhoneCh() {
    window.open("/mypage/p/PhoneCh","pop","width=700,height=500,left=950,top=500")
}

function NewNick() {
    window.open("/mypage/p/NickCh","pop","width=700,height=500,left=950,top=500")
}

function ProfileCh() {
    window.open("/mypage/p/ProfileCh","pop","width=900,height=600,left=950,top=500")
}

function MemberDel() {
    if (confirm("정말로 탈퇴하시겠습니까?") === true){
        alert("이용해 주셔셔 감사합니다.");
        location.href="/mypage/p/UserDel";
    }else{
        return;
    }
}