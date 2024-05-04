/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tristan
 */
public class CourseServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    Connection conn;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            Class.forName(config.getInitParameter("driver"));
            String username = config.getInitParameter("username");
            String password = config.getInitParameter("password");
            String url = config.getInitParameter("url");
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CourseServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CourseServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String fullname = (String) session.getAttribute("fullnamesession");
        String selectedPlace = request.getParameter("place");

        try {
            if (conn.isClosed()) {
                init(getServletConfig());
            }

            String selectQuery = "SELECT course, startdate, enddate, durationhours, link, img FROM courses_info WHERE instructor = ?";
            PreparedStatement selectPs = conn.prepareStatement(selectQuery);
            selectPs.setString(1, fullname);
            ResultSet resultSet = selectPs.executeQuery();
            

            ArrayList<ArrayList<String>> courses = new ArrayList<>();

            while (resultSet.next()) {
                String courseName = resultSet.getString("course");
                String startDate = resultSet.getString("startdate");
                String endDate = resultSet.getString("enddate");
                String duration = resultSet.getString("durationhours");
                String link = resultSet.getString("link");
                String img = resultSet.getString("img");

                ArrayList<String> courseDetails = new ArrayList<>();
                courseDetails.add(courseName);
                courseDetails.add(startDate);
                courseDetails.add(endDate);
                courseDetails.add(duration);
                courseDetails.add(link);
                courseDetails.add(img);
                
                courses.add(courseDetails);
               
            }

            session.setAttribute("coursessession", courses);

            request.setAttribute("courses", courses);

            RequestDispatcher dispatcher;

            if (selectedPlace == null) {
                dispatcher = request.getRequestDispatcher("admin_courses.jsp");
                dispatcher.forward(request, response);
            } else if (selectedPlace.equals("Enrollment")) {
                dispatcher = request.getRequestDispatcher("admin_enrollment.jsp");
                dispatcher.forward(request, response);
            } else if (selectedPlace.equals("MyCourses")) {
                dispatcher = request.getRequestDispatcher("admin_courses.jsp");
                dispatcher.forward(request, response);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String fullname = (String) session.getAttribute("fullnamesession");
        String selectedCourse = request.getParameter("course");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String selectedAction = request.getParameter("option");
        RequestDispatcher dispatcher;

        try {
            if (conn.isClosed()) {
                init(getServletConfig());
            }
            if (selectedAction.equals("Add")) {
                if (endDate.compareTo(startDate) >= 0) {
                    String countQuery = "SELECT COUNT(*) FROM courses_info WHERE instructor = ?";
                    PreparedStatement countPs = conn.prepareStatement(countQuery);
                    countPs.setString(1, fullname);
                    ResultSet countResult = countPs.executeQuery();
                    int numberOfCourses = 0;
                    if (countResult.next()) {
                        numberOfCourses = countResult.getInt(1);
                    }
                    countResult.close();
                    countPs.close();

                    if (numberOfCourses < 3) {
                        String updateQuery = "UPDATE courses_info SET instructor = ?, startdate = ?, enddate = ? WHERE course = ? AND instructor IS NULL";
                        PreparedStatement ps = conn.prepareStatement(updateQuery);
                        ps.setString(1, fullname);
                        ps.setString(2, startDate);
                        ps.setString(3, endDate);
                        ps.setString(4, selectedCourse);
                        int rowsUpdated = ps.executeUpdate();
                        ps.close();
                        if (rowsUpdated == 0) {
                            String instructorQuery = "SELECT instructor FROM courses_info WHERE course = ?";
                            PreparedStatement instructorPS = conn.prepareStatement(instructorQuery);
                            instructorPS.setString(1, selectedCourse);
                            ResultSet rs = instructorPS.executeQuery();
                            if (rs.next()) {
                                String instructorName = rs.getString("instructor");
                                if (instructorName.equals(fullname)) {
                                    request.setAttribute("handledalready", "You already handle the course.");
                                } else {
                                    request.setAttribute("handledalready", "Course is already handled by another instructor.");
                                }
                            }
                            rs.close();
                            instructorPS.close();
                        }
                    } else {
                        request.setAttribute("limitreached", "Limit of 3 courses per instructor.");
                    }
                } else {
                    request.setAttribute("invaliddate", "End date should not be earlier than start date.");
                }
            } else if (selectedAction.equals("Delete")) {
                String updateQuery = "UPDATE courses_info SET instructor = NULL, startdate = NULL, enddate = NULL WHERE course = ? AND instructor = ?";
                PreparedStatement ps = conn.prepareStatement(updateQuery);
                ps.setString(1, selectedCourse);
                ps.setString(2, fullname);
                int rowsUpdated = ps.executeUpdate();
                updateQuery = "DELETE FROM students_info WHERE coursetaken = ?";
                ps = conn.prepareStatement(updateQuery);
                ps.setString(1, selectedCourse);
                rowsUpdated = ps.executeUpdate();
                ps.close();
            }

            String selectQuery = "SELECT course, startdate, enddate, durationhours FROM courses_info WHERE instructor = ?";
            PreparedStatement selectPs = conn.prepareStatement(selectQuery);
            selectPs.setString(1, fullname);
            ResultSet resultSet = selectPs.executeQuery();

            ArrayList<ArrayList<String>> courses = new ArrayList<>();

            while (resultSet.next()) {
                String courseName = resultSet.getString("course");
                startDate = resultSet.getString("startdate");
                endDate = resultSet.getString("enddate");
                String duration = resultSet.getString("durationhours");

                ArrayList<String> courseDetails = new ArrayList<>();
                courseDetails.add(courseName);
                courseDetails.add(startDate);
                courseDetails.add(endDate);
                courseDetails.add(duration);

                courses.add(courseDetails);
            }

            session.setAttribute("coursessession", courses);

            request.setAttribute("courses", courses);

            dispatcher = request.getRequestDispatcher("admin_enrollment.jsp");
            dispatcher.forward(request, response);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
