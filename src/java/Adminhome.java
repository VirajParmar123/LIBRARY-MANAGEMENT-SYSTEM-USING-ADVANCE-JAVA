import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Adminhome extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Adminhome</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
            out.println("</head>");
            out.println("<body background=https://wallpaperaccess.com/full/253390.jpg>");

            out.println("</br>");
            out.println("<center>");
            out.println("<form action=\"Selectadminhome\" method=\"post\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Issue Book\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Return Book\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Request Book\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Request Data\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Issue Data\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Register User\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Remove User\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Add Book\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Remove Book\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Show Users\" name=\"btn\">");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Your Issued Books\" name=\"btn\"></br></br>");
            out.println("<input class=\"btn btn-primary\" type=\"submit\" value=\"Logout\" name=\"btn\">");
            out.println("</center>");
            out.println("</form>");

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from books");

                out.println("</br>");
                out.println("<center>");
                out.println("<table border=5 width=50% height=50% class=\"p-3 mb-2 bg-primary text-light\">");
                out.println("<tr><th>Book_Id</th><th>Book_Name</th><th>Author_Name</th><th>Book_Edition</th><th>Quantity</th><tr>");
                out.println("</center>");
                while (rs.next()) {
                    int bookid = rs.getInt(1);
                    String bookname = rs.getString(2);
                    String authorname = rs.getString(3);
                    String bookedition = rs.getString(4);
                    int quan = rs.getInt(5);

                    out.println(".<tr><td>" + bookid + "</td><td>" + bookname + "</td><td>" + authorname + "</td><td>" + bookedition + "</td><td>" + quan + "</td></tr>");
                }
                out.println("</table>");
                conn.close();
            } catch (Exception e) {
                out.println(e);
            }

            out.println("</body>");
            out.println("</html>");
        }
    }
}
