package entities;

public class MyClass {
    String name;
    String grade;
    String subject;
    String year;

    //Default constructor.
    public MyClass() {
    }

    //Constructor with parameters.
    public MyClass(String name, String grade, String subject, String year) {
        this.name = name;
        this.grade = grade;
        this.subject = subject;
        this.year = year;
    }

    //Getters and setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getGrade() {return grade;}
    public void setGrade(String grade) {this.grade = grade;}
    public String getSubject() {return subject;}
    public void setSubject(String subject) {this.subject = subject;}
    public String getYear() {return year;}
    public void setYear(String year) {this.name = year;}
}
