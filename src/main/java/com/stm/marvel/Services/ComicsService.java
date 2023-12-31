package com.stm.marvel.Services;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Entities.Comics;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ComicsService {
   // Page<Comics> getAllComicsByCharacterId(Integer characterId,String name, String description, Integer characters,Integer page);

    Page<ComicsDTO> findAll();

    Optional<Comics> findById(Integer id);

    ComicsDTO save(ComicsDTO comics);

    Page<Comics> find(String name, String description, Integer comics, Integer page,String sortBy);
    ComicsDTO save(ComicsDTO character, MultipartFile file, String ids);
    ComicsDTO edit(ComicsDTO character, MultipartFile file,String ids,Integer ComicsId);

}