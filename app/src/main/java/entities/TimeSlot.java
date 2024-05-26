package entities;

public class TimeSlot {
    private String name;
    private Enum<DayOfWeek> day;
    private String time;
    //Default constructor
    public TimeSlot() {

    }
    //Constructor
    public TimeSlot (String name, Enum<DayOfWeek> day, String time) {
        this.name = name;
        this.day = day;
        this.time = time;
    }

    //Getters and setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Enum<DayOfWeek> getDay() {return day;}
    public void setDay(Enum<DayOfWeek> day) {this.day = day;}
    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}
}
