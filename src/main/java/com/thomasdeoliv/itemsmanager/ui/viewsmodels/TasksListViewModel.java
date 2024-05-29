package com.thomasdeoliv.itemsmanager.ui.viewsmodels;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryFailedException;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import com.thomasdeoliv.itemsmanager.ui.viewsmodels.cells.TaskListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class TasksListViewModel {

	private ObservableList<Task> tasks;

	public TasksListViewModel() {
		try {
			// Query all tasks
			this.tasks = FXCollections.observableArrayList(Launcher.taskDAO.getAllTasks(Launcher.SelectedProject.getId()));
		} catch (QueryFailedException e) {
			// Use an empty list if the request failed
			this.tasks = FXCollections.observableArrayList();
		}
	}

	private @FXML ListView<Task> tasksListView;
	public @FXML Button newTaskButton;
	public @FXML Button updateTaskButton;
	public @FXML Button closeTaskButton;
	public @FXML Button deleteTaskButton;
	public @FXML Text sortLink;

	public @FXML void initialize() {
		// Fill all ListView items with project Observable list
		tasksListView.getItems().addAll(tasks);
		// Select first element
		if (!tasks.isEmpty()) {
			tasksListView.getSelectionModel().selectFirst();
			Launcher.SelectedTask = tasksListView.getSelectionModel().getSelectedItem();
		}
		// Custom cells for projects ListView
		tasksListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			@Override
			public ListCell<Task> call(ListView<Task> listView) {
				return new TaskListCell();
			}
		});
	}

	public @FXML void handleMouseEnter(MouseEvent mouseEvent) {
	}

	public @FXML void handleMouseExit(MouseEvent mouseEvent) {
	}

	public @FXML void selectListViewElement(MouseEvent mouseEvent) {
	}

	public @FXML void sortCollection(MouseEvent mouseEvent) {
	}
}