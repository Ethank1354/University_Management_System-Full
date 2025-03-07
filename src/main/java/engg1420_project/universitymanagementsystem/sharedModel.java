package engg1420_project.universitymanagementsystem;

import java.util.HashMap;

/*
This file has a sample hashmap that other classes can access
Just so  I don't need multiple sample hashmaps set up
 */

public class sharedModel {
    private static String selectedName;
    //private HashMap<String, Student> studentHashMap = new HashMap<String, Student>();
    private HashMap<String, Student> studentHashMap;



    public static String getSelectedName() {
        return selectedName;
    }

    public static void setSelectedName(String selectedName) {
        sharedModel.selectedName = selectedName;
    }

    public sharedModel() {
        //Creates and populates the hashmap with sample student files
        studentHashMap = new HashMap<>();

        Student Ky = new Student("Kyle", "545 Happy Street", "911", "Kyle@gmail.com", "Research", "Undergrad" );
        Student Da = new Student("Daniel", "222 Less Happy Cresent", "226-500-1567", "DannyH@hotmail.com", "Research", "Undergrad" );
        Student Ac = new Student("Achebe", "535 Normal Road", "519-232-4532", "Achebe123@gmail.com", "Research", "Undergrad" );
        Student An = new Student("Anthony", "1 Upset Street", "519-243-5343", "AntAnthony@gmail.com", "Research", "Undergrad" );
        Student Ma = new Student("Mateo", "45 Angry Road", "226-345-0987", "Mateo@gmail.com", "Research", "Undergrad" );

        studentHashMap.put("Kyle", Ky);
        studentHashMap.put("Daniel", Da);
        studentHashMap.put("Achebe", Ac);
        studentHashMap.put("Anthony", An);
        studentHashMap.put("Mateo", Ma);

    }

    public Student getValueForKey(String key) {
        return studentHashMap.get(key);
    }


    //Ignore this stuff, It's not important to the first phase
    //This is involved in getting the sample hashmap to update when different things are edited
    public HashMap<String, Student> getDataMap() {
        return studentHashMap;
    }

//Same with this
    public void updatePerson(String key, Student person) {
        studentHashMap.put(key, person); // Update the custom object in the map
    }





}
