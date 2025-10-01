package JDBC;

import java.sql.*;

/*
* 负责加载驱动、获取连接、关闭资源：
* */

public class JDBCUtil {

    private static String url = "jdbc:mysql:///student?useSSL=false";
    private static String user = "root";
    private static String password = "1234567";


/*
* // 静态方法：获取数据库连接（外部可调用，需要时才创建连接）
* 为什么不用static，因为static代码块，只有在加载驱动的时候才会执行一次
*
* */
    public static Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, password);

        }catch(SQLException e){
            e.printStackTrace();
//            throw new RuntimeException("数据库连接失败！"); // 连接失败时抛出异常
        }
        return conn;
    }


   public static void close(Connection conn, Statement pstmt, ResultSet rs){
       try{
           if(rs != null) rs.close();
           if(conn!=null) conn.close();
           if(pstmt !=null) pstmt.close();
       }catch (SQLException e){
           e.printStackTrace();
       }
   }











}
