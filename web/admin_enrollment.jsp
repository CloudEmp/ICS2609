<%-- 
    Document   : admin_enrollment
    Created on : 04 29, 24, 8:24:30 PM
    Author     : Andrea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Active Learning</title>

        <link href="https://db.onlinewebfonts.com/c/7515664cb5fad83d8ce956ad409ccbb7?family=Helvetica+Rounded+LT+Std+Bold" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet'>
        <link rel="stylesheet" href="css/admin_enrollment.css">
        <style>
            .modal-content {
                background-image: url(images/classroom.png);
                background-size: cover;
                border-radius: 15px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
                width: 400px;
                padding: 30px;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }
        </style>
    </head>

    <body>
        <h1 class="al">al.</h1>

        <div class="container-course">
            <p1 class="hello">Hello, <%= session.getAttribute("username")%>!</p1>
            <p1 class="role"><%= session.getAttribute("role")%></p1>
        </div>

        <div class="container1-course">

            <img id="image-con" src="images/container1-admin.png" class="box" alt="bg image"/>
            <img id="cover" src="images/cover.png" class="box" alt="cover classroom"/>
            <img id="profile" src="images/profile.png" class="box" alt="profile picture"/>
            <img id="line" src="images/line.png" class="box" alt="separator"/>

            <div class="con-item">
                <div class="navbar-item">
                    <a href="https://activelearning.ph/courses/" class="navbar-item-text" target="_blank">
                        <img src="images/book.png" class="img-courses">
                        Courses
                    </a>
                </div>

                <div class="navbar-item">
                    <a href="https://activelearning.ph/news/" class="navbar-item-text" target="_blank">
                        <img src="images/news.png" class="img-news">
                        News
                    </a>
                </div>

                <div class="navbar-item">
                    <a href="https://activelearning.ph/careers/" class="navbar-item-text" target="_blank">
                        <img src="images/careers.png" class="img-careers">
                        Careers
                    </a>
                </div>

                <div class="navbar-item">
                    <a href="https://activelearning.ph/about/" class="navbar-item-text" target="_blank">
                        <img src="images/company.png" class="img-comp">
                        The Company
                    </a>
                </div>

                <div class="navbar-item">
                    <a href="https://activelearning.ph/contact/" class="navbar-item-text" target="_blank">
                        <img src="images/contact.png" class="img-contact">
                        Contact Us
                    </a>
                </div>

                <form action="index.jsp" method="get">
                    <input id="logout" type="submit" value="Logout">
                </form>

            </div>
        </div>


        <div class="container2-course">
            <form action="admin_courses.jsp" method="get">
                <input id="courses" type="submit" value="My Courses">
            </form>

            <form action="admin_enrollment.jsp" method="get">
                <input id="enroll" type="submit" value="Enrollment">
            </form>

            <form action="download_record.jsp" method="get">
                <input id="download-record" type="submit" value="download record">
            </form>

        </div>


        <div class="container3-course">

            <input id="create-course" type="button" value="create course" onclick="toggleModal()">

            <main class="table">
                <section class="table_header">
                    <h1>

                    </h1>
                </section>
                <section class="table_body">
                    <table id="courseTable">
                        <thead>
                            <tr>
                                <th> Courses</th>
                                <th> Start Date </th>
                                <th> End Date </th>
                                <th> Delete Course </th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </section>
            </main>
        </div>


        <script>
            function toggleModal() {
                var modal = document.getElementById('modal');
                modal.style.display = modal.style.display === 'none' ? 'block' : 'none';
            }


            function addCourse() {
                var courseName = document.getElementById("courseName").value;
                var startDate = document.getElementById("startDate").value;
                var endDate = document.getElementById("endDate").value;

                // Convert the date strings to Date objects for comparison
                var startDateObj = new Date(startDate);
                var endDateObj = new Date(endDate);

                if (!startDate || !endDate) {
                    alert("Please enter both start and end dates.");
                    return; // Exit the function without adding the course
                }

                // Check if the start date is later than the end date
                if (startDateObj > endDateObj) {
                    alert("Start date cannot be later than end date.");
                    return; // Exit the function without adding the course
                }

                var table = document.getElementById("courseTable");
                var newRow = table.insertRow(-1);
                var cell1 = newRow.insertCell(0);
                var cell2 = newRow.insertCell(1);
                var cell3 = newRow.insertCell(2);
                var cell4 = newRow.insertCell(3);

                cell1.innerHTML = courseName;
                cell2.innerHTML = startDate;
                cell3.innerHTML = endDate;
                cell4.innerHTML = '<img src="images/delete.png" alt="Delete" onclick="deleteCourse(this)" style="width: 30px; height: 26px;">';

                // Close the modal after adding the course
                toggleModal();

                // Clear the input fields
                document.getElementById("courseName").value = "";
                document.getElementById("startDate").value = "";
                document.getElementById("endDate").value = "";
            }

            function deleteCourse(row) {
                var i = row.parentNode.parentNode.rowIndex;
                document.getElementById("courseTable").deleteRow(i);
            }
        </script>


        <!-- input record -->
        <div id="modal" class="modal">
            <div class="modal-content">
                <form>
                    <label for="courseName">Course Name:</label>
                    <input type="text" id="courseName">
                    <br><br>
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate">
                    <br><br>
                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate">
                    <br><br>
                    <button type="button" onclick="addCourse()">Add</button>
                </form>

                <span class="close" onclick="toggleModal()">&times;</span>
            </div>
        </div>


        <div  class="container4-course">
            <img id="image-bg" src="images/bg-admin.png" class="box" alt="bg image"/>

        </div>



    </body>
</html>
