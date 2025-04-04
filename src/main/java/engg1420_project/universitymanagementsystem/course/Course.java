package engg1420_project.universitymanagementsystem.course;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private double courseCode; // Using double for course code
    private String subjectCode;
    private String sectionNumber; // Using String to store "Section X"
    private String teacherName;
    private double capacity; // Using double for capacity
    private int currentCapacity;
    private String lectureTime;
    private String location;
    private String finalExamDateTime;
    private List<StudentCM> enrolledStudents = new ArrayList<>();

    public Course(double courseCode, String courseName, String subjectCode, String sectionNumber,
                  String teacherName, double capacity, String lectureTime, String location, String finalExamDateTime) {

        this.courseName = courseName;
        this.courseCode = courseCode;
        this.subjectCode = subjectCode;
        this.sectionNumber = sectionNumber;
        this.teacherName = teacherName;
        this.capacity = capacity;
        this.currentCapacity = 0; // Initially, no students are enrolled
        this.lectureTime = lectureTime;
        this.location = location;
        this.finalExamDateTime = finalExamDateTime;
    }

    // Getters
    public String getCourseName() { return courseName; }
    public double getCourseCode() { return courseCode; }
    public String getSubjectCode() { return subjectCode; }
    public String getSectionNumber() { return sectionNumber; }
    public String getTeacherName() { return teacherName; }
    public double getCapacity() { return capacity; }
    public int getCurrentCapacity() { return currentCapacity; }
    public String getLectureTime() { return lectureTime; }
    public String getLocation() { return location; }
    public String getFinalExamDateTime() { return finalExamDateTime; }
    public List<StudentCM> getEnrolledStudents() { return enrolledStudents; }

    // Setters
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCourseCode(double courseCode) { this.courseCode = courseCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    public void setSectionNumber(String sectionNumber) { this.sectionNumber = sectionNumber; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void setCapacity(double capacity) { this.capacity = capacity; }
    public void setLectureTime(String lectureTime) { this.lectureTime = lectureTime; }
    public void setLocation(String location) { this.location = location; }
    public void setFinalExamDateTime(String finalExamDateTime) { this.finalExamDateTime = finalExamDateTime; }
    public void setCurrentCapacity(int currentCapacity) { this.currentCapacity = currentCapacity; }

    @Override
    public String toString() {
        return subjectCode + " " + courseName;
    }
}



