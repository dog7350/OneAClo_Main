let dataLabels;
let dataDatasets;

const memberAG = () => {
    fetch("http://43.202.160.36:8810/member/ageGender", {
        method: "GET"
    }).then((res) => {
        console.log(res);
    });
}

const memberAddress = () => {
    fetch("http://43.202.160.36:8810/member/address", {
        method: "GET"
    }).then((res) => {
        console.log(res);
    });
}

const dataInquiry = () => {

}

const dataOrder = () => {

}

const dataAnalysis = () =>{

}