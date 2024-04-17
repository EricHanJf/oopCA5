package org.example.TaskObjects;

import org.example.DAOs.MySqlTaskDAO;
import org.example.DAOs.TaskDaoInterface;
import org.example.DTOs.Task;
import org.example.Exceptions.DaoException;
import org.example.JSON.JsonConv;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskApp {


    private Scanner sc = new Scanner(System.in);
    private TaskDaoInterface taskDao;
    private JsonConv jsonConv; //add json convert field

    public TaskApp(TaskDaoInterface taskDao) {
        this.taskDao = taskDao;
        this.jsonConv = new JsonConv();
    }

    public static void main(String[] args) {
        TaskDaoInterface taskDao = new MySqlTaskDAO();
        TaskApp taskApp = new TaskApp(taskDao);

        while (true) {
            taskApp.displayMenu();
            taskApp.handleMenu();
        }
    }

    public void displayMenu() {
        System.out.println("1. Display All Tasks");
        System.out.println("2. Display Task by ID");
        System.out.println("3. Delete Task by ID");
        System.out.println("4. Add New Task");
        System.out.println("5. Update Task By ID");
        System.out.println("6. Filter by Status & Priority");
        System.out.println("7. Convert List of Entities to a JSON String ");
        System.out.println("8. Convert a single Entity by Key as a JSON String  ");
        System.out.println("7. Exit");
    }

    public void handleMenu() {
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                displayAllTasks();
                break;
            case "2":
                displayTaskById();
                break;
            case "3":
                deleteTaskById();
                break;
            case "4":
                insertTask();
                break;
            case "5":
                updateTask();
                break;
            case "6":
                filterTasks();
                break;
            case "7":
                JsonConversionOfTasks();
                break;
            case "8":
                JsonFormEntityByKey();
                break;

            case "0":
                System.out.println("Exiting...");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
        }
    }

    /*
    Jianfeng Han 12 Mar 2024
    * */
    private void displayAllTasks() {
        try {
            List<Task> allTasks = taskDao.getAllTasks();
            System.out.println("All Tasks:");
            displayTasks(allTasks);
        } catch (DaoException e) {
            System.out.println("Error retrieving tasks: " + e.getMessage());
        }
    }

    /**
     * Meghan Keightley 9 Mar 2024
     */
    private void displayTaskById() {
        try {
            System.out.print("Enter Task ID: ");
            int id = Integer.parseInt(sc.nextLine());

            Task task = taskDao.getTaskById(id);
            if (task != null) {
                System.out.println("Task by ID " + id + ": " + task);
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for Task ID.");
        } catch (DaoException e) {
            System.out.println("Error retrieving task: " + e.getMessage());
        }
    }

    private void displayTasks(List<Task> tasks) {

//        for (Task task : tasks) {
//            System.out.println(task);
//        }
        System.out.printf("%-5s | %-35s | %-10s | %-10s | %-40s | %-25s%n",
                "ID", "Title", "Status", "Priority", "Description", "Due Date");
        System.out.println("========================================================================================================================================");

        for (Task task : tasks) {
            System.out.printf("%-5d | %-35s | %-10s | %-10s | %-40s | %-25s%n",
                    task.getTaskId(), task.getTitle(), task.getStatus(), task.getPriority(),
                    task.getDescription(), task.getDueDate());
        }
    }

    /**
     * Meghan Keightley 9 Mar 2024
     */
    private void deleteTaskById() {
        try {
            System.out.print("Enter Task ID to delete: ");
            int taskId = Integer.parseInt(sc.nextLine());

            Task deletedTask = taskDao.deleteTaskById(taskId);
            if (deletedTask != null) {
                System.out.println("Task with ID " + taskId + " deleted.");
            } else {
                System.out.println("Task with ID " + taskId + " not found or could not be deleted.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for Task ID.");
        } catch (DaoException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }
    /*Feature 4 - inserting Task for tony*/
    private void insertTask() {
        System.out.println("Task Title:");
        String title = sc.nextLine();

        System.out.println("Task Status (DONE, PROGRESS, OPEN):");
        String status = sc.nextLine();

        System.out.println("Task Priority (CRITICAL, HIGH, MEDIUM, LOW, MIN):");
        String priority = sc.nextLine();

        System.out.println("Task Description:");
        String description = sc.nextLine();

        System.out.println("Task Due Date (yyyy-MM-dd):");
        String dateString = sc.nextLine();
        Date dueDate = parseDate(dateString);

        Task newTask = new Task(title, status, priority, description, dueDate);

        try {
            taskDao.insertTask(newTask);
            System.out.println("Task inserted successfully!");
        } catch (DaoException e) {
            System.out.println("Error inserting task: " + e.getMessage());
        }
    }

    private Date parseDate(String dateString) {
        // Implement date parsing logic here
        // For simplicity, I'm just returning the current date
        return new Date();
    }

    /*Feature 5
    * Jianfeng Han 14 Mar 2024
    * */
    private void updateTask(){
        try{
            System.out.println("Enter Task ID to update");
            int taskid = Integer.parseInt(sc.nextLine());

            Task exsitingTask = taskDao.getTaskById(taskid);
            if(exsitingTask != null){
                System.out.println("Existing Task Details:");
                System.out.println(exsitingTask);
                System.out.println("Task Title:");
                String title = sc.nextLine();
                System.out.println("Task Status (DONE, PROGRESS, OPEN):");
                String status = sc.nextLine();
                System.out.println("Task Priority (CRITICAL, HIGH, MEDIUM, LOW, MIN):");
                String priority = sc.nextLine();
                System.out.println("Task Description:");
                String description = sc.nextLine();
                System.out.println("Task Due Date (yyyy-MM-dd):");
                String dateString = sc.nextLine();
                Date dueDate = parseDate(dateString);

                Task updatedTask = new Task(taskid, title, status, priority, description, dueDate);
                Task previousTask = taskDao.updateTaskById(taskid, updatedTask);

                if (previousTask != null) {
                    System.out.println("Task with ID " + taskid + " updated successfully!");
                    System.out.println("Previous Task Details:");
                    System.out.println(previousTask);
                }else{
                    System.out.println("Task with ID " + taskid + " could not be updated.");
                }
            }else{
                System.out.println("Task with ID " + taskid + " not found.");
            }
        }catch(NumberFormatException e){
            System.out.println("Invalid input. Please enter a valid number for Task ID.");
        }catch (DaoException e){
            System.out.println("Error updating task: " + e.getMessage());
        }
    }
    /*Feature 6
     * Meghan Keightley 9 Mar 2024
     * */
    private void filterTasks() {
        Task filter = new Task();

        System.out.println("Enter Filter Criteria:");
        System.out.print("Task Status (DONE, PROGRESS, OPEN): ");
        filter.setStatus(sc.nextLine().toUpperCase());
        System.out.print("Task Priority (CRITICAL, HIGH, MEDIUM, LOW, MIN): ");
        filter.setPriority(sc.nextLine());

        try {
            List<Task> filteredTasks = taskDao.FilteringTasks(filter);
            if (!filteredTasks.isEmpty()) {
                System.out.println("Filtered Tasks:");
                displayTasks(filteredTasks);
            } else {
                System.out.println("No tasks found matching the filter criteria.");
            }
        } catch (DaoException e) {
            System.out.println("Error filtering tasks: " + e.getMessage());
        }
    }

    /**
     * Meghan Keightley 7th April 2024
     */
    private void JsonConversionOfTasks() {
        try {
            List<Task> ConvertAllTasks = taskDao.getAllTasks();
            String json = JsonConv.TaskConversionToJson(ConvertAllTasks);

            StringBuilder cleanedUpJson = new StringBuilder();
            String[] parts = json.split("},");
            for (int i = 0; i < parts.length; i++) {
                if (i != 0) {
                    cleanedUpJson.append("},\n");
                }
                cleanedUpJson.append(parts[i].trim());
            }

            System.out.println("JSON representation of tasks:");
            System.out.println(cleanedUpJson.toString());
        } catch (DaoException e) {
            System.out.println("Error converting tasks to Json: " + e.getMessage());
        }
    }



    //    private void convertTaskToJson(Task task) {
//        String jsonString = jsonConv.entityToJson(task);
//        System.out.println("Task in JSON format:");
//        System.out.println(jsonString);
//    }
    private void JsonFormEntityByKey() {
        try {
            System.out.print("Enter Task ID: ");
            int taskId = Integer.parseInt(sc.nextLine());
            String jsonTask = taskDao.JsonFormEntityByKey(taskId);
            if (jsonTask != null) {
                System.out.println("Json representation of task with ID " + taskId + ":");
                String[] lines = jsonTask.split("\\r?\\n");
                StringBuilder formattedJson = new StringBuilder();
                for (int i = 0; i < lines.length; i++) {
                    formattedJson.append(lines[i]);
                    if (i < lines.length - 1) {
                        formattedJson.append(",\n");
                    }
                }
                System.out.println(formattedJson.toString());
            } else {
                System.out.println("Task with ID " + taskId + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for Task ID.");
        } catch (DaoException e) {
            System.out.println("Error converting task to Json: " + e.getMessage());
        }
    }





}
