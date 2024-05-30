package com.thomasdeoliv.itemsmanager.ui.viewsmodels;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.database.entities.comparators.ProjectComparator;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import com.thomasdeoliv.itemsmanager.ui.cells.ProjectListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.List;

/**
 * This class represents the ViewModel for the projects list.
 */
public class ProjectsListViewModel {

	/**
	 * Comparator for sorting projects by starting dates.
	 */
	private final ProjectComparator projectComparator;

	/**
	 * Constructor initializes the ViewModel by fetching all projects from the database.
	 */
	public ProjectsListViewModel() {
		this.projectComparator = new ProjectComparator();
		// Query all projects
		List<Project> projectsList = Launcher.getProjectDAO().getAllProjects();
		this.projects = FXCollections.observableArrayList(projectsList);
	}

	/**
	 * List of projects to be displayed.
	 */
	@FXML
	private ObservableList<Project> projects;

	/**
	 * ListView to display the projects.
	 */
	@FXML
	private ListView<Project> projectsListView;

	/**
	 * Text element acting as a link to trigger sorting.
	 */
	@FXML
	private Text sortLink;

	/**
	 * Button to create a new project.
	 */
	@FXML
	public Button newProjectButton;

	/**
	 * Button to update the selected project.
	 */
	@FXML
	public Button updateProjectButton;

	/**
	 * Button to close the selected project.
	 */
	@FXML
	public Button closeProjectButton;

	/**
	 * Button to delete the selected project.
	 */
	@FXML
	public Button deleteProjectButton;

	/**
	 * Initializes the ViewModel.
	 */
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

	/**
	 * Handles mouse entering the sort link, changing the cursor to a hand.
	 *
	 * @param event the mouse event.
	 */
	@FXML
	private void handleMouseEnter(MouseEvent event) {
		this.sortLink.setCursor(Cursor.HAND);
	}

	/**
	 * Handles mouse exiting the sort link, reverting the cursor to default.
	 *
	 * @param event the mouse event.
	 */
	@FXML
	private void handleMouseExit(MouseEvent event) {
		this.sortLink.setCursor(Cursor.DEFAULT);
	}

	/**
	 * Handles selecting a project from the ListView.
	 *
	 * @param event the mouse event.
	 */
	@FXML
	private void selectListViewElement(MouseEvent event) {
		// Get the selected project
		Project selectedProject = this.projectsListView.getSelectionModel().getSelectedItem();
		// Ensure the selection is not null
		if (selectedProject != null) {
			// Set the selected project in the launcher
			Launcher.selectedProjectProperty().set(selectedProject);
		}
	}

	/**
	 * Handles sorting the project list when the sort link is clicked.
	 *
	 * @param actionEvent the mouse event.
	 */
	@FXML
	public void sortCollection(MouseEvent actionEvent) {
		// Update text
		if (this.sortLink.getText().equals("Trier du plus ancien au plus récent")) {
			// Sort collection with provided comparator
			this.projects.sort(this.projectComparator);
			// Remove all items in listview
			this.projectsListView.setItems(null);
			// Put again sorted list
			this.projectsListView.setItems(this.projects);
			// Select first item
			this.projectsListView.getSelectionModel().selectFirst();
			// Change text
			this.sortLink.setText("Trier du plus récent au plus ancien");
		} else {
			// Sort collection with provided comparator
			this.projects.sort(this.projectComparator.reversed());
			// Remove all items in listview
			this.projectsListView.setItems(null);
			// Put again sorted list
			this.projectsListView.setItems(this.projects);
			// Select first item
			this.projectsListView.getSelectionModel().selectFirst();
			// Change text
			this.sortLink.setText("Trier du plus ancien au plus récent");
		}
	}
}