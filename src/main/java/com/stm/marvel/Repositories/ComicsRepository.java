package com.stm.marvel.Repositories;

import com.stm.marvel.Entities.Comics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComicsRepository extends JpaRepository<Comics, Integer>, JpaSpecificationExecutor<Comics> {
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "characters"
            }

    )
    Page<Comics> findAll(Specification<Comics> spec, Pageable pageable);
    Optional<Comics> findByTitle(String title);

}
