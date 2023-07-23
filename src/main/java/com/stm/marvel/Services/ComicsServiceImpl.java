package com.stm.marvel.Services;

import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Entities.Comics;
import com.stm.marvel.Repositories.ComicsRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicsServiceImpl implements ComicsService{
    ComicsRepository comicsRepository;

    public ComicsServiceImpl(ComicsRepository comicsRepository) {
        this.comicsRepository = comicsRepository;
    }

    @Override
    public Page<ComicsDTO> findComisByCharacterId(Integer characterId) {
        return null;
    }

    @Override
    public Page<ComicsDTO> findAll() {
        return null;
    }

    @Override
    public Comics findById(Integer id) {
        return comicsRepository.findById(id).orElseThrow();
    }

    @Override
    public ComicsDTO save(ComicsDTO comics) {
        return null;
    }
}
