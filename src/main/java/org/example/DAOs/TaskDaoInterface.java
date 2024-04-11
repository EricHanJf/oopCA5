package org.example.DAOs;

import org.example.DTOs.Task;
import org.example.Exceptions.DaoException;


import java.util.List;


public interface TaskDaoInterface {

    /**
     * Jianfeng Han 12 Mar 2024
     */
    // Feature 1 - Get all Entities
    List<Task> getAllTasks() throws DaoException;

    /**
     * Meghan Keightley 9 Mar 2024.
     */
    // Feature 2 - Find and Display a single Entity by Key
    Task getTaskById(int taskId) throws DaoException;

    /**
     * Meghan Keightley 9 Mar 2024
     */
    // Feature 3 - Placeholder for future method
    public Task deleteTaskById (int taskId ) throws DaoException;
    /**
     * Tony
     */
    /*Feature4- */
    Task insertTask(Task newTask) throws DaoException;

    /*Feature 5
    * Jianfeng Han 14 Mar 2024
    * */
    Task updateTaskById(int id, Task updatetask) throws DaoException;

    /**
     * Meghan Keightley 15 Mar 2024
     */
    // Feature 6 -  Get list of entities matching a filter (based on DTO object)
    List<Task> FilteringTasks(Task filter) throws DaoException;
}

