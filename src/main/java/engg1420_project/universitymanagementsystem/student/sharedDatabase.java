//package engg1420group2.universitymanagementsystem.studentmanagement;
package engg1420_project.universitymanagementsystem.student;

public class sharedDatabase {

    private static String selectedName;

    public static String getSelectedName() {
        return selectedName;
    }

    public static void setSelectedName(String selectedName) {
        sharedDatabase.selectedName = selectedName;
    }
}