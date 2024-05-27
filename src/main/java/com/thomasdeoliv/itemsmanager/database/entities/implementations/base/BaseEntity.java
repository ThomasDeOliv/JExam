package com.thomasdeoliv.itemsmanager.database.entities.implementations.base;

import com.thomasdeoliv.itemsmanager.database.entities.IEntity;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;

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
	private LocalDateTime startedAt;
	/**
	 * The nullable closing date and time of the entity.
	 */
	private @Nullable LocalDateTime endedAt;
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
	public boolean getIsActive() {
		return this.isActive;
	}

	@Override
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public LocalDateTime getStartedAt() {
		return this.startedAt;
	}

	@Override
	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	@Override
	public @Nullable LocalDateTime getEndedAt() {
		return this.endedAt;
	}

	@Override
	public void setEndedAt(@Nullable LocalDateTime endedAt) {
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
