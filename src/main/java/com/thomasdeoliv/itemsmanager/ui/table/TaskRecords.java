package com.thomasdeoliv.itemsmanager.ui.table;

import com.thomasdeoliv.itemsmanager.database.daos.ITaskDAO;
import com.thomasdeoliv.itemsmanager.database.daos.models.ExtendedResponseDTO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A JPanel with a JTable that displays the provided records of a database table.
 */
public class TaskRecords extends JPanel {

	private final ITaskDAO taskDAO;
	private DefaultListModel<String> listModel;
	private JList<String> list;

	/**
	 * Constructs a new TablePanel and initializes the GUI.
	 */
	public TaskRecords(ITaskDAO taskDAO) {
		super();
		this.taskDAO = taskDAO;
		this.setLayout(new BorderLayout());
	}

	public void refresh(long projectId) {

		// Create a new thread
		Thread thread = new Thread(() -> {

			// Fetch response
			ExtendedResponseDTO<List<Task>> response = taskDAO.getAllProjects(projectId);
			List<Task> tasks = response.getData();

			// Ensure projects exists
			if (tasks != null) {
				// Initialize the list model and populate it with items
				this.listModel = new DefaultListModel<>();

				// Fill list
				for (Task project : tasks) {
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