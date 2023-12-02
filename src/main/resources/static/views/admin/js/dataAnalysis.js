let dataLabels;
let dataDatasets;

const url = "http://43.202.160.36:8810";

const colors = ['#FF0000', '#FF8C00', '#FFFF00', '#008000', '#0000FF', '#4B0082', '#800080']

const memberAG = () => {
    fetch(url + "/member/agegender", {
        method: "GET"
    }).then((res) => res.json()).then((json) => {
        canvasClear();
        removeBtn();

        let label = json.label;
        let legend = json.legend;
        let data = json.data;

        dataLabels = label;
        dataDatasets = [];

        for(i = 0; i < legend.length; i++) {
            tmp = { data:data[legend[i]], label:legend[i], borderColor: colors[i % 7], fill: false };
            dataDatasets.push(tmp);
        }

        new Chart(document.getElementById("ChartDraw"), {
            type: 'line',
            data: {
                labels: dataLabels,
                datasets: dataDatasets
            }, 
            options: {
                title: {
                    display: true,
                    text: "연령별 성별 추이",
                    fontSize: 15
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            display: true,
                            min: 0,
                            stepSize : 10
                        }
                    }]
                },
                legend: {
                    position: 'top',
                    labels: {
                        fontSize: 12
                    }
                }
            }
        })
    }).catch(error => { console.log("Error : ", error) });
}

const memberAddress = (type) => {
    fetch(url + "/member/address", {
        method: "GET"
    }).then((res) => res.json()).then((json) => {
        canvasClear();
        removeBtn();
        createBtn();

        let label = json.label;
        let data = json.data;

        dataLabels = label;

        let col = []
        for (i = 0; i < label.length; i++) col.push(colors[i % 7])

        dataDatasets = [{
            label : '회원 지역 분포',
            backgroundColor : col,
            data : data['count']
        }];

        let options;
        if (type == undefined) type = 'bar';

        if (type == 'pie' || type =='doughnut') {
            options = {
                title: {
                    display: true,
                    text: "회원 지역 분포도",
                    fontSize: 15
                },
                legend: {
                    position: 'top',
                    labels: {
                        fontSize: 12
                    }
                }
            }
        } else {
            options = {
                title: {
                    display: true,
                    text: "회원 지역 분포도",
                    fontSize: 15
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            display: true,
                            min: 0,
                            stepSize : 10
                        }
                    }]
                },
                legend: {
                    position: 'top',
                    labels: {
                        fontSize: 12
                    }
                }
            }
        }

        new Chart(document.getElementById("ChartDraw"), {
            type: type,
            data: {
                labels: dataLabels,
                datasets: dataDatasets
            },
            options: options
        })
    }).catch(error => { console.log("Error : ", error) });
}

const inquiryChart = () => {
    let queryData = inputDataInit();

    fetch(url + "/chart/inquiry", {
        method: "POST",
        headers: {'Content-Type' : 'Application/JSON'},
        body: JSON.stringify(queryData)
    }).then((res) => res.json()).then((json) => {
        canvasClear();
        removeBtn();

        let age = "";
        if (queryData.age != "") age = queryData.age + " 대 ";
        let year = "";
        if (queryData.year != "") year = queryData.year + " 년도 ";
        let month = "";
        if (queryData.month != "") month = queryData.month + " 월 ";

        let label = json.label;
        let legend = json.legend;
        let data = json.data;

        dataLabels = label;
        dataDatasets = [];

        for(i = 0; i < legend.length; i++) {
            tmp = { data:data[legend[i]], label:legend[i], borderColor: colors[i % 7], fill: false };
            dataDatasets.push(tmp);
        }

        new Chart(document.getElementById("ChartDraw"), {
            type: 'line',
            data: {
                labels: dataLabels,
                datasets: dataDatasets
            },
            options: {
                title: {
                    display: true,
                    text: age + year + month + " 관심많은 상품 카테고리",
                    fontSize: 15
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            display: true,
                            min: 0,
                            stepSize : 10
                        }
                    }]
                },
                legend: {
                    position: 'top',
                    labels: {
                        fontSize: 12
                    }
                }
            }
        })
    }).catch(error => { console.log("Error : ", error) });
}

const orderChart = () => {
    let queryData = inputDataInit();

    fetch(url + "/chart/order", {
        method: "POST",
        headers: {'Content-Type' : 'Application/JSON'},
        body: JSON.stringify(queryData)
    }).then((res) => res.json()).then((json) => {
        canvasClear();
        removeBtn();

        console.log(json);
    }).catch(error => { console.log("Error : ", error) });
}

const dataAnalysis = () =>{
    fetch(url + "/data/analysis", {
        method: "POST"
    }).then((res) => res.json()).then((json) => {
        console.log(json);
    }).catch(error => { console.log("Error : ", error) });
}

const chartDataRemove = () => {
    document.getElementById("inputAge").value = "";
    document.getElementById("inputGender").value = "";
    document.getElementById("inputYear").value = "";
    document.getElementById("inputMonth").value = "";
}

const inputDataInit = () => {
    let age = document.getElementById("inputAge").value;
    let gender = document.getElementById("inputGender").value;
    let year = document.getElementById("inputYear").value;
    let month = document.getElementById("inputMonth").value;

    if (age != "") {
        if (age < 20) age = "10";
        else if (age < 30) age = "20";
        else if (age < 40) age = "30";
        else if (age < 50) age = "40";
        else if (age < 60) age = "50";
        else if (age < 70) age = "60";
        else if (age < 80) age = "70";
        else if (age < 90) age = "80";
        else if (age < 100) age = "90";
        else age = "100";
    }

    if (gender != "") {
        if (gender == "남" || gender == "남성" || gender == "남자") gender = "male";
        else if (gender == "여" || gender == "여성" || gender == "여자") gender = "female";
    }

    if (month != "") {
        if (month < 1) month = 1;
        else if (month > 12) month = 12;
    }

    return {'age' : age, 'gender' : gender, 'year' : year, 'month' : month}
}

const canvasClear = () => {
    document.getElementById("ChartDraw").remove();
    let div = document.getElementById('ChartDiv');
    let chart = document.createElement('canvas');
    chart.id = 'ChartDraw';
    div.appendChild(chart)
}

const createBtn = () => {
    let div = document.getElementById('ChartBtns');

    let bar = document.createElement('input');
    bar.className = 'btn btn-outline-secondary';
    bar.type = 'button';
    bar.value = '막대';
    bar.addEventListener('click', () => memberAddress('bar'));
    div.appendChild(bar);

    let pie = document.createElement('input');
    pie.className = 'btn btn-outline-secondary';
    pie.type = 'button';
    pie.value = '원형';
    pie.addEventListener('click', () => memberAddress('pie'));
    div.appendChild(pie);

    let polarArea = document.createElement('input');
    polarArea.className = 'btn btn-outline-secondary';
    polarArea.type = 'button';
    polarArea.value = '폴라';
    polarArea.addEventListener('click', () => memberAddress('polarArea'));
    div.appendChild(polarArea);

    let doughnut = document.createElement('input');
    doughnut.className = 'btn btn-outline-secondary';
    doughnut.type = 'button';
    doughnut.value = '도넛';
    doughnut.addEventListener('click', () => memberAddress('doughnut'));
    div.appendChild(doughnut);
}

const removeBtn = () => {
    let div = document.getElementById('ChartBtns');
    while (div.hasChildNodes()) div.removeChild(div.firstChild);
}