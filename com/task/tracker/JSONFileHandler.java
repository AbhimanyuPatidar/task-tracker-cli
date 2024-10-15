// Purpose: Handles the creation and management of the Tasks.json file.

package com.task.tracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class JSONFileHandler {
    private static final Logger logger = Logger.getLogger(JSONFileHandler.class.getName());
    
    private String filePath = "data/Tasks.json";

    public JSONFileHandler() throws IOException {
        logger.info("JSONFileHandler initialized...");

        // Create the data directory if it doesn't exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            if (dataDir.mkdir()) {
                System.out.println("Data directory created.");
            } else {
                System.err.println("Failed to create data directory.");
            }
        } else {
            logger.info("Data directory already exists.");
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
            logger.info("Tasks.json file already exists.");
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Reads the entire file for listTasks(), listTasksByStatus(), deleteTask() methods
    public String readContent() throws FileNotFoundException, IOException {
        logger.info("Reading entire file...");
        
        StringBuilder content = new StringBuilder();

        // Read the entire file content into a StringBuilder
        FileReader reader = new FileReader(filePath);
        int ch;
        while ((ch = reader.read()) != -1) {
            content.append((char) ch);
        }

        reader.close();

        logger.info("Content: " + content.toString());

        return content.toString();
    }

    // Writes string into the file for deleteTask() method
    public void writeContent(String content) throws IOException {
        logger.info("Writing content to file...");
        logger.info("Content: " + content);

        if (content.length() == 0) {
            System.out.println("No tasks to write.");
            return;
        } else if (content.length() == 2 && (content.charAt(0) == '[' && content.charAt(1) == ']')) {
            FileWriter writer = new FileWriter(filePath);
            logger.info("Deleting square brackets.");
            writer.write("");
            writer.flush();
            writer.close();
            System.out.println("No tasks remaining!");
        } else {
            FileWriter writer = new FileWriter(filePath);
            logger.info("Writing content");
            writer.write(content);
            writer.flush();
            writer.close();
            System.out.println("Content updated in file.");
        }
    }
}