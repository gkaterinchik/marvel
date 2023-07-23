package com.stm.marvel.Services;

import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Entities.Comics;
import org.springframework.data.domain.Page;

public interface ComicsService {
Page<ComicsDTO> findComisByCharacterId(Integer characterId);
Page<ComicsDTO> findAll();
Comics findById(Integer id);

ComicsDTO save(ComicsDTO comics);

}
