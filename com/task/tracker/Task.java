// Model class for a Task

package com.task.tracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Task {
    private int id;
    private File nextIdFile = new File("data/NextIdFile.txt");
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Task(String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt) throws NumberFormatException, IOException {
        Scanner scanner = new Scanner(nextIdFile);
        this.id = Integer.parseInt(scanner.next());
        scanner.close();

        FileWriter fileWriter = new FileWriter(nextIdFile);
        fileWriter.write(String.valueOf(this.id + 1));
        fileWriter.flush();
        fileWriter.close();
        
        this.description = description;

        if (status.equalsIgnoreCase("todo") || status.equalsIgnoreCase("in-progress") || status.equalsIgnoreCase("done")) {
            this.status = status.toLowerCase();
        } else {
            System.out.println("Invalid status. Defaulting to 'todo'.");
            this.status = "todo";
        }

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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