package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    private static DatabaseManager db = new DatabaseManager(HelloApplication.class.getResource("test.db").toString());

    public static List<Course> getCourses() throws SQLException {
        List<String> courseRows = db.getTables();
        List<Course> courses = new ArrayList<>();

        for (String row : courseRows) {
            String[] courseData = row.split(",");
            if (courseData.length < 9) {
                System.out.println("Invalid course data: " + row);
                continue;
            }
            Course course = new Course(
                    courseData[0], // Course name
                    Integer.parseInt(courseData[1]), // Course code
                    courseData[2], // Subject name
                    Integer.parseInt(courseData[3]), // Section number
                    courseData[4], // Instructor
                    Integer.parseInt(courseData[5]), // Max capacity
                    courseData[6], // Lecture time
                    courseData[7], // Room
                    courseData[8]  // Exam time
            );
            courses.add(course);
        }

        return courses;
    }

    public static boolean addCourse(Course newCourse) throws SQLException {
        if (checkForLectureTimeConflict(newCourse)) {
            System.out.println("Conflict with Schedule: Overlapping Lecture Time/Room.");
            return false;
        }

        String[] courseValues = {
                newCourse.getCourseName(),
                String.valueOf(newCourse.getCourseCode()),
                newCourse.getSubjectName(),
                String.valueOf(newCourse.getSectionNumber()),
                newCourse.getTeacherName(),
                String.valueOf(newCourse.getMaxCapacity()),
                newCourse.getLectureTime(),
                newCourse.getLocation(),
                newCourse.getFinalExamDateTime()
        };

        boolean success = db.addRowToTable("courses", courseValues);
        if (success) {
            System.out.println("Course added successfully.");
        }
        return success;
    }

//    public static Course getCourseByNameAndSection(String courseName, int section) throws SQLException {
//        List<String> courseRows = db.getTables();
//
//        for (String row : courseRows) {
//            String[] courseData = row.split(",");
//            if (courseData.length < 9) {
//                System.out.println("Invalid course data: " + row);
//                continue;
//            }
//            if (courseData[0].equals(courseName) && Integer.parseInt(courseData[3]) == section) {
//                return new Course(
//                        courseData[0], // Course name
//                        Integer.parseInt(courseData[1]), // Course code
//                        courseData[2], // Subject name
//                        Integer.parseInt(courseData[3]), // Section number
//                        courseData[4], // Instructor
//                        Integer.parseInt(courseData[5]), // Max capacity
//                        courseData[6], // Lecture time
//                        courseData[7], // Room
//                        courseData[8]  // Exam time
//                );
//            }
//        }
//        return null;
//    }

//    public static boolean addStudentToCourse(Course course, StudentCM student) {
//        return course.enrollStudent(student);
//    }
//
//    public static boolean removeStudentFromCourse(Course course, StudentCM student) {
//        return course.removeStudent(student);
//    }

    public static void deleteCourse(Course course) throws SQLException {
        String filterColumn1 = "courseCode"; // Course code filter
        String filterValue1 = String.valueOf(course.getCourseCode());

        String filterColumn2 = "sectionNumber"; // Section number filter
        String filterValue2 = String.valueOf(course.getSectionNumber());

        db.deleteRowFromTable("courses", filterColumn1, filterValue1, filterColumn2, filterValue2);
    }

    private static boolean checkForLectureTimeConflict(Course newCourse) throws SQLException {
        List<String> existingCourses = db.getTables();

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


