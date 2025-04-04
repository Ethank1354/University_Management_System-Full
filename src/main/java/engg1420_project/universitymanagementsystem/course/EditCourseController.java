//Mateo
package engg1420_project.universitymanagementsystem.course;

import engg1420_project.universitymanagementsystem.HelloApplication;
import engg1420_project.universitymanagementsystem.projectClasses.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class EditCourseController {

    @FXML private TextField nameField;
    @FXML private TextField codeField;
    @FXML private TextField subjectField;
    @FXML private TextField sectionField;
    @FXML private TextField teacherField;
    @FXML private TextField capacityField;
    @FXML private TextField timeField;
    @FXML private TextField locationField;
    @FXML private TextField examField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Course course;
    private static DatabaseManager db;

    static {
        String dbPath = (HelloApplication.class.getResource("test.db") != null)
                ? HelloApplication.class.getResource("test.db").toString()
                : "Database file not found!";
        System.out.println("Database path: " + dbPath);
        db = new DatabaseManager(dbPath);
    }

    public void setCourse(Course course) {
        this.course = course;
        nameField.setText(course.getCourseName());
        codeField.setText(String.valueOf(course.getCourseCode()));
        subjectField.setText(course.getSubjectCode());
        sectionField.setText(String.valueOf(course.getSectionNumber()));
        teacherField.setText(course.getTeacherName());
        capacityField.setText(String.valueOf(course.getCapacity()));
        timeField.setText(course.getLectureTime());
        locationField.setText(course.getLocation());
        examField.setText(course.getFinalExamDateTime());
    }

    @FXML
    private void saveChanges() {
        System.out.println("saveChanges() called.");
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save changes?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirm Save");
        confirmation.setHeaderText(null);

        if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                // Parse the numeric fields
                double courseCode = Double.parseDouble(codeField.getText().trim());
                String sectionNumber = sectionField.getText().trim();
                double capacity = Double.parseDouble(capacityField.getText().trim());

                // Original values to identify the course for update
                String originalSectionNumber = course.getSectionNumber();
                double originalCourseCode = course.getCourseCode();

                System.out.println("Updating course - Original Code: " + originalCourseCode +
                        ", Original Section: " + originalSectionNumber);

                // Update object in memory with the new values
                course.setCourseName(nameField.getText().trim());
                course.setSubjectCode(subjectField.getText().trim());
                course.setTeacherName(teacherField.getText().trim());
                course.setLectureTime(timeField.getText().trim());
                course.setLocation(locationField.getText().trim());
                course.setFinalExamDateTime(examField.getText().trim());
                course.setCourseCode(courseCode);
                course.setSectionNumber(sectionNumber);
                course.setCapacity(capacity);

                // Get the list of column names in the correct order
                List<String> columnNames = db.getTableHeaders("Courses");
                if (columnNames.isEmpty()) {
                    showAlert("Error", "Could not retrieve table structure.");
                    return;
                }

                // Prepare values array in the same order as the table columns
                String[] updatedValues = new String[columnNames.size()];
                updatedValues[columnNames.indexOf("Course Code")] = String.valueOf(courseCode);
                updatedValues[columnNames.indexOf("Course Name")] = nameField.getText().trim();
                updatedValues[columnNames.indexOf("Subject Code")] = subjectField.getText().trim();
                updatedValues[columnNames.indexOf("Section Number")] = sectionNumber;
                updatedValues[columnNames.indexOf("Capacity")] = String.valueOf(capacity);
                updatedValues[columnNames.indexOf("Lecture Time")] = timeField.getText().trim();
                updatedValues[columnNames.indexOf("Final Exam Date/Time")] = examField.getText().trim();
                updatedValues[columnNames.indexOf("Location")] = locationField.getText().trim();
                updatedValues[columnNames.indexOf("Teacher Name")] = teacherField.getText().trim();

                // Convert array to list for updateRowInTable
                List<String> updatedValuesList = Arrays.asList(updatedValues);

                // Use the DatabaseManager's updateRowInTable method
                boolean updated = false;
                try {
                    // First try to update using both Course Code and Section Number
                    String whereClause = "\"Course Code\" = '" + originalCourseCode + "' AND \"Section Number\" = '" + originalSectionNumber + "'";
                    updated = executeCustomUpdate("Courses", whereClause, updatedValuesList, columnNames);

                    if (!updated) {
                        // If that fails, try with just Course Code
                        whereClause = "\"Course Code\" = '" + originalCourseCode + "'";
                        updated = executeCustomUpdate("Courses", whereClause, updatedValuesList, columnNames);
                    }

                    if (updated) {
                        System.out.println("Course updated successfully!");
                        // Close the window after updating
                        Stage stage = (Stage) saveButton.getScene().getWindow();
                        stage.close();
                    } else {
                        showAlert("Error", "Failed to update course. Record may not exist.");
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Error: " + e.getMessage());
                    e.printStackTrace();
                    showAlert("Database Error", "Failed to update the course: " + e.getMessage());
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numbers for Course Code, Section, and Capacity.");
            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
                e.printStackTrace();
                showAlert("Database Error", "An error occurred: " + e.getMessage());
            }
        }
    }

    // Helper method to execute a custom update query
    private boolean executeCustomUpdate(String tableName, String whereClause, List<String> newValues, List<String> columnNames) throws SQLException {
        String quotedTableName = tableName.contains(" ") ? "\"" + tableName + "\"" : tableName;
        StringBuilder setClause = new StringBuilder();

        for (int i = 0; i < columnNames.size(); i++) {
            String quotedColumn = columnNames.get(i).contains(" ") ? "\"" + columnNames.get(i) + "\"" : columnNames.get(i);
            setClause.append(quotedColumn).append(" = ?");
            if (i < columnNames.size() - 1) {
                setClause.append(", ");
            }
        }

        String sql = "UPDATE " + quotedTableName + " SET " + setClause + " WHERE " + whereClause;
        System.out.println("Executing SQL: " + sql);

        Connection conn = db.getConnection(); // Get connection from DatabaseManager
        if (conn == null) {
            System.out.println("Error: Could not get database connection");
            return false;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < newValues.size(); i++) {
                stmt.setString(i + 1, newValues.get(i));
            }

            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
            return rowsUpdated > 0;
        }
    }

    @FXML
    private void cancelEdit() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to discard changes?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirm Cancel");
        confirmation.setHeaderText(null);

        if (confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

