package view;

import domain.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUser extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        //获取从控制器传递的User 对象
       User user =  (User)req.getAttribute("userinfo");
        pw.println("<img src = 'imgs/hello.jpg'/>&nbsp;&nbsp;&nbsp;"
            + "<a href = '#'>Back to Main</a>&nbsp;&nbsp;&nbsp;"
            + "<a href = '#'>Safe Quit</a><hr/>");
        pw.println("<h1>Edit User</h1>");
        pw.println("<form action='/UserManager3/userservlet?type=update' method= 'post'>");
        pw.println("<table border = 1 width = 200px bordercolor = green cellspacing = 0>");
        pw.println("<tr><td>id</td><td><input type = 'text' name = 'id' readonly value = '"+user.getId()+"'/></td></tr>");
        pw.println("<tr><td>Name</td><td><input type = 'text' name = 'username' value = '"+user.getName()+"'/></td></tr>");
        pw.println("<tr><td>Email</td><td><input type = 'text' name = 'email' value = '"+user.getEmail()+"'/></td></tr>");
        pw.println("<tr><td>Grade</td><td><input type = 'text' name = 'grade' value = '"+user.getGrade()+"'/></td></tr>");
        pw.println("<tr><td>Password</td><td><input type = 'text' name = 'password' value = '"+user.getPwd()+"'/></td></tr>");
        pw.println("<tr><td><button type = 'submit' value = 'update'>Update</button></td><td><button type = 'reset' value = 'Refill'>Refill</button></td></tr>");
        pw.println("</table>");
        pw.println("</form>");
        pw.println("<hr/><img src = 'imgs/welcome.jpg'/>");

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        this.doPost(req,res);
    }
}
