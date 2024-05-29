package com.thomasdeoliv.itemsmanager.ui.viewsmodels;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.database.daos.exceptions.QueryFailedException;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import com.thomasdeoliv.itemsmanager.ui.cells.TaskListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class TasksListViewModel {
    @FXML
    public Button newTaskButton;
    @FXML
    public Button updateTaskButton;
    @FXML
    public Button closeTaskButton;
    @FXML
    public Button deleteTaskButton;
    @FXML
    public Text sortLink;
    @FXML
    private ObservableList<Task> tasks;
    @FXML
    private ListView<Task> tasksListView;

    public TasksListViewModel() {
        try {
            // Query all tasks
            this.tasks = FXCollections.observableArrayList(Launcher.getTaskDAO().getAllTasks(Launcher.selectedProjectProperty().get().getId()));
        } catch (QueryFailedException e) {
            // Use an empty list if the request failed
            this.tasks = FXCollections.observableArrayList();
        }
    }

    @FXML
    public void initialize() {
        // Fill all ListView items with project Observable list
        tasksListView.getItems().addAll(tasks);
        // Select first element
        if (!tasks.isEmpty()) {
            tasksListView.getSelectionModel().selectFirst();
            Launcher.selectedTaskProperty().set(tasksListView.getSelectionModel().getSelectedItem());
        }
        // Custom cells for projects ListView
        tasksListView.setCellFactory(listView -> new TaskListCell());
    }

    @FXML
    public void handleMouseEnter(MouseEvent mouseEvent) {
    }

    @FXML
    public void handleMouseExit(MouseEvent mouseEvent) {
    }

    @FXML
    public void selectListViewElement(MouseEvent mouseEvent) {
    }

    @FXML
    public void sortCollection(MouseEvent mouseEvent) {
    }
}