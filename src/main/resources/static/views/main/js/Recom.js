const url = "http://43.202.160.36:8810";

window.onload = () => {
    RecomExists();
}

const RecomExists = () => {
    let recom = document.getElementById("recomInput").value;

    if (recom == '') RecomAiStart();
    else PrintRecom(recom);
}

const RecomAiStart = () => {
    const id = document.getElementById("recomId").value;
    const age = document.getElementById("recomAge").value;
    const gender = document.getElementById("recomGender").value;

    const data = {"id" : id, "age" : age, "gender" : gender};
    fetch(url + "/data/recom", {
        method: "POST",
        headers: {'Content-Type' : 'Application/JSON'},
        body: JSON.stringify(data)
    }).then((res) => res.json()).then((json) => {
        PrintRecom(json.category);
    }).catch(error => { console.log("Error : ", error) });
}

const PrintRecom = (category) => {
    fetch("/recomCreate?category=" + category, {
        method: "GET"
    }).then((res) => res.json()).then((json) => {

    }).catch(error => { console.log("Error : ", error) });
}