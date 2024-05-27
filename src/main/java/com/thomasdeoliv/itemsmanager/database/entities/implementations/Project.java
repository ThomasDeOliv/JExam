package com.thomasdeoliv.itemsmanager.database.entities.implementations;

import com.thomasdeoliv.itemsmanager.database.entities.implementations.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a project row in the database.
 */
public class Project extends BaseEntity {
	/**
	 * The list of related tasks.
	 */
	private final List<Task> tasks;

	/**
	 * Constructor.
	 */
	public Project() {
		// Call base entity constructor.
		super();
		// Instantiate list of tasks
		tasks = new ArrayList<>();
	}

	/**
	 * Accessor for the list of related tasks.
	 *
	 * @return The related tasks.
	 */
	public List<Task> GetTasks() {
		return tasks;
	}
}
