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

        //接收用户id
        String id = req.getParameter("id");
        //接收用户的logintime
        String logintime = req.getParameter("login");

        //输出登陆界面
        pw.println("<img src = 'imgs/hello.jpg'/> Welcome "+id+"!!"+logintime+""
            + "<a style='text-decoration: none' href = 'loginframe'>&nbsp;&nbsp;&nbsp;&nbsp;BackLogin</a>"
            + "<a style='text-decoration: none' href = 'loginframe'>&nbsp;&nbsp;&nbsp;&nbsp;SafeQuit</a><hr/>");
        pw.println("<h3>Please select...</h3></br>");
        pw.println("<a style='text-decoration: none' href='/UserManager3/manageuser'>Manage User</a></br>");
        pw.println("<a style='text-decoration: none' href='/UserManager3/userservlet?type=gotoadduser'>Add User</a></br>");
        pw.println("<a style='text-decoration: none' href='#'>Check User</a></br>");
        pw.println("<a style='text-decoration: none' href='#'>Quit System</a></br>");
        pw.println("<hr/><img src = 'imgs/welcome.jpg'/>");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        this.doPost(req, res);
    }
}

