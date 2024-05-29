package com.thomasdeoliv.itemsmanager.database.entities.implementations.base;

import com.thomasdeoliv.itemsmanager.database.entities.IEntity;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Represent an entity.
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
	private @Nullable String description;
	/**
	 * Indicate if the entity is currently active.
	 */
	private boolean isActive;
	/**
	 * The creation date and time of the entity.
	 */
	private Timestamp startedAt;
	/**
	 * The nullable closing date and time of the entity.
	 */
	private @Nullable Timestamp endedAt;
	/**
	 * If exists, the related ID of the current item.
	 */
	private @Nullable Long relatedItemId;

	/**
	 * Entity constructor.
	 */
	protected BaseEntity() {

	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public @Nullable String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(@Nullable String description) {
		this.description = description;
	}

	@Override
	public Timestamp getStartedAt() {
		return this.startedAt;
	}

	@Override
	public void setStartedAt(Timestamp startedAt) {
		this.startedAt = startedAt;
	}

	@Override
	public @Nullable Timestamp getEndedAt() {
		return this.endedAt;
	}

	@Override
	public void setEndedAt(@Nullable Timestamp endedAt) {
		this.endedAt = endedAt;
	}

	@Override
	public @Nullable Long getRelatedItemId() {
		return this.relatedItemId;
	}

	@Override
	public void setRelatedItemId(@Nullable Long relatedItemId) {
		this.relatedItemId = relatedItemId;
	}
}
