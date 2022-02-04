function getUserName() {
    document.getElementById("userLogin");
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = xhr.responseText;
            document.getElementById("getUserName").innerHTML = response;
        }
    };
    xhr.open("GET", "http://localhost:8080/ERS/login", true);
    xhr.send();
}


