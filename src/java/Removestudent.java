import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Removestudent extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Remove student</title>");
            out.println("</head>");
            out.println("<body>");

            try {
                out.println("<form action=\"Removestudent\" method=\"post\">");
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM login WHERE pid = ?");

                String click = request.getParameter("btn1");
                if (click.equals("Remove")) {
                    int rid = Integer.parseInt(request.getParameter("id"));
                    pstmt.setInt(1, rid);
                    int i = pstmt.executeUpdate();

                    if (i == 0) {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("_____________________________________________________________________________________Error______________________________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Removestudentview.html");
                        rd.include(request, response);
                    } else {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("___________________________________________________________________________________Succesfull____________________________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Removestudentview.html");
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
