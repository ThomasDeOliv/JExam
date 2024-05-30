package com.thomasdeoliv.itemsmanager.ui.cells;

import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import javafx.scene.control.ListCell;

public class ProjectListCell extends ListCell<Project> {
    @Override
    protected void updateItem(Project project, boolean empty) {
        super.updateItem(project, empty);

        if (empty || project == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(project.getName());
        }
    }
}
