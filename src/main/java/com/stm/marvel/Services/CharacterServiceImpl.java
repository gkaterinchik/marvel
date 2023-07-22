package com.stm.marvel.Services;

import com.stm.marvel.Entities.Character;
import com.stm.marvel.Repositories.CharacterRepository;
import com.stm.marvel.Repositories.Specifications.CharacterSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService{
    CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Page<Character> find(String name, String description, Integer comics, Integer page) {
        Specification<Character> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(CharacterSpecification.nameLike(name));
        }
        if (description != null) {
            spec = spec.and(CharacterSpecification.descriptionLike(description));
        }
        if (comics != null) {
            spec = spec.and(CharacterSpecification.ContainsComicsWhithId(comics));
        }
        return characterRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    @Override
    public Optional<Character> findById(Integer id) {
        return characterRepository.findById(id);
    }
}
