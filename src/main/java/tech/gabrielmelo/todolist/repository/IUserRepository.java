package tech.gabrielmelo.todolist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.gabrielmelo.todolist.models.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, UUID>{
  UserModel findByUsername(String username);
}
