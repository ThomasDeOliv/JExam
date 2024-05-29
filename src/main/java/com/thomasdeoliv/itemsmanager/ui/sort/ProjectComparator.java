package com.thomasdeoliv.itemsmanager.ui.sort;

import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;

import java.util.Comparator;

/**
 * Comparator for comparing two Project objects based on their start date.
 */
public class ProjectComparator implements Comparator<Project> {

	/**
	 * Compares two Project objects by their start date.
	 *
	 * @param p1 the first project to be compared.
	 * @param p2 the second project to be compared.
	 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
	 */
	@Override
	public int compare(Project p1, Project p2) {
		// Validate datas
		if (p1 == null || p2 == null) {
			throw new IllegalArgumentException("Projects to compare cannot be null");
		}
		// Validate datas
		if (p1.getStartedAt() == null || p2.getStartedAt() == null) {
			throw new IllegalArgumentException("Project start date cannot be null");
		}
		// Return statement
		return p1.getStartedAt().compareTo(p2.getStartedAt());
	}
}
