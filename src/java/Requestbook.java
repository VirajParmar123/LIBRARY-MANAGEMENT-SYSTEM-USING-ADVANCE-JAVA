
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Requestbook extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Requestbook</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");

            out.println("</head>");
            out.println("<body>");

            HttpSession session = request.getSession(false);
            String rname = (String) session.getAttribute("globaluname");
            String s = (String) session.getAttribute("globalid");
            String status = (String) session.getAttribute("globalstatus");
            int rpid = Integer.parseInt(s);

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO requested (Person_ID, Person_Name, Book_Name, Author_Name, Book_Edition, Date) VALUES (?,?,?,?,?,?)");

                String click = request.getParameter("btn1");
                if (click.equals("Request")) {

                    String rebname = request.getParameter("abname");
                    String reaname = request.getParameter("aaname");
                    String rebe = request.getParameter("abe");

                    pstmt.setInt(1, rpid);
                    pstmt.setString(2, rname);
                    pstmt.setString(3, rebname);
                    pstmt.setString(4, reaname);
                    pstmt.setString(5, rebe);
                    pstmt.setDate(6, sqlDate);

                    int i = pstmt.executeUpdate();

                    if (i == 0) {
                        out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                        out.println("_____________________________________________________________________________________Error______________________________________________________________________________________");
                        out.println("</div>");
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Requestbookview.html");
                        rd.include(request, response);
                    } else if (i != 0) {
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("select * from books");
                        int flag = 0;
                        while (rs.next()) {
                            if (rebname.equals(rs.getString(2)) && reaname.equals(rs.getString(3)) && rebe.equals(rs.getString(4))) {
                                flag = 1;
                            }
                        }
                        if (flag == 1) {
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                            out.println("_____________________________________________________________________Request Succesfull: Book found!_____________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Requestbookview.html");
                            rd.include(request, response);
                        }
                        else
                        {
                            out.println("<div class=\"p-3 mb-2 bg-primary text-white\">");
                            out.println("_________________________________________________________________Request Succesfull: Sorry, Book not found!_________________________________________________________________");
                            out.println("</div>");
                            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Requestbookview.html");
                            rd.include(request, response);
                        }

                    }
                } else if (click.equals("Home")) {
                    if (status.equals("student")) {
                        RequestDispatcher rd = request.getRequestDispatcher("Home");
                        rd.forward(request, response);
                    } else if (status.equals("admin")) {
                        System.out.println("abc");
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
