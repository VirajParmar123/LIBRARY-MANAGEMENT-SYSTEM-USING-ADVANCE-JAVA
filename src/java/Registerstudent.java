
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Registerstudent extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");

            out.println("<head>");
            out.println("<title>Registerstudent</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");

            out.println("</head>");
            out.println("<body>");

            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

                String click = request.getParameter("btn1");
                if (click.equals("Register")) {

                    String rename = request.getParameter("rname");
                    String repass = request.getParameter("rpass");
                    String restatus = request.getParameter("rstatus");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from login");
                    int flag = 0;
                    while (rs.next()) {
                        if (rename.equals(rs.getString(2)) || repass.equals(rs.getString(3))) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 1) {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("_________________________________________________________________Error: Username or password not unique!________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Registerstudentview.html");
                        rd.include(request, response);
                    } 
                    else 
                    {
                        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO login (Name, Password, who) VALUES (?,?,?)");
                        pstmt.setString(1, rename);
                        pstmt.setString(2, repass);
                        pstmt.setString(3, restatus);
                        int i = pstmt.executeUpdate();

                        if (i == 0) {
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                            out.println("__________________________________________________________________________Error: Registration failed!_____________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Registerstudentview.html");
                            rd.include(request, response);
                        } else if (i == 1) {
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                            out.println("___________________________________________________________________________________Succesfull____________________________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Registerstudentview.html");
                            rd.include(request, response);
                        }
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
