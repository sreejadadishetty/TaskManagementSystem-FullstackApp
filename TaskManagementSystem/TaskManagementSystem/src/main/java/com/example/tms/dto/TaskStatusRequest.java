package com.example.tms.dto;

import com.example.tms.entity.TaskStatus;

public class TaskStatusRequest {
    private TaskStatus status;

    // Default Constructor
    public TaskStatusRequest() {}

    // Constructor
    public TaskStatusRequest(TaskStatus status) {
        this.status = status;
    }

    // Getter
    public TaskStatus getStatus() {
        return status;
    }

    // Setter
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskStatusRequest{" +
                "status=" + status +
                '}';
    }
}
