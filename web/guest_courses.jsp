<%-- 
    Document   : guest
    Created on : 04 29, 24, 3:59:00 PM
    Author     : Micah
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Active Learning</title>
        <link rel="stylesheet" href="css/guest_courses.css">
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
        
        <p1 class="hello">Hello, <%= session.getAttribute("username") %>!</p1>
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
        
        <a href="https://activelearning.ph/course/basic-python-training-philippines/">
        <div class="gc-container-1">
            <img src="images/basicpython.png" class="img-python">
            <p class="gc-python">Basic Python Programming</p>
            <img src="images/clock.png" class="img-clock">
            <p class="duration">20 hours</p>      
        </div>
        </a>
        
    </body>
    
</html>
