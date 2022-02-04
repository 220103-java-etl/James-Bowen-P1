function getAllRequests() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = JSON.parse(xhr.responseText);

            let body = document.getElementsByTagName('body')[0];
            let table = document.createElement('table');
            table.setAttribute('id', 'requestsTable');
            let thead = document.createElement('thead');
            let tr = document.createElement('tr');
            let th = document.createElement('th');
            th = document.createElement('th');
            th.innerHTML = "Username";
            tr.appendChild(th);
            th = document.createElement('th');
            th.innerHTML = "Request Description";
            tr.appendChild(th);
            th = document.createElement('th');
            th.innerHTML = "Reimbursement Amount";
            tr.appendChild(th);
            th = document.createElement('th');
            th.innerHTML = "Request Date";
            tr.appendChild(th);
            th = document.createElement('th');
            th.innerHTML = "Request Status";
            tr.appendChild(th);
            thead.appendChild(tr);
            table.appendChild(thead);
            let tbody = document.createElement('tbody');
            for (let i = 0; i < response.length; i++) {
                let tr = document.createElement('tr');
                let td = document.createElement('td');
                td.innerHTML = response[i].author.username;
                tr.appendChild(td);
                td = document.createElement('td');
                td.innerHTML = response[i].description;
                tr.appendChild(td);
                td = document.createElement('td');
                td.innerHTML = response[i].amount;
                const moneyFormat = new Intl.NumberFormat('en-US', {
                    style: 'currency',
                    currency: 'USD',
                });
                td.innerHTML = moneyFormat.format(response[i].amount);
                tr.appendChild(td);
                td = document.createElement('td');
                td.innerHTML = response[i].date;
                tr.appendChild(td);
                td = document.createElement('td');
                td.innerHTML = response[i].status;
                tr.appendChild(td);
                tbody.appendChild(tr);

                let button = document.createElement('button');
                button.setAttribute('id', "editButton" + i);
                button.setAttribute('onclick', "editAllRequest(" + i + ")");
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