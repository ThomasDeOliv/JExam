package com.thomasdeoliv.itemsmanager.database.entities;

import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;

/**
 * Contract containing all getters and setters for an entity.
 */
public interface IEntity {

	// ID **********************************************************************

	/**
	 * Get the entity ID.
	 *
	 * @return The entity ID.
	 */
	Long getId();

	/**
	 * Set the entity id.
	 *
	 * @param id The provided entity id.
	 */
	void setId(Long id);

	// NAME ********************************************************************

	/**
	 * Get the entity name.
	 *
	 * @return The entity name.
	 */
	String getName();

	/**
	 * Set the entity name.
	 *
	 * @param name The provided entity name.
	 */
	void setName(String name);

	// DESCRIPTION *************************************************************

	/**
	 * Get the entity description.
	 *
	 * @return The entity description.
	 */
	String getDescription();

	/**
	 * Set the entity description.
	 *
	 * @param description The provided entity description.
	 */
	void setDescription(String description);

	// OPENING DATE AND TIME ***************************************************

	/**
	 * Get the entity opening date and time.
	 *
	 * @return The entity opening date and time.
	 */
	Timestamp getStartedAt();

	/**
	 * Mark the current date and time as the starting time for the entity.
	 *
	 * @param startedAt The date and time when the entity started.
	 */
	void setStartedAt(Timestamp startedAt);

	// CLOSING DATE AND TIME ***************************************************

	/**
	 * Get the entity closing date and time.
	 *
	 * @return The entity closing date and time.
	 */
	Timestamp getEndedAt();

	/**
	 * Mark the current date and time as the ending time for the entity.
	 *
	 * @param endedAt The date and time when the entity ended.
	 */
	void setEndedAt(Timestamp endedAt);

	// RELATED ITEM ID *********************************************************

	/**
	 * Get the entity related item ID.
	 *
	 * @return The entity related item ID, or {@code null} if there is no related item.
	 */
	@Nullable
	Long getRelatedItemId();

	/**
	 * Set the entity related item ID.
	 *
	 * @param relatedItemId The provided entity related item ID.
	 */
	void setRelatedItemId(@Nullable Long relatedItemId);
}