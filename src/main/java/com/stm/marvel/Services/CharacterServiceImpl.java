package com.stm.marvel.Services;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Exceptions.SuchElementAlreadyExist;
import com.stm.marvel.Repositories.CharacterRepository;
import com.stm.marvel.Repositories.Specifications.CharacterSpecification;
import com.stm.marvel.Repositories.Specifications.ComicsSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static java.util.Optional.ofNullable;

@Service
public class CharacterServiceImpl implements CharacterService {
    @Autowired
    @Lazy
    CharacterRepository characterRepository;
    @Autowired
    StorageService fileSystemStorageService;
    @Autowired
    ComicsService comicsService;
    public void setCharacterRepository(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public void setFileSystemStorageService(StorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    public void setComicsService(ComicsService comicsService) {
        this.comicsService = comicsService;
    }


//    public CharacterServiceImpl(CharacterRepository characterRepository, StorageService fileSystemStorageService) {
//
//        this.characterRepository = characterRepository;
//        this.fileSystemStorageService = fileSystemStorageService;
//    }

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
        return characterRepository.findAll(spec, PageRequest.of(page - 1, 5, Sort.by("name").descending()));
    }

    @Override
    public Optional<Character> findById(Integer id) {
        return characterRepository.findById(id);
    }

    @Transactional
    @Override
    public CharacterDTO save(CharacterDTO incomingCharacter, MultipartFile file, String ids) {
        List<Comics> comicsList = new ArrayList<>();
        if (characterRepository.findByName(incomingCharacter.getName()).isPresent())
            throw new SuchElementAlreadyExist("character with name= " + incomingCharacter.getName() + " already Exist");
        if (!Objects.equals(ids, "") && ids != null)
            comicsList = getComicsListById(ids);

        String imageUri = fileSystemStorageService.store(file);
        incomingCharacter.setThumbnail_uri(imageUri);
        Character character = new Character()
                .setName(incomingCharacter.getName())
                .setDescription(incomingCharacter.getDescription())
                .setThumbnailUri(incomingCharacter.getThumbnail_uri())
                .setComics(comicsList);
        return new CharacterDTO(characterRepository.save(character));
    }

    @Override
    public CharacterDTO edit(CharacterDTO incomingCharacter, MultipartFile file, String ids, Integer characterId) {
        List<Comics> comicsList = new ArrayList<>();
        Optional<Character> character = characterRepository.findById(characterId);
        if (character.isEmpty())
            throw new ElementNotFound("character with id= " + characterId + " not found");
        if (!Objects.equals(ids, "") && ids != null)
            comicsList = getComicsListById(ids);

        ofNullable(incomingCharacter.getName()).map(character.get()::setName);
        ofNullable(incomingCharacter.getDescription()).map(character.get()::setDescription);
        ofNullable(incomingCharacter.getThumbnail_uri()).map(character.get()::setThumbnailUri);
        ofNullable(comicsList).map(character.get()::setComics);
        try {
            return new CharacterDTO(characterRepository.save(character.get()));
        } catch (Exception e) {
            throw new SuchElementAlreadyExist(e.getCause().getCause().getLocalizedMessage());
        }


    }

    @Override
    public Page<Character> getAllCharactersByComicsId(Integer ComicsId, String name, String description, Integer comics, Integer page) {
        Specification<Character> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(CharacterSpecification.nameLike(name));
        }
        if (description != null) {
            spec = spec.and(CharacterSpecification.descriptionLike(description));
        }

        spec = spec.and(CharacterSpecification.byComicsId(ComicsId));
        return characterRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    List<Comics> getComicsListById(String ids) {
        List<Integer> IntList = Arrays.stream(ids.split(",")).toList().stream().map(Integer::parseInt).toList();
        List<Comics> comicsList = new ArrayList<>();
        for (Integer id : IntList) {
            try {
                comicsList.add(comicsService.findById(id).orElseThrow(() -> new ElementNotFound("Comics with id = " + id + " not exist")));
            } catch (Exception e) {
                throw new ElementNotFound(e.getMessage());
            }
        }
        return comicsList;

    }


}
