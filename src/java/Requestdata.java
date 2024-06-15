
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Requestdata extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");

            out.println("<head>");
            out.println("<title>Requestdata</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
            out.println("</head>");

            out.println("<body background=\"https://images.wallpaperscraft.com/image/books_library_shelves_138556_1920x1080.jpg\">");
            
            out.println("</br></br></br></br>");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from requested");

                out.println("<center>");
                out.println("<table border=5 width=80% height=50% class=\"p-3 mb-2 bg-primary text-light\">");
                out.println("<tr><th>Person_Id</th><th>Person_Name</th><th>Book_Name</th><th>Author_Name</th><th>Book Edition</th><th>Date</th><tr>");
                out.println("</center>");
                
                while (rs.next()) {
                    int personid = rs.getInt(1);
                    String personname = rs.getString(2);
                    String bookname = rs.getString(3);
                    String authorname = rs.getString(4);
                    String edition = rs.getString(5);
                    String date = rs.getString(6);

                    out.println("<tr><td>" + personid + "</td><td>" + personname + "</td><td>" + bookname + "</td><td>" + authorname + "</td><td>" + edition + "</td><td>" + date + "</td></tr>");
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
