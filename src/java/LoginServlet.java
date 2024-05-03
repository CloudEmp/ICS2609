/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tristan
 */
public class LoginServlet extends HttpServlet {

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
    private static byte[] key;
    private static String cipherr;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            Class.forName(config.getInitParameter("driver"));
            String username = config.getInitParameter("username");
            String password = config.getInitParameter("password");
            StringBuffer url = new StringBuffer(config.getInitParameter("url"))
                    .append("://")
                    .append(config.getInitParameter("hostname"))
                    .append(":")
                    .append(config.getInitParameter("port"))
                    .append("/")
                    .append(config.getInitParameter("databasename"));
            conn
                    = DriverManager.getConnection(url.toString(), username, password);
        } catch (SQLException sqle) {
            System.out.println("SQLException error occured - "
                    + sqle.getMessage());
        } catch (ClassNotFoundException nfe) {
            System.out.println("ClassNotFoundException error occured - "
                    + nfe.getMessage());
        }
        
        ServletContext context = getServletContext();
        String keyString = context.getInitParameter("encryption_key");

        key = keyString.getBytes();

        cipherr =  context.getInitParameter("cipher");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        processRequest(request, response);
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

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String logout = request.getParameter("logout");

        try {
            if (conn.isClosed()) {
                init(getServletConfig());
            }
            if (logout != null) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect("CaptchaServlet");
            } else {
                try {

                    if (username.isEmpty() && password.isEmpty()) {
                        throw new NullPointerException();
                    } else {
                        password = encrypt(password);
                        if (!(usernameExists(conn, username)) && password.isEmpty()) {
                            response.sendRedirect("error_1.jsp");
                        } else if (!usernameExists(conn, username) && !passwordExists(conn, password)) {
                            response.sendRedirect("error_3.jsp");
                        } else if (usernameExists(conn, username)) {
                            
                            String query = "SELECT * FROM USER_INFO WHERE username = ?";
                            PreparedStatement pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, username);
                            ResultSet rs = pstmt.executeQuery();
                            int error = 0;
                            while (rs.next()) {
                                String dbPassword = rs.getString("password").trim();
                                String dbUserRole = rs.getString("userrole").trim();
                                String dbFullName = rs.getString("fullname").trim();
                                String userrole = "Instructor";
                                error = 0;
                                if (dbPassword.equals(password)) {

                                    if (dbUserRole.equals("Student")) {
                                        userrole = "Student";
                                    }
                                    HttpSession session = request.getSession();
                                    session.setAttribute("usernamesession", username);
                                    session.setAttribute("fullnamesession", dbFullName);
                                    session.setAttribute("userrolesession", userrole);
                                    session.setAttribute("passwordsession", password);
                                    response.sendRedirect("CaptchaServlet");
                                    conn.close();
                                    break;
                                } else if (dbPassword.equals(password)) {
                                    response.sendRedirect("CaptchaServlet");
                                } else {
                                    error = 1;
                                }
                            }
                            if (error == 1) {
                                response.sendRedirect("error_2.jsp");
                            }
                            rs.close();
                            pstmt.close();
                        }
                    }
                } catch (NullPointerException npe) {

                    response.sendRedirect("noLoginCredentials.jsp");
                }
            }
        } catch (SQLException sqle) {
            System.out.println("SQLException error occured - "
                    + sqle.getMessage());
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

    private static boolean usernameExists(Connection conn, String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM USER_INFO WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count > 0;
    }

    private static boolean passwordExists(Connection conn, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM USER_INFO WHERE password = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, password);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count > 0;
    }

    public static String encrypt(String strToEncrypt) {
        String encryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherr);
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return encryptedString;
    }
    


}
