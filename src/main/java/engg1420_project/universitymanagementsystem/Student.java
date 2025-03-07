package engg1420_project.universitymanagementsystem;
import java.util.ArrayList;
import java.util.Random;

//Student Class
//This should be pretty obvious what things do
public class Student {

    private String name;
    private String id;
    private String address;
    private String phone;
    private String email;
    //private profile picture
    private double tution;
    //grades
    //current semester
    //registered classes
    //subjects registerd
    private String academicLvl;
    private String thesis_title;
    //progress


    public Student() {


    }

    public Student(String name, String address, String phone, String email, String thesis_title, String academicLvl) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.thesis_title = thesis_title;

        switch(academicLvl) {
            case "Undergrad":
                tution = 5000;
                break;
            case "Graduate":
                tution = 7000;
                break;
            case "PhD":
                tution = 10000;
                break;
            default:
                tution = 5000;
                break;
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getThesis_title() {
        return thesis_title;
    }

    public String getAcademicLvl() {
        return academicLvl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }









}
