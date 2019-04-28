package dao;
/*
 * 这是一个连接数据库的类
 * */

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class SqlHelper {

    public static Connection getCon() {
        return con;
    }

    public static PreparedStatement getPs() {
        return ps;
    }

    public static ResultSet getRs() {
        return rs;
    }

    public static CallableStatement getCs() {
        return cs;
    }

    //定义需要的变量
    private static Connection con = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static CallableStatement cs = null;

    //

    private static String driver = "";
    private static String url = "";
    private static String username = "";
    private static String password = "";

    private static Properties pp = null;
    private static InputStream fis = null;

    static {
        try {
            //访问配置文件dbinfo
            pp = new Properties();
            fis = new FileInputStream(
                "/Users/cuiyunqi/Desktop/HSP/UserManager3/src/main/java/dbinfo2.properties");//搞清楚tomcat的主目录的问题
            //JAVAEE访问文件 需要使用类加载器
//            fis = ClassLoader.getSystemResourceAsStream("/Users/cuiyunqi/Desktop/HSP/UserManager3/dbinfo2.properties");


            pp.load(fis);
            url = pp.getProperty("url");
            username = pp.getProperty("user");
            driver = pp.getProperty("Driver");
            password = pp.getProperty("password");

            //???? ??????
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fis = null;
        }

    }

    public static Connection getConnection() {
        try {
//            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    //Sql
    //普通select语句
    public static ResultSet queryExecute(String sql, String[] params) {
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setString(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    //Sql
    //普通select语句
    public static ResultSet queryExecute(String sql, int[] params) {
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setInt(i + 1, params[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    //?SQL?? ????
    public static void queryExecuteMul(String sql[], String[][] parameter) {
        try {
            //获得连接
            con = getConnection();

            //用户可能传入的是多个sql语句
            con.setAutoCommit(false);

            for (int i = 0; i < sql.length; i++) {
                if (parameter[i] != null) {
                    ps = con.prepareStatement(sql[i]);
                    for (int j = 0; j < parameter[i].length; j++) {
                        ps.setString(j + 1, parameter[i][j]);
                    }
                    ps.executeUpdate();
                }
            }
            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
            //回滚
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    public static void executeUpdate(String sql, String[] params) {

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            if(params!=null){
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }}
            ps.executeUpdate();
        } catch (Exception e) {

            e.printStackTrace();
            // handle exception
            //运行时异常是可以捕获或者不捕获的
            throw new RuntimeException(e.getMessage());
        } finally {
            close(rs, ps, con);
        }
    }

    public static void close(ResultSet rs, Statement ps, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
