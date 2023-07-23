package com.stm.marvel.Services;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.DTO.ComicsWithoutCharactersDTO;
import com.stm.marvel.DTO.CreateCharacterResponse;
import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Exceptions.SuchElementAlreadyExist;
import com.stm.marvel.Repositories.CharacterRepository;
import com.stm.marvel.Repositories.Specifications.CharacterSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class CharacterServiceImpl implements CharacterService {
    CharacterRepository characterRepository;
    StorageService fileSystemStorageService;
    ComicsService comicsService;


    public CharacterServiceImpl(CharacterRepository characterRepository, StorageService fileSystemStorageService, ComicsService comicsService) {
        this.comicsService = comicsService;
        this.characterRepository = characterRepository;
        this.fileSystemStorageService = fileSystemStorageService;
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
@Transactional
    @Override
    public CharacterDTO save(CharacterDTO incomingCharacter, MultipartFile file,String ids) {
    List<Comics> comicsList=new ArrayList<>();
        if (characterRepository.findByName(incomingCharacter.getName()).isPresent())
            throw new SuchElementAlreadyExist("character with name= " + incomingCharacter.getName() + " already Exist");
        if(!Objects.equals(ids, "")&&ids!=null)
            comicsList = getComicsListById(ids);

        String imageUri = fileSystemStorageService.store(file);
        incomingCharacter.setThumbnail_uri(imageUri);
        Character character = new Character()
                .setName(incomingCharacter.getName())
                .setDescription(incomingCharacter.getDescription())
                .setThumbnailUri(incomingCharacter.getThumbnail_uri())
                .setComics(comicsList);
       // return  characterRepository.save(character);

        return new CharacterDTO(characterRepository.save(character));
    }

List<Comics> getComicsListById(String ids){
        List<Integer>IntList= Arrays.stream(ids.split(",")).toList().stream().map(Integer::parseInt).toList();
        List<Comics>comicsList =new ArrayList<>();
    for (Integer id:IntList) {
        try {
            comicsList.add(comicsService.findById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    return comicsList;

}

}
