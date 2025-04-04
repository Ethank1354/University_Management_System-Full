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

    private static DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("info.db").toString());

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
        Connection conn = db.getConnection(); // Get connection from DatabaseManager
        if (conn == null) {
            System.out.println("Error: Could not get database connection");
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Courses WHERE \"Course Code\" = ? AND \"Section Number\" = ?")) {

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
        System.out.println("Checking for lecture time conflict for: " + newCourse.getCourseName() +
                ", Time: " + newCourse.getLectureTime() + ", Location: " + newCourse.getLocation());

        List<String> existingCourses = db.getFilteredValues("Courses",
                new String[]{"Course Name", "Lecture Time", "Location"},
                "Course Code",
                "%"
        );

        for (String courseRow : existingCourses) {
            String[] courseData = courseRow.split(",");
            if (courseData.length < 3) {
                continue;
            }

            String existingCourseName = courseData[0];
            String existingCourseTime = courseData[1].trim(); // Trim whitespace
            String existingRoom = courseData[2].trim(); // Trim whitespace

            System.out.println("Existing course: " + existingCourseName +
                    ", Time (DB): " + existingCourseTime + ", Location (DB): " + existingRoom);

            if (!existingCourseName.equals(newCourse.getCourseName())) {
                boolean timesOverlap = doTimesOverlap(existingCourseTime, newCourse.getLectureTime());
                boolean roomsMatch = existingRoom.equals(newCourse.getLocation());

                System.out.println("Times overlap: " + timesOverlap + ", Rooms match: " + roomsMatch);

                if (timesOverlap || roomsMatch) {
                    System.out.println("Conflict detected!");
                    return true;
                }
            }
        }
        System.out.println("No conflict detected.");
        return false;
    }

    private static boolean doTimesOverlap(String time1, String time2) {
        System.out.println("Checking time overlap: Time1=" + time1 + ", Time2=" + time2);

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

        System.out.println("Days1: " + days1 + ", Days2: " + days2);
        if (!doDaysOverlap(days1, days2)) {
            System.out.println("Days do not overlap.");
            return false;
        }

        int[] range1 = extractTimeRange(timeRange1);
        int[] range2 = extractTimeRange(timeRange2);

        if (range1 == null || range2 == null) {
            return false;
        }

        System.out.println("Range1: [" + range1[0] + ", " + range1[1] + "], Range2: [" + range2[0] + ", " + range2[1] + "]");

        boolean overlap = range1[0] < range2[1] && range1[1] > range2[0];
        System.out.println("Time overlap result: " + overlap);
        return overlap;
    }

    private static boolean doDaysOverlap(String days1, String days2) {
        System.out.println("Checking day overlap: Days1=" + days1 + ", Days2=" + days2);

        String[] daysArray1 = days1.split("/");
        String[] daysArray2 = days2.split("/");

        for (String day1 : daysArray1) {
            for (String day2 : daysArray2) {
                if (day1.equalsIgnoreCase(day2)) { // Case-insensitive comparison
                    System.out.println("Days overlap: " + day1);
                    return true;
                }
            }
        }
        System.out.println("Days do not overlap.");
        return false;
    }

    private static int[] extractTimeRange(String timeRange) {
        System.out.println("Extracting time range: " + timeRange);

        try {
            String[] times = timeRange.split("-");
            int startTime = convTo24Hrs(times[0]);
            int endTime = convTo24Hrs(times[1]);
            System.out.println("Extracted time range: [" + startTime + ", " + endTime + "]");
            return new int[]{startTime, endTime};
        } catch (Exception e) {
            System.out.println("Invalid Time Format: " + timeRange);
            return null;
        }
    }

    private static int convTo24Hrs(String time) {
        System.out.println("Converting to 24-hour format: " + time);

        boolean isPM = time.toUpperCase().contains("PM");
        time = time.replaceAll("[APM]", "").trim();

        String[] parts = time.split(":");
        if (parts.length != 2) { // Added check for invalid format
            System.out.println("Invalid time format (missing colon): " + time);
            return -1; // Indicate error
        }

        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);

            if (isPM && hours != 12) {
                hours += 12;
            }
            if (!isPM && hours == 12) {
                hours = 0;
            }

            int result = hours * 60 + minutes;
            System.out.println("Converted time: " + result);
            return result;
        } catch(NumberFormatException e) {
            System.out.println("Invalid number format in time: " + time);
            return -1;
        }
    }

}


