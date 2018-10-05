package com.interglobe.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interglobe.enums.TaskStatus;
import com.interglobe.model.Task;
import com.interglobe.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskrepo;

	public void createTask(Task task) {
		LocalDate currentDate = LocalDate.now();
		task.setCreatedDate(currentDate);
		task.setStatus(TaskStatus.PENDING);
		taskrepo.save(task);
	}

	public void deleteTask(long id) {
		taskrepo.deleteById(id);
	}

	public Optional<Task> findTaskById(long id) {
		return taskrepo.findById(id);
	}

	public void updateTask(Task task) {
		taskrepo.save(task);
	}
	
	public boolean isTaskExist(long id) {
		return taskrepo.existsById(id);
	}
	
	public List<Task> getAllTask(){
		List<Task> tasks = new ArrayList<>();
		taskrepo.findAll().forEach(t ->tasks.add(t) );
		return tasks;
	}
}
