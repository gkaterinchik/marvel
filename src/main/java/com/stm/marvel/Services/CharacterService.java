package com.stm.marvel.Services;

import com.stm.marvel.Entities.Character;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CharacterService {
Page<Character> find(String name,String nameStartsWith, Integer comics, Integer page);
Optional<Character> findById(Integer id);



}
