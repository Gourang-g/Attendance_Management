const BASE_URL = "http://localhost:8080";
//Login
async function loginUser() {
    const role = document.getElementById("role").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const message = document.getElementById("message");

    let endpoint = "";

    if(role === "teacher"){
        endpoint = `/api/auth/teacher/login?name=${username}&password=${password}`;
    }
    else if(role === "student"){
        endpoint = `/api/auth/student/login?name=${username}&password=${password}`;
    }
    else if(role === "admin"){
        endpoint = `/api/admin/login?username=${username}&password=${password}`;
    }

    try{
        const response = await fetch(BASE_URL + endpoint, {
            method: "POST"
        });

        const data = await response.json();

        if(data !== null){
            message.style.color = "green";
            message.innerText = "Login Successful";

            localStorage.setItem("userId", data.id);
            localStorage.setItem("userName", data.name);

            if(role === "teacher"){
                window.location.href = "teacher.html";
            }
            else if(role === "student"){
                window.location.href = "student.html";
            }
            else{
                window.location.href = "admin.html";
            }
        }
        else{
            message.style.color = "red";
            message.innerText = "Login Failed";
        }

    }catch(error){
        message.innerText = "Cannot connect to backend";
    }
}

//logout
function logout(){
    window.location.href = "index.html";
}
