let socket;
let dateFormat;

window.onload = () => {
    socket = new WebSocket(`ws://43.202.160.36:8811`);

    dateFormat = (date) => {
        DAYS = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        return DAYS;
    }

    socket.onmessage = function(event) {
        event.data.text().then(msg => {
            if (window.location.pathname == "/qna/chat") {
                const mySite = document.getElementById("site").value;
                const myId = document.getElementById("id").value;

                const str = String(msg).split('|');
                if (mySite == str[0]) {
                    const wprofile = str[1];
                    const writer = str[2];
                    const ext = str[3];
                    let content = str[4];
                    const nick = content.split(':')[0];
                    content = content.replace(nick + ": ", "");

                    if (ext != "String") {
                        if (ext == "Image") content = `<img src="/upload/file/${content}" style="width: 300px; height: 300px;">`;
                        else if (ext == "Music") content = `<audio src="/upload/file/${content}" controls />`;
                        else if (ext == "Video") content = `<video src="/upload/file/${content}" controls />`;
                    }

                    div = ``;
                    if (writer == myId) { // 자신이 작성
                        div = `<div class="rightContent">
                                   <div class="rightInnerLDiv">
                                       <b>${nick}&nbsp;(${writer})</b><br>
                                       ${content}
                                   </div>
                                   <div class="rightInnerRDiv">
                                       <img src="/upload/profile/${wprofile}"><br>
                                       <span class="createTime">${dateFormat(new Date())}</span>
                                   </div>
                               </div>`;
                    } else { // 대상이 작성
                        div = `<div class="leftContent">
                                   <div class="leftInnerLDiv">
                                       <img src="/upload/profile/${wprofile}"><br>
                                       <span class="createTime">${dateFormat(new Date())}</span>
                                   </div>
                                   <div class="leftInnerRDiv">
                                       <b>(${writer})&nbsp;${nick}</b><br>
                                       ${content}
                                   </div>
                               </div>`;
                    }

                    $('#ChatLog').append(div);
                    $('#ChatLog').scrollTop($('#ChatLog')[0].scrollHeight);
                }
            }
        })
    }
}

window.onbeforeunload = () => {
    if (socket.readyState === socket.OPEN) socket.close();
}

const enterKey = () => { if (window.event.keyCode == 13) ChatSend(); }

const ChatSend = () => {
    if (socket.readyState === socket.OPEN) {
        const InputChat = document.getElementById("ChatTxt");

        const site = document.getElementById("site").value;
        const img = document.getElementById("img").value;
        const id = document.getElementById("id").value;
        const nick = document.getElementById("nick").value;
        const ext = "String";
        let content = InputChat.value;

        const str = `${site}|${img}|${id}|${ext}|${nick}: ${content}`;
        InputChat.value = "";

        socket.send(str);
    } else {
        console.log("채팅 서버에 연결되지 않았습니다.");
    }
}

const image = ['JPG', 'JPEG', 'PNG', 'GIF'];
const movie = ['MP4', 'AVI'];
const music = ['MP3', 'WAV'];

const ChatFileUpload = (e) => {
    const id = document.getElementById("id").value;
    var files = e.files[0];

    var formData = new FormData();
    formData.append("id", id);
    formData.append('files', files);

    let ext = files.name.split(".")[1].toUpperCase();
    if (image.indexOf(ext) != -1) ext = "Image";
    else if (movie.indexOf(ext) != -1) ext = "Video";
    else if (music.indexOf(ext) != -1) ext = "Music";
    else {
        alert("정상적인 확장자가 아닙니다.");
        e.value = "";
        return false;
    }

    $.ajax({
        type : "POST",
        enctype : "multipart/form-data",
        url : "/qna/fileUpload",
        data : formData,
        processData : false,
        contentType : false,
        cache : false,
        success : (data) => {
            e.value = "";

            const site = document.getElementById("site").value;
            const img = document.getElementById("img").value;
            const nick = document.getElementById("nick").value;

            const content = data;

            const str = `${site}|${img}|${id}|${ext}|${nick}: ${content}`;

            socket.send(str);

            location.reload();
        },
        error : (e) => {

        }
    })
}