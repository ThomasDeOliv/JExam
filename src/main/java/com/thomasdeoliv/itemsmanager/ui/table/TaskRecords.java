package com.thomasdeoliv.itemsmanager.ui.table;

import com.thomasdeoliv.itemsmanager.database.daos.ITaskDAO;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel with a JTable that displays the provided records of a database table.
 */
public class TaskRecords extends JPanel {

	private final ITaskDAO taskDAO;
	private JTable table;

	/**
	 * Constructs a new TablePanel and initializes the GUI.
	 */
	public TaskRecords(ITaskDAO taskDAO) {
		super();
		this.taskDAO = taskDAO;
		this.setLayout(new BorderLayout());
	}

	public void refresh() {
		
	}
}