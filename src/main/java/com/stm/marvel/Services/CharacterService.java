package com.stm.marvel.Services;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.Entities.Character;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CharacterService {
Page<Character> find(String name,String nameStartsWith, Integer comics, Integer page);
Optional<Character> findById(Integer id);
CharacterDTO save(CharacterDTO character, MultipartFile file,String ids);
CharacterDTO edit(CharacterDTO character, MultipartFile file,String ids,Integer characterId);




}
