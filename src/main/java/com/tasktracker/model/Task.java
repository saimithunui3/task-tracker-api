package com.tasktracker.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean completed;

    @Column(name = "due_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

}
