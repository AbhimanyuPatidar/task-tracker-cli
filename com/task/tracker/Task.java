// Model class for a Task

package com.task.tracker;

import java.time.LocalDateTime;

enum Status {
    PENDING, IN_PROGRESS, COMPLETED
}

public class Task {
    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Task(int id, String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        
        switch (status) {
            case "pending":
                this.status = Status.PENDING;
                break;
            case "in_progress":
                this.status = Status.IN_PROGRESS;
                break;
            case "completed":
                this.status = Status.COMPLETED;
                break;
            default:
                this.status = Status.PENDING;
                break;
        }

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(String status) {
        switch (status) {
            case "pending":
                this.status = Status.PENDING;
                break;
            case "in_progress":
                this.status = Status.IN_PROGRESS;
                break;
            case "completed":
                this.status = Status.COMPLETED;
                break;
            default:
                this.status = Status.PENDING;
                break;
        }
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
        return "Task [id=" + id + ", description=" + description + ", status=" + status + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
    }
}