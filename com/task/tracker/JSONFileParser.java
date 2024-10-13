// Purpose: This class is used to parse JSON files.

package com.task.tracker;

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

        content = refinedContent.toString();

        logger.info("Refined content: " + content);

        // Split the JSON array into individual JSON objects
        List<Map<String, String>> tasks = splitIntoJSONObjects(content);
        
        // logger.info("JSON objects: " + jsonObjects);

        // // Convert each JSON object to a map
        // List<Map<String, String>> tasks = new ArrayList<>();
        // for (String jsonObject : jsonObjects) {
        //     tasks.add(convertJSONObjectToMap(jsonObject));
        // }

        return tasks;
    }

    private List<Map<String, String>> splitIntoJSONObjects(String content) {
        logger.info("Splitting into JSON objects...");

        List<Map<String, String>> tasks = new ArrayList<>();

        int braceCount = 0;
        StringBuilder jsonObject = new StringBuilder();

        // for (char c : content.toCharArray()) {
        //     if (c == '{') {
        //         braceCount++;
        //     } 
            
        //     if (c == '}') {
        //         braceCount--;
        //     }

        //     jsonObject.append(c);

        //     if (braceCount == 0 && jsonObject.length() > 0) {
        //         jsonObjects.add(jsonObject.toString().trim());
        //         jsonObject.setLength(0);
        //     }
        // }

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
                tasks.add(task);
                jsonObject.setLength(0);
            }
        }

        return tasks;
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

            task.put(key, value);
        }

        logger.info("Task map: " + task);

        return task;
    }
}
