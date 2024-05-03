/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

        
import com.itextpdf.text.BaseColor;
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
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tristan
 */
public class CoursesPDF extends HttpServlet {

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
    String username1;

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
            out.println("<title>Servlet CoursesPDF</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CoursesPDF at " + request.getContextPath() + "</h1>");
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
        String report = request.getParameter("reports");
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();

        try {
            if (report.equals("Course List")) {
                Document document = new Document(PageSize.LETTER);
                PdfWriter writer = PdfWriter.getInstance(document, out);
                HeaderFooterPageEvent event = new HeaderFooterPageEvent();
                writer.setPageEvent(event);
                document.open();

                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10);

                table.addCell("Course");
                table.addCell("Instructor");
                table.addCell("Start Date");
                table.addCell("End Date");

                Statement stmt = conn.createStatement();
                String query = "SELECT * FROM COURSES_INFO";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String course = rs.getString("COURSE");
                    String fullname = rs.getString("INSTRUCTOR");
                    String startdate = rs.getString("STARTDATE");
                    String enddate = rs.getString("ENDDATE");

                    table.addCell(course);
                    table.addCell(fullname);
                    table.addCell(startdate);
                    table.addCell(enddate);
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
    }// </editor-fold>

    class HeaderFooterPageEvent extends PdfPageEventHelper {

        private PdfTemplate totalPageTemplate;

        public void onStartPage(PdfWriter writer, Document document) {
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLDITALIC, new BaseColor(0, 0, 0));

            Rectangle pageSize = document.getPageSize();

            float centerX = pageSize.getWidth() / 2;
            float topY = pageSize.getHeight() - 30;

            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Courses Report", headerFont), centerX, topY, 0);
        }

        public void onOpenDocument(PdfWriter writer, Document document) {
            totalPageTemplate = writer.getDirectContent().createTemplate(30, 10);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(0, 0, 0));
            PdfContentByte cb = writer.getDirectContent();
            Phrase footer = new Phrase(username1, new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer, document.left() + 36, document.bottom() - 20, 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER,
                    new Paragraph(getTimestamp(), footerFont),
                    document.getPageSize().getWidth() / 2,
                    document.bottom() - 20, 0);

            int pageNumber = writer.getPageNumber();
            String text = "Page " + pageNumber + " of ";
            float textSize = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false).getWidthPoint(text, 10);
            float textBase = document.bottom() - 20;
            cb.beginText();
            cb.setFontAndSize(new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC).getCalculatedBaseFont(false), 10);
            cb.setTextMatrix(document.right() - textSize - 36, textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(totalPageTemplate, document.right() - 36, textBase);
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
