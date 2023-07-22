package com.stm.marvel.Repositories;

import com.stm.marvel.Entities.Comics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicsRepository extends JpaRepository<Comics,Integer>, JpaSpecificationExecutor<Comics> {
    @Query("select c from Character c join fetch c.comics where c.id = :id")
    List<Comics>findByCharacterId(Integer id);
}
