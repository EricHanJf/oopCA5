package org.example.DAOs;

import org.example.DTOs.Task;
import org.example.Exceptions.DaoException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySqlTaskDAO extends MySqlDao implements TaskDaoInterface {

    /**
     * Main author: jianfeng han
     * 12 Mar 2024
     */
    @Override
    public List<Task> getAllTasks() throws DaoException {
        List<Task> tasksList = new ArrayList<>();
        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String status = resultSet.getString("status");
                String priority = resultSet.getString("priority");
                String description = resultSet.getString("description");
                Date dueDate = resultSet.getDate("due_date");

                Task task = new Task(id, title, status, priority, description, dueDate);
                tasksList.add(task);
            }
        } catch (SQLException e) {
            throw new DaoException("Error in getAllTasks(): " + e.getMessage());
        }
        return tasksList;
    }

    /**
     * Meghan Keightley 9 Mar 2024
     */
    @Override
    public Task getTaskById(int id) throws DaoException {
        Task task = null;
        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE id = ?");
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String status = resultSet.getString("status");
                    String priority = resultSet.getString("priority");
                    String description = resultSet.getString("description");
                    Date dueDate = resultSet.getDate("due_date");

                    task = new Task(id, title, status, priority, description, dueDate);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in getTaskById(): " + e.getMessage());
        }
        return task;
    }

    /**
     * Meghan Keightley 9 Mar 2024
     */
    @Override
    public Task deleteTaskById(int id) throws DaoException {
        Task deletedTask = null;
        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tasks WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // If a task was deleted, create a Task object with the deleted task_id
                deletedTask = new Task(id, null, null, null, null, null);
            }

        } catch (SQLException e) {
            throw new DaoException("Error in deleteTaskById(): " + e.getMessage());
        }
        return deletedTask;
    }

    /* Feature 4 - Insert new Task to Database */
    /*
    main author: tony
    * */

    public Task insertTask(Task task) throws DaoException {

        String query = "INSERT INTO tasks (title, status, priority, description, due_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getStatus());
            preparedStatement.setString(3, task.getPriority());
            preparedStatement.setString(4, task.getDescription());
            preparedStatement.setDate(5, new Date(task.getDueDate().getTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error in insertData(): " + e.getMessage());
        }

        return null;
    }

    /*
    * Jianfeng Han 14 Mar 2024
    * */
    @Override
    public Task updateTaskById(int id, Task updatetask) throws DaoException{
        Task previousTask = null;
        String query = "UPDATE tasks SET title=?,status=?,priority=?,description=?,due_date=? WHERE id=?";

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            previousTask = getTaskById(id);
            preparedStatement.setString(1,updatetask.getTitle());
            preparedStatement.setString(2,updatetask.getStatus());
            preparedStatement.setString(3,updatetask.getPriority());
            preparedStatement.setString(4,updatetask.getDescription());
            preparedStatement.setDate(5,new Date(updatetask.getDueDate().getTime()));
            preparedStatement.setInt(6,id);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected == 0){
                throw new DaoException("No task found with ID: " + id);
            }
        }catch (SQLException e) {
            throw new DaoException("Error in updateTaskById(): " + e.getMessage());
        }
        return previousTask;
    }

    @Override
    public List<Task> FilteringTasks(Task filter) throws DaoException {
        List<Task> filteredTasks = new ArrayList<>();
        // Assuming that each attribute in the Task class represents a filtering criterion
        String query = "SELECT * FROM tasks WHERE status = ? AND priority = ?"; // Add more conditions as needed
        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, filter.getStatus());
            preparedStatement.setString(2, filter.getPriority());
            // Set other parameters based on additional attributes in the Task class
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String status = resultSet.getString("status");
                    String priority = resultSet.getString("priority");
                    String description = resultSet.getString("description");
                    Date dueDate = resultSet.getDate("due_date");
                    Task task = new Task(id, title, status, priority, description, dueDate);
                    filteredTasks.add(task);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error in findTasksUsingFilter(): " + e.getMessage());
        }
        return filteredTasks;
    }





    }