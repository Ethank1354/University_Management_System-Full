package engg1420_project.universitymanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    // Static list to hold predefined courses
    private static List<Course> courses = new ArrayList<>();

    // Static block to initialize predefined courses
    static {
        courses.add(new Course("Calculus I", 1, "MATH001", 1, "Dr. Alan Turing", 30, "Mon/Wed 9-11AM", "Room 101", "2025-12-15 9:00AM"));
        courses.add(new Course("Literature Basics", 2, "ENG101", 1, "Prof. Emily Brontë", 25, "Tue/Thu 10-12 PM", "Room 102", "2025-12-16 10:00AM"));
        courses.add(new Course("Literature Basics", 2, "ENG101", 2, "Prof. Emily Brontë", 25, "Mon/Wed 10-12 PM", "Room 102", "2025-12-16 10:00AM"));
        courses.add(new Course("Introduction to Programming", 3, "CS201", 1, "Prof. Bahar Nozari", 42, "Tue/Thu 12-2 PM", "Room 103", "2025-12-16 12:30PM"));
        courses.add(new Course("Introduction to Chemistry", 4, "CHEM200", 1, "Dr. Lucka Lucku", 50, "Mon/Thu 3-4 PM", "Room 201", "2025-12-14 4:00PM"));
        courses.add(new Course("Introduction to Chemistry", 4, "CHEM200", 2, "Dr. Lucka Lucku", 50, "Mon/Tue 5-6 PM", "Room 201", "2025-12-14 4:00PM"));
        courses.add(new Course("Introduction to Chemistry", 4, "CHEM200", 3, "Dr. Lucka Lucku", 50, "Fri/Thu 2-3 PM", "Room 201", "2025-12-14 4:00PM"));
        courses.add(new Course("Introduction to French", 5, "ENG101", 1, "Dr. Lakyn Copeland", 25, "Tue/Thu 4:30-5:30 PM", "Room 202", "2025-12-13 10:00AM"));
        courses.add(new Course("Introduction to French", 5, "ENG101", 2, "Dr. Lakyn Copeland", 25, "Tue/Thu 5:30-6:30 PM", "Room 202", "2025-12-13 10:00AM"));
        courses.add(new Course("Water Resources", 6, "ENGG402", 1, "Dr. Albozr Gharabaghi", 50, "Mon/Fri 9:00-10:30 AM", "Room 203", "2025-12-01 9:00AM"));
    }

    // Method to retrieve the courses
    public static List<Course> getCourses() {
        return courses;
    }

    // Method to add a new course (if needed)
    public static void addCourse(Course course) {
        courses.add(course);
    }
}
