package engg1420_project.universitymanagementsystem;
public class Course {
    private String courseName;
    private int courseCode;
    private String subjectName;
    private int sectionNumber;
    private String teacherName;
    private int capacity;
    private String lectureTime;
    private String location;
    private String finalExamDateTime;

    //Course Calc = new Course();
//"Calculus I", "MATH001", 1, "Mrs. " 300, "30 Mon/Wed 9-11 AM", "Place", "April 01/7:00-9:00pm"
    public Course(String courseName, int courseCode, String subjectName, int sectionNumber, String teacherName, int capacity, String lectureTime, String location, String finalExamDateTime) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.subjectName = subjectName;
        this.sectionNumber = sectionNumber;
        this.teacherName = teacherName;
        this.capacity = capacity;
        this.lectureTime = lectureTime;
        this.location = location;
        this.finalExamDateTime = finalExamDateTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public String getLocation() {
        return location;
    }

    public String getFinalExamDateTime() {
        return finalExamDateTime;
    }


    @Override
    public String toString(){
        return subjectName + courseCode;
    }

}


