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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
            String selectQuery = "SELECT course, startdate, enddate, durationhours, instructor, link, img FROM courses_info "
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
                String instructors = resultSet.getString("instructor");
                String durationhours = resultSet.getString("durationhours");
                String link = resultSet.getString("link");
                String img = resultSet.getString("img");

                ArrayList<String> courseDetails = new ArrayList<>();
                courseDetails.add(courseName);
                courseDetails.add(startDate);
                courseDetails.add(endDate);
                courseDetails.add(durationhours);
                courseDetails.add(instructors);
                courseDetails.add(link);
                courseDetails.add(img);

                courses.add(courseDetails);
            }

            //=========================================================================//
            String getCourses = "SELECT coursetaken, instructor, startdate, enddate, durationhours, link, img FROM students_info WHERE student = ?";
            PreparedStatement Ps = conn.prepareStatement(getCourses);
            Ps.setString(1, fullname);
            ResultSet rs = Ps.executeQuery();
            ArrayList<ArrayList<String>> students = new ArrayList<>();

            while (rs.next()) {
                String courseName = rs.getString("coursetaken");
                String instructors = rs.getString("instructor");
                String startdate = rs.getString("startdate");
                String enddates = rs.getString("enddate");
                String durationhourss = rs.getString("durationhours");
                String studentlinkss = rs.getString("link");
                String studentimgs = rs.getString("img");

                ArrayList<String> studentDetails = new ArrayList<>();
                studentDetails.add(courseName);
                studentDetails.add(instructors);
                studentDetails.add(startdate);
                studentDetails.add(enddates);
                studentDetails.add(durationhourss);
                studentDetails.add(studentlinkss);
                studentDetails.add(studentimgs);

                students.add(studentDetails);
            }

            //=========================================================================//
            HashMap<String, String> courseDescriptions = new HashMap<>();
            courseDescriptions.put("Laravel Framework", "Laravel Framework is an open-source PHP class library designed to support the development of dynamic websites, Web applications and Web services. The framework aims to alleviate the overhead associated with common activities used in Web development such as database access, authentication, templating, allowing you to focus on your application’s specific requirements. By the end of the course, students will have completed a secure, Create, Read, Update, Delete (CRUD) application.");

            courseDescriptions.put("Advanced Python Programming", "Python is a widely used, open-source programming language that is especially suited for a wide range of applications including web development, machine learning, and data science. In this Python Programming course, you will learn advanced concepts like Object-Oriented concepts, exception handling and the basics of web application development using Django.");

            courseDescriptions.put("Microsoft Excel Advanced", "In this Microsoft Excel Advanced Training course, you will learn how to maximize Excel even further to help you analyze data quicker. You will learn features like PivotTables, macros, and how to protect your spreadsheets from being misused by others.");

            courseDescriptions.put("User Experience(UX)", "This boot-camp is fast-paced, hands-on and interactive. It has been designed to focus on “learning by doing” rather than “learning by listening”.");

            courseDescriptions.put("CompTIA Security+", "CompTIA Security+ provides a global benchmark for best practices in IT network and operational security, one of the fastest-growing fields in IT.");

            courseDescriptions.put("Microsoft Excel Essentials", "Microsoft Excel is the industry leading tool to help workers work with data on the desktop. This Microsoft Excel training course enables you to become more productive by allowing you to automate data processing essential to your daily work.");

            courseDescriptions.put("ITIL 4 Foundation Certification Program", "ITIL 4 Foundation is designed as an introduction to ITIL 4 and enables candidates to look at IT service management through an end-to-end operating model for the creation, delivery, and continual improvement of tech-enabled products.");

            courseDescriptions.put("Agile Project Management with Scrum", "In this Agile and Scrum training course, you will learn how to apply Agile and Scrum techniques to deliver value to project stakeholders in short amounts of time. ActiveLearning’s Agile Scrum training Through lectures and simulations, this course is ideal if you are looking have a firm foundation of how agile and scrum methodologies can be put into practice. This training will also help you prepare for the Professional Scrum Master (PSM) certification, which is one of the most recognized agile project management certifications in the world.");

            //=========================================================================//
            // Ewan para san to??
            session.setAttribute("coursessession", courses);

            // data of all courses
            request.setAttribute("allCourses", courses);

            // data of all courses of students
            request.setAttribute("studentCourses", students);

            // data for course description
            request.setAttribute("courseDescriptions", courseDescriptions);

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
        String durationhours = request.getParameter("durationhours");
        String studentlink = request.getParameter("link");
        String studentimg = request.getParameter("img");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        try {
            if (conn.isClosed()) {
                init(getServletConfig());
            }
            
            if (selectedAction.equals("enrollCourse")) {
                String checkTakenQuery = "SELECT * FROM students_info WHERE student = ? AND coursetaken = ?";
                PreparedStatement checkTakenPs = conn.prepareStatement(checkTakenQuery);
                checkTakenPs.setString(1, fullname);
                checkTakenPs.setString(2, takenCourse);
                ResultSet checkTakenResult = checkTakenPs.executeQuery();
                //=========================================================================//
                if (checkTakenResult.next()) {
                } else {
                    // 
                    String countQuery = "SELECT COUNT(*) FROM students_info WHERE student = ?";
                    PreparedStatement countPs = conn.prepareStatement(countQuery);
                    countPs.setString(1, fullname);
                    ResultSet countResult = countPs.executeQuery();
                    int numberOfCoursesTaken = 0;
                    if (countResult.next()) {
                        numberOfCoursesTaken = countResult.getInt(1);
                    }
                    countResult.close();
                    countPs.close();

                    //=========================================================================//
                   if (numberOfCoursesTaken < 3) {
                        String insertQuery = "INSERT INTO students_info (student, coursetaken, instructor, startdate, enddate, durationhours, link, img, enrollment_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement insertPs = conn.prepareStatement(insertQuery);
                        insertPs.setString(1, fullname);
                        insertPs.setString(2, takenCourse);
                        insertPs.setString(3, instructor);
                        insertPs.setString(4, stardate);
                        insertPs.setString(5, enddate);
                        insertPs.setString(6, durationhours);
                        insertPs.setString(7, studentlink);
                        insertPs.setString(8, studentimg);
                        insertPs.setString(9, currentDate);
                        int rowsAffected = insertPs.executeUpdate();
                    } else {
                         session.setAttribute("enrollmentLimitReached", "You have reached the enrollment limit (3 courses).");
                    }
                }
                checkTakenResult.close();
                checkTakenPs.close();
            }
            //=========================================================================//

            String selectQuery = "SELECT course, startdate, enddate, instructor, link, img FROM courses_info WHERE startdate IS NOT NULL AND enddate IS NOT NULL";
            PreparedStatement selectPs = conn.prepareStatement(selectQuery);
            ResultSet resultSet = selectPs.executeQuery();
            ArrayList<ArrayList<String>> courses = new ArrayList<>();

            while (resultSet.next()) {
                String courseName = resultSet.getString("course");
                String startDate = resultSet.getString("startdate");
                String endDate = resultSet.getString("enddate");
                String instructors = resultSet.getString("instructor");
                String link = resultSet.getString("link");
                String img = resultSet.getString("img");

                ArrayList<String> courseDetails = new ArrayList<>();
                courseDetails.add(courseName);
                courseDetails.add(startDate);
                courseDetails.add(endDate);
                courseDetails.add(instructor);
                courseDetails.add(link);
                courseDetails.add(img);

                courses.add(courseDetails);
            }

            //=========================================================================//
            String getCourses = "SELECT coursetaken, instructor, startdate, enddate, durationhours, link, img FROM students_info WHERE student = ?";
            PreparedStatement Ps = conn.prepareStatement(getCourses);
            Ps.setString(1, fullname);
            ResultSet rs = Ps.executeQuery();
            ArrayList<ArrayList<String>> students = new ArrayList<>();

            while (rs.next()) {
                String courseName = rs.getString("coursetaken");
                String instructors = rs.getString("instructor");
                String startdate = rs.getString("startdate");
                String enddates = rs.getString("enddate");
                String durationhourss = rs.getString("durationhours");
                String studentlinkss = rs.getString("link");
                String studentimgs = rs.getString("img");

                ArrayList<String> studentDetails = new ArrayList<>();
                studentDetails.add(courseName);
                studentDetails.add(instructors);
                studentDetails.add(startdate);
                studentDetails.add(enddates);
                studentDetails.add(durationhourss);
                studentDetails.add(studentlinkss);
                studentDetails.add(studentimgs);

                students.add(studentDetails);
            }
            // Ewan para san to??
            session.setAttribute("coursessession", courses);

            // data of all courses
            request.setAttribute("allCourses", courses);

            // data of all courses of students
            request.setAttribute("studentCourses", students);

            RequestDispatcher dispatcher;
             response.sendRedirect("StudentServlet");
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
