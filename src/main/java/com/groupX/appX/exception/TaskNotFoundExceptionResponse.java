package com.groupX.appX.exception;

public class TaskNotFoundExceptionResponse {

    private String ProjectNotFound;

    public TaskNotFoundExceptionResponse(String projectNotFound) {
        ProjectNotFound = projectNotFound;
    }

    public String getProjectNotFound() {
        return ProjectNotFound;
    }

    public void setProjectNotFound(String projectNotFound) {
        ProjectNotFound = projectNotFound;
    }
}
