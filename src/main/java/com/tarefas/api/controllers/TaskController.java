package com.tarefas.api.controllers;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarefas.api.dtos.PatchTaskSituationDto;
import com.tarefas.api.dtos.TaskRecordDto;
import com.tarefas.api.models.TaskModel;
import com.tarefas.api.repositories.TaskRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*")
public class TaskController {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @PostMapping()
    public ResponseEntity<TaskModel> createTask(@RequestBody @Valid TaskRecordDto taskRecordDto){
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskRecordDto, taskModel);
        return ResponseEntity.ok(this.taskRepository.save(taskModel));
    }

    @GetMapping()
    public ResponseEntity<List<TaskModel>> getAllTask(){
        return ResponseEntity.ok(this.taskRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTaskById(@PathVariable(value="id") UUID id){
        Optional<TaskModel> task = taskRepository.findById(id);

        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        return ResponseEntity.ok(task.get());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value="id") UUID id, @RequestBody @Valid TaskRecordDto taskRecordDto){
        Optional<TaskModel> task = taskRepository.findById(id);

        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        var taskModel = task.get();
        BeanUtils.copyProperties(taskRecordDto, taskModel);

        return ResponseEntity.ok(this.taskRepository.save(taskModel));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateTaskStatus(@PathVariable(value="id") UUID id, @RequestBody @Valid PatchTaskSituationDto patchTaskSituationDto){
        Optional<TaskModel> task = taskRepository.findById(id);

        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        var taskModel = task.get();
        taskModel.setSituation(patchTaskSituationDto.situation());

        return ResponseEntity.ok(this.taskRepository.save(taskModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value="id") UUID id){
        Optional<TaskModel> task = taskRepository.findById(id);

        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        taskRepository.delete(task.get());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
