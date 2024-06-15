
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Removebook extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");

            out.println("<head>");
            out.println("<title>Removebook</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
            out.println("</head>");

            out.println("<body>");

            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE bookid = ?");

                String click = request.getParameter("btn1");
                if (click.equals("Remove")) {
                    int rid = Integer.parseInt(request.getParameter("id"));
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from issued");
                    int flag = 0;
                    while (rs.next()) {
                        if (rid == rs.getInt(1)) {
                            if (rs.getString(8).equals("Issued")) {
                                flag = 1;
                                break;
                            }
                        }
                    }
                    if (flag == 0) {
                        pstmt.setInt(1, rid);
                        int i = pstmt.executeUpdate();
                        if (i == 0) {
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                            out.println("_____________________________________________________________________________________Error______________________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Removebookview.html");
                            rd.include(request, response);
                        } else {
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                            out.println("___________________________________________________________________________________Succesfull____________________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Removebookview.html");
                            rd.include(request, response);
                        }
                    }
                    else
                    {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                            out.println("________________________________________________________________Error: book can't be removed until it's issued!________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Removebookview.html");
                            rd.include(request, response);
                    }

                } else if (click.equals("Home")) {
                    RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                    rd.forward(request, response);
                }

            } catch (Exception e) {
                out.println(e);
            }

            out.println("</body>");
            out.println("</html>");
        }
    }
}
