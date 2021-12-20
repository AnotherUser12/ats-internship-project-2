var depList = document.getElementById("depList");
console.log(depNames);
for(var i = 0; i < depNames.length; i++){
    var dep = document.createElement("div");
    dep.classList.add("dep");
    dep.Id = depIds[i];
    dep.name = depNames[i];
    depList.appendChild(dep);

    var depId = document.createElement("div");
    depId.classList.add("depId");
    depId.innerHTML = depIds[i];
    dep.appendChild(depId);

    var depName = document.createElement("div");
    depName.classList.add("depName");
    depName.innerHTML = depNames[i];
    dep.appendChild(depName);

    var depEditLinkContainer = document.createElement("div");
    depEditLinkContainer.classList.add("depEditLinkContainer");
    dep.appendChild(depEditLinkContainer);

    var depEditLink = document.createElement("a");
    depEditLink.classList.add("depEditLink");
    depEditLink.innerHTML = "Edit";
    depEditLinkContainer.appendChild(depEditLink);
    depEditLink.addEventListener("click", function(e){
        e.target.parentElement.parentElement.querySelector('.depName').contentEditable = true;
        e.target.innerHTML = "Submit";
        e.target.addEventListener("click", function(e){
            var id = e.target.parentElement.parentElement.Id;
            window.location.href = "http://localhost:7979/ManageDepartments?editedDepId=" + id + "&editedDepName=" + e.target.parentElement.parentElement.querySelector('.depName').innerHTML;
        });
    });

    var depDeleteLinkContainer = document.createElement("div");
    depDeleteLinkContainer.classList.add("depDeleteLinkContainer");
    dep.appendChild(depDeleteLinkContainer);

    var depDeleteLink = document.createElement("a");
    depDeleteLink.classList.add("depDeleteLink");
    depDeleteLink.href = "http://localhost:7979/ManageDepartments?deletedDepId=" + depIds[i];
    depDeleteLink.innerHTML = "Delete";
    depDeleteLinkContainer.appendChild(depDeleteLink);
}
var depAdd = document.createElement("div");
depAdd.classList.add("dep");
depList.appendChild(depAdd);

var depAddId = document.createElement("div");
depAddId.classList.add("depId");
depAddId.innerHTML = depIds[depIds.length-1]+1;
depAdd.appendChild(depAddId);

var depAddName = document.createElement("input");
depAddName.classList.add("depName");
depAddName.innerHTML = depNames[i];
depAdd.appendChild(depAddName);

var depAddLinkContainer = document.createElement("div");
depAddLinkContainer.classList.add("depEditLinkContainer");
depAdd.appendChild(depAddLinkContainer);

var depAddLink = document.createElement("a");
depAddLink.classList.add("depEditLink");
depAddLink.style.backgroundColor="grey";
depAddLink.innerHTML = "Add";
depAddLink.href = "http://localhost:7979/";
depAddLinkContainer.appendChild(depAddLink);


depAddName.onchange=function(){
    if(depAddName.value.length>0) {
        depAddLink.href = "http://localhost:7979/ManageDepartments?addedDepName=" + depAddName.value;
    }
}
document.getElementById("searchBar").onchange = function(){
    // for(var i = 0; i < document.getElementsByClassName("dep").length; i++){
    //     var dep = document.getElementsByClassName("dep")[i];
    //     var depName = document.getElementsByClassName("dep")[i].getElementsByClassName("depName")[0].innerHTML;
    //     var searchStr = document.getElementById("searchBar").value;
    //
    //     dep.style.display = "flex";
    //     if(depName.indexOf(searchStr) < 0)
    //         dep.style.display = "none";
    // }

    for(var i = 0; i < depNames.length; i++){
        var depName = depNames[i];
        var dep = getDepWithName(depName);
        var searchStr = document.getElementById("searchBar").value;

        dep.style.display = "flex";
        if(depName.indexOf(searchStr) < 0)
            dep.style.display = "none";
    }
};

document.getElementById("sort").onchange = function(){
    var sortType = document.getElementById("sort").value;
    var deps = document.getElementsByClassName("dep");

    if(sortType=="Id"){
        for(var i = depIds.length-1; i>-1; i--){
            var id = depIds[i];
            var dep = getDepWithId(id);
            console.log(dep);
            dep.remove();
            depList.prepend(dep);
        }
    }else if(sortType=="Name"){
        const sortedDepNames = [...depNames].sort();
        for(var i = sortedDepNames.length-1; i>-1; i--){
            var name = sortedDepNames[i];
            var dep = getDepWithName(name);
            dep.remove();
            depList.prepend(dep);
        }
    }
};

function getDepWithId(id){
    for(var i = 0; i < document.getElementsByClassName("dep").length; i++){
        var dep = document.getElementsByClassName("dep")[i];
        if(dep.Id==id){
            return dep;
        }
    }
}

function getDepWithName(name){
    for(var i = 0; i < document.getElementsByClassName("dep").length; i++){
        var dep = document.getElementsByClassName("dep")[i];
        if(dep.name==name){
            return dep;
        }
    }
}

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart1);

// Draw the chart and set the chart values
function drawChart1() {
    var array = [['Department', 'Employee Count']];
    for(var i = 0; i < depEmployeeCounts.length; i++){
        array.push([depNames[i],depEmployeeCounts[i]]);
    }
    console.log(array);
    var data = google.visualization.arrayToDataTable(array);

    // Optional; add a title and set the width and height of the chart
    var options = {'title': 'Employee Distribution', 'width': 550, 'height': 400};

    // Display the chart inside the <div> element with id="piechart"
    var chart = new google.visualization.PieChart(document.getElementById('piechart1'));
    chart.draw(data, options);
}

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart2);
function drawChart2() {
    var array = [['Department', 'Average Salary']];
    for(var i = 0; i < depAverageSalaries.length; i++){
        array.push([depNames[i],depAverageSalaries[i]]);
    }
    console.log(array);
    var data = google.visualization.arrayToDataTable(array);

    // Optional; add a title and set the width and height of the chart
    var options = {'title': 'Average Salaries', 'width': 550, 'height': 400};

    // Display the chart inside the <div> element with id="piechart"
    var chart = new google.visualization.PieChart(document.getElementById('piechart2'));
    chart.draw(data, options);
}
