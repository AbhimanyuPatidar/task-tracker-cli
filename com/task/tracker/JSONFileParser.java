// Purpose: This class is used to parse JSON files.

package com.task.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONFileParser {
    public JSONFileParser() {
        System.out.println("JSONFileParser initialized...");
    }

    // Converts a list of JSON strings to a list of maps for listTasks() method
    public List<Map<String, String>> convertFromJSONToMap(List<String> tasks) {
        List<Map<String, String>> listOfTaskMaps = new ArrayList<>();

        for (String task: tasks) {
            Map<String, String> taskMap = new HashMap<>();
            task = task.trim();
            
            if (task.charAt(task.length() - 1) == ',') {
                task = task.substring(0, task.length() - 1); // Remove trailing comma
            }
            task = task.substring(1, task.length() - 1); // Remove curly braces

            String[] taskFields = task.split(",");

            for (String field: taskFields) {
                String[] keyValue = field.split(":");
                taskMap.put(keyValue[0].trim(), keyValue[1].trim());
            }

            listOfTaskMaps.add(taskMap);
        }

        return listOfTaskMaps;
    }
}
