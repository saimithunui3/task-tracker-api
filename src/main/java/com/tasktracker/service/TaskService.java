package com.tasktracker.service;

import com.tasktracker.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    List<Task> getAllTasks();
    Task getTaskById(long id);
    Task updateTask(long id, Task task);
    void deletetask(long id);


}
