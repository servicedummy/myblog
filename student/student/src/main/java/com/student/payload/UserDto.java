package com.student.payload;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String name;
    private String city;
    private String email;
}
