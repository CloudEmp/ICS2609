<%-- 
    Document   : error_4
    Created on : 05 1, 24, 3:40:49 PM
    Author     : Lennard Flores
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
    <% response.setHeader("Pragma", "no-cache"); %>
    <% response.setDateHeader("Expires", 0); %>
    <%
        if (session.getAttribute("usernamesession") != null && session.getAttribute("captchasession") != null && session.getAttribute("userrole") == "Instructor") {
            response.sendRedirect("admin_courses.jsp");
        } else if (session.getAttribute("usernamesession") != null && session.getAttribute("captchasession") != null && session.getAttribute("userrole") == "Student") {
            response.sendRedirect("guest_courses.jsp");
        } else if (session.getAttribute("usernamesession") != null) {
            response.sendRedirect("CaptchaServlet");
        }
    %>
    <head>
        <meta charset="UTF-8">
        <title>Error - Missing Login Credentials</title>
        <link href="https://db.onlinewebfonts.com/c/7515664cb5fad83d8ce956ad409ccbb7?family=Helvetica+Rounded+LT+Std+Bold" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet'>
        <link rel="stylesheet" href="css/error.css">
    </head>
    <body>
        <div id="header">
            <p style="font-family: Courier New;"><% out.print(getServletContext().getInitParameter("name")); %> <% out.print(getServletContext().getInitParameter("section"));%></p>
        </div>

        <div class="site-login">al.</div>

        <!--  <form action="https://activelearning.ph/contact/" method="get" target="_blank">
              <input id="contact-login" type="submit" value="Contact Us">
          </form> -->
        <img src="https://i.postimg.cc/YC3DVFY3/POchacho-error.png" alt="Girl in a jacket">

        <div class="Error-message">
            <h1>Oopss!<h1>
            <h2>Missing Login Credentials</h2>
                <p>You've left both the username and password fields blank.</p>
                <p>To proceed, please provide your username and password.</p>
        </div>

        <!--<div class="container1-login"></div> --->
        <!-- <div class="container2-login"></div> -->
        <div class="container3-login">

            <form action="index.jsp" method="post">
                <input type="submit" value="Return">
            </form>
        </div>

        <div id="footer"
            <p style="font-family: Courier New;"><% out.print(getServletContext().getAttribute("date"));%> <% out.print(getServletContext().getInitParameter("subject")); %> <% out.print(getServletContext().getInitParameter("mp"));%></p>
        </div>
    </body>
</html>
