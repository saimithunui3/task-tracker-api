package com.tasktracker.controller;

import com.tasktracker.model.Task;
import com.tasktracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskWebController {

    private final TaskService service;

    @Autowired
    public TaskWebController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/tasks")
    public String viewTasks(@RequestParam(value = "status", required = false) String status, Model model) {
        List<Task> tasks;
        if ("completed".equalsIgnoreCase(status)) {
            tasks = service.getAllTasks().stream().filter(Task::isCompleted).toList();
        } else if ("pending".equalsIgnoreCase(status)) {
            tasks = service.getAllTasks().stream().filter(t -> !t.isCompleted()).toList();
        } else {
            tasks = service.getAllTasks();
        }
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/tasks/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute Task task) {
        System.out.println("Saving Task: " + task); //
        if (task.getId() == null) {
            service.createTask(task);
        } else {
            service.updateTask(task.getId(), task);
        }
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Task task = service.getTaskById(id);
        if (task == null) {
            // Redirect to task list if invalid ID
            return "redirect:/tasks";
        }
        model.addAttribute("task", task);
        return "task-form";
    }



    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        service.deletetask(id);
        return "redirect:/tasks";
    }
}
