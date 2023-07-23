package com.stm.marvel.Services;

import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Repositories.ComicsRepository;
import com.stm.marvel.Repositories.Specifications.CharacterSpecification;
import com.stm.marvel.Repositories.Specifications.ComicsSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComicsServiceImpl implements ComicsService{
    ComicsRepository comicsRepository;

    public ComicsServiceImpl(ComicsRepository comicsRepository) {
        this.comicsRepository = comicsRepository;
    }



    @Override
    public Page<ComicsDTO> findAll() {
        return null;
    }

    @Override
    public Optional<Comics> findById(Integer id) {
        return comicsRepository.findById(id);
    }

    @Override
    public ComicsDTO save(ComicsDTO comics) {
        return null;
    }

    @Override
    public Page<Comics> find(String name, String description, Integer comics, Integer page) {
        Specification<Comics> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(ComicsSpecification.titleLike(name));
        }
        if (description != null) {
            spec = spec.and(ComicsSpecification.descriptionLike(description));
        }
        if (comics != null) {
            spec = spec.and(ComicsSpecification.ContainsCharacterWhithId(comics));
        }
        return comicsRepository.findAll(spec, PageRequest.of(page - 1, 5, Sort.by("title").descending()));
    }
    public Page<Comics> getAllComicsByCharacterId(Integer characterId,String name, String description, Integer characters,Integer page){
        Specification<Comics> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(ComicsSpecification.titleLike(name));
        }
        if (description != null) {
            spec = spec.and(ComicsSpecification.descriptionLike(description));
        }

        spec = spec.and(ComicsSpecification.byCharacterId(characterId));
        return comicsRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }
}
