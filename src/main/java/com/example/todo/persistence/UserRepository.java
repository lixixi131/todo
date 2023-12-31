package com.example.todo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todo.model.UserEntity;
public interface UserRepository extends JpaRepository<UserEntity , String> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    UserEntity findByEmailAndPassword(String email , String password);
}
