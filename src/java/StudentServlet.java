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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Melchor
 */
public class StudentServlet extends HttpServlet {

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
            out.println("<title>Servlet StudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentServlet at " + request.getContextPath() + "</h1>");
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
        String selectedPlace = request.getParameter("place");
        String fullname = (String) session.getAttribute("fullnamesession");

        try {
            if (conn.isClosed()) {
                init(getServletConfig());
            }

            
            //=========================================================================//
            String selectQuery = "SELECT course, startdate, enddate, durationhours, instructor FROM courses_info "
                    + "WHERE startdate IS NOT NULL AND enddate IS NOT NULL "
                    + "AND course NOT IN (SELECT coursetaken FROM students_info WHERE student = ?)";
            PreparedStatement selectPs = conn.prepareStatement(selectQuery);
            selectPs.setString(1, fullname);
            ResultSet resultSet = selectPs.executeQuery();
            ArrayList<ArrayList<String>> courses = new ArrayList<>();

            while (resultSet.next()) {
                String courseName = resultSet.getString("course");
                String startDate = resultSet.getString("startdate");
                String endDate = resultSet.getString("enddate");
                String durationHours = resultSet.getString("durationhours");
                String instructor = resultSet.getString("instructor");

                ArrayList<String> courseDetails = new ArrayList<>();
                courseDetails.add(courseName);
                courseDetails.add(startDate);
                courseDetails.add(endDate);
                courseDetails.add(durationHours);
                courseDetails.add(instructor);

                courses.add(courseDetails);
            }

            //=========================================================================//
            String getCourses = "SELECT coursetaken, instructor, startdate, enddate FROM students_info WHERE student = ?";
            PreparedStatement Ps = conn.prepareStatement(getCourses);
            Ps.setString(1, fullname);
            ResultSet rs = Ps.executeQuery();
            ArrayList<ArrayList<String>> students = new ArrayList<>();

            while (rs.next()) {
                String courseName = rs.getString("coursetaken");
                String instructor = rs.getString("instructor");
                String startdate = rs.getString("startdate");
                String enddate = rs.getString("enddate");

                ArrayList<String> studentDetails = new ArrayList<>();
                studentDetails.add(courseName);
                studentDetails.add(instructor);
                studentDetails.add(startdate);
                studentDetails.add(enddate);

                students.add(studentDetails);
            }

            //=========================================================================//
            // Ewan para san to??
            session.setAttribute("coursessession", courses);

            // data of all courses
            request.setAttribute("allCourses", courses);

            // data of all courses of students
            request.setAttribute("studentCourses", students);

            RequestDispatcher dispatcher;

            if (selectedPlace == null) {
                dispatcher = request.getRequestDispatcher("guest_courses.jsp");
                dispatcher.forward(request, response);
            } else if (selectedPlace.equals("Enrollment")) {
                dispatcher = request.getRequestDispatcher("guest_enrollment.jsp");
                dispatcher.forward(request, response);
            } else if (selectedPlace.equals("MyCourses")) {
                dispatcher = request.getRequestDispatcher("guest_courses.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String selectedAction = request.getParameter("enrollCourse");
        String fullname = (String) session.getAttribute("fullnamesession");
        String takenCourse = request.getParameter("course");
        String instructor = request.getParameter("instructor");
        String stardate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");

        try {
            if (conn.isClosed()) {
                init(getServletConfig());
            }
            //Check number allowed number of courses
            if (selectedAction.equals("enrollCourse")) {
                String countQuery = "SELECT COUNT(*) FROM students_info WHERE student = ?";
                PreparedStatement countPs = conn.prepareStatement(countQuery);
                countPs.setString(1, fullname);
                ResultSet countResult = countPs.executeQuery();
                int numberOfcourseTaken = 0;
                if (countResult.next()) {
                    numberOfcourseTaken = countResult.getInt(1);
                }
                countResult.close();
                countPs.close();

                //=========================================================================//
                //Insert data in students_info
                if (numberOfcourseTaken < 3) {
                    String insertQuery = "INSERT INTO students_info (student, coursetaken, instructor, startdate, enddate) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

                    preparedStatement.setString(1, fullname);
                    preparedStatement.setString(2, takenCourse);
                    preparedStatement.setString(3, instructor);
                    preparedStatement.setString(4, stardate);
                    preparedStatement.setString(5, enddate);

                    int rowsAffected = preparedStatement.executeUpdate();
                    preparedStatement.close();
                } else {

                    request.setAttribute("enrollmentLimitReached", "You have reached the Enrollment limit.");
                }
            }
            //=========================================================================//

            String selectQuery = "SELECT course, startdate, enddate, instructor FROM courses_info WHERE startdate IS NOT NULL AND enddate IS NOT NULL";
            PreparedStatement selectPs = conn.prepareStatement(selectQuery);
            ResultSet resultSet = selectPs.executeQuery();
            ArrayList<ArrayList<String>> courses = new ArrayList<>();

            while (resultSet.next()) {
                String courseName = resultSet.getString("course");
                String startDate = resultSet.getString("startdate");
                String endDate = resultSet.getString("enddate");
                String instructors = resultSet.getString("instructor");

                ArrayList<String> courseDetails = new ArrayList<>();
                courseDetails.add(courseName);
                courseDetails.add(startDate);
                courseDetails.add(endDate);
                courseDetails.add(instructor);

                courses.add(courseDetails);
            }

            //=========================================================================//
            String getCourses = "SELECT coursetaken, instructor, startdate, enddate FROM students_info WHERE student = ?";
            PreparedStatement Ps = conn.prepareStatement(getCourses);
            Ps.setString(1, fullname);
            ResultSet rs = Ps.executeQuery();
            ArrayList<ArrayList<String>> students = new ArrayList<>();

            while (rs.next()) {
                String courseName = rs.getString("coursetaken");
                String instructors = rs.getString("instructor");
                String startdate = rs.getString("startdate");
                String enddates = rs.getString("enddate");

                ArrayList<String> studentDetails = new ArrayList<>();
                studentDetails.add(courseName);
                studentDetails.add(instructors);
                studentDetails.add(startdate);
                studentDetails.add(enddates);

                students.add(studentDetails);
            }
            // Ewan para san to??
            session.setAttribute("coursessession", courses);

            // data of all courses
            request.setAttribute("allCourses", courses);

            // data of all courses of students
            request.setAttribute("studentCourses", students);

            RequestDispatcher dispatcher;
            dispatcher = request.getRequestDispatcher("guest_courses.jsp");
            dispatcher.forward(request, response);

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
