package com.thomasdeoliv.itemsmanager;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.creation.Seeder;
import com.thomasdeoliv.itemsmanager.database.daos.IProjectDAO;
import com.thomasdeoliv.itemsmanager.database.daos.ITaskDAO;
import com.thomasdeoliv.itemsmanager.database.daos.implementations.ProjectDAO;
import com.thomasdeoliv.itemsmanager.database.daos.implementations.TaskDAO;
import com.thomasdeoliv.itemsmanager.ui.modals.ErrorDialog;
import com.thomasdeoliv.itemsmanager.ui.table.ProjectRecords;
import com.thomasdeoliv.itemsmanager.ui.table.TaskRecords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The class which contains the Main function.
 */
public class Launcher extends JFrame {

	private static IProjectDAO projectDAO;
	private static ITaskDAO taskDAO;

	/**
	 * Constructor
	 */
	public Launcher() {

		// Call base constructor
		super();

		// Safely initialize main window and dependencies
		try {
			// Set window parameters
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			this.setSize(1280, 720);
			this.setResizable(false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLayout(new BorderLayout());

			// Create panels
			ProjectRecords projectPanel = new ProjectRecords(projectDAO);
			TaskRecords taskPanel = new TaskRecords(taskDAO);

			// Create a split pane with a horizontal split
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, projectPanel, taskPanel);
			splitPane.setDividerLocation(0.5); // Set the divider to the middle (50%)

			// Add split pane to the frame
			this.add(splitPane, BorderLayout.CENTER);

			// Load data into panels
			projectPanel.loadProjects();

		} catch (Exception ex) {
			// Instance a model window to display errors
			ErrorDialog errorDialog = new ErrorDialog("Error", ex.getMessage());
			// Show the modal windows to the user
			errorDialog.setVisible(true);
		}
	}

	/**
	 * The Main entry point of the application.
	 *
	 * @param args the command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		try {
			// Set dependencies
			Configuration configuration = new Configuration();
			projectDAO = new ProjectDAO(configuration);
			taskDAO = new TaskDAO(configuration);

			// Create database if requested by the user
			if (args.length != 0 && args[0].equals("--init-db")) {
				// Deploy database
				Seeder seeder = new Seeder(configuration);
				seeder.EnsureDatabaseCreated();
				// Build schema and tables
				seeder.EnsureSchemaAndTablesCreated();
			}

			// Display window asynchronously
			SwingUtilities.invokeLater(() -> {
				// Instantiate the main window
				JFrame frame = new Launcher();
				// Set visibility to true
				frame.setVisible(true);
			});

		} catch (Exception ex) {
			// Instance a model window to display errors
			ErrorDialog errorDialog = new ErrorDialog("Error", ex.getMessage());
			// Show the modal windows to the user
			errorDialog.setVisible(true);
		}
	}
}
