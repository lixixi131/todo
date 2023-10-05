package com.example.todo.persistence;

import com.example.todo.dto.TodoDTO;
import com.example.todo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity , String> {

    @Query("select t from TodoEntity t where t.userId = ?1")
    List<TodoEntity> findByUserId(String userId);

    @Query("select t from TodoEntity t where t.userId = ?1 and t.done = true")
    List<TodoEntity> findByDone(String userId);

    @Transactional
    @Modifying
    @Query("update TodoEntity t set t.done = ?3 where t.userId = ?1 and t.id = ?2")
    List<TodoEntity> updateDoneById(String userId , String id , boolean check);
}
