package com.interglobe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interglobe.exception.CustomTaskException;
import com.interglobe.model.Task;
import com.interglobe.service.TaskService;

@RestController
public class TaskHandlerController {

	@Autowired
	TaskService taskservice;

	@PostMapping("/add")
	public ResponseEntity<?> addTask(@RequestBody Task task) {
		taskservice.createTask(task);
		return new ResponseEntity<String>("Task Created", HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable("id") long taskid) {
		Optional<Task> task = taskservice.findTaskById(taskid);
		if (task.isPresent()) {
			taskservice.deleteTask(taskid);
			return new ResponseEntity<String>("Task Deleted", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<CustomTaskException>(
					new CustomTaskException("Unable to delete the task. Task does not exist."), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateTaskStatus(@PathVariable("id") long id, @RequestBody Task task) {
		Optional<Task> opTask = taskservice.findTaskById(id);
		if (opTask.isPresent()) {
			Task currentTask = opTask.get();
			currentTask.setStatus(task.getStatus());
			taskservice.updateTask(currentTask);
			return new ResponseEntity<Task>(currentTask, HttpStatus.OK);
		} else {
			return new ResponseEntity<CustomTaskException>(new CustomTaskException("Task does not exist"),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/showAllTasks")
	public ResponseEntity<?> showAllTasks() {
		List<Task> tasks = taskservice.getAllTask();
		if ( tasks.size() > 0) {
			return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
		}else {
			return new ResponseEntity<CustomTaskException>(new CustomTaskException("There is no task. Create one."),
					HttpStatus.OK);
		}
	}
	
	
}
