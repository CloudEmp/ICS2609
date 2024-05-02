/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
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
public class CaptchaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    int captchalength;
    String captchatext;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        captchalength = Integer.parseInt(config.getInitParameter("captcha_length"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CaptchaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CaptchaServlet at " + request.getContextPath() + "</h1>");
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

        captchatext = generateCaptcha(captchalength);
        request.setAttribute("captchatext", captchatext);
        request.setAttribute("captchalength", captchalength);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Captcha.jsp");
        dispatcher.forward(request, response);
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
        String captcha = request.getParameter("captcha");
        String userrole = (String) session.getAttribute("userrolesession");

        if (captchatext.equals(captcha) && userrole.equals("Instructor")) {   
            session.setAttribute("captchasession", captcha);
            response.sendRedirect("CourseServlet");
        } else if (captchatext.equals(captcha) && userrole.equals("Student")) {
            session.setAttribute("captchasession", captcha);
            response.sendRedirect("guest_courses.jsp");
        } else {
            response.sendRedirect("CaptchaServlet");
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

    public static String generateCaptcha(int n) {
        String chrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String captcha = "";
        while (n-- > 0) {
            int index = (int) (Math.random() * 62);
            captcha += chrs.charAt(index);
        }
        return captcha;
    }

}
