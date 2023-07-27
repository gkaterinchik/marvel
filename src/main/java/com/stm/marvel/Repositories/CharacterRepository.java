package com.stm.marvel.Repositories;

import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character,Integer>, JpaSpecificationExecutor<Character> {
Optional<Character> findByName(String name);
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "comics"
            }

    )
    Page<Character> findAll(Specification<Character> spec, Pageable pageable);



}
