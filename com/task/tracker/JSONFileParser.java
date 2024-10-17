/**
 * JSONFileParser class is responsible for parsing JSON strings, refining content, splitting JSON array into individual JSON objects, converting JSON objects to maps and vice versa.
 * 
 * @Author: Abhimanyu Patidar
 */

package com.task.tracker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import java.util.logging.Logger;

public class JSONFileParser {
    // private static final Logger logger = Logger.getLogger(JSONFileParser.class.getName());

    public JSONFileParser() {
        // logger.info("JSONFileParser initialized...");
    }

    /**
     * Converts a JSON array to a list of maps.
     * 
     * @param content The JSON array to be converted.
     * 
     * @return A list of maps containing the key-value pairs from the JSON array.
     * 
     */
    public List<Map<String, String>> convertJSONArrayToMaps(String content) {
        // logger.info("Converting JSON Array to maps...");

        // Refine the content by removing unwanted characters
        content = refineContent(content);

        // logger.info("Refined content: " + content);

        // Split the JSON array into individual JSON objects and simultaneously convert them to maps
        List<Map<String, String>> listOfTaskMaps = splitIntoJSONObjects(content);

        return listOfTaskMaps;
    }

    /**
     * Converts a list of maps to a JSON array.
     * 
     * @param listOfTaskMaps The list of maps to be converted.
     * 
     * @return A JSON array containing the key-value pairs from the list of maps.
     * 
     */
    public String convertMapsToJSONArray(List<Map<String, String>> listOfTaskMaps) {
        // logger.info("Converting maps to JSON Array...");

        StringBuilder jsonArray = new StringBuilder();
        jsonArray.append("[");

        // Convert each map to a JSON object
        for (Map<String, String> task : listOfTaskMaps) {
            jsonArray.append("{");

            jsonArray.append("\"id\":\"" + task.get("id") + "\",");
            jsonArray.append("\"description\":\"" + task.get("description") + "\",");
            jsonArray.append("\"status\":\"" + task.get("status") + "\",");
            jsonArray.append("\"createdAt\":\"" + task.get("createdAt") + "\",");

            if (task.containsKey("updatedAt")) {
                jsonArray.append("\"updatedAt\":\"" + task.get("updatedAt") + "\",");
            }

            jsonArray.deleteCharAt(jsonArray.length() - 1); // Remove the last comma
            jsonArray.append("},");
        }

        jsonArray.deleteCharAt(jsonArray.length() - 1); // Remove the last comma
        jsonArray.append("]");

        // logger.info("JSON Array: " + jsonArray);

        return jsonArray.toString();
    }

    /**
     * Creates content by adding a new task to the existing list of tasks.
     * 
     * @param description The description of the task.
     * @param status The status of the task.
     * @param listOfTaskMaps The list of maps containing the existing tasks.
     * 
     * @return The new content after adding the new task.
     * 
     * @throws NumberFormatException
     * @throws IOException
     * 
     */
    public String createContent(String description, String status, List<Map<String, String>> listOfTaskMaps) throws NumberFormatException, IOException {
        // logger.info("Creating content...");
        
        Map<String, String> taskMap = new HashMap<>();

        // Formatting Date and Time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime createdAt = LocalDateTime.now();
        
        // Creating new task
        Task task = new Task(description, status, createdAt, null);

        // Adding task to map
        taskMap.put("id", String.valueOf(task.getId()));
        taskMap.put("description", task.getDescription());
        taskMap.put("status", task.getStatus());
        taskMap.put("createdAt", task.getCreatedAt().format(formatter));

        if (task.getUpdatedAt() != null) {
            taskMap.put("updatedAt", task.getUpdatedAt().format(formatter));
        }

        // If the list of task maps is null, create a new list
        if (listOfTaskMaps == null) {
            listOfTaskMaps = new ArrayList<>();
        }
        listOfTaskMaps.add(taskMap);

        // calling convertMapsToJSONArray to convert list of map to JSON Array (content)
        return convertMapsToJSONArray(listOfTaskMaps);
    }

    /**
     * Splits the JSON array into individual JSON objects.
     * Then, converts each JSON object to a map.
     * 
     * @param content The JSON array to be split.
     * 
     * @return A list of maps containing the key-value pairs from the JSON array.
     * 
     */
    private List<Map<String, String>> splitIntoJSONObjects(String content) {
        // logger.info("Splitting into JSON objects...");

        List<Map<String, String>> listOfTaskMaps = new ArrayList<>();

        // Count the number of braces to identify each JSON object
        int braceCount = 0;
        StringBuilder jsonObject = new StringBuilder();

        for (int i = 0; i < content.length(); i++) {            
            char c = content.charAt(i);

            if (c == '{') {
                braceCount++;
            }

            if (c == '}') {
                braceCount--;
            }

            jsonObject.append(c);

            /*
             * When the brace count is 0, we have reached the end of a JSON object.
             * Convert the JSON object to a map and add it to the list.
             */
            if (braceCount == 0 && jsonObject.length() > 0) {
                // logger.info("JSON Object: " + jsonObject);
                // logger.info("JSON Object: " + jsonObject.toString().trim());
                Map<String, String> task = convertJSONObjectToMap(jsonObject.toString().trim());
                // logger.info("Task: " + task);
                listOfTaskMaps.add(task);
                jsonObject.setLength(0);
            }
        }

        return listOfTaskMaps;
    }

    /**
     * Converts a JSON object to a map.
     * 
     * @param jsonObject The JSON object to be converted.
     * 
     * @return A map containing the key-value pairs from the JSON object.
     * 
     */
    private Map<String, String> convertJSONObjectToMap(String jsonObject) {
        // logger.info("Converting JSON object to map...");

        Map<String, String> task = new HashMap<>();

        // Remove outer braces
        if (jsonObject.startsWith("{") && jsonObject.endsWith("}")) {
            jsonObject = jsonObject.substring(1, jsonObject.length() - 1);
        }

        // Split the JSON object into key-value pairs
        String[] keyValuePairs = jsonObject.split(",");

        // Extract key and value from each key-value pair
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");

            if (keyValue.length < 2) {
                // logger.warning("Skipping invalid key-value pair: " + pair);
                continue;
            }
            
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            // logger.info("Key: " + key + ", Value: " + value);

            // Modify key and value so they don't contain quotes, if present and add to map
            // logger.info("Removing quotes from key and value, if present...");
            if (key.startsWith("\"")) {
                key = key.substring(1, key.length());
            }

            if (key.endsWith("\"")) {
                key = key.substring(0, key.length() -1);
            }

            if (value.startsWith("\"")) {
                value = value.substring(1, value.length());
            }

            if (value.endsWith("\"")) {
                value = value.substring(0, value.length() -1);
            }

            // logger.info("Key: " + key + ", Value: " + value);

            task.put(key, value);
        }

        // logger.info("Task map: " + task);

        return task;
    }

    /**
     * Refines the content by removing unwanted characters.
     * 
     * @param content The content to be refined.
     * 
     * @return The refined content.
     * 
     */
    private String refineContent(String content) {
        // logger.info("Refining content...");

        // Remove the outer square brackets
        if (content.startsWith("[") && content.endsWith("]")) {
            content = content.substring(1, content.length() - 1);
        }

        // logger.info("Content after removing square brackets: " + content);

        // Remove anything outside the curly braces
        int start = content.indexOf("{");
        int end = content.lastIndexOf("}");
        content = content.substring(start, end + 1);

        // logger.info("Content after removing anything outside curly braces: " + content);

        StringBuilder refinedContent = new StringBuilder();
        boolean isAppend = false;

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);

            if (c == '{') {
                isAppend = true;
            }

            if (c == '}') {
                refinedContent.append(c);
                isAppend = false;
            }

            if (isAppend == true) {
                refinedContent.append(c);
            }
        }

        return refinedContent.toString();
    }
}
