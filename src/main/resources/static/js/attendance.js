const BASE_URL = "https://onrender.com";

//mark attendance
async function showMarkAttendance(){
    const content = document.getElementById("content");
    const classes = await getTeacherClasses();

    let options = "";

    classes.forEach(cls => {
        options += `<option value="${cls.id}">${cls.className} - ${cls.year} Year</option>`;
    });

    content.innerHTML = `
        <h3>Mark Attendance</h3>
        <select id="classId">
            ${options}
        </select>
        <input type="text" id="subject" placeholder="Enter Subject">
        <button onclick="loadStudents()">Load Students</button>
        <div id="studentList"></div>
    `;
}

//TO VIEW CLASS ATTENDANCE (VIEW ATT BUTTON)
async function viewClassAttendance(){
    const content = document.getElementById("content");
    const classes = await getTeacherClasses();

    let options = "";

    classes.forEach(cls => {
        options += `<option value="${cls.id}">${cls.className} - ${cls.year} Year</option>`;
    });

    content.innerHTML = `
        <h3>View Class Attendance</h3>
        <select id="viewClassId">
            ${options}
        </select>
        <button onclick="loadClassAttendance()">View Attendance</button>
        <div id="attendanceTable"></div>
    `;
}

//VIEW REPORT (BUTTON)
async function viewClassReport(){
    const content = document.getElementById("content");
    const classes = await getTeacherClasses();

    let options = "";

    classes.forEach(cls => {
        options += `<option value="${cls.id}">${cls.className} - ${cls.year} Year</option>`;
    });

    content.innerHTML = `
        <h3>View Class Report</h3>
        <select id="reportClassId">
            ${options}
        </select>
        <button onclick="loadClassReport()">Load Report</button>
        <div id="reportTable"></div>
    `;
}
