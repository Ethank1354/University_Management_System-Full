//package com.example.project;
package engg1420_project.universitymanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    // list to hold predefined courses
    private static List<Course> courses = new ArrayList<>();

    // predefined courses
    static {
        courses.add(new Course("Calculus I", 1, "MATH001", 1, "Dr. Alan Turing", 30, "Mon/Wed 9:00-11:00AM", "Room 101", "2025-12-15 9:00AM"));
        courses.add(new Course("Literature Basics", 2, "ENG101", 1, "Prof. Emily Brontë", 25, "Tue/Thu 10:00-12:00PM", "Room 102", "2025-12-16 10:00AM"));
        courses.add(new Course("Literature Basics", 2, "ENG101", 2, "Prof. Emily Brontë", 25, "Mon/Wed 10:00-12:00PM", "Room 102", "2025-12-16 10:00AM"));
        courses.add(new Course("Introduction to Programming", 3, "CS201", 1, "Prof. Bahar Nozari", 42, "Tue/Thu 12:00-2:00PM", "Room 103", "2025-12-16 12:30PM"));
        courses.add(new Course("Introduction to Chemistry", 4, "CHEM200", 1, "Dr. Lucka Lucku", 50, "Mon/Thu 3:00-4:00PM", "Room 201", "2025-12-14 4:00PM"));
        courses.add(new Course("Introduction to Chemistry", 4, "CHEM200", 2, "Dr. Lucka Lucku", 50, "Mon/Tue 5:00-6:00PM", "Room 201", "2025-12-14 4:00PM"));
        courses.add(new Course("Introduction to Chemistry", 4, "CHEM200", 3, "Dr. Lucka Lucku", 50, "Fri/Thu 2:00-3:00PM", "Room 201", "2025-12-14 4:00PM"));
        courses.add(new Course("Introduction to French", 5, "ENG101", 1, "Dr. Lakyn Copeland", 25, "Tue/Thu 4:30-5:30PM", "Room 202", "2025-12-13 10:00AM"));
        courses.add(new Course("Introduction to French", 5, "ENG101", 2, "Dr. Lakyn Copeland", 25, "Tue/Thu 5:30-6:30PM", "Room 202", "2025-12-13 10:00AM"));
        courses.add(new Course("Water Resources", 6, "ENGG402", 1, "Dr. Albozr Gharabaghi", 50, "Mon/Fri 9:00-10:30AM", "Room 203", "2025-12-01 9:00AM"));
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static boolean addCourse(Course newCourse) {
        for (Course existingCourse: courses){
            if (!existingCourse.getCourseName().equals(newCourse.getCourseName())){
                if(doTimesOverlap(existingCourse.getLectureTime(), newCourse.getLectureTime())){
                    if (existingCourse.getLocation().equals(newCourse.getLocation())){
                        System.out.println("Conflict with Schedule: Overlapping Lecture Time/Room. ");
                        return false;
                    }
                }
            }
        }
        courses.add(newCourse);
        System.out.println("Course added successfully. ");
        return true;


    }

    private String extractRoomNum(String location){
        return location.replace("Room ", "").trim();
    }

    private static boolean doTimesOverlap(String time1, String time2){
        int[] range1 = extractTimeRange(time1);
        int[] range2 = extractTimeRange(time2);

        if (range1 == null || range2 == null){
            return false;
        }
        return range1[0] < range2[1] && range1[1] > range2[0];
    }

    private static int[] extractTimeRange(String lectureTime){
        try {
            String timePart = lectureTime.substring(lectureTime.indexOf(" ") + 1);
            String[] times = timePart.split("-");

            int startTime = convTo24Hrs(times[0]);
            int endTime = convTo24Hrs(times[1]);

            return new int[]{startTime, endTime};

        } catch (Exception e){
            System.out.println("Invalid Time Format: " + lectureTime);
            return null;
        }
    }

    private static int convTo24Hrs(String time){
        boolean isPM = time.toUpperCase().contains("PM");
        time = time.replaceAll("[APM]", "").trim();

        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        if (isPM && hours != 12){
            hours += 12;
        }

        if (isPM && hours == 12){
            hours += 0;
        }

        return hours * 60 + minutes; //converts everyhting to minutes for easier comparison

    }

    public static Course getCourseByNameAndSection(String courseName, int section) {
        for (Course course : courses) {
            if (course.getCourseName().equals(courseName) && course.getSectionNumber() == section) {
                return course;
            }
        }
        return null;
    }

    // Add a student to a course if there is room
    public static boolean addStudentToCourse(Course course, StudentCM student) {
        if (course != null) {
            return course.enrollStudent(student);
        }
        return false;
    }

    // Remove a student from a course
    public static boolean removeStudentFromCourse(Course course, StudentCM student) {
        if (course != null) {
            return course.removeStudent(student);
        }
        return false;
    }

    public static void deleteCourse(Course course) {
        courses.remove(course);
    }

}
