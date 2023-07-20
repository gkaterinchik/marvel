package com.stm.marvel.Services;

import com.stm.marvel.Entities.Role;
import com.stm.marvel.Entities.User;
import com.stm.marvel.Repositories.RoleRepository;
import com.stm.marvel.Repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {

    UserRepository repository;
    RoleRepository roleRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    // @Transactional
    public User createUser(String username, String password) {
        ArrayList<Role> roles = new ArrayList<>();
        System.out.println("Create USER");
        roles.add(roleRepository.findFirstByName("ROLE_USER"));
        System.out.println(roles);
        password = encoder.encode(password);
        User user = new User().setPassword(password).setUsername(username).setRoles(roles);
        System.out.println(user.toString());
        if (repository.findByUsername(username) == null) {
            return repository.saveAndFlush(user);
        }
        throw new RuntimeException("такой пользователь уже существует");
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}
