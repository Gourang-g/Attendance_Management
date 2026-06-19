const BASE_URL = "https://onrender.com";

// Login
async function loginUser() {
    const role = document.getElementById("role").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const message = document.getElementById("message");

    let endpoint = "";

    if (role === "teacher") {
        endpoint = `/api/auth/teacher/login?name=${username}&password=${password}`;
    }
    else if (role === "student") {
        endpoint = `/api/auth/student/login?name=${username}&password=${password}`;
    }
    else if (role === "admin") {
        endpoint = `/api/admin/login?username=${username}&password=${password}`;
    }

    try {
        const response = await fetch(BASE_URL + endpoint, {
            method: "POST"
        });

        // If login failed, do not redirect
        if (!response.ok) {
            const errorText = await response.text();

            message.style.color = "red";
            message.innerText = errorText || "Login Failed";
            return;
        }

        // Login successful
        const data = await response.json();

        message.style.color = "green";
        message.innerText = "Login Successful";

        localStorage.setItem("userId", data.id);
        localStorage.setItem("userName", data.name);
        localStorage.setItem("role", role.toUpperCase());
        localStorage.setItem("token",data.token);

        // Redirect based on role
        if (role === "teacher") {
            window.location.href = "teacher.html";
        }
        else if (role === "student") {
            window.location.href = "student.html";
        }
        else if(role === "admin")
            window.location.href = "admin.html";


    } catch (error) {
        message.style.color = "red";
        message.innerText = "Cannot connect to backend";
    }
}

// Logout
function logout() {
    localStorage.clear();
    window.location.href = "index.html";
}