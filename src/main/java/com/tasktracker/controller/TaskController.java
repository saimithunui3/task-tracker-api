package com.tasktracker.controller;


import com.tasktracker.model.Task;
import com.tasktracker.service.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService Service;


    public TaskController(TaskService service) {
        this.Service = service;
    }
    @PostMapping
    public Task create(@RequestBody Task task){
        return Service.createTask(task);
    }
    @GetMapping
    public List<Task>getAll(){
        return Service.getAllTasks();
    }
    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id){
        return Service.getTaskById(id);
    }
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task){
        return Service.updateTask(id, task);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Service.deletetask(id);
    }
}
