package com.student.service;

import com.student.payload.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    List<UserDto> getAllRecords();

    void delete(long id);

    UserDto update(UserDto userDto, long id);
}
