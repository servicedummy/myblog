package com.student.controller;

import com.student.entity.User;
import com.student.payload.UserDto;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create (@RequestBody UserDto userDto){
        UserDto dto = userService.create(userDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserDto> getAllRecords(){
        List<UserDto> dtos = userService.getAllRecords();
        return dtos;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id){
      userService.delete(id);
      return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto, @PathVariable("id") long id){
        UserDto update = userService.update(userDto, id);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
}
