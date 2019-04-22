package Ser;

import dao.SqlHelper;
import domain.User;
import java.sql.*;
import java.util.ArrayList;

public class UserService {

    //获取pageCount
    public int getPageCount(int pageSize){
        String sql = "select count(*) from users";
        int []empty = null;
        int rowCount = 0;

        ResultSet rs = SqlHelper.queryExecute(sql,empty);
        try {
            rs.next();
            rowCount = rs.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            SqlHelper.close(rs,SqlHelper.getPs(),SqlHelper.getCon());
        }
        return rowCount = (rowCount - 1) / pageSize + 1;
    }

    //写一个验证用户是否合法的函数 用于用户登陆
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

    //按照分页来获取应该显示的用户数据
    //把ResultSet 映射成User对象 然后存储在ArrayList里
    //好处1 ArrayList 封装 User 更符合面向对象的编程方式
    //2 我们通过把ResultSet记录放入每一个User对象中去，再放入ArrayList中去，就可以及时的关闭数据库资源

    //删除用户

    public boolean deleteUser(String id){

        boolean b = true;
        String sql = "delete from users where id = ?";
        String []parameters = {id};
        try{
            SqlHelper.executeUpdate(sql,parameters);
        }catch (Exception e){
            e.printStackTrace();
            b=false;
        }
        return b;

    }

    public ArrayList<User> getUserByPage(int pageNow, int pageSize){

        ArrayList<User> userlist = new ArrayList<User>();

        //TODO sql Syntax error
        String sql = "select * from users limit ?,?";
        int []parameters ={(pageNow - 1) * 3,pageSize};

        ResultSet rs = SqlHelper.queryExecute(sql,parameters);

        try{
            while (rs.next()){
                User u = new User();
                u.setId(rs.getInt(1));
                u.setName(rs.getString(2));
                u.setEmail(rs.getString(3));
                u.setGrade(rs.getInt(4));
                //把User对象放入arrraylist中去
                userlist.add(u);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            SqlHelper.close(rs,SqlHelper.getPs(),SqlHelper.getCon());
        }
        return userlist;
    }
}

