/**
 * Class that manages the tasks by adding, updating, deleting, listing and listing tasks by status.
 * 
 * @param JSONFileHandler
 * @param JSONFileParser
 * 
 * @Author: Abhimnanyu Patidar
 */

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
// import java.util.logging.Logger;

public class TaskManager {
    // private static final Logger logger = Logger.getLogger(TaskManager.class.getName());

    JSONFileHandler fileHandler = null;
    JSONFileParser fileParser = null;

    /**
     * Constructor to initialize the TaskManager.
     * Initializes the JSONFileHandler and JSONFileParser.
     * 
     * @throws IOException
     * 
     */
    public TaskManager() throws IOException {
        // logger.info("TaskManager initialized...");
        fileHandler = new JSONFileHandler();
        fileParser = new JSONFileParser();
    }

    /**
     * Adds a task with the given description and status.
     * 
     * @param description description of the task
     * @param status status of the task
     * 
     * @throws IOException
     * 
    */
    public void addTask(String description, String status) throws IOException {
        // logger.info("addTask() executing...");

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
            // logger.info("NextIdFile.txt already exists.");
        }

        // Read the content of Tasks.json file
        String content = fileHandler.readContent();
        // logger.info("Returned to addTask() from readContent()...");
        List<Map<String, String>> listOfTaskMaps = null;
        if (!content.isEmpty()) {
            // logger.info("Content is not empty");
            
            // Convert the content to List of Maps
            listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
        }

        // Create json array from the list of task maps and write it to Tasks.json file
        fileHandler.writeContent(fileParser.createContent(description, status, listOfTaskMaps));
    }

    /**
     * Updates the task with the given ID with the new description and/or status.
     * 
     * @param id unique identifier of the task
     * @param description description of the task
     * @param status status of the task
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * 
    */
    public void updateTask(int id, String description, String status) throws FileNotFoundException, IOException {
        // logger.info("updateTask() executing...");

        String content = fileHandler.readContent();
        // logger.info("Returned to updateTask() from readContent()...");
        // logger.info("Content: " + content);

        if (content.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            List<Map<String, String>> listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
            // logger.info("Returned to updateTask() from convertJSONArrayToMaps()...");
            // logger.info("List of task maps: " + listOfTaskMaps);

            /*
             * Check if the task with the given ID exists.
             * If it exists, update the description and/or status.
             * Add the updatedAt timestamp.
             */
            boolean taskFound = false;
            for (Map<String, String> taskMap : listOfTaskMaps) {
                if (Integer.parseInt(taskMap.get("id")) == id) {
                    taskFound = true;
                    if (taskFound) {
                        // logger.info("TaskMap: " + taskMap);
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

                    // logger.info("TaskMap after updating: " + taskMap);
                    break;
                }
            }

            if (taskFound) {
                // Convert the list of task maps to JSON array and write it to Tasks.json file
                fileHandler.writeContent(fileParser.convertMapsToJSONArray(listOfTaskMaps));
                // logger.info("Returned to updateTask() from writeContent()...");
                System.out.println("Task with ID " + id + " updated successfully.");
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        }
    }

    /**
     * Deletes the task with the given ID.
     * 
     * @param id unique identifier of the task
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * 
     */
    public void deleteTask(int id) throws FileNotFoundException, IOException {
        // logger.info("deleteTask() executing...");

        String content = fileHandler.readContent();
        // logger.info("Returned to deleteTask() from readContent()...");
        // logger.info("Content: " + content);

        if (content.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            List<Map<String, String>> listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
            // logger.info("Returned to deleteTask() from convertJSONArrayToMaps()...");
            // logger.info("List of task maps: " + listOfTaskMaps);

            boolean taskFound = false;
            for (Map<String, String> taskMap : listOfTaskMaps) {
                if (Integer.parseInt(taskMap.get("id")) == id) {
                    taskFound = true;
                    if (taskFound) {
                        // logger.info("TaskMap: " + taskMap);
                    }

                    listOfTaskMaps.remove(taskMap);
                    // logger.info("List of task maps after deleting task: " + listOfTaskMaps);
                    break;
                }
            }

            if (taskFound) {
                fileHandler.writeContent(fileParser.convertMapsToJSONArray(listOfTaskMaps));
                // logger.info("Returned to deleteTask() from writeContent()...");
                System.out.println("Task with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        }
    }

    /**
     * Lists all the tasks.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * 
     */
    public void listTasks() throws FileNotFoundException, IOException {
        // logger.info("listTasks() executing...");

        String content = fileHandler.readContent();
        // logger.info("Returned to listTasks() from readContent()...");
        // logger.info("Content: " + content);
        
        if (content.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            List<Map<String, String>> listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
            // logger.info("Returned to listTasks() from convertJSONArrayToMaps()...");
            // logger.info("List of task maps: " + listOfTaskMaps);

            printAllTasks(listOfTaskMaps);
        }
    }

    /**
     * Lists the tasks with the given status.
     * 
     * @param status status of the tasks
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * 
     */
    public void listTasksByStatus(String status) throws FileNotFoundException, IOException {
        // logger.info("listTasksByStatus() executing...");

        String content = fileHandler.readContent();
        // logger.info("Returned to listTasksByStatus() from readContent()...");
        // logger.info("Content: " + content);

        if (content.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            List<Map<String, String>> listOfTaskMaps = fileParser.convertJSONArrayToMaps(content);
            // logger.info("Returned to listTasksByStatus() from convertJSONArrayToMaps()...");
            // logger.info("List of task maps: " + listOfTaskMaps);

            printTasksByStatus(listOfTaskMaps, status);
        }
    }

    /**
     * Prints all the tasks.
     * 
     * @param listOfTaskMaps list of task maps containing the key-value pairs from the JSON array.
     * 
     */
    private void printAllTasks(List<Map<String, String>> listOfTaskMaps) {
        // logger.info("Printing all tasks...");
        System.out.println("Here are all the tasks:");
        System.out.println();

        // Extract the task details from the list of task maps
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
                // logger.info("No updatedAt key found in taskMap.");
            }

            System.out.println();
        }

        System.out.println("End of tasks.");
    }

    /**
     * Prints the tasks with the given status.
     * 
     * @param listOfTaskMaps list of task maps containing the key-value pairs from the JSON array.
     * @param status status of the tasks
     * 
     */
    private void printTasksByStatus(List<Map<String, String>> listOfTaskMaps, String status) {
        // logger.info("Printing tasks by status...");
        System.out.println("Here are all the tasks with status: " + status);
        System.out.println();

        // Extract the task details from the list of task maps
        for (Map<String, String> taskMap : listOfTaskMaps) {
            // Check if the status of the task is the same as the given status
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
                    // logger.info("No updatedAt key found in taskMap.");
                }

                System.out.println();
            }
        }

        System.out.println("End of tasks with status: " + status);
    }

    /**
     * Formats the date and time.
     * 
     * @param dateTime date and time
     * 
     * @return formatted date and time as a string
     * 
     */
    private String format(String dateTime) {
        // logger.info("Formatting date and time...");
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
