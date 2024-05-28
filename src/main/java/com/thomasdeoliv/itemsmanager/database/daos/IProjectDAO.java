package com.thomasdeoliv.itemsmanager.database.daos;

import com.thomasdeoliv.itemsmanager.database.daos.base.IBaseDAO;
import com.thomasdeoliv.itemsmanager.database.daos.models.ExtendedResponseDTO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IProjectDAO extends IBaseDAO<Project> {
	/**
	 * Retrieves all projects from the database.
	 *
	 * @return a list of all projects.
	 */
	ExtendedResponseDTO<List<Project>> getAllProjects();

	/**
	 * Retrieves a project by its ID.
	 *
	 * @param id the ID of the project to retrieve.
	 * @return the project with the specified ID, or {@code null} if no such entity exists.
	 */
	ExtendedResponseDTO<@Nullable Project> getProjectById(Long id);
}
