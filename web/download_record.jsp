<%-- 
    Document   : download_record
    Created on : 04 30, 24, 12:10:29 AM
    Author     : Andrea
--%>

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
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Active Learning</title>

        <link href="https://db.onlinewebfonts.com/c/7515664cb5fad83d8ce956ad409ccbb7?family=Helvetica+Rounded+LT+Std+Bold" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet'>
        <link rel="stylesheet" href="css/admin_enrollment.css">
    </head>

    <body>
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
                    <input id="logout" type="submit" value="logout" name="logout">
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


        <form action="ReportsPDF" method="post" target="pdfFrame">
            <input type="hidden" name="download" value="false">
            <input id="my-record" class="record-button" type="submit" value="My Record" name="reports">
        </form>

        <form action="ReportsPDF" method="post" target="pdfFrame">
            <input type="hidden" name="download" value="true">
            <input id="my-record2" class="record-button2" type="submit" value="↓" name="reports">
        </form>

        <form action="ReportsPDF" method="post" target="pdfFrame">
            <input type="hidden" name="download" value="false">
            <input id="all-records" class="record-button" type="submit" value="All Records" name="reports">
        </form>

        <form action="ReportsPDF" method="post" target="pdfFrame">
            <input type="hidden" name="download" value="true">
            <input id="all-records2" class="record-button2" type="submit" value="↓" name="reports">
        </form>

        <form action="ReportsPDF" method="post" target="pdfFrame">
            <input type="hidden" name="download" value="false">
            <input id="courses" class="record-button" type="submit" value="Courses" name="reports">
        </form>

        <form action="ReportsPDF" method="post" target="pdfFrame">
            <input type="hidden" name="download" value="true">
            <input id="courses2" class="record-button2" type="submit" value="↓" name="reports">
        </form>

        <br>

        <iframe name="pdfFrame" width="100%" height="600"></iframe>

        <script>
            function resizeIframe() {
                var iframe = document.getElementById('pdfFrame');
                if (iframe.contentWindow.document.body) {
                    iframe.style.height = iframe.contentWindow.document.body.scrollHeight + 'px';
                }
            }

            window.addEventListener('resize', resizeIframe);
            document.addEventListener('DOMContentLoaded', resizeIframe);
        </script>

    </div>

</body>
</html>
