package com.thomasdeoliv.itemsmanager.ui.viewsmodels;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import com.thomasdeoliv.itemsmanager.ui.cells.TaskListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * This class represents the ViewModel for the tasks list.
 */
public class TasksListViewModel {

	/**
	 * Constructor initializes the ViewModel by fetching all tasks for the selected project from the database.
	 */
	public TasksListViewModel() {
		// Query all tasks
		this.tasks = FXCollections.observableArrayList(Launcher.getTaskDAO().getAllTasks(Launcher.selectedProjectProperty().get().getId()));
	}

	/**
	 * List of tasks to be displayed.
	 */
	@FXML
	private ObservableList<Task> tasks;

	/**
	 * ListView to display the tasks.
	 */
	@FXML
	private ListView<Task> tasksListView;

	/**
	 * Button to create a new task.
	 */
	@FXML
	public Button newTaskButton;

	/**
	 * Button to update the selected task.
	 */
	@FXML
	public Button updateTaskButton;

	/**
	 * Button to close the selected task.
	 */
	@FXML
	public Button closeTaskButton;

	/**
	 * Button to delete the selected task.
	 */
	@FXML
	public Button deleteTaskButton;

	/**
	 * Text element acting as a link to trigger sorting.
	 */
	@FXML
	public Text sortLink;

	/**
	 * Initializes the ViewModel.
	 */
	@FXML
	public void initialize() {
		// Fill ListView with tasks datas
		tasksListView.getItems().addAll(tasks);
		// Select the first task if the list is not empty
		if (!tasks.isEmpty()) {
			tasksListView.getSelectionModel().selectFirst();
			Launcher.selectedTaskProperty().set(tasksListView.getSelectionModel().getSelectedItem());
		}
		// Set custom cells for the tasks ListView
		tasksListView.setCellFactory(listView -> new TaskListCell());
	}

	/**
	 * Handles mouse entering the sort link, changing the cursor to a hand.
	 *
	 * @param mouseEvent the mouse event.
	 */
	@FXML
	public void handleMouseEnter(MouseEvent mouseEvent) {

	}

	/**
	 * Handles mouse exiting the sort link, reverting the cursor to default.
	 *
	 * @param mouseEvent the mouse event.
	 */
	@FXML
	public void handleMouseExit(MouseEvent mouseEvent) {

	}

	/**
	 * Handles selecting a task from the ListView.
	 *
	 * @param mouseEvent the mouse event.
	 */
	@FXML
	public void selectListViewElement(MouseEvent mouseEvent) {

	}

	/**
	 * Handles sorting the task list when the sort link is clicked.
	 *
	 * @param mouseEvent the mouse event.
	 */
	@FXML
	public void sortCollection(MouseEvent mouseEvent) {

	}
}