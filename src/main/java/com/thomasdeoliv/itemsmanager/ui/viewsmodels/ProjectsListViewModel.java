package com.thomasdeoliv.itemsmanager.ui.viewsmodels;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryFailedException;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ProjectsListViewModel {

	private ObservableList<Project> projects;

	@FXML
	private ListView<Project> projectsListView;

	@FXML
	public void initialize() {
		// Fill all ListView items with project Observable list
		projectsListView.getItems().addAll(projects);
		// Select first element
		if (!projects.isEmpty()) {
			projectsListView.getSelectionModel().selectFirst();
			Launcher.SelectedProject = projectsListView.getSelectionModel().getSelectedItem();
		}
	}

	@FXML
	private void selectListViewElement(MouseEvent event) {
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

	public ProjectsListViewModel() {
		try {
			// Query all tasks
			this.projects = FXCollections.observableArrayList(Launcher.projectDAO.getAllProjects());
		} catch (QueryFailedException e) {
			//
			this.projects = FXCollections.observableArrayList();
		}
	}
}