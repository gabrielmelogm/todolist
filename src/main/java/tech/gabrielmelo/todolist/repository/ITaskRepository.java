package tech.gabrielmelo.todolist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.gabrielmelo.todolist.models.TaskModel;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID>{
  
}
