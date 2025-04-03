package engg1420_project.universitymanagementsystem;

import java.util.List;

public class Event {
    private String eventName;
    private String eventCode;
    private String description;
    private String headerImage;  // Path to the image file
    private String location;
    private String dateTime;
    private int capacity;
    private double cost;  // 0 if free, otherwise the cost of the event
    private List<String> registeredStudents;

    public Event() {
    }

    public Event(String eventName, String eventCode,
                 String description, String headerImage,
                 String location, String dateTime, int capacity, double cost) {
        this.eventName = eventName;
        this.eventCode = eventCode;
        this.description = description;
        this.headerImage = headerImage;
        this.location = location;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.cost = cost;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<String> getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(List<String> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
}