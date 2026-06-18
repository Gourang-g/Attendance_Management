const BASE_URL = "http://localhost:8080";
if(localStorage.getItem("role") !== "ADMIN"){
    window.location.href = "index.html";
}

function getAuthHeaders() {
    return {
        "Authorization":
            "Bearer " + localStorage.getItem("token"),

        "Content-Type":
            "application/json"
    };
}

window.onload = function(){
    const teacherWelcome = document.getElementById("teacherWelcome");
    const adminWelcome = document.getElementById("adminWelcome");

    if(teacherWelcome){
        const name = localStorage.getItem("userName");
        teacherWelcome.innerText = "Welcome " + name;
    }

    if(adminWelcome){
        const name = localStorage.getItem("userName");
        adminWelcome.innerText = "Welcome " + name;
        loadAdminDashboard();
    }

    loadClassesForRegister();
}
//logout
function logout(){
    window.location.href = "index.html";
}

//create teacher
function showCreateTeacher(){
    const content = document.getElementById("content");

    content.innerHTML = `
        <h3>Create Teacher</h3>
        <input type="text" id="tname" placeholder="Teacher Name">
        <input type="text" id="temail" placeholder="Email">
        <input type="text" id="tsubject" placeholder="Subject">
        <input type="text" id="tdept" placeholder="Department">
        <input type="password" id="tpass" placeholder="Password">
        <button onclick="createTeacher()">Create Teacher</button>
    `;
}
async function createTeacher(){
    const teacherData = {
        name: document.getElementById("tname").value,
        email: document.getElementById("temail").value,
        subject: document.getElementById("tsubject").value,
        department: document.getElementById("tdept").value,
        password: document.getElementById("tpass").value
    };

    const response = await fetch(`${BASE_URL}/api/auth/teacher/register`,{
        method:"POST",
        headers: getAuthHeaders(),
        body: JSON.stringify(teacherData)
    });

    const result = await response.text();
    alert(result);
}

//Create class
function showCreateClass(){
    const content = document.getElementById("content");

    content.innerHTML = `
        <h3>Create Class</h3>
        <input type="text" id="cname" placeholder="Class Name">
        <input type="text" id="csem" placeholder="Semester">
        <input type="text" id="cyear" placeholder="Year">
        <button onclick="createClass()">Create Class</button>
    `;
}

async function createClass(){
    const classData = {
        className: document.getElementById("cname").value,
        semester: document.getElementById("csem").value,
        year: document.getElementById("cyear").value
    };

    const response = await fetch(`${BASE_URL}/api/classes/create`,{
        method:"POST",
        headers: getAuthHeaders(),
        body: JSON.stringify(classData)
    });

    const result = await response.json();
    alert("Class Created: " + result.className);
}

//Assign student to class
async function showAssignStudent(){

    const content = document.getElementById("content");

    const classResponse =
        await fetch(`${BASE_URL}/api/classes`, { headers: getAuthHeaders() });

    const classes = await classResponse.json();

    const studentResponse =
        await fetch(`${BASE_URL}/api/students`, { headers: getAuthHeaders() });

    const students = await studentResponse.json();

    let classOptions = "";
    let studentOptions = "";

    classes.forEach(cls => {

        classOptions += `
            <option value="${cls.id}">
                ${cls.className} - ${cls.year}
            </option>
        `;
    });

    students.forEach(student => {

        studentOptions += `
            <option value="${student.id}">
                ${student.name} (${student.rollNo})
            </option>
        `;
    });

    content.innerHTML = `
        <h3>Assign Student To Class</h3>

        <select id="assignStudentId">
            ${studentOptions}
        </select>

        <select id="assignStudentClassId">
            ${classOptions}
        </select>

        <button onclick="assignStudentToClass()">
            Assign Student
        </button>
    `;
}

async function assignStudentToClass(){

    const studentId =
        document.getElementById("assignStudentId").value;

    const classId =
        document.getElementById("assignStudentClassId").value;

    const response = await fetch(
        `${BASE_URL}/api/classes/${classId}/add-student/${studentId}`,
        {
            method:"PUT",
            headers: getAuthHeaders()
        }
    );

    const result = await response.json();

    alert(
        `${result.name} assigned successfully`
    );
}

//assign teacher to class
async function showAssignTeacher(){

    const content = document.getElementById("content");

    const classResponse =
        await fetch(`${BASE_URL}/api/classes`, { headers: getAuthHeaders() });

    const classes = await classResponse.json();

    const teacherResponse =
        await fetch(`${BASE_URL}/api/teachers`, { headers: getAuthHeaders() });

    const teachers = await teacherResponse.json();

    let classOptions = "";
    let teacherOptions = "";

    classes.forEach(cls => {

        classOptions += `
            <option value="${cls.id}">
                ${cls.className} - ${cls.year}
            </option>
        `;
    });

    teachers.forEach(teacher => {

        teacherOptions += `
            <option value="${teacher.id}">
                ${teacher.name}
            </option>
        `;
    });

    content.innerHTML = `
        <h3>Assign Teacher To Class</h3>

        <select id="aclassid">
            ${classOptions}
        </select>

        <select id="ateacherid">
            ${teacherOptions}
        </select>

        <button onclick="assignTeacherToClass()">
            Assign Teacher
        </button>
    `;
}

async function assignTeacherToClass(){
    const classId = document.getElementById("aclassid").value;
    const teacherId = document.getElementById("ateacherid").value;

    const response = await fetch(`${BASE_URL}/api/classes/${classId}/assign-teacher/${teacherId}`, {
        method: "PUT",
        headers: getAuthHeaders()
    });

    const result = await response.json();
    alert("Teacher Assigned To " + result.className);
}

function showRegisterStudent(){
    const content = document.getElementById("content");

    content.innerHTML = `
        <h3>Register Student</h3>
        <input type="text" id="sroll" placeholder="Roll No">
        <input type="text" id="sname" placeholder="Student Name">
        <input type="text" id="sbranch" placeholder="Branch">
        <input type="text" id="ssem" placeholder="Semester">
        <input type="text" id="syear" placeholder="Year">
        <input type="password" id="spass" placeholder="Password">
        <input type="number" id="sclassid" placeholder="Class ID">
        <button onclick="registerStudent()">Register Student</button>
    `;
}

//view teachers
async function viewAllTeachers(){

    const content = document.getElementById("content");

    const response =
        await fetch(`${BASE_URL}/api/teachers`, { headers: getAuthHeaders() });

    const teachers = await response.json();

    if(teachers.length === 0){
        content.innerHTML = "<p>No teachers found</p>";
        return;
    }

    let html = `
        <h3>All Teachers</h3>

        <table border="1" width="100%"
        style="margin-top:20px; border-collapse:collapse;">

            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Subject</th>
            </tr>
    `;

    teachers.forEach(teacher => {

        html += `
            <tr>
                <td>${teacher.id}</td>
                <td>${teacher.name}</td>
                <td>${teacher.email}</td>
                <td>${teacher.department}</td>
                <td>${teacher.subject}</td>
            </tr>
        `;
    });

    html += `</table>`;

    content.innerHTML = html;
}

//view student
async function viewAllStudents(){

    const content = document.getElementById("content");

    const response =
        await fetch(`${BASE_URL}/api/students`, { headers: getAuthHeaders() });

    const students = await response.json();

    if(students.length === 0){
        content.innerHTML = "<p>No students found</p>";
        return;
    }

    let html = `
        <h3>All Students</h3>

        <table border="1" width="100%"
        style="margin-top:20px; border-collapse:collapse;">

            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Roll No</th>
                <th>Branch</th>
                <th>Semester</th>
                <th>Year</th>
            </tr>
    `;

    students.forEach(student => {

        html += `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.rollNo}</td>
                <td>${student.branch}</td>
                <td>${student.semester}</td>
                <td>${student.year}</td>
            </tr>
        `;
    });

    html += `</table>`;

    content.innerHTML = html;
}

//view class
async function viewAllClasses(){

    const content = document.getElementById("content");

    const response =
        await fetch(`${BASE_URL}/api/classes`, { headers: getAuthHeaders() });

    const classes = await response.json();

    if(classes.length === 0){
        content.innerHTML = "<p>No classes found</p>";
        return;
    }

    let html = `
        <h3>All Classes</h3>

        <table border="1" width="100%"
        style="margin-top:20px; border-collapse:collapse;">

            <tr>
                <th>ID</th>
                <th>Class Name</th>
                <th>Semester</th>
                <th>Year</th>
                <th>Teacher Assigned</th>
            </tr>
    `;

    classes.forEach(cls => {

        html += `
            <tr>
                <td>${cls.id}</td>
                <td>${cls.className}</td>
                <td>${cls.semester}</td>
                <td>${cls.year}</td>
                <td>${cls.teacher ? cls.teacher.name : "Not Assigned"}</td>
            </tr>
        `;
    });

    html += `</table>`;

    content.innerHTML = html;
}

//sync register to adminregister
function openStudentRegister(){
    window.location.href = "register.html";
}

//search student
function showSearchStudent(){

    const content = document.getElementById("content");

    content.innerHTML = `
        <h3>Search Student</h3>

        <input type="text"
               id="searchKeyword"
               placeholder="Enter Name or Roll No">

        <button onclick="searchStudent()">
            Search
        </button>

        <div id="searchResult"></div>
    `;
}

async function searchStudent(){

    const keyword =
        document.getElementById("searchKeyword").value;

    const response = await fetch(
        `${BASE_URL}/api/students/search?keyword=${keyword}`, { headers: getAuthHeaders() }
    );

    const students = await response.json();

    const resultDiv =
        document.getElementById("searchResult");

    if(students.length === 0){
        resultDiv.innerHTML = "<p>No student found</p>";
        return;
    }

    let html = `
        <table border="1" width="100%">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Roll No</th>
                <th>Branch</th>
                <th>Year</th>
            </tr>
    `;

    students.forEach(student => {
        html += `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.rollNo}</td>
                <td>${student.branch}</td>
                <td>${student.year}</td>
            </tr>
        `;
    });

    html += `</table>`;

    resultDiv.innerHTML = html;
}

//department Management

/* =========================
   DEPARTMENT FUNCTIONS
========================= */

// Load all departments into table
async function loadDepartments() {
    const tbody = document.getElementById("departmentTableBody");
    if (!tbody) return;

    try {
        const response = await fetch(`${BASE_URL}/api/departments`, { headers: getAuthHeaders() });
        const departments = await response.json();

        tbody.innerHTML = "";

        departments.forEach(dept => {
            const row = `
                <tr>
                    <td>${dept.id}</td>
                    <td>${dept.name}</td>
                    <td>
                        <button onclick="deleteDepartment(${dept.id})">
                            Delete
                        </button>
                    </td>
                </tr>
            `;
            tbody.innerHTML += row;
        });
    } catch (error) {
        console.error("Error loading departments:", error);
        alert("Failed to load departments.");
    }
}

// Add new department
async function addDepartment() {
    const nameInput = document.getElementById("departmentName");
    if (!nameInput) return;

    const name = nameInput.value.trim();

    if (name === "") {
        alert("Please enter department name.");
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/api/departments`, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify({
                name: name
            })
        });

        if (response.ok) {
            alert("Department added successfully.");
            nameInput.value = "";
            loadDepartments();
            loadDepartmentsDropdown();
        } else {
            const errorText = await response.text();
            alert("Failed to add department.\n" + errorText);
        }
    } catch (error) {
        console.error("Error adding department:", error);
        alert("Error adding department.");
    }
}

// Delete department
async function deleteDepartment(id) {
    if (!confirm("Are you sure you want to delete this department?")) {
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/api/departments/${id}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });

        if (response.ok) {
            alert("Department deleted successfully.");
            loadDepartments();
            loadDepartmentsDropdown();
        } else {
            alert("Failed to delete department.");
        }
    } catch (error) {
        console.error("Error deleting department:", error);
        alert("Error deleting department.");
    }
}

/* =========================
   SUBJECT FUNCTIONS
========================= */

// Load departments into dropdown
async function loadDepartmentsDropdown() {
    const select = document.getElementById("subjectDepartment");
    if (!select) return;

    try {
        const response = await fetch(`${BASE_URL}/api/departments`, { headers: getAuthHeaders() });
        const departments = await response.json();

        select.innerHTML = '<option value="">Select Department</option>';

        departments.forEach(dept => {
            const option = document.createElement("option");
            option.value = dept.id;
            option.textContent = dept.name;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Error loading departments dropdown:", error);
    }
}

// Load all subjects into table
async function loadSubjects() {
    const tbody = document.getElementById("subjectTableBody");
    if (!tbody) return;

    try {
        const response = await fetch(`${BASE_URL}/api/subjects`, { headers: getAuthHeaders() });
        const subjects = await response.json();

        tbody.innerHTML = "";

        subjects.forEach(subject => {
            const row = `
                <tr>
                    <td>${subject.id}</td>
                    <td>${subject.code || ""}</td>
                    <td>${subject.name}</td>
                    <td>${subject.semester || ""}</td>
                    <td>${subject.department ? subject.department.name : ""}</td>
                    <td>
                        <button onclick="deleteSubject(${subject.id})">
                            Delete
                        </button>
                    </td>
                </tr>
            `;
            tbody.innerHTML += row;
        });
    } catch (error) {
        console.error("Error loading subjects:", error);
        alert("Failed to load subjects.");
    }
}

// Add new subject
async function addSubject() {
    const code = document.getElementById("subjectCode").value.trim();
    const name = document.getElementById("subjectName").value.trim();
    const semester = document.getElementById("subjectSemester").value.trim();
    const departmentId = document.getElementById("subjectDepartment").value;

    if (name === "" || departmentId === "") {
        alert("Please fill all required fields.");
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/api/subjects`, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify({
                code: code,
                name: name,
                semester: semester,
                department: {
                    id: departmentId
                }
            })
        });

        if (response.ok) {
            alert("Subject added successfully.");

            document.getElementById("subjectCode").value = "";
            document.getElementById("subjectName").value = "";
            document.getElementById("subjectSemester").value = "";
            document.getElementById("subjectDepartment").value = "";

            loadSubjects();
        } else {
            const errorText = await response.text();
            alert("Failed to add subject.\n" + errorText);
        }
    } catch (error) {
        console.error("Error adding subject:", error);
        alert("Error adding subject.");
    }
}

// Delete subject
async function deleteSubject(id) {
    if (!confirm("Are you sure you want to delete this subject?")) {
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/api/subjects/${id}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });

        if (response.ok) {
            alert("Subject deleted successfully.");
            loadSubjects();
        } else {
            alert("Failed to delete subject.");
        }
    } catch (error) {
        console.error("Error deleting subject:", error);
        alert("Error deleting subject.");
    }
}

function showDepartmentManagement() {
    const content = document.getElementById("content");

    content.innerHTML = `
        <h3>Department Management</h3>

        <input type="text" id="departmentName" placeholder="Department Name">
        <button onclick="addDepartment()">Add Department</button>

        <table border="1" width="100%" style="margin-top:20px; border-collapse:collapse;">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Action</th>
            </tr>
            <tbody id="departmentTableBody"></tbody>
        </table>

        <hr style="margin:30px 0;">

        <h3>Subject Management</h3>

        <input type="text" id="subjectCode" placeholder="Subject Code">
        <input type="text" id="subjectName" placeholder="Subject Name">
        <input type="text" id="subjectSemester" placeholder="Semester">

        <select id="subjectDepartment">
            <option value="">Select Department</option>
        </select>

        <button onclick="addSubject()">Add Subject</button>

        <table border="1" width="100%" style="margin-top:20px; border-collapse:collapse;">
            <tr>
                <th>ID</th>
                <th>Code</th>
                <th>Name</th>
                <th>Semester</th>
                <th>Department</th>
                <th>Action</th>
            </tr>
            <tbody id="subjectTableBody"></tbody>
        </table>
    `;

    loadDepartments();
    loadDepartmentsDropdown();
    loadSubjects();
}

async function loadAdminDashboard() {
    const dashboard = document.getElementById("dashboard");
    if (!dashboard) return;

    const response = await fetch(`${BASE_URL}/api/dashboard/admin`);
    const data = await response.json();

    dashboard.innerHTML = `
        <div class="dashboard-card"><h4>Departments</h4><p>${data.totalDepartments}</p></div>
        <div class="dashboard-card"><h4>Subjects</h4><p>${data.totalSubjects}</p></div>
        <div class="dashboard-card"><h4>Classes</h4><p>${data.totalClasses}</p></div>
        <div class="dashboard-card"><h4>Teachers</h4><p>${data.totalTeachers}</p></div>
        <div class="dashboard-card"><h4>Students</h4><p>${data.totalStudents}</p></div>
        <div class="dashboard-card"><h4>Attendance Records</h4><p>${data.totalAttendanceRecords}</p></div>
        <div class="dashboard-card"><h4>Below 75%</h4><p>${data.lowAttendanceStudents}</p></div>
    `;
}

async function viewLowAttendance() {
    const content = document.getElementById("content");

    const threshold = prompt("Enter attendance threshold (%)", "75");

    if (threshold === null) {
        return;
    }

    const response = await fetch(
        `${BASE_URL}/api/attendance/low-attendance?threshold=${threshold}`, { headers: getAuthHeaders() }
    );
    const students = await response.json();

    if (!students || students.length === 0) {
        content.innerHTML =
            `<h3>Low Attendance Students</h3>
             <p>No students found below ${threshold}%.</p>`;
        return;
    }

    let html = `
        <h3>Low Attendance Students (Below ${threshold}%)</h3>

        <table border="1"
               width="100%"
               style="margin-top:20px; border-collapse:collapse;">
            <tr>
                <th>Name</th>
                <th>Roll No</th>
                <th>Class</th>
                <th>Percentage</th>
            </tr>
    `;

    students.forEach(student => {
        html += `
            <tr>
                <td>${student.name}</td>
                <td>${student.rollNo}</td>
                <td>${student.className}</td>
                <td>${student.percentage}%</td>
            </tr>
        `;
    });

    html += `</table>`;

    content.innerHTML = html;
}