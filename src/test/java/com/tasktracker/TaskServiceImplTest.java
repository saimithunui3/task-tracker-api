// src/test/java/com/tasktracker/service/impl/TaskServiceImplTest.java
package com.tasktracker;


import com.tasktracker.model.Task;
import com.tasktracker.repository.TaskRepository;
import com.tasktracker.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTask = new Task(1L, "Test Task", "Test Description", false);
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(sampleTask)).thenReturn(sampleTask);
        Task created = taskService.createTask(sampleTask);
        assertEquals("Test Task", created.getTitle());
        verify(taskRepository, times(1)).save(sampleTask);
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(sampleTask);
        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> result = taskService.getAllTasks();
        assertEquals(1, result.size());
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        Task task = taskService.getTaskById(1L);
        assertEquals("Test Task", task.getTitle());
    }

    @Test
    void testUpdateTask() {
        Task updatedDetails = new Task(null, "Updated Title", "Updated Desc", true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task updated = taskService.updateTask(1L, updatedDetails);
        assertEquals("Updated Title", updated.getTitle());
        assertTrue(updated.isCompleted());
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);
        taskService.deletetask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
