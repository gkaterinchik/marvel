package com.stm.marvel.Repositories;

import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComicsRepository extends JpaRepository<Comics, Integer>, JpaSpecificationExecutor<Comics> {
    Optional<Comics> findByTitle(String title);

}
