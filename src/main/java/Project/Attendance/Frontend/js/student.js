const BASE_URL = "http://localhost:8080";

window.onload = function(){
    const studentWelcome = document.getElementById("studentWelcome");
    if(studentWelcome){
        const name = localStorage.getItem("userName");
        studentWelcome.innerText = "Welcome " + name;
    }
}
//logout
function logout(){
    window.location.href = "index.html";
}

// STUDENT -> VIEW ATTENDANCE (WITH SUBJECT FILTER)
async function viewMyAttendance() {
    const studentId = localStorage.getItem("userId");
    const content = document.getElementById("content");

    // Get all attendance records
    const response = await fetch(`${BASE_URL}/api/attendance/student/${studentId}`);
    const records = await response.json();

    if (records.length === 0) {
        content.innerHTML = "<p>No attendance records found</p>";
        return;
    }

    // Get unique subjects
    const subjects = [...new Set(records.map(record => record.subject?.name || record.subject || "Unknown"))];

    // Build HTML
    let html = `
        <h3>My Attendance</h3>

        <label><b>Filter by Subject:</b></label>
        <select id="attendanceSubjectFilter" onchange="filterAttendanceTable()">
            <option value="ALL">All Subjects</option>
    `;

    subjects.forEach(subject => {
        html += `<option value="${subject}">${subject}</option>`;
    });

    html += `
        </select>

        <table id="attendanceTable"
               border="1"
               width="100%"
               style="margin-top:20px; border-collapse:collapse;">
            <tr>
                <th>Date</th>
                <th>Subject</th>
                <th>Status</th>
            </tr>
    `;

    records.forEach(record => {
        const subject = record.subject?.name || record.subject || "Unknown";
        html += `
            <tr data-subject="${subject}">
                <td>${record.date}</td>
                <td>${subject}</td>
                <td>${record.status}</td>
            </tr>
        `;
    });

    html += `</table>`;

    content.innerHTML = html;
}

// FILTER ATTENDANCE TABLE BY SUBJECT
function filterAttendanceTable() {
    const selectedSubject =
        document.getElementById("attendanceSubjectFilter").value.trim().toLowerCase();

    const rows = document.querySelectorAll("#attendanceTable tr[data-subject]");

    rows.forEach(row => {
        const subject =
            row.getAttribute("data-subject").trim().toLowerCase();

        if (selectedSubject === "all" || subject === selectedSubject) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
}

// STUDENT -> MY REPORT
async function viewMyReport() {
    const studentId = localStorage.getItem("userId");
    const content = document.getElementById("content");

    const response = await fetch(`${BASE_URL}/api/attendance/student/${studentId}/report`);
    const report = await response.json();

    content.innerHTML = `
        <h3>My Attendance Report</h3>
        <p><b>Name:</b> ${report.name}</p>
        <p><b>Roll No:</b> ${report.rollNo}</p>
        <p><b>Total Classes:</b> ${report.totalClasses}</p>
        <p><b>Present:</b> ${report.present}</p>
        <p><b>Absent:</b> ${report.absent}</p>
        <p><b>Percentage:</b> ${report.percentage}%</p>
    `;
}

// STUDENT -> SUBJECT WISE REPORT
async function viewSubjectReport() {
    const studentId = localStorage.getItem("userId");
    const content = document.getElementById("content");

    const response = await fetch(
        `${BASE_URL}/api/attendance/student/${studentId}/subject-report`
    );
    const reports = await response.json();

    if (reports.length === 0) {
        content.innerHTML = "<p>No subject report found</p>";
        return;
    }

    let html = `
        <h3>Subject Wise Attendance Report</h3>

        <table border="1"
               width="100%"
               style="margin-top:20px; border-collapse:collapse;">
            <tr>
                <th>Subject</th>
                <th>Total Classes</th>
                <th>Present</th>
                <th>Absent</th>
                <th>Percentage</th>
            </tr>
    `;

   reports.forEach(report => {
       const totalClasses = report.totalClasses ?? report.total ?? 0;
       const present = report.present ?? 0;
       const absent = report.absent ?? (totalClasses - present);
       const percentage = report.percentage ?? 0;
       const subject = report.subject?.name || report.subject || "Unknown";

         html += `
                <tr>
                    <td>${subject}</td>
                    <td>${totalClasses}</td>
                    <td>${present}</td>
                    <td>${absent}</td>
                    <td>${percentage}%</td>
                </tr>
            `;
    });

    html += `</table>`;

    content.innerHTML = html;
}

async function loadStudentDashboard() {
    const dashboard = document.getElementById("dashboard");
    if (!dashboard) return;

    const studentId = localStorage.getItem("userId");

    const response = await fetch(
        `${BASE_URL}/api/dashboard/student/${studentId}`
    );
    const data = await response.json();

    dashboard.innerHTML = `
        <div class="dashboard-card"><h4>Total Classes</h4><p>${data.totalClasses}</p></div>
        <div class="dashboard-card"><h4>Present</h4><p>${data.present}</p></div>
        <div class="dashboard-card"><h4>Absent</h4><p>${data.absent}</p></div>
        <div class="dashboard-card"><h4>Attendance %</h4><p>${data.percentage}%</p></div>
        <div class="dashboard-card"><h4>Subjects Below 75%</h4><p>${data.subjectsBelow75}</p></div>
    `;
}