
package controller;

import Ser.UserService;
import domain.User;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        //Get User input
        String id = req.getParameter("id");
        String password = req.getParameter("password");

        //创建UserService对象 完成到数据库的验证
        UserService userService = new UserService();
        //创建User 对象
        User user = new User();

        user.setId(Integer.parseInt(id));
        user.setPwd(password);

        //判断用户是否要储存用户名
        String val = req.getParameter("iskeepinfo");
        if ("keep".equals(val)) {
            //保存用户id到Cookie中
            Cookie cookie = new Cookie("id", id);
            cookie.setMaxAge(7 * 2 * 24 * 3600);
            res.addCookie(cookie);
        } else if ("nokeep".equals(val)) {
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    cookie.setMaxAge(0);
                    res.addCookie(cookie);
                }
            }
        }

        //显示上一次登陆时间

        Cookie[] cookies = req.getCookies();
        boolean b = false;//假设没有lasttime cookie
        String logintime = "";

        if (cookies != null) {//要保证有cookie传过来
            for (Cookie cookie : cookies) {
                //取出名
                String name = cookie.getName();
                if (name.equals("lasttime"+user.getId())) {
                    logintime = "Your last login time ：" + cookie.getValue();
                    //更新时间
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    String nowTime = simpleDateFormat.format(new java.util.Date());
                    cookie.setValue(nowTime);
                    cookie.setMaxAge(30);
                    res.addCookie(cookie);
                    b = true;
                    break;
                }
            }
        }

        if (!b) {
            logintime = "This is your first login!";
            //没有找到
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            String nowTime = simpleDateFormat.format(new java.util.Date());
            Cookie cookie = new Cookie("lasttime"+user.getId(), nowTime);
            cookie.setMaxAge(30);
            res.addCookie(cookie);
        }


        if (userService.checkUser(user)) {
            //如果进来的话，说明该用户合法
            //如果可以使用请求转发 就不要使用重定向
            req.getRequestDispatcher("/mainframe?id=" + user.getId()+"&login=" +logintime).forward(req, res);

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
