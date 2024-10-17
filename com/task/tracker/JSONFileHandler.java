/**
 * JSONFileHandler class is responsible for creating the data directory and Tasks.json file if they don't exist.
 * It also reads the entire file content and writes content to the file.
 * The JSONFileHandler class is used by the TaskManager class to read and write tasks to the Tasks.json file.
 * 
 * @param filePath: The path to the Tasks.json file.
 * 
 * @Author: Abhimanyu Patidar
 */

package com.task.tracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// import java.util.logging.Logger;

public class JSONFileHandler {
    // private static final Logger logger = Logger.getLogger(JSONFileHandler.class.getName());
    
    /**
     * The path to the Tasks.json file.
     */
    private String filePath = "data/Tasks.json";

    /**
     * Constructor to initialize the JSONFileHandler class.
     * It creates the data directory and Tasks.json file if they don't exist.
     * 
     * @throws IOException
     * 
     */
    public JSONFileHandler() throws IOException {
        // logger.info("JSONFileHandler initialized...");

        // Create the data directory if it doesn't exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            if (dataDir.mkdir()) {
                System.out.println("Data directory created.");
            } else {
                System.err.println("Failed to create data directory.");
            }
        } else {
            // logger.info("Data directory already exists.");
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
            // logger.info("Tasks.json file already exists.");
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the entire file content and returns it as a string.
     * 
     * @return The entire file content as a string.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * 
     */
    public String readContent() throws FileNotFoundException, IOException {
        // logger.info("Reading entire file...");
        
        StringBuilder content = new StringBuilder();

        // Read the entire file content into a StringBuilder
        FileReader reader = new FileReader(filePath);
        int ch;
        while ((ch = reader.read()) != -1) {
            content.append((char) ch);
        }

        reader.close();

        // logger.info("Content: " + content.toString());

        return content.toString();
    }

    /**
     * Writes the content to the file.
     * 
     * @param content The content to write to the file.
     * 
     * @throws IOException
     * 
     */
    public void writeContent(String content) throws IOException {
        // logger.info("Writing content to file...");
        // logger.info("Content: " + content);

        if (content.length() == 0) {
            System.out.println("No tasks to write.");
            return;
        } else if (content.length() == 2 && (content.charAt(0) == '[' && content.charAt(1) == ']')) {
            FileWriter writer = new FileWriter(filePath);
            // logger.info("Deleting square brackets.");
            writer.write("");
            writer.flush();
            writer.close();
            System.out.println("No tasks remaining!");
        } else {
            FileWriter writer = new FileWriter(filePath);
            // logger.info("Writing content");
            writer.write(content);
            writer.flush();
            writer.close();
            System.out.println("Content updated in file.");
        }
    }
}