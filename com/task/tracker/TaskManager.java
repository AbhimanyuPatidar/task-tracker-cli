// Purpose: TaskManager class to manage tasks.

package com.task.tracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TaskManager {
    private static final Logger logger = Logger.getLogger(TaskManager.class.getName());

    JSONFileHandler fileHandler = null;
    JSONFileParser fileParser = null;

    public TaskManager() throws IOException {
        logger.info("TaskManager initialized...");
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
        logger.info("listTasks() executing...");
        String content = fileHandler.readContent();
        logger.info("Returned to listTasks() from readContent()...");
        logger.info("Content: " + content);
        
        if (content.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            List<Map<String, String>> listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
            logger.info("Returned to listTasks() from convertJSONArrayToMaps()...");
            logger.info("List of task maps: " + listOfTaskMaps);

            printAllTasks(listOfTaskMaps);
        }
    }

    public void listTasksByStatus(String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listTasksByStatus'");
    }

    private void printAllTasks(List<Map<String, String>> listOfTaskMaps) {
        logger.info("Printing all tasks...");
        System.out.println("Here are all the tasks:");
        System.out.println();

        for (Map<String, String> taskMap : listOfTaskMaps) {
            for (Map.Entry<String, String> entry : taskMap.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println();
        }

        System.out.println("End of tasks.");
    }
}
