package com.task.tracker;

import java.io.IOException;

public class TaskManager {
    JSONFileHandler fileHandler = null;

    public TaskManager() throws IOException {
        System.out.println("TaskManager initialized...");
        fileHandler = new JSONFileHandler();
    }

    public void addTask(String description, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTask'");
    }

    public void updateTask(int id, String desciption, Object status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTask'");
    }

    public void deleteTask(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTask'");
    }

    public void listTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listTasks'");
    }

    public void listTasksByStatus(String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listTasksByStatus'");
    }
}
