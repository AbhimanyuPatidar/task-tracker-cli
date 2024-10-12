package com.task.tracker;

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
    public void execute(String[] args) throws InvNumOfArgsException {
        // For 'add' command
        if (args[0].equalsIgnoreCase("add") && args.length > 1) {
            if (args.length == 2) {
                addTask(args[1], "pending");
            } else if (args.length == 3) {
                addTask(args[1], args[2]);
            } else {
                throw new InvNumOfArgsException("Invalid number of arguments provided for 'add' command.");
            }
        }
    }

    private int addTask(String description, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTask'");
    }
}