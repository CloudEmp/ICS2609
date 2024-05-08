<%-- 
    Document   : enrolled_students
    Created on : 05 5, 24, 12:49:31 AM
    Author     : Andrea
--%>

<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Enrolled Students</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }

            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: center;
            }

            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <h2>List of Enrolled Students</h2>
        <table>
            <thead>
                <tr>
                    <th>Student Name</th>
                   <!-- <th>Start Date</th> -->
                   <!-- <th>End Date</th> -->
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<ArrayList<String>> enrolledStudents = (ArrayList<ArrayList<String>>) session.getAttribute("enrolledStudents");
                    if (enrolledStudents != null && !enrolledStudents.isEmpty()) {
                        for (ArrayList<String> student : enrolledStudents) {
                %>
                <tr>
                    <td><%= student.get(0)%></td>
                   <!-- <td><%= student.get(1)%></td> -->
                    <!-- <td><%= student.get(2)%></td>  -->
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="3">No students enrolled for this course.</td>
                </tr>
                <% }%>
            </tbody>
        </table>
    </body>
</html>
