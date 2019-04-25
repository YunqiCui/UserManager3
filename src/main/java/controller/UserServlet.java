package controller;

import Ser.UserService;
import domain.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        //创建userservice
        UserService userService = new UserService();

        //接收type
        String type = req.getParameter("type");

        if("del".equals(type)) {
            String id = req.getParameter("id");
            //调用UserService 来删除用户
            if (userService.deleteUser(id)) {
                //跳转到删除成功的页面
                //能用forward 就不用sendRedirect
                req.setAttribute("info","Delete Complete!");
                req.getRequestDispatcher("/ok").forward(req, res);
            } else {
                req.setAttribute("info","Delete Error!");
                req.getRequestDispatcher("/error").forward(req, res);
            }
        }else if("gotoupdate".equals(type)){
            //去修改界面
            //得到id
            String id = req.getParameter("id");
            //通过id获取一个user bean
            User user = userService.getUserById(id);
            //为了让下一个Servlet能够使用我们的User对象。我们可以把该User放入到request域对象中。
            req.setAttribute("userinfo",user);

            req.getRequestDispatcher("/updateuser").forward(req,res);
        }else if("update".equals(type)){
            //接收用户的新信息
            String id = req.getParameter("id");
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String grade = req.getParameter("grade");
            String password = req.getParameter("password");

            //修改用户 构建User 对象
            User user = new User(Integer.parseInt(id),username,email,Integer.parseInt(grade),password);
            if(userService.updateUser(user)){
                req.setAttribute("info","Update Complete!");
                req.getRequestDispatcher("/ok").forward(req,res);
            }else {
                req.setAttribute("info","Update Error!");
                req.getRequestDispatcher("/error").forward(req, res);
            }
        }else if ("gotoadduser".equals(type)){
            req.getRequestDispatcher("/adduserview").forward(req,res);
        }else if ("add".equals(type)){
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String grade = req.getParameter("grade");
            String password = req.getParameter("password");
            //构建User 对象

            User user = new User();
            user.setName(username);
            user.setName(email);
            user.setGrade(Integer.parseInt(grade));
            user.setPwd(password);

            if(userService.addUser(user)){
                req.setAttribute("info","Add Complete!");
                req.getRequestDispatcher("/ok").forward(req,res);
            }else {
                req.setAttribute("info","Add Error!");
                req.getRequestDispatcher("/error").forward(req, res);
            }
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        this.doPost(req,res);
    }
}
