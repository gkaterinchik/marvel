package com.stm.marvel.Services;

import com.stm.marvel.Entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(String username, String password);
}
