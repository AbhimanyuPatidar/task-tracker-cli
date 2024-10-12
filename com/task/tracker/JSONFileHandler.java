// Purpose: Handles the creation and management of the Tasks.json file.

package com.task.tracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONFileHandler {
    private String filePath = "data/Tasks.json";

    public JSONFileHandler() throws IOException {
        System.out.println("JSONFileHandler initialized...");

        // Create the data directory if it doesn't exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            if (dataDir.mkdir()) {
                System.out.println("Data directory created.");
            } else {
                System.err.println("Failed to create data directory.");
            }
        } else {
            System.out.println("Data directory already exists.");
        }

        // Check if the Tasks.json file exists
        File tasksFile = new File(filePath);
        if (!tasksFile.exists()) {
            System.out.println("Tasks.json file not found. Creating new file at " + filePath + " ...");
            
            if (tasksFile.createNewFile()) {
                System.out.println("Tasks.json file created.");
            } else {
                throw new IOException("Failed to create Tasks.json file.");
            }
        } else {
            System.out.println("Tasks.json file already exists.");
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Reads the entire file for listTasks() method
    public List<String> readEntireFile() throws FileNotFoundException, IOException {
        List<String> jsonStrings = new ArrayList<>();
        StringBuilder content = new StringBuilder();

        // Read the entire file content into a StringBuilder
        FileReader reader = new FileReader(filePath);
        int ch;
        while ((ch = reader.read()) != -1) {
            content.append((char) ch);
        }

        reader.close();

        // Split the file content into individual JSON strings and add them to the list
        String[] tasks = content.toString().split("\n");
        for (String task : tasks) {
            if (!task.trim().isEmpty()) {
            jsonStrings.add(task.trim());
            }
        }

        return jsonStrings;
    }
}