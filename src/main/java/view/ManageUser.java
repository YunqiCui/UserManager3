package view;

import Ser.UserService;
import domain.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageUser extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws IOException {

        PrintWriter pw = res.getWriter();
        pw.println("<script type = 'text/javascript'>");
        pw.println("function goToPage(){"
            + "var pageNow = document.getElementById('pageto').value;"
            + "window.open('/UserManager2/manageuser?pageNow='+pageNow,'_self');"
            + "}"
            + "function confirmOper(){ return window.confirm('Sure you want to delete this User?');}");
        pw.println("</script>");
        res.setContentType("text/html");

        pw.println("<img src = 'imgs/hello.jpg'/>&nbsp;&nbsp;&nbsp;Welcome!!&nbsp;&nbsp;&nbsp;"
            + "<a href = '#'>Back to Main</a>&nbsp;&nbsp;&nbsp;"
            + "<a href = '#'>Safe Quit</a><hr/>");
        pw.println("<h1>User Manager</h1>");

        //分页功能
        //定义分页需要的变量

        int pageNow = 1;//pageNow 表示第几页。该变量是由用户来决定的，因此它是变化的。
        int pageCount = 0;

        //接收用户的pageNow
        String sPageNow = req.getParameter("pageNow");
        if (sPageNow != null) {
            pageNow = Integer.parseInt(sPageNow);
        }

        int pageSize = 3;//pageSize 每夜显示几条记录。一般来说是由程序指定的。也可以由用户定制。

        try {
            UserService userService = new UserService();
            pageCount = userService.getPageCount(pageSize);
            ArrayList<User> al = userService.getUserByPage(pageNow,pageSize);
            //根据结果处理
            pw.println("<table border = 1 width = 500px bordercolor = green cellspacing = 0>");
            pw.println("<tr><th>id</th><th>Username</th><th>Email</th><th>Grade</th><th>Delete User</th><th>Edit User</th></tr>");
            for (User user1 : al) {
                //循环显示所有用户信息
                pw.println("<tr><td>" + user1.getId()
                    + "</td><td>" + user1.getName()
                    + "</td><td>" + user1.getEmail()
                    + "</td><td>" + user1.getGrade()
                    + "</td><td><a  onClick = 'return confirmOper();' href = '/UserManager2/deleteuserservlet?id="+ user1.getId()+"'>Delete User</a>"
                    + "</td><td><a href = '#'>Edit User</a>"
                    + "</td></tr><br/>");
            }
            pw.println("</table>");

            //显示上一页
            if (pageNow != 1) {
                pw.println("<a href ='/UserManager2/manageuser?pageNow=" + (pageNow - 1) + "'>Back</a>");
            }

            //显示分页超链接
            for (int i = 1; i <= pageCount; i++) {
                pw.println("<a href ='/UserManager2/manageuser?pageNow=" + i + "'><" + i + "></a>");
            }
            //显示下一页
            if (pageNow != pageCount) {
                pw.println("<a href ='/UserManager2/manageuser?pageNow=" + (pageNow + 1) + "'>Next</a>");
            }

            //显示当前页
            pw.println("&nbsp;&nbsp;&nbsp;Page:"+pageNow+"/"+pageCount+"<br/>");

            //直接跳转到特定页 如何在servlet中使用JS技术
            pw.println("Jump to : <input id = 'pageto' type = 'text' name = 'pageto'/>&nbsp;&nbsp;&nbsp;<button value='go' onclick = 'goToPage()'>Go</button>");


            pw.println("<hr/><img src = 'imgs/welcome.jpg'/>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        this.doPost(req, res);

    }
}
