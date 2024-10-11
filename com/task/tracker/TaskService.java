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

    public int execute(String[] args) {
        return 0;
    }
}