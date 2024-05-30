package com.thomasdeoliv.itemsmanager.ui.cells;

import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import javafx.scene.control.ListCell;

/**
 * A custom ListCell for displaying Tasks objects in a ListView.
 */
public class TaskListCell extends ListCell<Task> {

	/**
	 * Overrides of the updateItem method to update the content of the ListCell.
	 *
	 * @param task  the Task object to display in the cell. This can be null if the cell is empty.
	 * @param empty a boolean indicating whether the cell is empty.
	 */
	@Override
	protected void updateItem(Task task, boolean empty) {
		super.updateItem(task, empty);

		// If the cell is empty or the task is null, clear the cell's text and graphic.
		if (empty || task == null) {
			setText(null);
			setGraphic(null);
		} else {
			// Otherwise, set the cell's text to the task's name.
			setText(task.getName());
		}
	}
}