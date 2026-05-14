const BASE_URL = "http://localhost:8080";

//register student
 async function registerStudent(){

 const password = document.getElementById("rpassword").value;
 const confirmPassword = document.getElementById("rconfirm").value;

 if(password !== confirmPassword){
     alert("Passwords do not match");
     return;
 }

     const studentData = {
         name: document.getElementById("rname").value,
         rollNo: document.getElementById("rroll").value,
         branch: document.getElementById("rbranch").value,
         semester: document.getElementById("rsemester").value,
         year: document.getElementById("ryear").value,
         classId: document.getElementById("rclassid").value,
         password: password                                                           //document.getElementById("rpassword").value
     };

     const response = await fetch(`${BASE_URL}/api/auth/student/register`,{
         method:"POST",
         headers:{"Content-Type":"application/json"},
         body: JSON.stringify(studentData)
     });

     const result = await response.text();

     alert(result);

     if(result.includes("successfully")){
         window.location.href = "index.html";
     }
 }

 //add load class to replace idnumber to class name
 async function loadClassesForRegister(){

     const classDropdown = document.getElementById("rclassid");

     if(!classDropdown){
         return;
     }

     const response = await fetch(`${BASE_URL}/api/classes`);
     const classes = await response.json();

     classes.forEach(cls => {

         const option = document.createElement("option");

         option.value = cls.id;
         option.text =
             `${cls.className} - ${cls.year} Year`;

         classDropdown.appendChild(option);
     });
 }
 //semesteroptions for year in registration
 function updateSemesterOptions(){

     const year = document.getElementById("ryear").value;
     const semesterDropdown = document.getElementById("rsemester");

     semesterDropdown.innerHTML =
         '<option value="">Select Semester</option>';

     let semesters = [];

     if(year === "1st"){
         semesters = ["1", "2"];
     }
     else if(year === "2nd"){
         semesters = ["3", "4"];
     }
     else if(year === "3rd"){
         semesters = ["5", "6"];
     }
     else if(year === "4th"){
         semesters = ["7", "8"];
     }

     semesters.forEach(sem => {

         const option = document.createElement("option");

         option.value = sem;
         option.text = sem;

         semesterDropdown.appendChild(option);
     });
 }

 //sync register to adminregister
 function openStudentRegister(){
     window.location.href = "register.html";
 }
