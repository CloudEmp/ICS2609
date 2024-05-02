/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tristan
 */
public class ReportsPDF extends HttpServlet {

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
    Connection connection;
    String username1;
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

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            Class.forName(config.getInitParameter("driver1"));
            String username1 = config.getInitParameter("username1");
            String password1 = config.getInitParameter("password1");
            String url1 = config.getInitParameter("url1");
            connection = DriverManager.getConnection(url1, username1, password1);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        ServletContext context = getServletContext();
        String keyString = context.getInitParameter("encryption_key");

        key = keyString.getBytes();

        cipherr = context.getInitParameter("cipher");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReportsPDF</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportsPDF at " + request.getContextPath() + "</h1>");
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
        username1 = (String) session.getAttribute("usernamesession");
        String password = decrypt((String) session.getAttribute("passwordsession"));
        String report = request.getParameter("reports");

        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();

        try {
            if (report.equals("All Records")) {
                Document document = new Document(PageSize.LETTER.rotate());
                PdfWriter writer = PdfWriter.getInstance(document, out);
                HeaderFooterPageEventInstructor event = new HeaderFooterPageEventInstructor();
                writer.setPageEvent(event);
                document.open();

                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setSpacingBefore(30);

                table.addCell("Username");
                table.addCell("User Role");

                Statement stmt = conn.createStatement();
                String query = "SELECT * FROM USER_INFO";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String username2 = rs.getString("USERNAME").trim();
                    String userrole1 = rs.getString("USERROLE").trim();

                    table.addCell(username2);
                    table.addCell(userrole1);
                }

                document.add(table);
                document.close();
            } else if (report.equals("My Record")) {
                Document document = new Document(new com.itextpdf.text.Rectangle(200, 400).rotate());
                PdfWriter writer = PdfWriter.getInstance(document, out);
                HeaderFooterPageEventStudent event = new HeaderFooterPageEventStudent();
                writer.setPageEvent(event);
                document.open();
                document.add(new Paragraph(""));
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph(""));
                document.add(Chunk.NEWLINE);

                Paragraph usernameParagraph = new Paragraph("Username: " + username1);
                usernameParagraph.setAlignment(Element.ALIGN_CENTER);
                document.add(usernameParagraph);

                Paragraph passwordParagraph = new Paragraph("Password: " + password);
                passwordParagraph.setAlignment(Element.ALIGN_CENTER);
                document.add(passwordParagraph);

                document.close();

            } else if (report.equals("Courses")) {
                Document document = new Document(new com.itextpdf.text.Rectangle(400, 600).rotate());
                PdfWriter writer = PdfWriter.getInstance(document, out);
                HeaderFooterPageEventCourses event = new HeaderFooterPageEventCourses();
                writer.setPageEvent(event);
                document.open();

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10);

                table.addCell("Course");
                table.addCell("Instructor");
                table.addCell("Start Date");
                table.addCell("End Date");
                table.addCell("Duration (Hours)");

                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM COURSES_INFO";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String coursename = rs.getString("COURSE");
                    String instructorname = rs.getString("INSTRUCTOR");
                    String coursestart = rs.getString("STARTDATE");
                    String courseend = rs.getString("ENDDATE");
                    String duration = rs.getString("DURATIONHOURS");

                    table.addCell(coursename);
                    table.addCell(instructorname);
                    table.addCell(coursestart);
                    table.addCell(courseend);
                    table.addCell(duration);
                }

                document.add(table);
                document.close();
            }
        } catch (Exception e) {
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
    }
    // Method to add content with alignment
    // </editor-fold>

    public static String decrypt(String codeDecrypt) {
        String decryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedString = new String(cipher.doFinal(Base64.decodeBase64(codeDecrypt)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return decryptedString;
    }

    private PdfTemplate totalPageTemplate;

    class HeaderFooterPageEventInstructor extends PdfPageEventHelper {

        public void onStartPage(PdfWriter writer, Document document) {
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLDITALIC, new BaseColor(0, 0, 0));
            Font headerFont1 = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(0, 0, 0));

            Rectangle pageSize = document.getPageSize();

            float centerX = pageSize.getWidth() / 2;
            float topY = pageSize.getHeight() - 30;

            ServletContext servletContext = getServletContext();
            String name = servletContext.getInitParameter("name");
            String section = servletContext.getInitParameter("section");

            float dynamicContentY = topY + 18; 
            String dynamicText = name + " " + section;
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(dynamicText, headerFont1), centerX, dynamicContentY, 0);

            String headerText = "Instructor Report (All Records)";
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(headerText, headerFont), centerX, topY, 0);
        }

        public void onOpenDocument(PdfWriter writer, Document document) {
            totalPageTemplate = writer.getDirectContent().createTemplate(30, 10);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(0, 0, 0));
            PdfContentByte cb = writer.getDirectContent();

            Phrase usernameFooter = new Phrase(username1, new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, usernameFooter, document.left() + 36, document.bottom() - 5, 0);

            String timestamp = getTimestamp();
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Paragraph(timestamp, footerFont),
                    document.getPageSize().getWidth() / 2, document.bottom() - 5, 0);

            int pageNumber = writer.getPageNumber();
            String pageNumberText = "Page " + pageNumber + " of ";
            float pageNumberTextSize = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false).getWidthPoint(pageNumberText, 10);
            float textBase = document.bottom() - 5;
            cb.beginText();
            cb.setFontAndSize(new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false), 10);
            cb.setTextMatrix(document.right() - pageNumberTextSize - 36, textBase);
            cb.showText(pageNumberText);
            cb.endText();
            cb.addTemplate(totalPageTemplate, document.right() - 36, textBase);

            ServletContext servletContext = getServletContext();
            String date = servletContext.getAttribute("date").toString();
            String subject = servletContext.getInitParameter("subject");
            String mp = servletContext.getInitParameter("mp");
            String dynamicContent = date + " " + subject + " " + mp;
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(dynamicContent, footerFont),
                    (document.right() + document.left()) / 2, document.bottom() - 30, 0);
        }

        private String getTimestamp() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(new Date());
        }

        public void onCloseDocument(PdfWriter writer, Document document) {

            totalPageTemplate.beginText();
            totalPageTemplate.setFontAndSize(new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false), 10);
            totalPageTemplate.setTextMatrix(0, 0);
            totalPageTemplate.showText(String.valueOf(writer.getPageNumber()));
            totalPageTemplate.endText();
        }
    }

    class HeaderFooterPageEventStudent extends PdfPageEventHelper {

        public void onStartPage(PdfWriter writer, Document document) {
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLDITALIC, new BaseColor(0, 0, 0));
            Font headerFont1 = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(0, 0, 0));

            Rectangle pageSize = document.getPageSize();

            float centerX = pageSize.getWidth() / 2;
            float topY = pageSize.getHeight() - 30;

            ServletContext servletContext = getServletContext();
            String name = servletContext.getInitParameter("name");
            String section = servletContext.getInitParameter("section");

            float dynamicContentY = topY + 18; 
            String dynamicText = name + " " + section;
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(dynamicText, headerFont1), centerX, dynamicContentY, 0);

            String headerText = "Instructor Report (My Record)";
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(headerText, headerFont), centerX, topY, 0);
        }

        public void onOpenDocument(PdfWriter writer, Document document) {
            totalPageTemplate = writer.getDirectContent().createTemplate(30, 10);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(0, 0, 0));
            PdfContentByte cb = writer.getDirectContent();

            Phrase usernameFooter = new Phrase(username1, new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, usernameFooter, document.left() + 36, document.bottom() - 5, 0);

            String timestamp = getTimestamp();
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Paragraph(timestamp, footerFont),
                    document.getPageSize().getWidth() / 2, document.bottom() - 5, 0);

            int pageNumber = writer.getPageNumber();
            String pageNumberText = "Page " + pageNumber + " of ";
            float pageNumberTextSize = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false).getWidthPoint(pageNumberText, 10);
            float textBase = document.bottom() - 5;
            cb.beginText();
            cb.setFontAndSize(new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false), 10);
            cb.setTextMatrix(document.right() - pageNumberTextSize - 36, textBase);
            cb.showText(pageNumberText);
            cb.endText();
            cb.addTemplate(totalPageTemplate, document.right() - 36, textBase);

            ServletContext servletContext = getServletContext();
            String date = servletContext.getAttribute("date").toString();
            String subject = servletContext.getInitParameter("subject");
            String mp = servletContext.getInitParameter("mp");
            String dynamicContent = date + " " + subject + " " + mp;
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(dynamicContent, footerFont),
                    (document.right() + document.left()) / 2, document.bottom() - 30, 0);
        }

        private String getTimestamp() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(new Date());
        }

        public void onCloseDocument(PdfWriter writer, Document document) {

            totalPageTemplate.beginText();
            totalPageTemplate.setFontAndSize(new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false), 10);
            totalPageTemplate.setTextMatrix(0, 0);
            totalPageTemplate.showText(String.valueOf(writer.getPageNumber()));
            totalPageTemplate.endText();
        }
    }

    class HeaderFooterPageEventCourses extends PdfPageEventHelper {

        public void onStartPage(PdfWriter writer, Document document) {
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLDITALIC, new BaseColor(0, 0, 0));
            Font headerFont1 = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(0, 0, 0));

            Rectangle pageSize = document.getPageSize();

            float centerX = pageSize.getWidth() / 2;
            float topY = pageSize.getHeight() - 30;

            ServletContext servletContext = getServletContext();
            String name = servletContext.getInitParameter("name");
            String section = servletContext.getInitParameter("section");

            float dynamicContentY = topY + 18;
            String dynamicText = name + " " + section;
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(dynamicText, headerFont1), centerX, dynamicContentY, 0);

            String headerText = "Instructor Report (Courses)";
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(headerText, headerFont), centerX, topY, 0);
        }

        public void onOpenDocument(PdfWriter writer, Document document) {
            totalPageTemplate = writer.getDirectContent().createTemplate(30, 10);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(0, 0, 0));
            PdfContentByte cb = writer.getDirectContent();

            Phrase usernameFooter = new Phrase(username1, new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, usernameFooter, document.left() + 36, document.bottom() - 5, 0);

            String timestamp = getTimestamp();
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Paragraph(timestamp, footerFont),
                    document.getPageSize().getWidth() / 2, document.bottom() - 5, 0);

            int pageNumber = writer.getPageNumber();
            String pageNumberText = "Page " + pageNumber + " of ";
            float pageNumberTextSize = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false).getWidthPoint(pageNumberText, 10);
            float textBase = document.bottom() - 5;
            cb.beginText();
            cb.setFontAndSize(new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false), 10);
            cb.setTextMatrix(document.right() - pageNumberTextSize - 36, textBase);
            cb.showText(pageNumberText);
            cb.endText();
            cb.addTemplate(totalPageTemplate, document.right() - 36, textBase);

            ServletContext servletContext = getServletContext();
            String date = servletContext.getAttribute("date").toString();
            String subject = servletContext.getInitParameter("subject");
            String mp = servletContext.getInitParameter("mp");
            String dynamicContent = date + " " + subject + " " + mp;
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(dynamicContent, footerFont),
                    (document.right() + document.left()) / 2, document.bottom() - 30, 0);
        }

        private String getTimestamp() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(new Date());
        }

        public void onCloseDocument(PdfWriter writer, Document document) {

            totalPageTemplate.beginText();
            totalPageTemplate.setFontAndSize(new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false), 10);
            totalPageTemplate.setTextMatrix(0, 0);
            totalPageTemplate.showText(String.valueOf(writer.getPageNumber()));
            totalPageTemplate.endText();
        }

    }
}
