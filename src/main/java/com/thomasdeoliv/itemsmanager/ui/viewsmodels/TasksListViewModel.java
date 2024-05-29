package com.thomasdeoliv.itemsmanager.ui.viewsmodels;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryFailedException;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TasksListViewModel {

	private ObservableList<Task> tasks;

	@FXML
	private ListView<Task> tasksListView;

	@FXML
	public void initialize() {
		tasksListView.getItems().addAll(tasks);
	}

	public TasksListViewModel() {
		try {
			// Query all tasks
			this.tasks = FXCollections.observableArrayList(Launcher.taskDAO.getAllTasks(Launcher.SelectedProject.getId()));
		} catch (QueryFailedException e) {
			// Use an empty list if the request failed
			this.tasks = FXCollections.observableArrayList();
		}
	}
}