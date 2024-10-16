/**
 * Represents a task with its unique identifier, description, status, creation and update timestamps.
 * 
 * @param id the unique identifier of the task
 * @param nextIdFile the file that stores the next unique identifier
 * @param description the description of the task
 * @param status the status of the task
 * @param createdAt the creation timestamp of the task
 * @param updatedAt the update timestamp of the task
 *
 * @author Abhimanyu Patidar
 */

package com.task.tracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Task {
    /**
     * Unique identifier of the task.
    */
    private int id;

    /**
     * File that stores the next unique identifier.
    */
    private File nextIdFile = new File("data/NextIdFile.txt");

    /**
     * Description of the task.
    */
    private String description;

    /**
     * Status of the task.
    */
    private String status;

    /**
     * Creation timestamp of the task.
    */
    private LocalDateTime createdAt;

    /**
     * Update timestamp of the task.
    */
    private LocalDateTime updatedAt;
    
    /**
     * Constructor to create a task with the given description and status.
     * Automatically assigns the next unique identifier to the task.
     * Status is only valid if it is 'todo', 'in-progress' or 'done'.
     * Default status is 'todo' if the given status is invalid.
     * 
     * @param description
     * @param status
     * @param createdAt
     * @param updatedAt
     * 
     * @throws NumberFormatException
     * @throws IOException
    */
    public Task(String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt) throws NumberFormatException, IOException {
        // Read the next unique identifier from the file, and increment it
        Scanner scanner = new Scanner(nextIdFile);
        this.id = Integer.parseInt(scanner.next());
        scanner.close();

        FileWriter fileWriter = new FileWriter(nextIdFile);
        fileWriter.write(String.valueOf(this.id + 1));
        fileWriter.flush();
        fileWriter.close();
        
        this.description = description;

        // Check if the status is valid
        if (status.equalsIgnoreCase("todo") || status.equalsIgnoreCase("in-progress") || status.equalsIgnoreCase("done")) {
            this.status = status.toLowerCase();
        } else {
            System.out.println("Invalid status. Defaulting to 'todo'.");
            this.status = "todo";
        }

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", description=" + description + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}