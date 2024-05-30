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
	 * The number of related tasks.
	 */
	private int tasksCount;

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
	public List<Task> getTasks() {
		return tasks;
	}

	/**
	 * Accessor for the count of related tasks.
	 *
	 * @return The number of related tasks.
	 */
	public int getTasksCount() {
		return this.tasksCount;
	}

	/**
	 * Set the number of tasks.
	 */
	public void setTasksCount(int tasksCount) {
		this.tasksCount = tasksCount;
	}
}
