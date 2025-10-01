package view;

import  DAO.studentDao;
import POJO.Student;

import java.util.List;
import java.util.Scanner;


public class StudentManager {

    private static studentDao  studentDao = new studentDao();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();// 消费换行符
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    deleteStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    queryStudent();
                    break;
                case 5:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice");

            }
        }
    }


    public static void showMenu() {
        System.out.println("---------------------------");
        System.out.println("      学生管理系统");
        System.out.println("---------------------------");
        System.out.println("| 1...增加学生             |");
        System.out.println("| 2...删除学生             |");
        System.out.println("| 3...修改学生             |");
        System.out.println("| 4...查询所有学生         |");
        System.out.println("| 5...退出系统             |");
        System.out.println("---------------------------");
    }

    public static String getString(String p){
        System.out.println(p);
        return scanner.nextLine();
    }

    public static int getInt(String p){
        System.out.println(p);
        while (!scanner.hasNextInt()) {
            System.out.println("输入无效，请输入整数！");
            System.out.print(p);
            scanner.next();
        }
        int res = scanner.nextInt();
        scanner.nextLine();// 消费换行符
        return res;
    }

    public static void addStudent() {
        System.out.println("===== 增加学生 =====");
        String id = getString("请输入ID：");
        int num = getInt("请输入学号：");
        String name = getString("请输入姓名：");
        String classID = getString("请输入班级号：");
        String sex = getString("请输入性别：");
        int age = getInt("请输入年龄：");

        Student s = new Student(id, num, name, classID, sex, age);
        if (studentDao.addStudent(s)) {
            System.out.println("添加成功！");
        } else {
            System.out.println("添加失败！");
        }
    }

    public static void deleteStudent() {
        System.out.println("===== 删除学生 =====");
        int num = getInt("请输入要删除的学生学号：");
        if(studentDao.deleteStudent(num)){
            System.out.println("删除成功！");
        }
        else{
            System.out.println("删除失败！");
        }
    }

    public static void updateStudent() {
        System.out.println("===== 修改学生 =====");
        int num = getInt("请输入要修改的学生学号：");
        Student oldStudent  = studentDao.findStudentByNum(num);
        if(oldStudent== null){
            System.out.println("修改失败，学生信息不存在");
            return;
        }

        String newId = getString("请输入新ID（原ID：" + oldStudent.getId() + "）：");
        String newName = getString("请输入新姓名（原姓名：" + oldStudent.getName() + "）：");
        String newClassID = getString("请输入新班级号（原班级号：" + oldStudent.getClassID() + "）：");
        String newSex = getString("请输入新性别（原性别：" + oldStudent.getSex() + "）：");
        int newAge = getInt("请输入新年龄（原年龄：" + oldStudent.getAge() + "）：");

        Student student = new Student(newId, num, newName, newClassID, newSex, newAge);
        if (studentDao.updateStudent(student)) {
            System.out.println("修改成功！");
        }else {
            System.out.println("修改失败！");
        }
    }

    public static void queryStudent() {
        System.out.println("===== 查询所有学生 =====");

        if (!studentDao.hasStudents()) {
            System.out.println("暂无学生信息，无法查询！");
            return;
        }
        else{
            List<Student> studentLink = studentDao.queryStudentByNum();
            for (Student s : studentLink) {
                System.out.println(s);
            }
        }
    }
}
