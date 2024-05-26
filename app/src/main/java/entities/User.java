package entities;

public class User {
    String name;
    String grade;
    String subject;
    String phoneNo;
    String email;
    String role;

    //Default constructor.
    public User() {
    }

    //Constructor with parameters.
    public User(String name, String grade, String subject, String phoneNo, String email, String role) {
        this.name = name;
        this.grade = grade;
        this.subject = subject;
        this.phoneNo = phoneNo;
        this.email = email;
        this.role = role;
    }

    //Getters and setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getGrade() {return grade;}
    public void setGrade(String grade) {this.grade = grade;}
    public String getSubject() {return subject;}
    public void setSubject(String subject) {this.subject = subject;}
    public String getPhoneNo() {return phoneNo;}
    public void setPhoneNo(String phoneNo) {this.name = phoneNo;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.name = email;}
    public String getRole() {return role;}
    public void setRole(String email) {this.role = role;}

    public static class TimeSlot {
        private String name;
        private enum DayOfWeek {
            SUNDAY,
            MONDAY,
            TUESDAY,
            WEDNESDAY,
            THURSDAY,
            FRIDAY,
            SATURDAY
        }
        private Enum<DayOfWeek> day;
        private String time;

        //Getters and setters
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public Enum<DayOfWeek> getDay() {return this.day;}
        public void setDay(Enum<DayOfWeek> day) {this.day = day;}
        public String getTime() {return time;}
        public void setTime(String time) {this.time = time;}
    }
}
