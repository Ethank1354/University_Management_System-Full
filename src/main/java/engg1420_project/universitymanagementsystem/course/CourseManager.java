package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    private static DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());

    // Method to retrieve all courses from the database
    public static List<Course> getCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();

        System.out.println("Table Headers: " + db.getTableHeaders("Courses"));

        try {
            // Get connection from DatabaseManager
            Connection conn = getConnectionFromDatabaseManager(db);
            if (conn == null) {
                System.out.println("Error: Could not get database connection");
                return courses;
            }

            // Modified query to get all rows from Courses table
            String sql = "SELECT \"Course Code\", \"Course Name\", \"Subject Code\", \"Section Number\", " +
                    "\"Capacity\", \"Lecture Time\", \"Final Exam Date/Time\", \"Location\", \"Teacher Name\" " +
                    "FROM Courses";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                System.out.println("Executing SQL: " + sql);

                while (rs.next()) {
                    String courseName = rs.getString("Course Name");
                    double courseCode = rs.getDouble("Course Code"); // Using double to handle decimals
                    String subjectCode = rs.getString("Subject Code");
                    String sectionNumber = rs.getString("Section Number"); // Handling "Section X" as a string
                    String teacherName = rs.getString("Teacher Name");
                    double capacity = rs.getDouble("Capacity"); // Using double to handle decimals
                    String lectureTime = rs.getString("Lecture Time");
                    String location = rs.getString("Location");
                    String finalExamDateTime = rs.getString("Final Exam Date/Time");

                    if (courseName == null || subjectCode == null || teacherName == null || lectureTime == null || location == null) {
                        continue; // Skip this entry if there's invalid data
                    }

                    System.out.println("Retrieved course: " + courseName + " - Section: " + sectionNumber);

                    // Add valid course to the list
                    courses.add(new Course(courseCode, courseName, subjectCode, sectionNumber,
                            teacherName, capacity, lectureTime, location, finalExamDateTime));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving courses: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Total courses retrieved: " + courses.size());
        return courses;
    }

    // Method to add a new course to the database
    // Method to add a new course to the database
    public static boolean addCourse(Course newCourse) throws SQLException {
        // Check for schedule conflicts before adding the course
        if (checkForLectureTimeConflict(newCourse)) {
            System.out.println("Conflict with Schedule: Overlapping Lecture Time/Room.");
            return false;
        }

        // Prepare course values in the SAME ORDER as the columns in the SELECT query
        String[] courseValues = {
                String.valueOf(newCourse.getCourseCode()),  // "Course Code"
                sanitize(newCourse.getCourseName()),        // "Course Name"
                sanitize(newCourse.getSubjectCode()),       // "Subject Code"
                sanitize(newCourse.getSectionNumber()),     // "Section Number"
                String.valueOf(newCourse.getCapacity()),    // "Capacity"
                sanitize(newCourse.getLectureTime()),       // "Lecture Time"
                sanitize(newCourse.getFinalExamDateTime()), // "Final Exam Date/Time"
                sanitize(newCourse.getLocation()),          // "Location"
                sanitize(newCourse.getTeacherName())        // "Teacher Name"
        };

        // Attempt to add the course to the database
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
        try {
            // Convert courseCode to string for consistency
            String courseCodeStr = String.valueOf(selectedCourse.getCourseCode());
            String sectionNumber = String.valueOf(selectedCourse.getSectionNumber());

            System.out.println("Attempting to delete course with code: " + courseCodeStr +
                    " and section: " + sectionNumber);

            // First, check if the course exists
            String[] columns = {"Course Code", "Section Number"};
            List<String> courseData = db.getFilteredValues("Courses", columns, "Course Code", courseCodeStr);

            if (courseData.isEmpty()) {
                System.out.println("No course found with code: " + courseCodeStr);
                return false;
            }

            System.out.println("Found course data: " + courseData);

            // Use a more specific deletion approach to match both course code and section
            // Since the DatabaseManager.deleteRowFromTable only allows a single filter,
            // we need to create a custom SQL statement
            Connection conn = db.getConnection();
            if (conn == null) {
                System.out.println("Error: Could not get database connection for deletion");
                return false;
            }

            String sql = "DELETE FROM Courses WHERE \"Course Code\" = ? AND \"Section Number\" = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set parameters with the correct types
                stmt.setString(1, courseCodeStr);
                stmt.setString(2, sectionNumber);

                System.out.println("Executing SQL: " + sql + " with params: [" + courseCodeStr + ", " + sectionNumber + "]");

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Course deleted successfully! Rows affected: " + rowsDeleted);
                    return true;
                } else {
                    System.out.println("Delete operation executed but no rows were affected.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error deleting course: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static Connection getConnectionFromDatabaseManager(DatabaseManager dbManager) {
        try {
            Field connField = dbManager.getClass().getDeclaredField("conn");
            connField.setAccessible(true);
            Connection conn = (Connection) connField.get(dbManager);

            if (conn == null || conn.isClosed()) {
                System.out.println("Database connection is closed or null.");
                return null;
            }

            return conn;
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
            System.out.println("Error accessing 'conn' field in DatabaseManager: " + e.getMessage());
            return null;
        }
    }

//    public static void deleteCourse(Course selectedCourse) {
//        String sql = "DELETE FROM Courses WHERE \"Course Code\" = ? AND \"Section Number\" = ?";
//
//        try (Connection conn = db.getConnection();  // Get a fresh connection
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            // Set the parameters with the correct types
//            stmt.setDouble(1, selectedCourse.getCourseCode()); // Use double for course code
//            stmt.setString(2, selectedCourse.getSectionNumber()); // Use string for section number
//
//            int rowsDeleted = stmt.executeUpdate();
//            if (rowsDeleted > 0) {
//                System.out.println("Course deleted successfully!");
//            } else {
//                System.out.println("No matching course found.");
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error deleting course: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }


    private static boolean checkForLectureTimeConflict(Course newCourse) throws SQLException {
        List<String> existingCourses = db.getFilteredValues("Courses",
                new String[]{"Lecture Time", "Location"},
                "Course Code",
                "%"
        );

        for (String courseRow : existingCourses) {
            String[] courseData = courseRow.split(",");
            if (courseData.length < 9) {
                continue;
            }

            String existingCourseTime = courseData[6];
            String existingRoom = courseData[7];

            if (doTimesOverlap(existingCourseTime, newCourse.getLectureTime()) &&
                    existingRoom.equals(newCourse.getLocation())) {
                return true;
            }
        }
        return false;
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


