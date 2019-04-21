package Ser;

import dao.SqlHelper;
import domain.User;
import java.sql.*;

public class UserService {

    //写一个验证用户是否合法的函数
    public boolean checkUser(User user) {

        boolean b = false;
        //使用 SQLHELPER
        String sql = "select * from users where id = ? and password = ?";
        String []parameters={user.getId()+"",user.getPwd()};
        ResultSet rs = SqlHelper.queryExecute(sql,parameters);

        //根据rs来判断用户是否存在
        try {
            if (rs.next()){
                b = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            SqlHelper.close(rs,SqlHelper.getPs(),SqlHelper.getCon());
        }
        return b;
    }
}

