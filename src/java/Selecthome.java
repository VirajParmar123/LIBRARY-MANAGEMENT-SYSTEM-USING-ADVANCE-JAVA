
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Selecthome extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Selecthome</title>");
            out.println("</head>");
            out.println("<body>");

            String choice = request.getParameter("btn");

            if (choice.equals("Issue Book")) {
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Issuebookview.html");
                rd.forward(request, response);
            } else if (choice.equals("Request Book")) {
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Requestbookview.html");
                rd.forward(request, response);
            } else if (choice.equals("Your Issued Books")) {
                RequestDispatcher rd = request.getRequestDispatcher("Yourissuedbooks");
                rd.forward(request, response);
            } else if (choice.equals("Return Book")) {
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Returnbookview.html");
                rd.forward(request, response);
            } else if (choice.equals("Logout")) {
                RequestDispatcher rd = request.getRequestDispatcher("logout");
                rd.forward(request, response);
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

}
