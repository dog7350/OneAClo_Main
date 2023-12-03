let dataLabels;
let dataDatasets;
let dataFalg = 'f';
let queryData;

const url = "http://43.202.160.36:8810";

const colors = ['#FF0000', '#FF8C00', '#FFFF00', '#008000', '#0000FF', '#4B0082', '#800080']

const memberAG = () => {
    dataFalg = 'f';

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
    dataFalg = 'f';

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
    queryData = inputDataInit();
    dataFalg = 'i';

    fetch(url + "/chart/inquiry", {
        method: "POST",
        headers: {'Content-Type' : 'Application/JSON'},
        body: JSON.stringify(queryData)
    }).then((res) => res.json()).then((json) => {
        canvasClear();
        removeBtn();

        let str = textFixd();

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
                    text: str['year'] + str['month'] + str['age'] + " 관심 많은 상품 카테고리",
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
    queryData = inputDataInit();
    dataFalg = 'o';

    fetch(url + "/chart/order", {
        method: "POST",
        headers: {'Content-Type' : 'Application/JSON'},
        body: JSON.stringify(queryData)
    }).then((res) => res.json()).then((json) => {
        canvasClear();
        removeBtn();

        let str = textFixd();

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
                    text: str['year'] + str['month'] + str['age'] + " 많이 팔린 상품 카테고리",
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

const dataAnalysis = () =>{
    if (!dataInputFlag()) {
        alert("먼저 차트 분석을 진행해주세요.");
        return false;
    }
    if (queryData.gender == "") {
        alert("성별 입력 필수!\n성별을 입력한 후 차트 분석을 진행해주세요.")
        return false;
    }

    queryData['flag'] = dataFalg;
    fetch(url + "/data/analysis", {
        method: "POST",
        headers: {'Content-Type' : 'Application/JSON'},
        body: JSON.stringify(queryData)
    }).then((res) => res.json()).then((json) => {
        let category = json.category;
        let age = json.age;
        let resultDiv = document.getElementById("AnalysisReulstDiv");

        if (category != "데이터가 너무 적습니다") {
            let str = textFixd();
            if (str['age'] == "") str['age'] = "회원 평균 연령(" + age + ")대 ";
            if (queryData.gender == 'male') str['gender'] = "남성";
            else str['gender'] = "여성";
            if (str['year'] == "") str['year'] = new Date().getFullYear() + " 년도 ";
            if (str['month'] == "") str['month'] = (new Date().getMonth() + 1) + " 월 ";
            if (dataFalg == 'i') str['cate'] = "가장 관심있는 상품 분석 AI 추천 카테고리 : ";
            else if (dataFalg == 'o') str['cate'] = "가장 많이 팔린 상품 분석 AI 추천 카테고리 : ";
            resultDiv.innerText = str['year'] + str['month'] + "기준, " + str['age'] + str['gender'] + "\n" + str['cate'] + category;

            crawling(age, str['gender'], category);
        } else resultDiv.innerText = category;
    }).catch(error => { console.log("Error : ", error) });
}

const crawling = (age, gender, category) => {
    let useragent = $('input[name=useragent]:checked').val();

    let data = {"age" : age.toString(), "gender" : gender, "category" : category, "useragent" : useragent}
    const crawlingFrame = document.getElementById("CrawlingResultFrame");
    crawlingFrame.innerHTML = "분석중.....\n화면을 끄지 말아주세요.";

    fetch(url + "/data/crawling", {
        method: "POST",
        headers: {'Content-Type' : 'Application/JSON'},
        body: JSON.stringify(data)
    }).then((res) => res.json()).then((json) => {
        const data = json;

        if (data['code'] != undefined) {
            crawlingFrame.innerText = `에러 코드 : ${data['code']}
                                        헤더 : ${data['headers']}
                                        URL : ${data['url']}`;
        } else {
            list = data['list']

            crawlingFrame.innerHTML = "";
            for (i = 0; i < list.length; i++) {
                if (i % 6 == 0) crawlingFrame.appendChild(document.createElement("br"));

                let child = stringToHtml(list[i]);
                let node = imgNodeFunc(child);

                crawlingFrame.appendChild(node);
            }
        }
    }).catch(error => { console.log("Error : ", error) });
}

const stringToHtml = (str) => {
    let dom = document.createElement('div');
    dom.innerHTML = str;
    return dom;
}

const imgNodeFunc = (child) => {
    let node = child.lastChild.lastChild;

    let div = document.createElement('div')
    div.className = "crawlingNode";

    let img = document.createElement('img')
    img.className = "crawlingImg";
    img.src = node.dataset.src;

    let p = document.createElement('p')
    p.className = "crawlingTitle";
    p.innerText = node.alt;

    div.appendChild(img);
    div.appendChild(p);

    return div;
}

const textFixd = () => {
    let age = "";
    if (queryData.age != "") age = queryData.age + " 대 ";
    let year = "";
    if (queryData.year != "") year = queryData.year + " 년도 ";
    let month = "";
    if (queryData.month != "") month = queryData.month + " 월 ";

    return {"age" : age, "year" : year, "month" : month}
}

const dataInputFlag = () => {
    if (dataFalg != 'f') return true;
    else return false;
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

    if (year != "" || month != "") {
        let date = new Date();
        if (year == "") year = date.getFullYear();
        if (month == "") month = date.getMonth() + 1;
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