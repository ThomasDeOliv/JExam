package com.thomasdeoliv.itemsmanager.ui.viewsmodels;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryFailedException;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import com.thomasdeoliv.itemsmanager.ui.viewsmodels.cells.ProjectListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ProjectsListViewModel {

	private ObservableList<Project> projects;

	public ProjectsListViewModel() {
		// Fetch collections
		try {
			// Query all projects
			this.projects = FXCollections.observableArrayList(Launcher.projectDAO.getAllProjects());
		} catch (QueryFailedException e) {
			// Use an empty list if the request failed
			this.projects = FXCollections.observableArrayList();
		}
	}

	private @FXML ListView<Project> projectsListView;
	public @FXML Button newProjectButton;
	public @FXML Button updateProjectButton;
	public @FXML Button closeProjectButton;
	public @FXML Button deleteProjectButton;
	private @FXML Text sortLink;

	public @FXML void initialize() {
		// Fill all ListView items with project Observable list
		projectsListView.getItems().addAll(projects);
		// Select first element
		if (!projects.isEmpty()) {
			projectsListView.getSelectionModel().selectFirst();
			Launcher.SelectedProject = projectsListView.getSelectionModel().getSelectedItem();
		}
		// Custom cells for projects ListView
		projectsListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> listView) {
				return new ProjectListCell();
			}
		});
	}

	private @FXML void handleMouseEnter(MouseEvent event) {
		sortLink.setCursor(Cursor.HAND);
	}

	private @FXML void handleMouseExit(MouseEvent event) {
		sortLink.setCursor(Cursor.DEFAULT);
	}

	private @FXML void selectListViewElement(MouseEvent event) {
		// Get selected project
		Project selectedProject = projectsListView.getSelectionModel().getSelectedItem();
		// Ensure the selection is not null
		if (selectedProject != null) {
			// Set selectedProject in launcher
			Launcher.SelectedProject = selectedProject;
			// Display the selected project in an alert
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Project Selected");
			alert.setHeaderText(null);
			alert.setContentText("Selected Project: " + selectedProject.getName());
			alert.showAndWait();
		}
	}

	public @FXML void sortCollection(MouseEvent actionEvent) {
		// Update text
		if (sortLink.getText().equals("Trier du plus ancien au plus récent")) {
			sortLink.setText("Trier du plus récent au plus ancien");
		} else {
			sortLink.setText("Trier du plus ancien au plus récent");
		}
		// Display alert
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Project Sort");
		alert.setHeaderText(null);
		alert.setContentText("Sorted Projects");
		alert.showAndWait();
	}
}