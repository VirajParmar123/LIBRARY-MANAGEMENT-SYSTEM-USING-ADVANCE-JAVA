
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Yourissuedbooks extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");

            out.println("<head>");
            out.println("<title>Yourissuedbooks</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
            out.println("</head>");

            out.println("<body background=\"https://images.wallpaperscraft.com/image/books_library_shelves_138556_1920x1080.jpg\">");
            HttpSession session = request.getSession(false);
            String status = (String) session.getAttribute("globalstatus");
            String s = (String) session.getAttribute("globalid");
            int iid = Integer.parseInt(s);

            try {
                Class.forName("com.mysql.jdbc.Driver");

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");
                PreparedStatement pstmt = conn.prepareStatement("select * from issued where personid = ?");

                pstmt.setInt(1, iid);
                ResultSet rs = pstmt.executeQuery();

                out.println("</br></br></br></br>");
                out.println("<center>");
                out.println("<table border=5 width=80% height=50% class=\"p-3 mb-2 bg-primary text-light\">");
                out.println("<tr><th>Issue_Id</th><th>Person_ID</th><th>Person_Name</th><th>Book_ID</th><th>Book_Name</th><th>Issued_On</th><th>Due_Date</th><th>Status</th><tr>");
                out.println("</center>");
                while (rs.next()) {
                    int issueid = rs.getInt(1);
                    int personid = rs.getInt(2);
                    String personname = rs.getString(3);
                    int bookid = rs.getInt(4);
                    String bookname = rs.getString(5);
                    Date issuedate = rs.getDate(6);
                    Date duedate = rs.getDate(7);
                    String st = rs.getString(8);

                    out.println("<tr><td>" + issueid + "</td><td>" + personid + "</td><td>" + personname + "</td><td>" + bookid + "</td><td>" + bookname + "</td><td>" + issuedate + "</td><td>" + duedate + "</td><td>" + st + "</td></tr>");
                }
                out.println("</table>");
                out.println("</br></br>");
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/HomeButton.html");
                rd.include(request, response);
                conn.close();

            } catch (Exception e) {
                out.println(e);
            }

            out.println("</body>");
            out.println("</html>");
        }
    }
}
