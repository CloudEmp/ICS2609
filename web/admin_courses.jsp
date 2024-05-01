<%-- 
    Document   : admin_courses
    Created on : 04 29, 24, 8:24:01 PM
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
        <link rel="stylesheet" href="css/admin_courses.css">
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

        <div  class="container4-course">
            <img id="image-bg" src="images/bg-admin.png" class="box" alt="bg image"/>

        </div>


        <div class="gc-container-1">
            <a href="https://activelearning.ph/course/basic-python-training-philippines/" target="_blank">
                <img src="images/basicpython.png" class="img-python"></a>
            <p class="gc-python">Basic Python Programming</p>
            <img src="images/clock.png" class="img-clock">
            <p class="duration">20 hours</p>      
            <img src="images/enrollees.png" onclick="openModal()" class="img-enrollees">

        </div>


        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h2>List of Enrolled Students</h2>

                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- ganitu na muna since wala pa db ^_^ -->
                            <tr>
                                <td>Toua Tokuchi</td>
                                <td>2024-04-30</td>
                            </tr>
                            <tr>
                                <td>Kim Sunoo</td>
                                <td>2024-04-29</td>
                            </tr>
                            <tr>
                                <td>Hiroki Dan</td>
                                <td>2024-04-29</td>
                            </tr>
                            <tr>
                                <td>Kim Ji-won</td>
                                <td>2024-04-29</td>
                            </tr>
                            <tr>
                                <td>Shinichi Izumi</td>
                                <td>2024-04-29</td>
                            </tr>
                            <tr>
                                <td>Park Bo-young</td>
                                <td>2024-04-29</td>
                            </tr>
                            <tr>
                                <td>Jeon Wonwoo</td>
                                <td>2024-04-29</td>
                            </tr>


                        </tbody>
                    </table>
                </div>

            </div>
        </div>

        <script>
         
            function openModal() {
                document.getElementById('myModal').style.display = "block";
                
            }

            function closeModal() {
                document.getElementById('myModal').style.display = "none";
            }
        </script>


    </body>
</html>
