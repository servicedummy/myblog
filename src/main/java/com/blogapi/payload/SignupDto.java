package com.blogapi.payload;

import lombok.Data;

@Data
public class SignupDto {
    private String name;
    private String email;
    private String username;
    private String password;
}
