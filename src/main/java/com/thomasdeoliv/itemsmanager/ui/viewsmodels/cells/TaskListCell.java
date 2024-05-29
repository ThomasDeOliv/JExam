package com.thomasdeoliv.itemsmanager.ui.viewsmodels.cells;

import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import javafx.scene.control.ListCell;

public class TaskListCell extends ListCell<Task> {
	@Override
	protected void updateItem(Task task, boolean empty) {
		super.updateItem(task, empty);

		if (empty || task == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(task.getName());
		}
	}
}
