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
        } else if (session.getAttribute("usernamesession") != null && session.getAttribute("coursessession") == null) {
            response.sendRedirect("StudentServlet");
        }
        session.setAttribute("coursessession", null);
    %>



    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Active Learning</title>

        <link href="https://db.onlinewebfonts.com/c/7515664cb5fad83d8ce956ad409ccbb7?family=Helvetica+Rounded+LT+Std+Bold" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet'>
        <link rel="stylesheet" href="css/guest_courses.css">
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
            <form action="StudentServlet" method="get">
                <input id="courses" type="submit" name="place" value="MyCourses">
            </form>

            <form action="StudentServlet" method="get">
                <input id="enroll" type="submit" name="place" value="Enrollment">
            </form>



        </div>

        <div  class="container4-course">
            <img id="image-bg" src="images/bg-admin.png" class="box" alt="bg image"/>

        </div>


        <%
            try {
                ArrayList<ArrayList<String>> studentCourses = (ArrayList<ArrayList<String>>) request.getAttribute("studentCourses");
                if (!studentCourses.isEmpty()) {
                    int counter = 1;
                    for (ArrayList<String> course : studentCourses) {
        %>
        <div class="gc-container-<%=counter%>">
            <a href="<%= course.get(5)%>" target="_blank">
                <img src="<%= course.get(6)%>" class="img-python"></a>
            <p class="gc-python"><%= course.get(0)%></p>
            <img src="images/clock.png" class="img-clock">
            <p class="duration"><%= course.get(4)%> hours</p>      
        </div>
        <%
                counter++;
            }
        } else {
        %>
        <!-- not enrolled -->
        <div class="default-message">
            <img src="images/qtpochacco.png" alt="default pochacco">
            <p>You haven't enrolled in a course yet</p>
        </div>
        <button class="find" onclick="window.open('https://activelearning.ph/courses/', '_blank')">Find course</button>
        <%
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>



        <%
            String enrollmentLimitReached = (String) session.getAttribute("enrollmentLimitReached");
            if (enrollmentLimitReached != null) {
        %>
        <script>
            alert('<%= enrollmentLimitReached%>');
        </script>
        <%
                session.removeAttribute("enrollmentLimitReached");
            }
        %>



        <div id="footer"
             <p style="font-family: Courier New;"><% out.print(getServletContext().getAttribute("date"));%> <% out.print(getServletContext().getInitParameter("subject")); %> <% out.print(getServletContext().getInitParameter("mp"));%></p>
    </div>
</body>
</html>
