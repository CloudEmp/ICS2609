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
                font-size: 16px;
            }

            h2 {
                color: #1E293B;
            }

            th:nth-child(1), td:nth-child(1) {
                width: 70%;
            }

            th:nth-child(2), td:nth-child(2) {
                width: 30%;
            }

            .no-students {
                text-align: center;
                padding: 30px;
            }
        </style>
    </head>
    <body>
        <h2>List of Enrolled Students</h2>
        <table>
            <thead>
                <tr>
                    <th>Student Name</th>
                    <th>Enrollment Date</th>
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
                    <td><%= student.get(1)%></td> 
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="3" class="no-students">No students enrolled for this course.</td>
                </tr>
                <% }%>
            </tbody>
        </table>
    </body>
</html>
