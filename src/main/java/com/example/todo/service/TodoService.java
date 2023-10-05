package com.example.todo.service;

import com.example.todo.model.TodoEntity;
import com.example.todo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public List<TodoEntity> create(final TodoEntity entity){
        validate(entity);
        repository.save(entity);
        return repository.findByUserId(entity.getUserId());

    }

    public List<TodoEntity> retrive(final String userId){
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity){
        validate(entity);

        if(repository.existsById(entity.getId())){
            repository.save(entity);
        }
        else{
            throw new RuntimeException("unKnown id");
        }

        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity>updateTodo(final TodoEntity entity){
        validate(entity);

        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo ->{
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            repository.save(todo);
        });

        return repository.findByUserId(entity.getUserId());
    }

    @Transactional
    public List<TodoEntity>updateDone(String userId , String id , boolean check){

        List<TodoEntity> entities = repository.findByUserId(userId);

        for(TodoEntity entity : entities){
            if(entity.getId().equals(id)){
                entity.setDone(check);
                repository.save(entity);
            }
        }

        return repository.findByUserId(userId);
    }

    public String deleteCheckedTodo(final String userId){

        //final Optional<TodoEntity> original = repository.findById(entity.getId());
        List<TodoEntity> thing = repository.findByDone(userId);

        for(TodoEntity a : thing){
            delete(a.getId());
        }

        return "deleted";
    }


    public String delete(final String id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        else{
            throw new RuntimeException("id does not exist");
        }

        return "deleted";
    }

    public void validate(final TodoEntity entity){
        if(entity == null){
            log.warn("entity cannot be null");
            throw new RuntimeException("entity cannot be null");
        }

        if(entity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user.");

        }
    }





}
