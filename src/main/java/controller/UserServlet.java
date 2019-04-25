package controller;

import Ser.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
 
        String id = req.getParameter("id");
        //接收type
        String type = req.getParameter("type");

        if("del".equals(type)) {
            //调用UserService 来删除用户
            if (new UserService().deleteUser(id)) {
                //跳转到删除成功的页面
                //能用forward 就不用sendRedirect
                req.getRequestDispatcher("/deleteok").forward(req, res);
            } else {
                req.getRequestDispatcher("/deletefailed").forward(req, res);
            }
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        this.doPost(req,res);
    }
}
