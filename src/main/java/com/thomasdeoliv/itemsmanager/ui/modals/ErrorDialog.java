package com.thomasdeoliv.itemsmanager.ui.modals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A dialog to display error messages with an icon and a message.
 */
public class ErrorDialog extends JDialog {

	/**
	 * Error Dialog constructor.
	 *
	 * @param title        Title of the dialog.
	 * @param errorMessage Message details.
	 */
	public ErrorDialog(String title, String errorMessage) {
		// Base constructor
		super();

		// Create components
		JLabel messageLabel = new JLabel(errorMessage, SwingConstants.CENTER);

		// Load the icon
		ImageIcon icon = new ImageIcon("src/main/resources/images/error.png");
		JLabel iconLabel = new JLabel(icon);

		// Shift the image to the right
		iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

		// Create a panel for the icon and message
		JPanel messagePanel = new JPanel(new BorderLayout());
		messagePanel.add(iconLabel, BorderLayout.WEST);
		messagePanel.add(messageLabel, BorderLayout.CENTER);

		// Create a button to close the window
		JButton okButton = new JButton("OK");

		// Add action listener to button
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// Create a panel for the button
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(okButton);

		// Set params
		this.setTitle(title);
		this.setLayout(new BorderLayout());
		this.add(messagePanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setModal(true);
		this.setResizable(false);
		this.setSize(450, 150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
