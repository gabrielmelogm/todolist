package tech.gabrielmelo.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.gabrielmelo.todolist.models.TaskModel;
import tech.gabrielmelo.todolist.repository.ITaskRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping()
  public TaskModel create(@RequestBody TaskModel taskModel) {
    var task = this.taskRepository.save(taskModel);
    return task;
  }
}
