package com.stm.marvel.DTO;

import lombok.Data;

@Data
public class CreateUserRequest {
    public String username;
    public String password;
    public String email;
}
