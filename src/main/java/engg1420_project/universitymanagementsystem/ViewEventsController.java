package engg1420_project.universitymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ViewEventsController {
    @FXML
    private ListView<String> eventsListView;

    @FXML
    public void initialize() {
        // Load events into the list view (dummy data for now)
        eventsListView.getItems().addAll("Event 1", "Event 2", "Event 3");
    }
}
