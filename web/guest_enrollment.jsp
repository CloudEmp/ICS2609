<%-- 
    Document   : guest
    Created on : 04 29, 24, 3:59:00 PM
    Author     : Micah
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
        } else if (session.getAttribute("usernamesession") != null && session.getAttribute("userrolesession") == "Instructor") {
            response.sendRedirect("admin_courses.jsp");
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Active Learning</title>
        <link rel="stylesheet" href="css/guest_enrollment.css">
        <link href="https://use.typekit.net/oov2wcw.css" rel="stylesheet">
    </head>

    <body>
        <h1 class="al">al.</h1>
        <div class="cover"></div>
        <div class="bg"></div>

        <a href="guest_courses.jsp" class="my-course-button">
            <div class="my-course-box"></div>
            <span class="mycourses-text">My Courses</span>
        </a>

        <a href="guest_enrollment.jsp" class="enrollment-button">
            <div class="enrollment-box"></div>
            <span class="enrollment-text">Enrollment</span>
        </a>

        <div class="profile">
            <img src="images/pochacco.jpg">
        </div>

    <p1 class="hello">Hello, <%= session.getAttribute("username")%>!</p1>
    <p1 class="role"><%= session.getAttribute("role")%></p1>
    <div class="line"></div>

    <div class="navbar-item">
        <a href="guest_courses.jsp" class="navbar-item-text">
            <img src="images/courses.png" class="img-courses">
            Courses
        </a>
    </div>

    <div class="navbar-item">
        <a href="https://activelearning.ph/news/" class="navbar-item-text">
            <img src="images/news.png" class="img-news">
            News
        </a>
    </div>

    <div class="navbar-item">
        <a href="https://activelearning.ph/careers/" class="navbar-item-text">
            <img src="images/careers.png" class="img-careers">
            Careers
        </a>
    </div>

    <div class="navbar-item">
        <a href="https://activelearning.ph/about/" class="navbar-item-text">
            <img src="images/company.png" class="img-comp">
            The Company
        </a>
    </div>

    <div class="navbar-item">
        <a href="https://activelearning.ph/contact/" class="navbar-item-text">
            <img src="images/contactus.png" class="img-contact">
            Contact Us
        </a>
    </div>

    <a href="index.jsp" class="logout-button">
        <div class="logout-box"></div>
        <span class="logout-text">Logout</span>
    </a>

    <div class="ge-container-1">
        <main class="table">
            <section class="table_header">
                <h1>

                </h1>
            </section>
            <section class="table_body">
                <table>
                    <thead>
                        <tr>
                            <th> Available Courses </th>
                            <th> Start Date </th>
                            <th> End Date </th>
                            <th> Add Course </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="course-name"> <img src="images/dropdown.png" class="img-dropdown"> <strong> Laravel Framework </strong> </td>
                            <td> 01/01/24 </td>
                            <td> 01/30/24 </td>
                            <td> <img src="images/plus.png"/> </td>
                        </tr>
                        <tr class="course-description" style="display: none;">
                            <td colspan="4">
                                <p>Laravel Framework is an open-source PHP class library designed to support the development of dynamic websites, Web applications and Web services. 
                                    The framework aims to alleviate the overhead associated with common activities used in Web development such as database access, authentication, templating, allowing you to focus on your application’s specific requirements. 
                                    By the end of the course, students will have completed a secure, Create, Read, Update, Delete (CRUD) application.</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="course-name"> <img src="images/dropdown.png" class="img-dropdown">  <strong>Advanced Python Programming</strong> </td>
                            <td> 07/01/24 </td>
                            <td> 08/01/24 </td>
                            <td> <img src="images/plus.png"/> </td>
                        </tr>
                        <tr class="course-description" style="display: none;">
                            <td colspan="4">
                                <p>Python is a widely used, open-source programming language that is especially suited for a wide range of applications including web development, machine learning, and data science.
                                    In this Python Programming course, you will learn advanced concepts like Object-Oriented concepts, exception handling and the basics of web application development using Django.</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="course-name"> <img src="images/dropdown.png" class="img-dropdown">  <strong>Microsoft Excel Advanced</strong> </td>
                            <td> 08/01/24 </td>
                            <td> 09/01/24 </td>
                            <td> <img src="images/plus.png"/> </td>
                        </tr>
                        <tr class="course-description" style="display: none;">
                            <td colspan="4">
                                <p>In this Microsoft Excel Advanced Training course, you will learn how to maximize Excel even further to help you analyze data quicker. 
                                    You will learn features like PivotTables, macros, and how to protect your spreadsheets from being misused by others.</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="course-name"> <img src="images/dropdown.png" class="img-dropdown">  <strong>User Experience(UX)</strong> </td>
                            <td> 07/10/24 </td>
                            <td> 08/10/24 </td>
                            <td> <img src="images/plus.png"/> </td>
                        </tr>
                        <tr class="course-description" style="display: none;">
                            <td colspan="4">
                                <p>This boot-camp is fast-paced, hands-on and interactive. 
                                    It has been designed to focus on “learning by doing” rather than “learning by listening”.</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="course-name"> <img src="images/dropdown.png" class="img-dropdown">  <strong>CompTIA Security+</strong> </td>
                            <td> 10/01/24 </td>
                            <td> 11/01/24 </td>
                            <td> <img src="images/plus.png"/> </td>
                        </tr>
                        <tr class="course-description" style="display: none;">
                            <td colspan="4">
                                <p>CompTIA Security+ provides a global benchmark for best practices in IT network and operational security, one of the fastest-growing fields in IT.</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="course-name"> <img src="images/dropdown.png" class="img-dropdown">  <strong>Microsoft Excel Essentials</strong> </td>
                            <td> 09/20/24 </td>
                            <td> 09/30/24 </td>
                            <td> <img src="images/plus.png"/> </td>
                        </tr>
                        <tr class="course-description" style="display: none;">
                            <td colspan="4">
                                <p>Microsoft Excel is the industry leading tool to help workers work with data on the desktop. 
                                    This Microsoft Excel training course enables you to become more productive by allowing you to automate data processing essential to your daily work.</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="course-name"> <img src="images/dropdown.png" class="img-dropdown">  <strong>ITIL 4 Foundation Certification Program</strong> </td>
                            <td> 02/14/24 </td>
                            <td> 02/29/24 </td>
                            <td> <img src="images/plus.png"/> </td>
                        </tr>
                        <tr class="course-description" style="display: none;">
                            <td colspan="4">
                                <p>ITIL 4 Foundation is designed as an introduction to ITIL 4 and enables candidates to look at IT service management through an end-to-end operating model for the creation, delivery, and continual improvement of tech-enabled products.</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="course-name"> <img src="images/dropdown.png" class="img-dropdown">  <strong>Agile Project Management with Scrum</strong> </td>
                            <td> 03/01/24 </td>
                            <td> 04/01/24 </td>
                            <td> <img src="images/plus.png"/> </td>
                        </tr>
                        <tr class="course-description" style="display: none;">
                            <td colspan="4">
                                <p>In this Agile and Scrum training course, you will learn how to apply Agile and Scrum techniques to deliver value to project stakeholders in short amounts of time. 
                                    ActiveLearning’s Agile Scrum training Through lectures and simulations, this course is ideal if you are looking have a firm foundation of how agile and scrum methodologies can be put into practice. 
                                    This training will also help you prepare for the Professional Scrum Master (PSM) certification, which is one of the most recognized agile project management certifications in the world.</p>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </section>
        </main>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const courseRows = document.querySelectorAll('.course-name');

            courseRows.forEach(row => {
                row.addEventListener('click', function () {
                    const description = this.parentElement.nextElementSibling;

                    if (description.style.display === 'none' || description.style.display === '') {
                        description.style.display = 'table-row';
                    } else {
                        description.style.display = 'none';
                    }
                });
            });
        });
    </script>

</body>

</html>
