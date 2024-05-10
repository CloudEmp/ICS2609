<%-- 
    Document   : admin_enrollment
    Created on : 04 29, 24, 8:24:30 PM
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
                        <img src="images/courses.png" class="img-courses">
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
                        <img src="images/contactus.png" class="img-contact">
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
                            <%
                                try {
                                    ArrayList<ArrayList<String>> courses = (ArrayList<ArrayList<String>>) request.getAttribute("courses");
                                    for (ArrayList<String> course : courses) {
                            %>
                            <tr>
                                <td><%= course.get(0)%></td>
                                <td><%= course.get(1)%></td>
                                <td><%= course.get(2)%></td>
                                <td><form action="CourseServlet" method="post">
                                        <input type="hidden" name="course" value="<%= course.get(0)%>">
                                        <button type="submit" name="option" value="Delete" style="border: none; background: none; padding: 0;">
                                            <img src="images/delete.png" alt="Delete" style="width: 30px; height: 26px; margin-left: 38px;">
                                        </button>                                        </form></td>
                            </tr>
                            <%
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            %>
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
        </script>

        <!-- input record -->
        <div id="modal" style="display: none;" class="modal">
            <div class="modal-content">
                <form action="CourseServlet" method="POST">
                    <label for="courseName">Course Name:</label>
                    <select id="course" name="course" required style="width: 75%;">
                        <option value="Laravel Framework">Laravel Framework</option>
                        <option value="Advanced Python Programming">Advanced Python Programming</option>
                        <option value="Microsoft Excel Advanced">Microsoft Excel Advanced</option>
                        <option value="User Experience(UX)">User Experience(UX)</option>
                        <option value="CompTIA Security">CompTIA Security</option>
                        <option value="Microsoft Excel Essentials">Microsoft Excel Essentials</option>
                        <option value="ITIL 4 Foundation Certification Program">ITIL 4 Foundation Certification Program</option>
                        <option value="Agile Project Management with Scrum">Agile Project Management with Scrum</option>
                    </select>
                    <br><br>
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startdate" required>
                    <br><br>
                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="enddate" required>
                    <br><br>
                    <input type="submit" onclick="addCourse()" name="option" value="Add">
                </form>

                <span class="close" onclick="toggleModal()">&times;</span>
            </div>
        </div>


        <div  class="container4-course">
            <img id="image-bg" src="images/bg-admin.png" class="box" alt="bg image"/>

        </div>

        <% if (request.getAttribute("limitreached") != null) {%>
        <script>
            alert('<%= request.getAttribute("limitreached")%>');
        </script>
        <% } %>

        <% if (request.getAttribute("invaliddate") != null) {%>
        <script>
            alert('<%= request.getAttribute("invaliddate")%>');
        </script>
        <% }%>

        <% if (request.getAttribute("handledalready") != null) {%>
        <script>
            alert('<%= request.getAttribute("handledalready")%>');
        </script>
        <% }%>

        <div id="footer"
             <p style="font-family: Courier New;"><% out.print(getServletContext().getAttribute("date"));%> <% out.print(getServletContext().getInitParameter("subject")); %> <% out.print(getServletContext().getInitParameter("mp"));%></p>
    </div>
</body>
</html>
