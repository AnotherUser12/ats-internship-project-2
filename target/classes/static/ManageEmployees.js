var empList = document.getElementById("empList");
console.log(empNames);
for(var i = 0; i < empNames.length; i++){
    var emp = document.createElement("div");
    emp.classList.add("emp");
    emp.Id = empIds[i];
    emp.name = empNames[i];
    emp.email = empEmails[i];
    emp.salary = empSalaries[i];
    emp.department = empDepartments[i];
    empList.appendChild(emp);

    var empId = document.createElement("div");
    empId.classList.add("empId");
    empId.innerHTML = empIds[i];
    emp.appendChild(empId);

    var empName = document.createElement("div");
    empName.classList.add("empName");
    empName.innerHTML = empNames[i];
    emp.appendChild(empName);

    var empEmail = document.createElement("div");
    empEmail.classList.add("empEmail");
    empEmail.innerHTML = empEmails[i];
    emp.appendChild(empEmail);

    var empSalary = document.createElement("div");
    empSalary.classList.add("empSalary");
    empSalary.innerHTML = empSalaries[i];
    emp.appendChild(empSalary);

    var empDepartment = document.createElement("div");
    empDepartment.classList.add("empDepartment");
    empDepartment.innerHTML = empDepartments[i];
    emp.appendChild(empDepartment);

    var empEditLinkContainer = document.createElement("div");
    empEditLinkContainer.classList.add("empEditLinkContainer");
    emp.appendChild(empEditLinkContainer);

    var empEditLink = document.createElement("a");
    empEditLink.classList.add("empEditLink");
    empEditLink.innerHTML = "Edit";
    empEditLinkContainer.appendChild(empEditLink);
    empEditLink.addEventListener("click", function(e){
        e.target.parentElement.parentElement.querySelector('.empName').contentEditable = true;
        e.target.parentElement.parentElement.querySelector('.empEmail').contentEditable = true;
        e.target.parentElement.parentElement.querySelector('.empSalary').contentEditable = true;
        e.target.parentElement.parentElement.querySelector('.empDepartment').contentEditable = true;

        e.target.innerHTML = "Submit";
        e.target.addEventListener("click", function(e){
            var id = e.target.parentElement.parentElement.Id;
            var name = e.target.parentElement.parentElement.querySelector('.empName').innerHTML;
            var email = e.target.parentElement.parentElement.querySelector('.empEmail').innerHTML;
            var salary = e.target.parentElement.parentElement.querySelector('.empSalary').innerHTML;
            var department = e.target.parentElement.parentElement.querySelector('.empDepartment').innerHTML

            window.location.href = "http://localhost:7979/ManageEmployees?editedEmpId=" + id + "&editedEmpName=" + name + "&editedEmpEmail=" + email+ "&editedEmpSalary=" + salary+ "&editedEmpDepartment=" + department ;
        });
    });

    var empDeleteLinkContainer = document.createElement("div");
    empDeleteLinkContainer.classList.add("empDeleteLinkContainer");
    emp.appendChild(empDeleteLinkContainer);

    var empDeleteLink = document.createElement("a");
    empDeleteLink.classList.add("empDeleteLink");
    empDeleteLink.href = "http://localhost:7979/ManageEmployees?deletedEmpId=" + empIds[i];
    empDeleteLink.innerHTML = "Delete";
    empDeleteLinkContainer.appendChild(empDeleteLink);
}
instantiateAddEmployeeElement();

document.getElementById("searchBar").onchange = function(){
    var searchType = document.getElementById("searchSelect").value;
    if(searchType=="Name"){
        for(var i = 0; i < empNames.length; i++){
            var empName = empNames[i];
            var searchStr = document.getElementById("searchBar").value;

            var emps = getEmpWithName(empName);
            console.log(emps);
            for(var j = 0; j < emps.length; j++){
                emp  = emps[j];
                emp.style.display = "flex";
                if (empName.indexOf(searchStr) < 0) {
                    emp.style.display = "none";
                }
            }
        }
    }else if(searchType=="Email") {
        for (var i = 0; i < empEmails.length; i++) {
            var empEmail = empEmails[i];
            var searchStr = document.getElementById("searchBar").value;

            var emps = getEmpWithEmail(empEmail);
            console.log(emps);
            for (var j = 0; j < emps.length; j++) {
                emp = emps[j];
                emp.style.display = "flex";
                if (empEmail.indexOf(searchStr) < 0) {
                    emp.style.display = "none";
                }
            }
        }
    }else if(searchType=="Department") {
        for (var i = 0; i < empDepartments.length; i++) {
            var empDepartment = empDepartments[i];
            var searchStr = document.getElementById("searchBar").value;

            var emps = getEmpWithDepartment(empDepartment);
            console.log(emps);
            for(var j = 0; j < emps.length; j++){
                emp  = emps[j];
                emp.style.display = "flex";
                if (empDepartment.indexOf(searchStr) < 0) {
                    emp.style.display = "none";
                }
            }
        }
    }else if(searchType=="With Equivalent Salary") {
        var emps = document.getElementsByClassName('emp');
        for (var i = 0; i < emps.length; i++) {
            var emp = emps[i];
            var searchStr = document.getElementById("searchBar").value;
            emp.style.display = "flex";
            if(emp.salary != searchStr){
                emp.style.display = "none";
            }
        }
    }else if(searchType=="With Greater Salary") {
        var emps = document.getElementsByClassName('emp');
        for (var i = 0; i < emps.length; i++) {
            var emp = emps[i];
            var searchStr = document.getElementById("searchBar").value;
            emp.style.display = "flex";
            if(emp.salary <= searchStr){
                emp.style.display = "none";
            }
        }
    }else if(searchType=="With Smaller Salary") {
        var emps = document.getElementsByClassName('emp');
        for (var i = 0; i < emps.length; i++) {
            var emp = emps[i];
            var searchStr = document.getElementById("searchBar").value;
            emp.style.display = "flex";
            if(emp.salary > searchStr){
                emp.style.display = "none";
            }
        }
    }

};
document.getElementById("sort").onchange = function(){
    var sortType = document.getElementById("sort").value;

    if(sortType=="Id"){
        for(var i = empIds.length-1; i>-1; i--){
            var id = empIds[i];
            var emp = getEmpWithId(id);
            console.log(emp);
            emp.remove();
            empList.prepend(emp);
        }
    }else if(sortType=="Name"){
        const sortedEmpNames = [...empNames].sort();
        for(var i = sortedEmpNames.length-1; i>-1; i--){
            var name = sortedEmpNames[i];
            var emps = getEmpWithName(name);
            console.log(emps);
            for(var j = 0; j < emps.length; j++){
                var emp = emps[j];
                emp.remove();
                empList.prepend(emp);
            }
            i-=emps.length-1;

        }
    }else if(sortType=="Email"){
        const sortedEmpEmails = [...empEmails].sort();
        for(var i = sortedEmpEmails.length-1; i>-1; i--){
            var email = sortedEmpEmails[i];
            var emps = getEmpWithEmail(email);
            console.log(emps);
            for(var j = 0; j < emps.length; j++){
                var emp = emps[j];
                console.log(emp);
                emp.remove();
                empList.prepend(emp);
            }
            i-=emps.length-1;
        }
    }else if(sortType=="Salary"){
        const sortedEmpSalaries = empSalaries.map(Number).sort();
        for(var i = sortedEmpSalaries.length-1; i>-1; i--) {
            var salary = sortedEmpSalaries[i];
            var emps = getEmpWithSalary(salary);
            for(var j = 0; j < emps.length; j++){
                var emp = emps[j];
                emp.remove();
                empList.prepend(emp);
            }
            i -= emps.length - 1;
        }
    }else if(sortType=="Department"){
        const sortedEmpDepartments = [...empDepartments].sort();
        for(var i = sortedEmpDepartments.length-1; i>-1; i--){
            var department = sortedEmpDepartments[i];
            var emps = getEmpWithDepartment(department);
            for(var j = 0; j < emps.length; j++){
                var emp = emps[j];
                emp.remove();
                empList.prepend(emp);
            }
            i-=emps.length-1;
        }
    }
};

function getEmpWithId(id){
    for(var i = 0; i < document.getElementsByClassName("emp").length; i++){
        var emp = document.getElementsByClassName("emp")[i];
        if(emp.Id==id){
            return emp;
        }
    }
}

function getEmpWithName(name){
    var arr = [];
    for(var i = 0; i < document.getElementsByClassName("emp").length; i++){
        var emp = document.getElementsByClassName("emp")[i];
        if(emp.name==name){
            arr.push(emp);
        }
    }
    return arr;
}

function getEmpWithEmail(email){
    var arr = [];
    for(var i = 0; i < document.getElementsByClassName("emp").length; i++){
        var emp = document.getElementsByClassName("emp")[i];
        if(emp.email==email){
            arr.push(emp);
        }
    }
    return arr;
}
function getEmpWithSalary(salary){
    var arr = [];
    for(var i = 0; i < document.getElementsByClassName("emp").length; i++){
        var emp = document.getElementsByClassName("emp")[i];
        if(emp.salary==salary){
            arr.push( emp );
        }
    }
    return arr;
}

function getEmpWithDepartment(department){
    var arr = [];
    for(var i = 0; i < document.getElementsByClassName("emp").length; i++){
        var emp = document.getElementsByClassName("emp")[i];
        if(emp.department==department){
            arr.push(emp);
        }
    }
    return  arr;
}

function instantiateAddEmployeeElement(){
    var empAdd = document.createElement("div");
    empAdd.classList.add("emp");
    empList.appendChild(empAdd);

    var empAddId = document.createElement("div");
    empAddId.classList.add("empId");
    empAddId.innerHTML = empIds[empIds.length-1]+1;
    empAdd.appendChild(empAddId);

    var empAddName = document.createElement("input");
    empAddName.classList.add("empName");
    empAdd.appendChild(empAddName);

    var empAddEmail = document.createElement("input");
    empAddEmail.classList.add("empEmail");
    empAdd.appendChild(empAddEmail);

    var empAddSalary = document.createElement("input");
    empAddSalary.classList.add("empSalary");
    empAddSalary.type = "number";
    empAdd.appendChild(empAddSalary);

    var empAddDepartment = document.createElement("input");
    empAddDepartment.classList.add("empDepartment");
    empAdd.appendChild(empAddDepartment);

    var empAddLinkContainer = document.createElement("div");
    empAddLinkContainer.classList.add("empEditLinkContainer");
    empAdd.appendChild(empAddLinkContainer);

    var empAddLink = document.createElement("a");
    empAddLink.classList.add("empEditLink");
    empAddLink.style.backgroundColor="grey";
    empAddLink.innerHTML = "Add";
    empAddLinkContainer.appendChild(empAddLink);


    empAddLink.addEventListener("click", function(e){
            var name = e.target.parentElement.parentElement.querySelector('.empName').value;
            var email = e.target.parentElement.parentElement.querySelector('.empEmail').value;
            var salary = e.target.parentElement.parentElement.querySelector('.empSalary').value;
            var department = e.target.parentElement.parentElement.querySelector('.empDepartment').value;

            window.location.href = "http://localhost:7979/ManageEmployees?addedEmpName=" + name + "&addedEmpEmail=" + email+ "&addedEmpSalary=" + salary+ "&addedEmpDepartment=" + department ;
    });
}