const BASE_URL = "http://localhost:8080";

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
    }

    loadClassesForRegister();
}
//logout
function logout(){
    window.location.href = "index.html";
}
//toget every class
async function getAllClasses() {
    try {
        const response = await fetch(`${BASE_URL}/api/classes`);

        if (!response.ok) {
            throw new Error("Failed to load classes");
        }

        return await response.json();
    } catch (error) {
        console.error("Error loading classes:", error);
        alert("Unable to load classes.");
        return [];
    }
}

async function getAllSubjects() {
    try {
        const response = await fetch(`${BASE_URL}/api/subjects`);

        if (!response.ok) {
            throw new Error("Failed to load subjects");
        }

        return await response.json();
    } catch (error) {
        console.error("Error loading subjects:", error);
        alert("Unable to load subjects.");
        return [];
    }
}

//TO VIEW MARKED ATTENDANCE
async function showMarkAttendance(){
    const content = document.getElementById("content");
    const classes = await getAllClasses();
    const subjects = await getAllSubjects();

    let classoptions = "";
    classes.forEach(cls => {
        classoptions += `<option value="${cls.id}">${cls.className} - ${cls.year} Year</option>`;
    });

    let subjectoptions = "";
    subjects.forEach(subject => {
        subjectoptions += `<option value="${subject.id}">${subject.name} ${subject.code ? "(" + subject.code + ")" : ""}</option>
        `;
        });

    if (subjectoptions === "") {
            subjectoptions = `<option value="">No subjects available</option>`;
        }

    content.innerHTML = `
    <h3>Mark Attendance</h3>

    <label>Select Class:</label><br>
            <select id="classId">
                ${classoptions}
            </select>

            <br><br>

            <label>Select Subject:</label><br>
            <select id="subject">
                ${subjectoptions}
            </select>

            <br><br>

            <button onclick="loadStudents()">Load Students</button>

            <div id="studentList"></div>
        `;
    }



//LOAD STUDENT LIST
async function loadStudents(){
    const classId = document.getElementById("classId").value;
    const studentList = document.getElementById("studentList");

    const response = await fetch(`${BASE_URL}/api/students/class/${classId}`);
    const students = await response.json();

    if(students.length === 0){
        studentList.innerHTML = "<p>No students found in this class</p>";
        return;
    }

    let html = "<h4>Select Attendance</h4>";

    students.forEach(student => {
        html += `
            <div style="margin:10px; text-align:left;">
                <b>${student.name}</b> (${student.rollNo})
                <select id="status-${student.id}">
                    <option value="PRESENT">PRESENT</option>
                    <option value="ABSENT">ABSENT</option>
                </select>
            </div>
        `;
    });

    html += `<button onclick="submitAttendance()">Submit Attendance</button>`;

    studentList.innerHTML = html;
}

//SUBMIT ATTENDANCE
async function submitAttendance(){
    const classId = document.getElementById("classId").value;
    const subjectId = document.getElementById("subject").value;

        if (!classId) {
            alert("Please select a class.");
            return;
        }

        if (!subjectId) {
            alert("Please select a subject.");
            return;
        }

    const response = await fetch(`${BASE_URL}/api/students/class/${classId}`);
    const students = await response.json();

    let attendanceList = [];

    students.forEach(student => {
        const status = document.getElementById(`status-${student.id}`).value;

        attendanceList.push({
            studentId: student.id,
            status: status
        });
    });

    const attendanceData = {
        classId: parseInt(classId),
        subjectId: parseInt(subjectId),
        attendanceList: attendanceList
    };

    const saveResponse = await fetch(`${BASE_URL}/api/attendance/mark`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(attendanceData)
    });

    const result = await saveResponse.text();

    alert(result);
}

//TO VIEW CLASS ATTENDANCE (VIEW ATT BUTTON)
async function viewClassAttendance(){
    const content = document.getElementById("content");
    const classes = await getAllClasses();
    const subjects = await getAllSubjects();

    let classoptions = "";

    classes.forEach(cls => {
        classoptions += `<option value="${cls.id}">${cls.className} - ${cls.year} Year</option>`;
    });

    let subjectoptions = "";
        subjects.forEach(subject => {
            subjectoptions += `<option value="${subject.id}">${subject.name} ${subject.code ? "(" + subject.code + ")" : ""}</option>
            `;
            });

    if (subjectoptions === "") {
            subjectoptions = `<option value="">No subjects available</option>`;
        }


    content.innerHTML = `
        <h3>View Class Attendance</h3>
        <select id="viewClassId">
            ${classoptions}
        </select>

        <br><br>
        <label>Select Subject:</label><br>
                <select id="subjectFilter">
                    ${subjectoptions}
                </select>
        <br><br>

        <label>Select Date:</label><br>
                <input type="date" id="dateFilter">
        <br><br>

       <button onclick="loadClassAttendance()">
           View Attendance
       </button>
        <div id="attendanceTable"></div>
    `;
}

//LOAD ATTENDANCE LIST
async function loadClassAttendance(){
    const classId = document.getElementById("viewClassId").value;
    const attendanceTable = document.getElementById("attendanceTable");
    const subjectId = document.getElementById("subjectFilter").value;
     const date = document.getElementById("dateFilter").value;

    //const response = await fetch(`${BASE_URL}/api/attendance/class/${classId}`);
    if (!classId || !subjectId || !date) {
            alert("Please select class, subject, and date.");
            return;
        }

        const response = await fetch(
            `${BASE_URL}/api/attendance/class/${classId}/subject/date?subjectId=${subjectId}&date=${date}`
        );

        const records = await response.json();

        if (records.length === 0) {
            attendanceTable.innerHTML = "<p>No attendance records found</p>";
            return;
        }


    let html = `
        <table border="1" width="100%" style="margin-top:20px; border-collapse:collapse;">
            <tr>
                <th>Student Name</th>
                <th>Roll No</th>
                <th>Date</th>
                <th>Subject</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
    `;

    records.forEach(record => {
        html += `
            <tr>
                <td>${record.student.name}</td>
                <td>${record.student.rollNo}</td>
                <td>${record.date}</td>
                <td>${record.subject.name}</td>
                <td>${record.status}</td>
                <td>
                    <button onclick="editAttendance(${record.id}, '${record.status}')">
                    Edit
                    </button>
                    </td>
            </tr>
        `;
    });

    html += `</table>`;

    attendanceTable.innerHTML = html;
}

//VIEW REPORT (BUTTON)
async function viewClassReport(){
    const content = document.getElementById("content");
    const classes = await getAllClasses();
    const subjects = await getAllSubjects();

    let classoptions = "";

    classes.forEach(cls => {
        classoptions += `<option value="${cls.id}">${cls.className} - ${cls.year} Year</option>`;
    });

    let subjectoptions = "";
            subjects.forEach(subject => {
                subjectoptions += `<option value="${subject.id}">${subject.name} ${subject.code ? "(" + subject.code + ")" : ""}</option>
                `;
                });

        if (subjectoptions === "") {
                subjectoptions = `<option value="">No subjects available</option>`;
            }


    content.innerHTML = `
            <h3>View Class Report</h3>

            <label>Select Class:</label><br>
            <select id="reportClassId">
                ${classoptions}
            </select>

            <br><br>
        <label>Select Subject:</label><br>
                <select id="subjectFilter">
                    ${subjectoptions}
                </select>
        <br><br>
            <br><br>

            <button onclick="loadClassReport()">
                Load Report
            </button>

            <div id="reportTable"></div>
        `;

}

//LOAD REPORT
// REPLACE ONLY THIS FUNCTION

async function loadClassReport() {
    const classId = document.getElementById("reportClassId").value;
    const reportTable = document.getElementById("reportTable");

    // In your HTML, the subject dropdown id is "subjectFilter"
    const subjectId = document.getElementById("subjectFilter").value;

    if (!classId) {
        alert("Please select a class.");
        return;
    }

    // Subject is optional
    let url = `${BASE_URL}/api/attendance/class/${classId}/report`;

    // Add subject filter only if selected
    if (subjectId && subjectId !== "") {
        url += `?subjectId=${subjectId}`;
    }

    try {
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error("Failed to load report");
        }

        const reports = await response.json();

        if (reports.length === 0) {
            reportTable.innerHTML = "<p>No report found</p>";
            return;
        }

        let html = `
            <table border="1" width="100%" style="margin-top:20px; border-collapse:collapse;">
                <tr>
                    <th>Name</th>
                    <th>Roll No</th>
                    <th>Total Classes</th>
                    <th>Present</th>
                    <th>Absent</th>
                    <th>Percentage</th>
                </tr>
        `;

        reports.forEach(report => {
            html += `
                <tr>
                    <td>${report.name}</td>
                    <td>${report.rollNo}</td>
                    <td>${report.totalClasses}</td>
                    <td>${report.present}</td>
                    <td>${report.absent}</td>
                    <td>${report.percentage}%</td>
                </tr>
            `;
        });

        html += `</table>`;
        reportTable.innerHTML = html;

    } catch (error) {
        console.error("Error loading class report:", error);
        reportTable.innerHTML = "<p>Failed to load report</p>";
    }
}
//get teacher list
async function getTeacherClasses(){
    const teacherId = localStorage.getItem("userId");

    const response = await fetch(`${BASE_URL}/api/classes/teacher/${teacherId}`);
    return await response.json();
}
async function loadTeacherDashboard() {
    const dashboard = document.getElementById("dashboard");
    if (!dashboard) return;

    const teacherId = localStorage.getItem("userId");

    const response = await fetch(
        `${BASE_URL}/api/dashboard/teacher/${teacherId}`
    );
    const data = await response.json();

    dashboard.innerHTML = `
        <div class="dashboard-card"><h4>Assigned Classes</h4><p>${data.assignedClasses}</p></div>
        <div class="dashboard-card"><h4>Assigned Subjects</h4><p>${data.assignedSubjects}</p></div>
        <div class="dashboard-card"><h4>Total Students</h4><p>${data.totalStudents}</p></div>
        <div class="dashboard-card"><h4>Attendance Today</h4><p>${data.attendanceMarkedToday}</p></div>
        <div class="dashboard-card"><h4>Below 75%</h4><p>${data.lowAttendanceStudents}</p></div>
    `;
}


async function viewLowAttendance() {
    const content = document.getElementById("content");

    // Optional threshold input (defaults to 75)
    const threshold = prompt("Enter attendance threshold (%)", "75");

    // If user cancels the prompt
    if (threshold === null) {
        return;
    }

    const response = await fetch(
        `${BASE_URL}/api/attendance/low-attendance?threshold=${threshold}`
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
// Add this function to your main JavaScript file

async function editAttendance(attendanceId, currentStatus) {
    // Ask for new status
    const newStatus = prompt(
        "Enter new status (PRESENT or ABSENT)",
        currentStatus
    );

    // If user cancels
    if (newStatus === null) {
        return;
    }

    const status = newStatus.trim().toUpperCase();

    // Validate input
    if (status !== "PRESENT" && status !== "ABSENT") {
        alert("Invalid status. Please enter PRESENT or ABSENT.");
        return;
    }

    // Update attendance
    const response = await fetch(
        `${BASE_URL}/api/attendance/${attendanceId}`,
        {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ status: status })
        }
    );

    if (response.ok) {
        alert("Attendance updated successfully.");

        // Reload current attendance list
        // Change this if you want to reload a different view.
        viewClassAttendance();
    } else {
        const errorText = await response.text();
        alert("Failed to update attendance.\n" + errorText);
    }
}
