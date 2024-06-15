
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>login</title>");
            out.println("</head>");
            out.println("<body >");

            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

                Statement stmt = conn.createStatement();

                String uname = request.getParameter("un");
                String pwd = request.getParameter("pass");
                String who = "";
                String pid = "";

                int flag = 0;

                ResultSet rs = stmt.executeQuery("select * from login");
                while (rs.next()) {
                    if (uname.equals(rs.getString(2)) && pwd.equals(rs.getString(3))) {
                        flag = 1;
                        who = rs.getString(4);
                        pid = rs.getString(1);
                        break;
                    } else {
                        flag = 0;
                    }
                }
                if ((flag == 1) && who.equals("student")) {

                    HttpSession session = request.getSession();
                    session.setAttribute("globaluname", uname);
                    session.setAttribute("globalid", pid);
                    session.setAttribute("globalstatus", who);

                    out.print("Given Username and Password are Correct!!!");
                    RequestDispatcher rd = request.getRequestDispatcher("Home");
                    rd.forward(request, response);
                } else if ((flag == 1) && who.equals("admin")) {

                    HttpSession session = request.getSession();
                    session.setAttribute("globaluname", uname);
                    session.setAttribute("globalid", pid);
                    session.setAttribute("globalstatus", who);

                    out.print("Given Username and Password are Correct!!!");
                    RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                    rd.forward(request, response);
                } else {
                    out.println("<font color= white>");
                    out.println("<br>");
                    out.println("<strong>");
                    out.print("Given Username and Password are Incorrect!!!");
                    out.println("</strong>");
                    out.println("</font>");
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    rd.include(request, response);
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
