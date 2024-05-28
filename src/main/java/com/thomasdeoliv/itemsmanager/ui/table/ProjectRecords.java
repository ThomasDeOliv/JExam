package com.thomasdeoliv.itemsmanager.ui.table;

import com.thomasdeoliv.itemsmanager.database.daos.IProjectDAO;
import com.thomasdeoliv.itemsmanager.database.daos.models.ExtendedResponseDTO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 */
public class ProjectRecords extends JPanel {

	private final IProjectDAO projectDAO;
	private DefaultListModel<String> listModel;
	private JList<String> list;

	/**
	 * Build a ProjectRecords component.
	 *
	 * @param projectDAO The DAO instance managing all projects
	 */
	public ProjectRecords(IProjectDAO projectDAO) {

		// Resolve DAO dependency
		this.projectDAO = projectDAO;

		// Define the type of layout
		this.setLayout(new BorderLayout());
	}

	/**
	 * Load projects in a background thread and update the UI.
	 */
	public void loadProjects() {

		// Create a new thread
		Thread thread = new Thread(() -> {

			// Fetch response
			ExtendedResponseDTO<List<Project>> response = projectDAO.getAllProjects();
			List<Project> projects = response.getData();

			// Ensure projects exists
			if (projects != null) {
				// Initialize the list model and populate it with items
				this.listModel = new DefaultListModel<>();

				// Fill list
				for (Project project : projects) {
					this.listModel.addElement(project.getName());
				}

				// Update the UI on the Event Dispatch Thread (EDT)
				SwingUtilities.invokeLater(() -> {
					this.list = new JList<>(listModel);
					this.add(new JScrollPane(list), BorderLayout.CENTER);
					this.revalidate();
					this.repaint();
				});
			}
		});

		// Start the thread
		thread.start();
	}
}