
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Addbook extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");

            out.println("<head>");
            out.println("<title>Addbook</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
            out.println("</head>");

            out.println("<body>");
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO books (bookname,authorname, bookedition, quantity) VALUES (?,?,?,?)");

                String click = request.getParameter("btn1");
                if (click.equals("Add")) {

                    String addbname = request.getParameter("abname");
                    String addaname = request.getParameter("aaname");
                    String addbe = request.getParameter("abe");
                    int addbq = Integer.parseInt(request.getParameter("abq"));

                    pstmt.setString(1, addbname);
                    pstmt.setString(2, addaname);
                    pstmt.setString(3, addbe);
                    pstmt.setInt(4, addbq);

                    int i = pstmt.executeUpdate();

                    if (i == 0) {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("_____________________________________________________________________________________Error______________________________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Addbookview.html");
                        rd.include(request, response);
                    } else {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("___________________________________________________________________________________Succesfull____________________________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Addbookview.html");
                        rd.include(request, response);
                    }
                } else if (click.equals("Home")) {
                    RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                    rd.forward(request, response);
                }
                conn.close();

            } catch (Exception e) {
                out.println(e);
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

}
