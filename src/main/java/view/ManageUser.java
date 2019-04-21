package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageUser extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        PrintWriter pw = res.getWriter();
        pw.println("<script type = 'text/javascript'>");
        pw.println("function goToPage(){"
            + "var pageNow = document.getElementById('pageto').value;"
            + "window.open('/UserManager2/manageuser?pageNow='+pageNow,'_self');"
            + "}");
        pw.println("</script>");
        res.setContentType("text/html");

        pw.println("<img src = 'imgs/hello.jpg'/>&nbsp;&nbsp;&nbsp;Welcome!!&nbsp;&nbsp;&nbsp;"
            + "<a href = '#'>Back to Main</a>&nbsp;&nbsp;&nbsp;"
            + "<a href = '#'>Safe Quit</a><hr/>");
        pw.println("<h1>User Manager</h1>");

        //从数据库中取出用户信息，并且显示
        Connection ct = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String user = "root";
        String databasepassword = "";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/UMS2";

        //分页功能
        //定义分页需要的变量

        int pageNow = 1;//pageNow 表示第几页。该变量是由用户来决定的，因此它是变化的。

        //接收用户的pageNow
        String sPageNow = req.getParameter("pageNow");
        if (sPageNow != null) {
            pageNow = Integer.parseInt(sPageNow);
        }

        int pageSize = 3;//pageSize 每夜显示几条记录。一般来说是由程序指定的。也可以由用户定制。
        int rowCount;//rowCount 共有多少条记录。该变量是查询数据库决定的。
        //计算pageCount

        //todo >>显示后十页 <<显示前十页
        //todo pageNow 过大的问题

        try {
            //1加载驱动
            Class.forName(driver);
            //2得到链接
            ct = DriverManager.getConnection(url, user, databasepassword);

            //算出共有多少页
            ps = ct.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            rowCount = rs.getInt(1);
            int pageCount = (rowCount - 1) / pageSize + 1;
            //pageCount 表示一共有多少页。该变量是计算出来的。

            //3创建PreparedStatement
            //请思考，如果给出的条件是 pageNow = 2, pageSize =3 sql语句应该如何写？
            ps = ct.prepareStatement("select * from users limit ?,?");

            ps.setInt(1, (pageNow - 1) * 3);
            ps.setInt(2, pageSize);

            //4执行操作
            rs = ps.executeQuery();
            //根据结果处理
            pw.println("<table border = 1 width = 500px bordercolor = green cellspacing = 0>");
            pw.println("<tr><th>id</th><th>Username</th><th>Email</th><th>Grade</th></tr>");
            while (rs.next()) {
                //循环显示所有用户信息
                pw.println("<tr><td>" + rs.getBigDecimal(1)
                    + "</td><td>" + rs.getString(2)
                    + "</td><td>" + rs.getString(3)
                    + "</td><td>" + rs.getBigDecimal(4)
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
        } finally {
            //关闭资源 最后使用的先关闭
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                rs = null;
            }
            if (ps != null) {
                try {
                    ps.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ps = null;
            }
            if (ct != null) {
                try {
                    ct.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ct = null;
            }
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        this.doPost(req, res);

    }
}
