/**
 * Class to process the command line arguments and calls the respective methods.
 * 
 * @author: Abhimanyu Patidar
*/

package com.task.tracker;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.task.tracker.exceptions.InvCmdPassedException;
import com.task.tracker.exceptions.InvIdFormatException;
import com.task.tracker.exceptions.InvNumOfArgsException;
import com.task.tracker.exceptions.InvUseOfOptionException;
// import java.util.logging.Logger;

public class TaskService {
    // private static final Logger logger = Logger.getLogger(TaskService.class.getName());

    public TaskService() {
        // logger.info("TaskService initialized...");
    }

    /**
     * Executes the task manager methods based on the command line arguments.
     * 
     * @param args the command line arguments
     * 
     * @throws InvNumOfArgsException if the number of arguments provided is invalid
     * @throws InvUseOfOptionException if the option provided is invalids
     * @throws InvCmdPassedException if the command passed is invalid
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if an I/O error occurs
     * @throws InvIdFormatException if the ID provided is invalid
     * 
     * @return void
     * 
    */
    public void execute(String[] args) throws InvNumOfArgsException, InvUseOfOptionException, InvCmdPassedException, FileNotFoundException, IOException, InvIdFormatException {
        // logger.info("Executing TaskService...");
        TaskManager taskManager = new TaskManager();
        
        // To check if 'add', 'update', 'delete' or 'list' command is not provided
        if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("update") && !args[0].equalsIgnoreCase("delete") && !args[0].equalsIgnoreCase("list")) {
            throw new InvCmdPassedException("Invalid command provided. Please provide a valid command.");
        }
        
        // For 'add' command
        if (args[0].equalsIgnoreCase("add") && args.length > 1) {
            String description = args[1];

            if (args.length == 2) {
                // Default status 'todo' applied
                // logger.info("Adding task with default status 'todo'...");
                taskManager.addTask(description, "todo");
            } else if (args.length == 3) {
                String status = args[2];
                // logger.info("Adding task with status '" + status + "'...");
                taskManager.addTask(description, status);
            } else {
                throw new InvNumOfArgsException("Invalid number of arguments provided for 'add' command.");
            }
        } 
        
        if (args[0].equalsIgnoreCase("add") && args.length == 1) {
            throw new InvNumOfArgsException("No additional arguments provided for 'add' command.");
        }

        // For 'update' command
        if (args[0].equalsIgnoreCase("update") && args.length == 4) {
            Integer id;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new InvIdFormatException("Invalid ID provided. ID should be a number.");
            }

            if (args[2].charAt(0) == '-') {
                if (args[2].length() == 1) {
                    throw new InvUseOfOptionException("Invalid use of '-' in 'update' command. There is no option provided after '-'.");
                }

                if (args[2].charAt(1) == 'd' || args[2].charAt(1) == 'D') {
                    // Updating description
                    String newDescription = args[3];
                    taskManager.updateTask(id, newDescription, null);
                } else if (args[2].charAt(1) == 's' || args[2].charAt(1) == 'S') {
                    // Updating status
                    String newStatus = args[3];
                    taskManager.updateTask(id, null, newStatus);
                } else {
                    // Invalid use of '-'
                    throw new InvUseOfOptionException("Invalid use of '-' in 'update' command.");
                }
            } else {
                // Updating both description and status
                String newDescription = args[2];
                String newStatus = args[3];
                taskManager.updateTask(id, newDescription, newStatus);
            }
        } 
        
        if (args[0].equalsIgnoreCase("update") && args.length != 4) {
            throw new InvNumOfArgsException("Invalid number of arguments provided for 'update' command.");
        }

        // For 'delete' command
        if (args[0].equalsIgnoreCase("delete") && args.length == 2) {
            Integer id;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new InvIdFormatException("Invalid ID provided. ID should be a number.");
            }

            taskManager.deleteTask(id);
        } 
        
        if (args[0].equalsIgnoreCase("delete") && args.length != 2) {
            throw new InvNumOfArgsException("Invalid number of arguments provided for 'delete' command.");
        }

        // To list all tasks or by status
        if (args[0].equalsIgnoreCase("list") && args.length == 1) {
            taskManager.listTasks();
        } else if (args[0].equalsIgnoreCase("list") && args.length == 2) {
            String status = args[1];
            if (!status.equalsIgnoreCase("todo") && !status.equalsIgnoreCase("in-progress") && !status.equalsIgnoreCase("done")) {
                System.out.println("Invalid status provided. Printing all tasks.");
                taskManager.listTasks();
                return;
            }

            taskManager.listTasksByStatus(status);
        } 
        
        if (args[0].equalsIgnoreCase("list") && args.length > 2) {
            throw new InvNumOfArgsException("Invalid number of arguments provided for 'list' command.");
        }
    }
}