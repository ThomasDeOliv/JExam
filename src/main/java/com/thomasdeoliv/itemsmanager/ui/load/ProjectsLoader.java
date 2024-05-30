package com.thomasdeoliv.itemsmanager.ui.load;

import com.thomasdeoliv.itemsmanager.database.daos.implementations.ProjectDAO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * A Thread fetching all projects from the database.
 */
public class ProjectsLoader extends Thread {

	private final ProjectDAO projectDAO;
	private final List<Project> projects;

	/**
	 * Constructs a new LoadProjects thread with the specified ProjectDAO.
	 *
	 * @param projectDAO The Data Access Object used to interact with the project database.
	 */
	public ProjectsLoader(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
		this.projects = new ArrayList<Project>();
	}

	/**
	 * The run method is executed when the thread is started.
	 */
	@Override
	public void run() {
		this.projects.addAll(this.projectDAO.getAllProjects());
	}

	/**
	 * Returns the list of projects loaded from the database.
	 *
	 * @return A list of Project objects.
	 */
	public List<Project> getProjects() {
		return projects;
	}
}