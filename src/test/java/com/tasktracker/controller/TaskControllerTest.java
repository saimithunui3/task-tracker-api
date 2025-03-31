package com.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktracker.model.Task;
import com.tasktracker.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTask() throws Exception {
        Task task = new Task(1L, "New Task", "Description", false);
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void testGetAllTasks() throws Exception {
        Task task1 = new Task(1L, "Task 1", "Desc 1", false);
        Task task2 = new Task(2L, "Task 2", "Desc 2", true);

        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetTaskById() throws Exception {
        Task task = new Task(1L, "Test Task", "Desc", true);
        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testUpdateTask() throws Exception {
        Task updated = new Task(1L, "Updated", "Updated Desc", true);
        when(taskService.updateTask(Mockito.eq(1L), any(Task.class))).thenReturn(updated);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
    }
}

