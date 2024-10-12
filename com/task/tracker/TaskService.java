package com.task.tracker;

import com.task.tracker.exceptions.InvCmdPassedException;
import com.task.tracker.exceptions.InvNumOfArgsException;
import com.task.tracker.exceptions.InvUseOfOptionException;

public class TaskService {
    private String[] args;

    public TaskService() {
        System.out.println("TaskService initialized...");
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    // Will check for args and call the respective method
    public void execute(String[] args) throws InvNumOfArgsException, InvUseOfOptionException, InvCmdPassedException {
        
        if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("update") && !args[0].equalsIgnoreCase("delete") && !args[0].equalsIgnoreCase("list")) {
            throw new InvCmdPassedException("Invalid command provided. Please provide a valid command.");
        }
        
        // For 'add' command
        if (args[0].equalsIgnoreCase("add") && args.length > 1) {
            String description = args[1];

            if (args.length == 2) {
                // Default status 'pending' applied
                addTask(description, "pending");
            } else if (args.length == 3) {
                String status = args[2];
                addTask(description, status);
            } else {
                throw new InvNumOfArgsException("Invalid number of arguments provided for 'add' command.");
            }
        } else {
            throw new InvNumOfArgsException("No additional arguments provided for 'add' command.");
        }

        // For 'update' command
        if (args[0].equalsIgnoreCase("update") && args.length == 4) {
            Integer id = Integer.parseInt(args[1]);

            if (args[2].charAt(0) == '-') {
                if (args[2].charAt(1) == 'd' || args[2].charAt(1) == 'D') {
                    // Updating description
                    String newDescription = args[3];
                    updateTask(id, newDescription, null);
                } else if (args[2].charAt(1) == 's' || args[2].charAt(1) == 'S') {
                    // Updating status
                    String newStatus = args[3];
                    updateTask(id, null, newStatus);
                } else {
                    // Invalid use of '-'
                    throw new InvUseOfOptionException("Invalid use of '-' in 'update' command.");
                }
            } else {
                // Updating both description and status
                String newDescription = args[2];
                String newStatus = args[3];
                updateTask(id, newDescription, newStatus);
            }
        } else {
            throw new InvNumOfArgsException("Insufficient number of arguments provided for 'update' command.");
        }

        // For 'delete' command
        if (args[0].equalsIgnoreCase("delete") && args.length == 2) {
            Integer id = Integer.parseInt(args[1]);
            deleteTask(id);
        } else {
            throw new InvNumOfArgsException("Invalid number of arguments provided for 'delete' command.");
        }

        // To list all tasks or by status
        if (args[0].equalsIgnoreCase("list") && args.length == 1) {
            listTasks();
        } else if (args[0].equalsIgnoreCase("list") && args.length == 2) {
            String status = args[1];
            listTasksByStatus(status);
        } else {
            throw new InvNumOfArgsException("Invalid number of arguments provided for 'list' command.");
        }
    }

    private int addTask(String description, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTask'");
    }

    private void updateTask(int id, String desciption, Object status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTask'");
    }

    private void deleteTask(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTask'");
    }

    private void listTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listTasks'");
    }

    private void listTasksByStatus(String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listTasksByStatus'");
    }
}