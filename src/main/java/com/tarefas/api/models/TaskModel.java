package com.tarefas.api.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TB_TASKS")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idTask;
    private String title;
    private String situation;
    private String description;
    private String responsible;
    private String priority;
    private LocalDate deadline;
}