package com.tasktracker.service.impl;

import com.tasktracker.model.Task;
import com.tasktracker.repository.TaskRepository;
import com.tasktracker.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }


    public Task createTask(Task task) {
        return repository.save(task);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(long id) {
        return repository.findById(id).orElse(null);
    }

    public Task updateTask(long id, Task updatedTask) {
        Task existingTask = repository.findById(id).orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setCompleted(updatedTask.isCompleted());
            existingTask.setDueDate(updatedTask.getDueDate());
            return repository.save(existingTask);
        }
        return null;
    }

    public void deletetask(long id) {
        repository.deleteById(id);

    }

}
