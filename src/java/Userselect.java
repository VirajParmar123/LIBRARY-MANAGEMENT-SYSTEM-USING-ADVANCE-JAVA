import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Userselect extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Userselect</title>");
            out.println("</head>");
            
            out.println("<body>");
            HttpSession session = request.getSession(false);
            String status = (String) session.getAttribute("globalstatus");

            if (status.equals("student")) {
                RequestDispatcher rd = request.getRequestDispatcher("Home");
                rd.forward(request, response);
            } else if (status.equals("admin")) {
                RequestDispatcher rd = request.getRequestDispatcher("Adminhome");
                rd.forward(request, response);
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

}
