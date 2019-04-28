package view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserView extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        pw.println("<img src = 'imgs/hello.jpg'/>&nbsp;&nbsp;&nbsp;"
            + "<a href = '#'>Back to Main</a>&nbsp;&nbsp;&nbsp;"
            + "<a href = '#'>Safe Quit</a><hr/>");
        pw.println("<h1>Add User</h1>");
        pw.println("<form action='/UserManager3/userservlet?type=add' method= 'post'>");
        pw.println("<table border = 1 width = 250px bordercolor = green cellspacing = 0>");
        pw.println("<tr><td>Name</td><td><input type = 'text' name = 'username'/></td></tr>");
        pw.println("<tr><td>Email</td><td><input type = 'text' name = 'email'/></td></tr>");
        pw.println("<tr><td>Grade</td><td><input type = 'text' name = 'grade'/></td></tr>");
        pw.println("<tr><td>Password</td><td><input type = 'password' name = 'password'/></td></tr>");
        pw.println("</table>");
        pw.println("<button type = 'submit' value = 'add'>Add User</button>&nbsp;&nbsp;&nbsp;<button type = 'reset' value = 'rest'>Rest All</button>");
        pw.println("</form>");
        pw.println("<hr/><img src = 'imgs/welcome.jpg'/>");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        this.doPost(req,res);
    }
}
