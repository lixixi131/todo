package com.example.todo.controller;


import com.example.todo.dto.ResponseDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.model.TodoEntity;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
//@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService service;


    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        try{
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(userId);
            entity.setId(null);

            List<TodoEntity> entities = service.create(entity);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response  = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);

        }catch(Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }






    }

    @GetMapping("/update")
    public ResponseEntity<?>update(@AuthenticationPrincipal String userId , @RequestBody TodoDTO dto){

        TodoEntity entity = TodoDTO.toEntity(dto);

        entity.setUserId(userId);

        List<TodoEntity> entities = service.update(entity);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();


        return ResponseEntity.ok().body(response);
    }


    @PutMapping
    public ResponseEntity<?>updateTodo(@AuthenticationPrincipal String userId ,@RequestBody TodoDTO dto){

        TodoEntity entity = TodoDTO.toEntity(dto);

        entity.setUserId(userId);

        List<TodoEntity> entities = service.updateTodo(entity);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();


        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal String userId ,@RequestBody TodoDTO dto){
        try{
            List<String> message = new ArrayList<>();
            String msg = service.delete(dto.getId());
            message.add(msg);

            ResponseDTO<String> response = ResponseDTO.<String>builder().data(message).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }

    }

    @DeleteMapping("/checked")
    public ResponseEntity<?> deleteChecked(@AuthenticationPrincipal String userId , @RequestBody List<TodoDTO> requestDtos){
        System.out.println("delete checked All Todo");
        try{

        	
            for(TodoDTO dto : requestDtos){
            	if(dto.isDone()) {
                    System.out.println(dto.getTitle());
                    service.delete(dto.getId());
            	}

            }

            List<TodoEntity> entities = service.retrive(userId);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/checked")
    public ResponseEntity<?> checkAllTodo(@AuthenticationPrincipal String userId , @RequestBody List<TodoDTO> requestDtos){

        for(TodoDTO dto : requestDtos){
            System.out.println(dto.getTitle());
            service.updateDone(userId , dto.getId() , true);
        }

        List<TodoEntity> entities = service.retrive(userId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
        //String temporaryUserId = "temporary-user";
        List<TodoEntity> entities = service.retrive(userId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/helloWorld")
    public String helloWorld(){
        return "hello world";
    }




}
