// Purpose: TaskManager class to manage tasks.

package com.task.tracker;

import java.io.IOException;
import java.util.List;

public class TaskManager {
    JSONFileHandler fileHandler = null;
    JSONFileParser fileParser = null;

    public TaskManager() throws IOException {
        System.out.println("TaskManager initialized...");
        fileHandler = new JSONFileHandler();
        fileParser = new JSONFileParser();
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
        List<String> tasks = fileHandler.readFromFile();
        
        if (tasks == null) {
            System.out.println("No tasks found.");
        } else {
            fileParser.convertFromJSONToMap(tasks);
            printAllTasks();
        }
    }

    public void listTasksByStatus(String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listTasksByStatus'");
    }

    private void printAllTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printAllTasks'");
    }
}
