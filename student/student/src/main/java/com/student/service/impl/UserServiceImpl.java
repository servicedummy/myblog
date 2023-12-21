package com.student.service.impl;

import com.student.entity.User;
import com.student.payload.UserDto;
import com.student.repository.UserRepository;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDto create(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setCity(userDto.getCity());
        user.setEmail(userDto.getEmail());

        User save = userRepo.save(user);
        return mapToDto(save);
    }

    @Override
    public List<UserDto> getAllRecords() {
        List<User> all = userRepo.findAll();
        return all.stream().map(user -> mapToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDto update(UserDto userDto, long id) {
        User user = userRepo.findById(id).get();
        user.setId(user.getId());
        user.setName(userDto.getName());
        user.setCity(userDto.getCity());
        user.setEmail(userDto.getEmail());

        User save = userRepo.save(user);
        return mapToDto(save);
    }

    public UserDto mapToDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setCity(user.getCity());
        dto.setEmail(user.getEmail());

        return dto;
    }
}
