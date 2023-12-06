let stompClient = null;

//window.addEventListener('load', () => { stompSocket(); })

const stompSocket = () => {
    let socket = new SockJS('/realtime');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => {
        let myId = frame.headers['user-name'];
        console.clear();

        stompClient.subscribe("/realSend/order/" + myId, (data) => {
            let div = document.getElementById("alarmList");

            let msg = data.body.split('"')[3];
            let id = msg.split("/*/")[0];
            let pno = msg.split("/*/")[1];
            let pname = msg.split("/*/")[2];

            let str = id + " 님께서 " + pname + " 상품을 구매하였습니다.";
            let url = "/admin/orderList?searchOption=pno&searchValue=" + pno;

            let node = document.createElement('div');
            node.className = "alarmNode";
            node.innerText = str;
            node.onclick = () => {
                location.href = url;
            }
            node.style.cursor = "pointer";
            div.appendChild(node);
        });
        stompClient.subscribe('/realSend/qna/' + myId, (data) => {
            let div = document.getElementById("alarmList");

            let id = data.body.split('"')[3];

            let str = id + " 님께서 Q & A를 남겼습니다.";
            let url = "/qna/chat?id=" + id;

            let node = document.createElement('div');
            node.className = "alarmNode";
            node.innerText = str;
            node.onclick = () => {
                location.href = url;
            }
            node.style.cursor = "pointer";
            div.appendChild(node);
        });
    })
}
const sendQnaAlarm = (id) => {
    if (id != 'admin') stompClient.send("/realReceive/qna", {}, JSON.stringify({'msg' : id}));
}
const sendOrderAlarm = (id, pno, pname) => {
    if (id != 'admin') stompClient.send("/realReceive/order", {}, JSON.stringify({'msg' : id + "/*/" + pno + "/*/" + pname}));
}