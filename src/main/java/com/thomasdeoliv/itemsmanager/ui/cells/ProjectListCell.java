package com.thomasdeoliv.itemsmanager.ui.cells;

import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import javafx.scene.control.ListCell;

/**
 * A custom ListCell for displaying Project objects in a ListView.
 */
public class ProjectListCell extends ListCell<Project> {

	/**
	 * Overrides of the updateItem method to update the content of the ListCell.
	 *
	 * @param project the Project object to display in the cell. This can be null if the cell is empty.
	 * @param empty   a boolean indicating whether the cell is empty.
	 */
	@Override
	protected void updateItem(Project project, boolean empty) {
		super.updateItem(project, empty);

		// If the cell is empty or the project is null, clear the cell's text and graphic.
		if (empty || project == null) {
			setText(null);
			setGraphic(null);
		} else {
			// Otherwise, set the cell's text to the project's name.
			setText(project.getName());
		}
	}
}