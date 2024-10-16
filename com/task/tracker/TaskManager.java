// Purpose: TaskManager class to manage tasks.

package com.task.tracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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

    public void addTask(String description, String status) throws IOException {
        logger.info("addTask() executing...");

        // Check if NextIdFile.txt exists
        File nextIdFile = new File("data/NextIdFile.txt");
        if (!nextIdFile.exists()) {
            System.out.println("NextIdFile.txt not found. Creating new file at data/NextIdFile.txt ...");
            if (nextIdFile.createNewFile()) {
                System.out.println("NextIdFile.txt created.");
                
                FileWriter fileWriter = new FileWriter(nextIdFile);
                fileWriter.write("1");
                fileWriter.flush();
                fileWriter.close();
            } else {
                throw new IOException("Failed to create NextIdFile.txt file.");
            }
        } else {
            logger.info("NextIdFile.txt already exists.");
        }

        String content = fileHandler.readContent();
        logger.info("Returned to addTask() from readContent()...");
        List<Map<String, String>> listOfTaskMaps = null;
        if (!content.isEmpty()) {
            logger.info("Content is not empty");
            listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
        }
        fileHandler.writeContent(fileParser.createContent(description, status, listOfTaskMaps));
    }

    public void updateTask(int id, String description, String status) throws FileNotFoundException, IOException {
        logger.info("updateTask() executing...");

        String content = fileHandler.readContent();
        logger.info("Returned to updateTask() from readContent()...");
        logger.info("Content: " + content);

        if (content.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            List<Map<String, String>> listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
            logger.info("Returned to updateTask() from convertJSONArrayToMaps()...");
            logger.info("List of task maps: " + listOfTaskMaps);

            boolean taskFound = false;
            for (Map<String, String> taskMap : listOfTaskMaps) {
                if (Integer.parseInt(taskMap.get("id")) == id) {
                    taskFound = true;
                    if (taskFound) {
                        logger.info("TaskMap: " + taskMap);
                    }

                    if (description != null) {
                        taskMap.put("description", description);
                    }

                    if (status != null) {
                        taskMap.put("status", status.toLowerCase());
                    }

                    // Formatting Date and Time
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
                    String updatedAt = LocalDateTime.now().format(formatter);

                    taskMap.put("updatedAt", updatedAt);

                    logger.info("TaskMap after updating: " + taskMap);
                    break;
                }
            }

            if (taskFound) {
                fileHandler.writeContent(fileParser.convertMapsToJSONArray(listOfTaskMaps));
                logger.info("Returned to updateTask() from writeContent()...");
                System.out.println("Task with ID " + id + " updated successfully.");
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        }
    }

    public void deleteTask(int id) throws FileNotFoundException, IOException {
        logger.info("deleteTask() executing...");

        String content = fileHandler.readContent();
        logger.info("Returned to deleteTask() from readContent()...");
        logger.info("Content: " + content);

        if (content.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            List<Map<String, String>> listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
            logger.info("Returned to deleteTask() from convertJSONArrayToMaps()...");
            logger.info("List of task maps: " + listOfTaskMaps);

            boolean taskFound = false;
            for (Map<String, String> taskMap : listOfTaskMaps) {
                if (Integer.parseInt(taskMap.get("id")) == id) {
                    taskFound = true;
                    if (taskFound) {
                        logger.info("TaskMap: " + taskMap);
                    }

                    listOfTaskMaps.remove(taskMap);
                    logger.info("List of task maps after deleting task: " + listOfTaskMaps);
                    break;
                }
            }

            if (taskFound) {
                fileHandler.writeContent(fileParser.convertMapsToJSONArray(listOfTaskMaps));
                logger.info("Returned to deleteTask() from writeContent()...");
                System.out.println("Task with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        }
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

    public void listTasksByStatus(String status) throws FileNotFoundException, IOException {
        logger.info("listTasksByStatus() executing...");

        String content = fileHandler.readContent();
        logger.info("Returned to listTasksByStatus() from readContent()...");
        logger.info("Content: " + content);

        if (content.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            List<Map<String, String>> listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
            logger.info("Returned to listTasksByStatus() from convertJSONArrayToMaps()...");
            logger.info("List of task maps: " + listOfTaskMaps);

            printTasksByStatus(listOfTaskMaps, status);
        }
    }

    private void printAllTasks(List<Map<String, String>> listOfTaskMaps) {
        logger.info("Printing all tasks...");
        System.out.println("Here are all the tasks:");
        System.out.println();

        for (Map<String, String> taskMap : listOfTaskMaps) {
            System.out.println("Task ID: " + '\"' + taskMap.get("id") + '\"');
            System.out.println("Description: " + '\"' + taskMap.get("description") + '\"');
            System.out.println("Status: " + '\"' + taskMap.get("status") + '\"');
            
            String createdAt = format(taskMap.get("createdAt"));
            System.out.println("CreatedAt: " + '\"' + createdAt + '\"');

            if (taskMap.containsKey("updatedAt")) {
                String updatedAt = format(taskMap.get("updatedAt"));
                System.out.println("UpdatedAt: " + '\"' + updatedAt + '\"');
            } else {
                logger.info("No updatedAt key found in taskMap.");
            }

            System.out.println();
        }

        System.out.println("End of tasks.");
    }

    private void printTasksByStatus(List<Map<String, String>> listOfTaskMaps, String status) {
        logger.info("Printing tasks by status...");
        System.out.println("Here are all the tasks with status: " + status);
        System.out.println();

        for (Map<String, String> taskMap : listOfTaskMaps) {
            if (taskMap.get("status").equalsIgnoreCase(status)) {
                System.out.println("Task ID: " + '\"' + taskMap.get("id") + '\"');
                System.out.println("Description: " + '\"' + taskMap.get("description") + '\"');
                System.out.println("Status: " + '\"' + taskMap.get("status") + '\"');

                String createdAt = format(taskMap.get("createdAt"));
                System.out.println("CreatedAt: " + '\"' + createdAt + '\"');

                if (taskMap.containsKey("updatedAt")) {
                    String updatedAt = format(taskMap.get("updatedAt"));
                    System.out.println("UpdatedAt: " + '\"' + updatedAt + '\"');
                } else {
                    logger.info("No updatedAt key found in taskMap.");
                }

                System.out.println();
            }
        }

        System.out.println("End of tasks with status: " + status);
    }

    private String format(String dateTime) {
        logger.info("Formatting date and time...");
        String[] dateTimeArray = dateTime.split("_");

        String[] dateArray = dateTimeArray[0].split("-");
        String[] timeArray = dateTimeArray[1].split("-");

        String year = dateArray[0];

        String month = Month.of(Integer.parseInt(dateArray[1])).name();
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();

        String day = dateArray[2];

        String minutes = timeArray[1];
        String seconds = timeArray[2];
        
        int hourInt = Integer.parseInt(timeArray[0]);
        if (hourInt < 12) {
            String hour = null;
            if (hourInt == 0) {
                hour = "12";
            } else {
                hour = Integer.toString(hourInt);
            }

            dateTime = "Date:- " + day + " " + month + ", " + year + "; Time:- " + hour + ":" + minutes + ":" + seconds + " am";
        } else {
            hourInt -= 12;
            String hour = Integer.toString(hourInt);

            dateTime = "Date:- " + day + " " + month + ", " + year + "; Time:- " + hour + ":" + minutes + ":" + seconds + " pm";
        }

        return dateTime;
    }
}
