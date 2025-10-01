package POJO;

public class Student {

    private String id;
    private int num;
    private String name;
    private String classID;
    private String sex;
    private int age;

    public Student() {}
    public Student(String id, int num, String name, String classID, String sex, int age) {
        this.id = id;
        this.num = num;
        this.name = name;
        this.classID = classID;
        this.sex = sex;
        this.age = age;
    }


    // id的getter和setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // num的getter和setter
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    // name的getter和setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // classID的getter和setter
    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    // sex的getter和setter
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    // age的getter和setter
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", classID='" + classID + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }





}
