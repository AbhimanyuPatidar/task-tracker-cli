// Purpose: This class is used to parse JSON files.

package com.task.tracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class JSONFileParser {
    private static final Logger logger = Logger.getLogger(JSONFileParser.class.getName());

    public JSONFileParser() {
        logger.info("JSONFileParser initialized...");
    }

    // Converts a list of JSON strings to a list of maps for listTasks() method
    public List<Map<String, String>> convertJSONArrayToMaps(String content) {
        logger.info("Converting JSON Array to maps...");

        content = refineContent(content);

        logger.info("Refined content: " + content);

        // Split the JSON array into individual JSON objects
        List<Map<String, String>> listOfTaskMaps = splitIntoJSONObjects(content);
        
        // logger.info("JSON objects: " + jsonObjects);

        // // Convert each JSON object to a map
        // List<Map<String, String>> tasks = new ArrayList<>();
        // for (String jsonObject : jsonObjects) {
        //     tasks.add(convertJSONObjectToMap(jsonObject));
        // }

        return listOfTaskMaps;
    }

    // Converts a list of maps to a JSON array for writeContent() method
    public String convertMapsToJSONArray(List<Map<String, String>> listOfTaskMaps) {
        logger.info("Converting maps to JSON Array...");

        StringBuilder jsonArray = new StringBuilder();
        jsonArray.append("[");

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

        logger.info("JSON Array: " + jsonArray);

        return jsonArray.toString();
    }

    public String createContent(String description, String status) {
        logger.info("Creating content...");

        // Creating list of map instead of map to reuse convertMapsToJSONArray method
        List<Map<String, String>> listOfTaskMaps = new ArrayList<>();
        Map<String, String> taskMap = new HashMap<>();

        // Preparing date for Task constructor
        LocalDateTime createdAt = LocalDateTime.now();

        Task task = new Task(description, status, createdAt, null);

        // Adding task to map
        taskMap.put("id", String.valueOf(task.getId()));
        taskMap.put("description", task.getDescription());
        taskMap.put("status", task.getStatus().toLowerCase());
        taskMap.put("createdAt", task.getCreatedAt().toString());

        if (task.getUpdatedAt() != null) {
            taskMap.put("updatedAt", task.getUpdatedAt().toString());
        }

        listOfTaskMaps.add(taskMap);

        // calling convertMapsToJSONArray to convert list of map to JSON Array (content)
        return convertMapsToJSONArray(listOfTaskMaps);
    }

    private List<Map<String, String>> splitIntoJSONObjects(String content) {
        logger.info("Splitting into JSON objects...");

        List<Map<String, String>> listOfTaskMaps = new ArrayList<>();

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

            if (braceCount == 0 && jsonObject.length() > 0) {
                Map<String, String> task = convertJSONObjectToMap(jsonObject.toString().trim());
                logger.info("Task: " + task);
                listOfTaskMaps.add(task);
                jsonObject.setLength(0);
            }
        }

        return listOfTaskMaps;
    }

    private Map<String, String> convertJSONObjectToMap(String jsonObject) {
        logger.info("Converting JSON object to map...");

        Map<String, String> task = new HashMap<>();

        // Remove outer braces
        if (jsonObject.startsWith("{") && jsonObject.endsWith("}")) {
            jsonObject = jsonObject.substring(1, jsonObject.length() - 1);
        }

        // Split the JSON object into key-value pairs
        String[] keyValuePairs = jsonObject.split(",");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");

            if (keyValue.length < 2) {
                logger.warning("Skipping invalid key-value pair: " + pair);
                continue;
            }
            
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            // Modify key and value so they don't contain quotes, if present
            logger.info("Removing quotes from key and value, if present...");
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

            task.put(key, value);
        }

        logger.info("Task map: " + task);

        return task;
    }

    private String refineContent(String content) {
        logger.info("Refining content...");

        // Remove the outer square brackets
        if (content.startsWith("[") && content.endsWith("]")) {
            content = content.substring(1, content.length() - 1);
        }

        logger.info("Content after removing square brackets: " + content);

        // Remove anything outside the curly braces
        int start = content.indexOf("{");
        int end = content.lastIndexOf("}");
        content = content.substring(start, end + 1);

        logger.info("Content after removing anything outside curly braces: " + content);

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
