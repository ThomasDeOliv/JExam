package com.thomasdeoliv.itemsmanager.database.daos;

import com.thomasdeoliv.itemsmanager.database.daos.models.ResponseDTO;
import com.thomasdeoliv.itemsmanager.database.entities.IEntity;

/**
 * Interface with all methods to query database.
 *
 * @param <T> Class implementing the {@link IEntity} interface.
 */
public interface IBaseDAO<T extends IEntity> {
	
	/**
	 * Saves a new entity to the database.
	 *
	 * @param entity the entity to save.
	 */
	ResponseDTO saveEntity(T entity);

	/**
	 * Updates an existing entity in the database.
	 *
	 * @param entity the entity to update.
	 */
	ResponseDTO updateEntity(T entity);

	/**
	 * Deletes an entity from the database by its ID.
	 *
	 * @param id the ID of the entity to delete.
	 */
	ResponseDTO deleteEntity(Long id);
}