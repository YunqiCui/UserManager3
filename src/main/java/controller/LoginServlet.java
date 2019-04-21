
package controller;

import Ser.UserService;
import domain.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        //Get User input
        String id = req.getParameter("id");
        String password = req.getParameter("password");

        //创建UserService对象 完成到数据库的验证
        UserService userService = new UserService();
        //创建User 对象
        User user = new User();

        user.setId(Integer.parseInt(id));
        user.setPwd(password);

        if (userService.checkUser(user)) {
            //如果进来的话，说明该用户合法
            //如果可以使用请求转发 就不要使用重定向
            req.getRequestDispatcher("/mainframe").forward(req, res);

        } else {
            req.setAttribute("err", "Username or password wrong...");
            req.getRequestDispatcher("/loginframe").forward(req, res);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        this.doPost(req, res);

    }
}
