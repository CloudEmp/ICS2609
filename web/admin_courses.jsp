<%-- 
    Document   : admin_courses
    Created on : 04 29, 24, 8:24:01 PM
    Author     : Andrea
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
    <% response.setHeader("Pragma", "no-cache"); %>
    <% response.setDateHeader("Expires", 0); %>
    <%
        if (session.getAttribute("usernamesession") == null) {
            response.sendRedirect("index.jsp");
        } else if (session.getAttribute("usernamesession") != null && session.getAttribute("captchasession") == null) {
            response.sendRedirect("CaptchaServlet");
        } else if (session.getAttribute("usernamesession") != null && session.getAttribute("userrolesession") == "Student") {
            response.sendRedirect("guest_courses.jsp");
        } else if (session.getAttribute("usernamesession") != null && session.getAttribute("coursessession") == null) {
            response.sendRedirect("CourseServlet");
        }
        session.setAttribute("coursessession", null);
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Active Learning</title>

        <link href="https://db.onlinewebfonts.com/c/7515664cb5fad83d8ce956ad409ccbb7?family=Helvetica+Rounded+LT+Std+Bold" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet'>
        <link rel="stylesheet" href="css/admin_courses.css">
        <style>
            .modal {
                display: block;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0, 0, 0, 0.5);
                z-index: 2;
            }

            .modal-content {
                background-color: white;
                margin: 10% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 600px;
                position: relative;
            }

            .close {
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 20px;
                cursor: pointer;
            }
        </style>
    </head>

    <body>
        <div id="header">
            <p style="font-family: Courier New;"><% out.print(getServletContext().getInitParameter("name")); %> <% out.print(getServletContext().getInitParameter("section"));%></p>
        </div>
        <h1 class="al">al.</h1>

        <div class="container-course">
            <p1 class="hello">Hello, <%= session.getAttribute("fullnamesession")%>!</p1>
            <p1 class="role"><%= session.getAttribute("userrolesession")%></p1>
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

                <form action="LoginServlet" method="post">
                    <input id="logout" type="submit" value="Logout" name="logout">
                </form>

            </div>
        </div>


        <div class="container2-course">
            <form action="CourseServlet" method="get">
                <input id="courses" type="submit" name="place" value="MyCourses">
            </form>

            <form action="CourseServlet" method="get">
                <input id="enroll" type="submit" name="place" value="Enrollment">
            </form>

            <form action="download_record.jsp" method="get">
                <input id="download-record" type="submit" value="download record">
            </form>
        </div>

        <div  class="container4-course">
            <img id="image-bg" src="images/bg-admin.png" class="box" alt="bg image"/>
        </div>

        <%
            try {
                ArrayList<ArrayList<String>> courses = (ArrayList<ArrayList<String>>) request.getAttribute("courses");
                if (!courses.isEmpty()) {
                    int counter = 1;
                    for (ArrayList<String> course : courses) {
        %>
        <div class="gc-container-<%=counter%>">
            <a href="<%= course.get(4)%>" target="_blank">
                <img src="<%= course.get(5)%>" class="img-python"></a>
            <p class="gc-python"><%= course.get(0)%></p>
            <img src="images/clock.png" class="img-clock">
            <p class="duration"><%= course.get(3)%> hours</p>
            <img src="images/enrollees.png" class="img-enrollees" onclick="loadEnrolledStudents('<%= course.get(0)%>')">
        </div>
        <%
                        counter++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>


        <script>
            function loadEnrolledStudents(course) {
                fetch("EnrolledServlet?course=" + course)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error("Network response was not ok");
                            }
                            return response.text();
                        })
                        .then(data => {
                            showModal(data);
                        })
                        .catch(error => {
                            console.error("There was a problem with the fetch operation:", error);
                        });
            }

            function showModal(content) {
                var modal = document.createElement('div');
                modal.classList.add('modal');
                modal.innerHTML = '<div class="modal-content">' + content + '<span class="close" onclick="closeModal()">&times;</span></div>';
                document.body.appendChild(modal);
            }

            function closeModal() {
                var modals = document.getElementsByClassName('modal');
                for (var i = 0; i < modals.length; i++) {
                    modals[i].remove();
                }
            }
        </script>

        <div id="footer"
             <p style="font-family: Courier New;"><% out.print(getServletContext().getAttribute("date"));%> <% out.print(getServletContext().getInitParameter("subject")); %> <% out.print(getServletContext().getInitParameter("mp"));%></p>
    </div>
</body>
</html>

