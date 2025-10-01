package DAO;
import JDBC.JDBCUtil;
import POJO.Student;


/*
*
* 封装 “增删改查” 的 SQL 逻辑，包含 “重复判断、存在性校验”：
* */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class studentDao {

    /*
    * 添加学生
    * */
    public boolean addStudent( Student student) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            /*
            判断学号有无重复
            * */
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);

            String checkID = "select * from stu where id=?";
            pstmt = conn.prepareStatement(checkID);
            pstmt.setString(1,student.getId());
            rs = pstmt.executeQuery();
            if(rs.next()){
                System.out.println("ID重复，添加失败！");
                conn.rollback();
                return false;
            }

            /*
            判断学号有无重复
            * */

            String checkNum = "select * from stu where num=?";
            pstmt = conn.prepareStatement(checkNum);

            pstmt.setInt(1,student.getNum());
            rs = pstmt.executeQuery();
            if(rs.next()){
                System.out.println("学号重复，添加失败！");
                conn.rollback();
                return false;
            }


            /*
            * 执行添加
            * */

            String addSql = "insert into stu (id, num, name, classID, sex, age) values(?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(addSql);
            pstmt.setString(1,student.getId());
            pstmt.setInt(2,student.getNum());
            pstmt.setString(3,student.getName());
            pstmt.setString(4,student.getClassID());
            pstmt.setString(5,student.getSex());
            pstmt.setInt(6,student.getAge());
            int count = pstmt.executeUpdate();
            conn.commit();
            return count>0;


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            JDBCUtil.close(conn, pstmt, rs);
        }

    }

    /*
    *
    * 删除学生信息
    *
    * */
    public boolean deleteStudent(int num){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            String checkNum = "select * from stu where num=?";
            pstmt = conn.prepareStatement(checkNum);
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();
            if(!rs.next()){
                System.out.println("学号不存在，删除失败！");
                conn.rollback();
                return false;
            }

            String deleteSql = "delete from stu where num=?";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1,num);
            int count = pstmt.executeUpdate();
            conn.commit();
            return count>0;



        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        finally {
            JDBCUtil.close(conn, pstmt, rs);
        }

    }

    /*
    *
    * 修改学生信息
    * */

    public boolean updateStudent(Student student){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "update stu set id = ?,name  = ?, classID = ?, sex = ?, age = ? WHERE num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,student.getId());
            pstmt.setString(2,student.getName());
            pstmt.setString(3,student.getClassID());
            pstmt.setString(4,student.getSex());
            pstmt.setInt(5,student.getAge());
            pstmt.setInt(6,student.getNum());
            int count = pstmt.executeUpdate();
            conn.commit();
            System.out.println("count  =  "+ count);
            return count>0;

        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        finally {
            JDBCUtil.close(conn, pstmt, null);
        }

    }

    /*
     * 按照学号查询学生【单个】
     * *
     */

    public Student findStudentByNum(int num){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Student student = null;

        try{
            conn = JDBCUtil.getConnection();
            String sql = "select * from stu where num=? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();

            if(rs.next()){
                student = new Student();
                student.setId(rs.getString("id"));
                student.setNum(rs.getInt("num"));
                student.setName(rs.getString("name"));
                student.setClassID(rs.getString("classID"));
                student.setSex(rs.getString("sex"));
                student.setAge(rs.getInt("age"));
            }
            else{
                System.out.println("暂无学生信息");
            }
            return student;

        }catch (SQLException e){
            e.printStackTrace();
            return student;
        }
        finally {
            JDBCUtil.close(conn, pstmt, rs);
        }

    }


    /*
    * 按照学号查询学生
    * *
     */

    public List<Student> queryStudentByNum(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Student> student = new ArrayList<Student>();

        try{
            conn = JDBCUtil.getConnection();
            String sql = "select * from stu ";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            boolean hasData = false;

            while(rs.next()){
                hasData = true;
                Student s = new Student();
                s.setId(rs.getString("id"));
                s.setNum(rs.getInt("num"));
                s.setName(rs.getString("name"));
                s.setClassID(rs.getString("classID"));
                s.setSex(rs.getString("sex"));
                s.setAge(rs.getInt("age"));
                student.add(s);
            }

            if(hasData==false){
               System.out.println("暂无学生信息");
            }
            return student;

        }catch (SQLException e){
            e.printStackTrace();
            return student;
        }
        finally {
            JDBCUtil.close(conn, pstmt, rs);

        }

    }


    public boolean hasStudents(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement("select * from stu ");
            String sql = "select count(*) from stu";
            rs = pstmt.executeQuery();
            if(rs.next()&&rs.getInt(1)>0){
                return true;
            }
            return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        finally {
            JDBCUtil.close(conn, pstmt, rs);
        }

    }





}
