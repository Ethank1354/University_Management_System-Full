//package engg1420group2.universitymanagementsystem.studentmanagement;
package engg1420_project.universitymanagementsystem.student;

import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import engg1420_project.universitymanagementsystem.projectClasses.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Kyle Egan

//Student Class
//This should be pretty obvious what things do
public class Student extends User {

    private DatabaseManager dbm;
    private String studentID;
    private List<String> studentMember = new ArrayList<>();

    private String phoneNumber;
    private String address;
    private String academicLvl;
    private String semster;
    private String thesis;
    private Double academicProgress;
    private String photoName;
    private String profilePhotoLocation;
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<String> grades = new ArrayList<>();

    private double tution;
    private double tutionPaid = 0.0;


    public Student(String studentID, DatabaseManager dbm) throws SQLException {
        this.dbm = dbm;
        this.studentMember = dbm.getRow("Students", "Student ID", studentID);
        this.studentID = studentMember.get(0);
        super.name = studentMember.get(1);
        this.address = studentMember.get(2);
        this.phoneNumber = studentMember.get(3);
        super.email = studentMember.get(4);
        this.academicLvl = studentMember.get(5);
        this.semster = studentMember.get(6);

        if (studentMember.get(8) != null) {
            String[] subjectlist = studentMember.get(8).split(", ");
            for (int i = 0; i < subjectlist.length; i++) {
                this.subjects.add(subjectlist[i]);
            }
            //grades = new String[subjects.size()];
            for (int i = 0; i < subjects.size(); i++) {
                grades.add("50%");
            }
        } else {
            this.subjects = null;
            this.grades = null;
        }

        this.thesis = studentMember.get(9);
        this.academicProgress = Double.parseDouble(studentMember.get(10));
        super.password = studentMember.get(11);

        if (studentMember.get(14) == null) {

            if (academicLvl.equals("Graduate")) {
                this.tution = 40000.0;
            } else {
                this.tution = 50000.0;
            }
        } else {
            tution = Double.parseDouble(studentMember.get(14));
        }

        if (studentMember.get(12) == null) {
            tutionPaid = 0.0;
        } else {
            tutionPaid = Double.parseDouble(studentMember.get(12));
        }

        this.profilePhotoLocation = studentMember.get(7);




    }

    public Student(String name, String address, String phone, String email, String academicLvl, String semster, String photoName, String thesis, String progress, String password) throws SQLException {
        this.name = name;
        this.address = address;
        this.phoneNumber = phone;
        this.email = email;
        this.academicLvl = academicLvl;
        this.semster = semster;
        this.thesis = thesis;
        this.academicProgress = Double.parseDouble(progress.toString());
        this.photoName = photoName;
        super.password = password;

        if (academicLvl.equals("Graduate")) {
            this.tution = 40000.0;
        } else {
            this.tution = 50000.0;
        }

    }

//Getter Methods
    public String getStudentID() {
        return studentID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getAcademicLvl() {
        return academicLvl;
    }

    public String getSemster() {
        return semster;
    }

    public String getThesis() {
        return thesis;
    }

    public Double getTution() {
        return tution;
    }

    public Double getTutionPaid() {
        return tutionPaid;
    }

    public double getAcademicProgress() {
        return academicProgress;
    }

    public ArrayList<String> getSubjects() {
        if (subjects == null || subjects.isEmpty()) {
            return new ArrayList<>();
        }
        return subjects;
    }

    public String getSubjectString() {
        String subjectsStr = "";
        if (subjects != null) {
            for (int i = 0; i < subjects.size(); i++) {
                subjectsStr += subjects.get(i) + ", ";
            }
        }
        return subjectsStr;
    }

    public ArrayList<String> getGrades() {
        if (grades == null) {
            return null;
        }
        return grades;
    }

    public String getGradeString() {
        String gradesStr = "";
        if (grades != null) {
            for (int i = 0; i < grades.size(); i++) {
                gradesStr += grades.get(i) + ", ";
            }
        }
        return gradesStr;
    }

    public String getPhotoLocation() {
        return profilePhotoLocation;
    }



    //Setter Methods

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAcademicLvl(String academicLvl) {
        this.academicLvl = academicLvl;
    }

    public void setSemster(String semster) {
        this.semster = semster;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    public void setAcademicProgress(double academicProgress) {
        this.academicProgress = academicProgress;
    }

    public void updateSubjects(String subject, int index) {
        this.subjects.set(index, subject);
    }

    public void deleteSubjects(String subjectName) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).equals(subjectName)) {
                subjects.remove(i);
                grades.remove(i);
            }
        }
    }

    public void addSubject(String subject) {
        this.subjects.add(subject);
        this.grades.add("50%");
    }

    public void updateStudent() throws SQLException {
        this.studentMember.set(0, this.studentID);
        this.studentMember.set(1, super.name);
        this.studentMember.set(2, this.address);
        this.studentMember.set(3, this.phoneNumber);
        this.studentMember.set(4, super.email);
        this.studentMember.set(5, this.academicLvl);
        this.studentMember.set(6, this.semster);
        this.studentMember.set(7, this.profilePhotoLocation);
        this.studentMember.set(8, this.getSubjectString());
        this.studentMember.set(9, this.thesis);
        this.studentMember.set(10, this.academicProgress.toString());
        this.studentMember.set(11, super.password);
        this.studentMember.set(12, Double.toString(this.tutionPaid));
        this.studentMember.set(13, this.getGradeString());
        this.studentMember.set(14, Double.toString(this.tution));


        try {
            this.dbm.updateRowInTable("Students", "Student ID", this.studentID, this.studentMember);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> studentToList() {
        List<String> studentAsList = new ArrayList<>(){{
            add(studentID);
            add(Student.super.name);
            add(address);
            add(phoneNumber);
            add(Student.super.email);
            add(academicLvl);
            add(semster);
            add(photoName);
            add(getSubjectString());
            add(thesis);
            add(academicProgress.toString());
            add(Student.super.password);

        }};

        return studentAsList;
    }

    public String generateID() throws SQLException {
        int largestID = 0;
        List<String> id = dbm.getColumnValues("Students", "Student ID");

        for (int i = 0; i < id.size(); i++) {
            if (Integer.parseInt(id.get(i).substring(1)) > largestID) {
                largestID = Integer.parseInt(id.get(i));
            }
        }

        return "S" + largestID++;
    }

    public void setTutionPaid(double tutionPaid) {
        this.tutionPaid = tutionPaid;
    }

    public void setTution(double tution) {
        this.tution = tution;
    }

    public void setProfilePhotoLocation(String profilePhotoLocation) {
        if (profilePhotoLocation.contains(".png")) {
            this.profilePhotoLocation = profilePhotoLocation.substring(0, profilePhotoLocation.indexOf(".png"));
        } else {
            this.profilePhotoLocation = profilePhotoLocation ;
        }
    }


    //Update Profile Picture
    public void updateProfilePhoto() {
        try {
            List<String> photoStore = new ArrayList<>();
            photoStore.add(this.studentID);
            photoStore.add(this.profilePhotoLocation);

            this.dbm.updateRowInTable("Photos", "ID", this.studentID, photoStore);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
