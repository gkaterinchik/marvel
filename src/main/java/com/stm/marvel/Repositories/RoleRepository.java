package com.stm.marvel.Repositories;

import com.stm.marvel.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findFirstByName(String name);
}
