package com.task.tracker;

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
    public void execute(String[] args) throws InvNumOfArgsException, InvUseOfOptionException {
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
                if (args[2].charAt(1) == 'd') {
                    // Updating description
                    String newDescription = args[3];
                    updateTask(id, newDescription, null);
                } else if (args[2].charAt(1) == 's') {
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
    }

    private int addTask(String description, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTask'");
    }

    private void updateTask(int id, String desciption, Object status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTask'");
    }
}