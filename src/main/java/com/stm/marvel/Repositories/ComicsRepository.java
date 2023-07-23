package com.stm.marvel.Repositories;

import com.stm.marvel.Entities.Comics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicsRepository extends JpaRepository<Comics, Integer>, JpaSpecificationExecutor<Comics> {


}
