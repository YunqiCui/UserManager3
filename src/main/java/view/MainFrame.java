package view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainFrame extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html;charset = utf-8");

        //输出登陆界面
        pw.println("<img src = 'imgs/hello.jpg'/> Welcome!!"
            + "<a href = 'loginframe'>&nbsp;&nbsp;&nbsp;BackLogin</a>"
            + "<a href = 'loginframe'>&nbsp;&nbsp;&nbsp;SafeQuit</a><hr/>");
        pw.println("<h3>Please select...</h3></br>");
        pw.println("<a href='/UserManager2/manageuser'>Manage User</a></br>");
        pw.println("<a href='/UserManager2/userservlet?type=gotoadduser'>Add User</a></br>");
        pw.println("<a href='#'>Check User</a></br>");
        pw.println("<a href='#'>Quit System</a></br>");
        pw.println("<hr/><img src = 'imgs/welcome.jpg'/>");

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        this.doPost(req, res);
    }
}

