package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Task;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.List;


public interface TaskDaoInterface {

    List<Task> getAllTasks() throws DaoException;

    Task getTaskById(int taskId) throws DaoException;

    public Task deleteTaskById (int taskId ) throws DaoException;

    Task insertTask(Task newTask) throws DaoException;
}

