package com.thomasdeoliv.itemsmanager.ui.load;

import com.thomasdeoliv.itemsmanager.database.daos.implementations.TaskDAO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A Thread fetching all related tasks of a project from the database.
 */
public class TasksLoader extends Thread {

	private final long projectId;
	private final TaskDAO taskDAO;
	private final List<Task> tasks;

	/**
	 * Constructs a new LoadProjects thread with the specified TaskDAO.
	 *
	 * @param projectId The ID of the project.
	 * @param taskDAO   The Data Access Object used to interact with the task database.
	 */
	public TasksLoader(long projectId, TaskDAO taskDAO) {
		this.projectId = projectId;
		this.taskDAO = taskDAO;
		this.tasks = new ArrayList<>();
	}

	/**
	 * The run method is executed when the thread is started.
	 */
	@Override
	public void run() {
		this.tasks.addAll(this.taskDAO.getAllTasks(projectId));
	}

	/**
	 * Returns the list of tasks loaded from the database.
	 *
	 * @return A list of Tasks objects.
	 */
	public List<Task> getTasks() {
		return tasks;
	}
}