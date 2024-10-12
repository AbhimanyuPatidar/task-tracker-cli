// Purpose: TaskManager class to manage tasks.

package com.task.tracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    public void listTasks() throws FileNotFoundException, IOException {
        List<String> tasks = fileHandler.readEntireFile();
        
        if (tasks == null) {
            System.out.println("No tasks found.");
        } else {
            List<Map<String, String>> listOfTaskMaps = fileParser.convertFromJSONToMap(tasks);
            printAllTasks(listOfTaskMaps);
        }
    }

    public void listTasksByStatus(String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listTasksByStatus'");
    }

    private void printAllTasks(List<Map<String, String>> listOfTaskMaps) {
        System.out.println("Here are all the tasks:");

        for (Map<String, String> taskMap : listOfTaskMaps) {
            System.out.println("Task ID: " + taskMap.get("id"));
            System.out.println("Description: " + taskMap.get("description"));
            System.out.println("Status: " + taskMap.get("status"));
            System.out.println("Created at: " + taskMap.get("createdAt"));

            if (taskMap.containsKey("updatedAt")) {
                System.out.println("Updated at: " + taskMap.get("updatedAt"));
            }
        }
    }
}
