
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Returnbook extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Returnbook</title>");
            out.println("</head>");
            out.println("<body>");
            HttpSession session = request.getSession(false);
            String status = (String) session.getAttribute("globalstatus");
            try {
                out.println("<form action=\"Returnbook\" method=\"post\">");
                Class.forName("com.mysql.jdbc.Driver");

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

                Statement stmt = conn.createStatement();
                PreparedStatement pstmt1 = conn.prepareStatement("UPDATE books SET quantity = quantity + 1 WHERE bookid = ?");
                PreparedStatement pstmt2 = conn.prepareStatement("UPDATE issued SET status = ? WHERE issueid = ?");

                String click = request.getParameter("btn1");
                String bookstatus = "";
                int bookid = 0, i = 0, j = 0;

                if (click.equals("Return")) {
                    int issueid = Integer.parseInt(request.getParameter("isid"));
                    ResultSet rs = stmt.executeQuery("select * from issued");

                    while (rs.next()) {
                        if (rs.getInt(1) == issueid) {
                            bookid = rs.getInt(4);
                            bookstatus = rs.getString(8);
                            break;
                        }
                    }
                    if (bookstatus.equals("Issued")) {
                        pstmt1.setInt(1, bookid);
                        pstmt2.setString(1, "returned");
                        pstmt2.setInt(2, issueid);

                        i = pstmt1.executeUpdate();
                        j = pstmt2.executeUpdate();
                    }

                    if (i == 0) {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("_____________________________________________________________________________________Error______________________________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Returnbookview.html");
                        rd.include(request, response);
                    } else {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("___________________________________________________________________________________Succesfull____________________________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Returnbookview.html");
                        rd.include(request, response);
                    }
                } 
                else if (click.equals("Home")) {
                    if (status.equals("student")) {
                        RequestDispatcher rd = request.getRequestDispatcher("Home");
                        rd.forward(request, response);
                    } else if (status.equals("admin")) {
                        RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                        rd.forward(request, response);
                    }
                }
            } catch (Exception e) {
                out.println(e);
            }

            out.println("</body>");
            out.println("</html>");
        }
    }
}
