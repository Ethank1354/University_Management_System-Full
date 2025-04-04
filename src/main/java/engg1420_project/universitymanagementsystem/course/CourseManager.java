package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    private static DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());

    public static List<Course> getCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();

        System.out.println("Table Headers: " + db.getTableHeaders("Courses"));

        Connection conn = db.getConnection(); // Get connection from DatabaseManager
        if (conn == null) {
            System.out.println("Error: Could not get database connection");
            return courses; // Return empty list or handle error appropriately
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT \"Course Code\", \"Course Name\", \"Subject Code\", \"Section Number\", " +
                     "\"Capacity\", \"Lecture Time\", \"Final Exam Date/Time\", \"Location\", \"Teacher Name\" " +
                     "FROM Courses")) {

            System.out.println("Executing SQL: " + "SELECT \"Course Code\", \"Course Name\", \"Subject Code\", \"Section Number\", " +
                    "\"Capacity\", \"Lecture Time\", \"Final Exam Date/Time\", \"Location\", \"Teacher Name\" " +
                    "FROM Courses");

            while (rs.next()) {
                String courseName = rs.getString("Course Name");
                double courseCode = rs.getDouble("Course Code");
                String subjectCode = rs.getString("Subject Code");
                String sectionNumber = rs.getString("Section Number");
                String teacherName = rs.getString("Teacher Name");
                double capacity = rs.getDouble("Capacity");
                String lectureTime = rs.getString("Lecture Time");
                String location = rs.getString("Location");
                String finalExamDateTime = rs.getString("Final Exam Date/Time");

                if (courseName == null || subjectCode == null || teacherName == null || lectureTime == null || location == null) {
                    continue;
                }

                System.out.println("Retrieved course: " + courseName + " - Section: " + sectionNumber);

                courses.add(new Course(courseCode, courseName, subjectCode, sectionNumber,
                        teacherName, capacity, lectureTime, location, finalExamDateTime));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving courses: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Total courses retrieved: " + courses.size());
        return courses;
    }

    // Method to add a new course to the database
    public static boolean addCourse(Course newCourse) throws SQLException {
        if (checkForLectureTimeConflict(newCourse)) {
            System.out.println("Conflict with Schedule: Overlapping Lecture Time/Room.");
            return false;
        }

        String[] courseValues = {
                String.valueOf(newCourse.getCourseCode()),
                sanitize(newCourse.getCourseName()),
                sanitize(newCourse.getSubjectCode()),
                sanitize(newCourse.getSectionNumber()),
                String.valueOf(newCourse.getCapacity()),
                sanitize(newCourse.getLectureTime()),
                sanitize(newCourse.getFinalExamDateTime()),
                sanitize(newCourse.getLocation()),
                sanitize(newCourse.getTeacherName())
        };

        boolean success = db.addRowToTable("Courses", courseValues);
        if (success) {
            System.out.println("Course added successfully.");
        }
        return success;
    }

    private static String sanitize(String value) {
        return (value == null || value.trim().isEmpty()) ? "N/A" : value;
    }

    // Method to delete a course from the database
    public static boolean deleteCourse(Course selectedCourse) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Courses WHERE \"Course Code\" = ? AND \"Section Number\" = ?")) {

            String courseCodeStr = String.valueOf(selectedCourse.getCourseCode());
            String sectionNumber = String.valueOf(selectedCourse.getSectionNumber());

            System.out.println("Attempting to delete course with code: " + courseCodeStr +
                    " and section: " + sectionNumber);

            if (db.getFilteredValues("Courses", new String[]{"Course Code", "Section Number"}, "Course Code", courseCodeStr).isEmpty()) {
                System.out.println("No course found with code: " + courseCodeStr);
                return false;
            }

            stmt.setString(1, courseCodeStr);
            stmt.setString(2, sectionNumber);

            System.out.println("Executing SQL: DELETE FROM Courses WHERE \"Course Code\" = ? AND \"Section Number\" = ? with params: [" + courseCodeStr + ", " + sectionNumber + "]");

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Course deleted successfully! Rows affected: " + rowsDeleted);
                return true;
            } else {
                System.out.println("Delete operation executed but no rows were affected.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error deleting course: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean checkForLectureTimeConflict(Course newCourse) throws SQLException {
        List<String> existingCourses = db.getFilteredValues("Courses",
                new String[]{"Course Name", "Lecture Time", "Location"}, // Include Course Name
                "Course Code",
                "%"
        );

        for (String courseRow : existingCourses) {
            String[] courseData = courseRow.split(",");
            if (courseData.length < 3) { // Adjusted for 3 columns
                continue;
            }

            String existingCourseName = courseData[0]; // Course Name
            String existingCourseTime = courseData[1]; // Lecture Time
            String existingRoom = courseData[2];       // Location

            // 1. Different Course Names
            if (!existingCourseName.equals(newCourse.getCourseName())) {
                // 2. Overlapping Lecture Times OR Same Room
                if (doTimesOverlap(existingCourseTime, newCourse.getLectureTime()) ||
                        existingRoom.equals(newCourse.getLocation())) {
                    return true; // Conflict found
                }
            }
        }
        return false; // No conflict found
    }

    private static boolean doTimesOverlap(String time1, String time2) {
        String[] parts1 = time1.split(" ", 2);
        String[] parts2 = time2.split(" ", 2);

        if (parts1.length < 2 || parts2.length < 2) {
            System.out.println("Invalid lecture time format.");
            return false;
        }

        String days1 = parts1[0];
        String days2 = parts2[0];
        String timeRange1 = parts1[1];
        String timeRange2 = parts2[1];

        if (!doDaysOverlap(days1, days2)) {
            return false;
        }

        int[] range1 = extractTimeRange(timeRange1);
        int[] range2 = extractTimeRange(timeRange2);

        if (range1 == null || range2 == null) {
            return false;
        }

        return range1[0] < range2[1] && range1[1] > range2[0];
    }

    private static boolean doDaysOverlap(String days1, String days2) {
        String[] daysArray1 = days1.split("/");
        String[] daysArray2 = days2.split("/");

        for (String day1 : daysArray1) {
            for (String day2 : daysArray2) {
                if (day1.equals(day2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int[] extractTimeRange(String timeRange) {
        try {
            String[] times = timeRange.split("-");
            int startTime = convTo24Hrs(times[0]);
            int endTime = convTo24Hrs(times[1]);
            return new int[]{startTime, endTime};
        } catch (Exception e) {
            System.out.println("Invalid Time Format: " + timeRange);
            return null;
        }
    }

    private static int convTo24Hrs(String time) {
        boolean isPM = time.toUpperCase().contains("PM");
        time = time.replaceAll("[APM]", "").trim();

        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        if (isPM && hours != 12) {
            hours += 12;
        }
        if (!isPM && hours == 12) {
            hours = 0;
        }

        return hours * 60 + minutes;
    }
}


