function getAllRequests() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = JSON.parse(xhr.responseText);

            let body = document.getElementsByTagName('body')[0];
            let table = document.createElement('table');
            table.setAttribute('id', 'requestsTable');
            let tableHead = document.createElement('tableHead');
            let tr = document.createElement('tr');
            let th = document.createElement('th');
            th1 = document.createElement('th');
            th1.innerHTML = "Username";
            tr.appendChild(th);
            th2 = document.createElement('th');
            th2.innerHTML = "Description";
            tr.appendChild(th);
            th3 = document.createElement('th');
            th3.innerHTML = "Reimbursement Amount";
            tr.appendChild(th);
            th4 = document.createElement('th');
            th4.innerHTML = "Request Date";
            tr.appendChild(th);
            th5 = document.createElement('th');
            th5.innerHTML = "Request Status";
            tr.appendChild(th);
            tableHead.appendChild(tr);
            table.appendChild(tableHead);
            let tbody = document.createElement('tbody');
            for (let i = 0; i < response.length; i++) {
                let tr = document.createElement('tr');
                let td = document.createElement('td');
                td.innerHTML = response[i].author.username;
                tr.appendChild(td);
                td = document.createElement('td');
                td.innerHTML = response[i].description;
                tr.appendChild(td);
                td2 = document.createElement('td');
                td2.innerHTML = response[i].amount;
                tr.appendChild(td);
                td3 = document.createElement('td');
                td3.innerHTML = response[i].date;
                tr.appendChild(td);
                td4 = document.createElement('td');
                td4.innerHTML = response[i].status;
                tr.appendChild(td);
                tbody.appendChild(tr);

                let button = document.createElement('button');
                button.setAttribute('id', "editButton" + i);
                button.innerHTML = "Edit";
                tr.appendChild(button);
            }
            table.appendChild(tbody);
            body.appendChild(table);
        }
    };

    xhr.open("GET", "http://localhost:8080/ERS/Manager", true);
    xhr.send();
}

function editAllRequest(id) {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = JSON.parse(xhr.responseText);
            console.log(response);
            document.getElementById('requestId').value = (response[id].id);
            document.getElementById('username').value = (response[id].author.username);
            document.getElementById('date').value = (response[id].date);
            document.getElementById('time').value = (response[id].time);
            document.getElementById('location').value = (response[id].location);
            document.getElementById('description').value = (response[id].description);
            document.getElementById('amount').value = (response[id].amount);
            document.getElementById('justify').value = (response[id].justify);
            document.getElementById("updateForm").style.display="block";
        }
    };
    xhr.open("GET", "http://localhost:8080/ERS/Manager", true);
    xhr.send();
}