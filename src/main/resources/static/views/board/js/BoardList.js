function SearchClear() {
    self.location ='/board/list'
}
function PageMove() {
    const target = event.target
    const num = target.getAttribute("data-num");
    const FormObj = document.querySelector("#BoardSetUp")
    FormObj.innerHTML += "<input type='hidden' name='page' value="+num+">"
    FormObj.submit();
}

function inqUp() {
    const target= event.target;
    const num = target.getAttribute("data-Bno");

    $.ajax({
        url: "/board/p/BoardInqUp",
        data : {bno:num},
        type : "get",
        success : () => {

        },error : () => {
            alert("실패")
        }
    })
}








