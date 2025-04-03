package engg1420_project.universitymanagementsystem.course;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private int courseCode;
    private String subjectName;
    private int sectionNumber;
    private String teacherName; // Changed from instructor to teacherName
    private int maxCapacity; // Renamed from capacity
    private int currentCapacity; // Tracks the number of enrolled students
    private String lectureTime;
    private String location;
    private String finalExamDateTime;
    private List<StudentCM> enrolledStudents = new ArrayList<>();

    public Course(String courseName, int courseCode, String subjectName, int sectionNumber,
                  String teacherName, int maxCapacity, String lectureTime, String location, String examTime) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.subjectName = subjectName;
        this.sectionNumber = sectionNumber;
        this.teacherName = teacherName;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = 0; // Initially, no students are enrolled
        this.lectureTime = lectureTime;
        this.location = location;
        this.finalExamDateTime = finalExamDateTime;
    }

    // Getters
    public String getCourseName() { return courseName; }
    public int getCourseCode() { return courseCode; }
    public String getSubjectName() { return subjectName; }
    public int getSectionNumber() { return sectionNumber; }
    public String getTeacherName() { return teacherName; }
    public int getMaxCapacity() { return maxCapacity; }
    public int getCurrentCapacity() { return currentCapacity; }
    public String getLectureTime() { return lectureTime; }
    public String getLocation() { return location; }
    public String getFinalExamDateTime() { return finalExamDateTime; }
    public List<StudentCM> getEnrolledStudents() { return enrolledStudents; }

    // Setters
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCourseCode(int courseCode) { this.courseCode = courseCode; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setSectionNumber(int sectionNumber) { this.sectionNumber = sectionNumber; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public void setLectureTime(String lectureTime) { this.lectureTime = lectureTime; }
    public void setLocation(String location) { this.location = location; }
    public void setFinalExamDateTime(String finalExamDateTime) { this.finalExamDateTime = finalExamDateTime; }
    public void setCurrentCapacity(int currentCapacity) { this.currentCapacity = currentCapacity; }

    @Override
    public String toString() {
        return subjectName + " " + courseCode;
    }
}


