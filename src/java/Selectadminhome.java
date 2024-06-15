import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Selectadminhome extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Selectadminhome</title>");            
            out.println("</head>");
            
            out.println("<body>");
            String choice = request.getParameter("btn");
                    
                    if(choice.equals("Issue Book"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Issuebookview.html");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Return Book"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Returnbookview.html");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Request Book"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Requestbookview.html");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Request Data"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("Requestdata");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Issue Data"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("Issuedata");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Register User"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Registerstudentview.html");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Remove User"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Removestudentview.html");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Add Book"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Addbookview.html");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Remove Book"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Removebookview.html");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Show Users"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("Userdata");
                        rd.forward(request, response);
                    }
                     else if(choice.equals("Your Issued Books"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("Yourissuedbooks");
                        rd.forward(request, response);
                    }
                    else if(choice.equals("Logout"))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("logout");
                        rd.forward(request, response);
                    }
            
            out.println("</body>");
            out.println("</html>");
        }
    }
}
