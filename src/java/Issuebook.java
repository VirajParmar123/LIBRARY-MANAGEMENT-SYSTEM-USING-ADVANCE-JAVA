
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Issuebook extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Issuebook</title>");
            out.println("</head>");
            out.println("<body>");

            HttpSession session = request.getSession(false);
            String iname = (String) session.getAttribute("globaluname");
            String s = (String) session.getAttribute("globalid");
            String status = (String) session.getAttribute("globalstatus");
            int iid = Integer.parseInt(s);
            System.out.println(iid);

            try {
                Class.forName("com.mysql.jdbc.Driver");

                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                java.sql.Date fsqld = new java.sql.Date(sqlDate.getTime() + 24 * 60 * 60 * 1000 * 15);

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

                PreparedStatement pstmt_update = conn.prepareStatement("UPDATE books SET quantity = quantity - 1 WHERE bookid= ?");
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO issued (personid, personname, bookid, bookname, issuedate, duedate, status) values(?,?,?,?,?,?,?)");
                String click = request.getParameter("btn1");
                String ibookname = "";
                int flag = 0, i = 0, j = 0;

                if (click.equals("Issue")) {
                    int ibookid = Integer.parseInt(request.getParameter("ibid"));
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from books");
                    while (rs.next()) {
                        if (rs.getInt(1) == ibookid) {
                            ibookname = rs.getString(2);
                            flag = rs.getInt(5);
                            break;
                        }
                    }

                    if (flag != 0) {
                        System.out.println("assd");
                        pstmt.setInt(1, iid);
                        pstmt.setString(2, iname);
                        pstmt.setInt(3, ibookid);
                        pstmt.setString(4, ibookname);
                        pstmt.setDate(5, sqlDate);
                        pstmt.setDate(6, fsqld);
                        pstmt.setString(7, "Issued");

                        pstmt_update.setInt(1, ibookid);

                        i = pstmt.executeUpdate();
                        j = pstmt_update.executeUpdate();
                    }

                    if (i == 0) {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("_________________________________________________________________________Error:Book not found!__________________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Issuebookview.html");
                        rd.include(request, response);
                    } else if (i != 0) {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("___________________________________________________________________________________Succesfull____________________________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Issuebookview.html");
                        rd.include(request, response);
                    }
                } else if (click.equals("Home")) {
                    if (status.equals("student")) {
                        RequestDispatcher rd = request.getRequestDispatcher("Home");
                        rd.forward(request, response);
                    } else if (status.equals("admin")) {
                        RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                        rd.forward(request, response);
                    }
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
