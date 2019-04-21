package view;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFrame extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        PrintWriter pw = res.getWriter();
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");
        pw.println("<img src = 'imgs/hello.jpg'/><hr/>");
        pw.println("<h1>User Login</h1>");
        pw.println("<form action = 'loginservlet'  method = 'post'>");
        pw.println("Userid : <input type = 'text' name = 'id'/></br>");
        pw.println("Password : <input type = 'password' name = 'password'/></br>");
        pw.println("<input type = 'submit' value = 'login'/>");
        pw.println("</form>");

        String errInfo = (String) req.getAttribute("err");
        //判断用户名是不是输入有误
        if (errInfo != null) {
            pw.println("<font color = 'red'>" + errInfo + "</font>");
        }
        pw.println("<hr/><img src = 'imgs/welcome.jpg'/>");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        this.doPost(req, res);

    }
}
