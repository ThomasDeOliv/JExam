package com.thomasdeoliv.itemsmanager.datas.database.daos;

import com.thomasdeoliv.itemsmanager.datas.database.daos.entities.IEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
    void saveEntity(T entity);

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity to update.
     */
    void updateEntity(T entity);

    /**
     * Deletes an entity from the database by its ID.
     *
     * @param id the ID of the entity to delete.
     */
    void deleteEntity(Long id);
}