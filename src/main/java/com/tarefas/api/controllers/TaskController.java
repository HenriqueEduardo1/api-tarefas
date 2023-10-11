package com.tarefas.api.controllers;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarefas.api.models.TaskModel;
import com.tarefas.api.repositories.TaskRepository;

@RestController
@RequestMapping("/task")
public class TaskController {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @PostMapping("/")
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel){
        return ResponseEntity.ok(this.taskRepository.save(taskModel));
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskModel>> getAll(){
        return ResponseEntity.ok(this.taskRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value="id") UUID id){
        Optional<TaskModel> task = taskRepository.findById(id);

        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        return ResponseEntity.ok(task.get());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value="id") UUID id, @RequestBody TaskModel taskModel){
        Optional<TaskModel> task = taskRepository.findById(id);

        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        var taskU = task.get();
        BeanUtils.copyProperties(taskModel, taskU);

        return ResponseEntity.ok(this.taskRepository.save(taskU));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value="id") UUID id){
        Optional<TaskModel> task = taskRepository.findById(id);

        if(task.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        taskRepository.delete(task.get());

        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
    }
}
