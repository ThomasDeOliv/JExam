package com.thomasdeoliv.itemsmanager.ui.viewsmodels;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryFailedException;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import com.thomasdeoliv.itemsmanager.ui.cells.ProjectListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.List;

public class ProjectsListViewModel {

    @FXML
    public Button newProjectButton;
    @FXML
    public Button updateProjectButton;
    @FXML
    public Button closeProjectButton;
    @FXML
    public Button deleteProjectButton;
    @FXML
    private ObservableList<Project> projects;
    @FXML
    private ListView<Project> projectsListView;
    @FXML
    private Text sortLink;

    public ProjectsListViewModel() {
        // Fetch collections
        try {
            // Query all projects
            List<Project> projectsList = Launcher.getProjectDAO().getAllProjects();
            this.projects = FXCollections.observableArrayList(projectsList);
        } catch (QueryFailedException e) {
            // Use an empty list if the request failed
            this.projects = FXCollections.observableArrayList();
        }
    }

    @FXML
    public void initialize() {
        // Fill all ListView items with project Observable list
        this.projectsListView.getItems().addAll(projects);
        // Select first element
        if (!projects.isEmpty()) {
            this.projectsListView.getSelectionModel().selectFirst();
            Launcher.selectedProjectProperty().set(this.projectsListView.getSelectionModel().getSelectedItem());
        }
        // Custom cells for projects ListView
        this.projectsListView.setCellFactory(listView -> new ProjectListCell());
    }

    @FXML
    private void handleMouseEnter(MouseEvent event) {
        this.sortLink.setCursor(Cursor.HAND);
    }

    @FXML
    private void handleMouseExit(MouseEvent event) {
        this.sortLink.setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void selectListViewElement(MouseEvent event) {
        // Get selected project
        Project selectedProject = this.projectsListView.getSelectionModel().getSelectedItem();
        // Ensure the selection is not null
        if (selectedProject != null) {
            // Set selectedProject in launcher
            Launcher.selectedProjectProperty().set(selectedProject);
            // Display the selected project in an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Project Selected");
            alert.setHeaderText(null);
            alert.setContentText("Selected Project: " + selectedProject.getName());
            alert.showAndWait();
        }
    }

    @FXML
    public void sortCollection(MouseEvent actionEvent) {
        // Update text
        if (this.sortLink.getText().equals("Trier du plus ancien au plus récent")) {
            this.sortLink.setText("Trier du plus récent au plus ancien");
        } else {
            this.sortLink.setText("Trier du plus ancien au plus récent");
        }
        // Display alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Project Sort");
        alert.setHeaderText(null);
        alert.setContentText("Sorted Projects");
        alert.showAndWait();
    }
}