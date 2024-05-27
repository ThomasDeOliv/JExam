package com.thomasdeoliv.itemsmanager.datas.database.entities.implementations;

import com.thomasdeoliv.itemsmanager.datas.database.daos.entities.implementations.base.BaseEntity;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a project row in the database.
 */
public class Project extends BaseEntity {
    private List<Task> tasks;

    /**
     * Constructor.
     */
    public Project() {
        // Call base entity constructor.
        super();
        // Instantiate list of tasks
        tasks = new ArrayList<>();
    }

    public List<Task> GetTasks() {
        return tasks;
    }
}
