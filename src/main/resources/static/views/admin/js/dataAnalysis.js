let dataLabels;
let dataDatasets;

//const url = "http://43.202.160.36:8810";
const url = "http://localhost:8810";

const memberAG = () => {
    fetch(url + "/member/ageGender", {
        method: "GET"
    }).then((res) => res.json()).then((json) => {
        console.log(json);
    });
}

const memberAddress = () => {
    fetch(url + "/member/address", {
        method: "GET"
    }).then((res) => res.json()).then((json) => {
        console.log(json);
    });
}

const inquiryChart = () => {
    fetch(url + "/chart/inquiry", {
        method: "GET"
    }).then((res) => res.json()).then((json) => {
        console.log(json);
    });
}

const orderChart = () => {
    fetch(url + "/chart/order", {
        method: "GET"
    }).then((res) => res.json()).then((json) => {
        console.log(json);
    });
}

const dataAnalysis = () =>{
    fetch(url + "/data/analysis", {
        method: "GET"
    }).then((res) => res.json()).then((json) => {
        console.log(json);
    });
}