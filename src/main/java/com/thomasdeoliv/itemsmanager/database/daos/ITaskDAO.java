package com.thomasdeoliv.itemsmanager.database.daos;

import com.thomasdeoliv.itemsmanager.database.daos.base.IBaseDAO;
import com.thomasdeoliv.itemsmanager.database.daos.models.ExtendedResponseDTO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.List;

public interface ITaskDAO extends IBaseDAO<Task> {
	/**
	 * Retrieves all tasks related to a project from the database.
	 *
	 * @param projectId the ID of the related project.
	 * @return a list of related tasks.
	 */
	ExtendedResponseDTO<List<Task>> getAllProjects(Long projectId) throws SQLException;

	/**
	 * Retrieves a task by its ID.
	 *
	 * @param id the ID of the task to retrieve.
	 * @return the task with the specified ID, or {@code null} if no such entity exists.
	 */
	ExtendedResponseDTO<@Nullable Task> getTaskById(Long id);
}
