package com.thomasdeoliv.itemsmanager.database.entities.implementations.base;

import com.thomasdeoliv.itemsmanager.database.entities.IEntity;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Represents a base entity with common attributes and methods for all entities.
 */
public abstract class BaseEntity implements IEntity, Serializable {

	/**
	 * The ID of the entity.
	 */
	private Long id;

	/**
	 * The name of the entity.
	 */
	private String name;

	/**
	 * The description of the entity.
	 */
	@Nullable
	private String description;

	/**
	 * The creation date and time of the entity.
	 */
	private Timestamp startedAt;

	/**
	 * The nullable closing date and time of the entity.
	 */
	@Nullable
	private Timestamp endedAt;

	/**
	 * If exists, the related ID of the current item.
	 */
	@Nullable
	private Long relatedItemId;

	/**
	 * Protected Entity constructor.
	 */
	protected BaseEntity() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public String getDescription() {
		return this.description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDescription(@Nullable String description) {
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp getStartedAt() {
		return this.startedAt;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStartedAt(Timestamp startedAt) {
		this.startedAt = startedAt;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public @Nullable Timestamp getEndedAt() {
		return this.endedAt;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEndedAt(@Nullable Timestamp endedAt) {
		this.endedAt = endedAt;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public Long getRelatedItemId() {
		return this.relatedItemId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRelatedItemId(@Nullable Long relatedItemId) {
		this.relatedItemId = relatedItemId;
	}
}
