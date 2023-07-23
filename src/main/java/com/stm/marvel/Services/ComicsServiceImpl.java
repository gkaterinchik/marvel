package com.stm.marvel.Services;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Exceptions.SuchElementAlreadyExist;
import com.stm.marvel.Repositories.ComicsRepository;
import com.stm.marvel.Repositories.Specifications.CharacterSpecification;
import com.stm.marvel.Repositories.Specifications.ComicsSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static java.util.Optional.ofNullable;

@Service
public class ComicsServiceImpl implements ComicsService {
    ComicsRepository comicsRepository;
    @Autowired
    @Lazy
    CharacterService characterService;
    FileSystemStorageService fileSystemStorageService;

    public ComicsServiceImpl(ComicsRepository comicsRepository, FileSystemStorageService fileSystemStorageService) {
        this.comicsRepository = comicsRepository;

        this.fileSystemStorageService = fileSystemStorageService;
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

    @Override
    public ComicsDTO save(ComicsDTO comics, MultipartFile file, String ids) {
        List<Character> characterList = new ArrayList<>();
        if (comicsRepository.findByTitle(comics.getTitle()).isPresent())
            throw new SuchElementAlreadyExist("character with name= " + comics.getTitle() + " already Exist");
        if (!Objects.equals(ids, "") && ids != null)
            characterList = getCharacterListById(ids);

        String imageUri = fileSystemStorageService.store(file);
        comics.setCoverUri(imageUri);
        Comics newComics = new Comics()
                .setTitle(comics.getTitle())
                .setDescription(comics.getDescription())
                .setCoverUri(comics.getCoverUri())
                .setCharacters(characterList)
                .setCreator(comics.getCreator());

        return new ComicsDTO(comicsRepository.save(newComics));
    }

    @Override
    public ComicsDTO edit(ComicsDTO comicsDTO, MultipartFile file, String ids, Integer ComicsId) {
        List<Character> characterList = new ArrayList<>();
        String imageUri = fileSystemStorageService.store(file);
        Optional<Comics> comics = comicsRepository.findById(ComicsId);
        if (comics.isEmpty())
            throw new ElementNotFound("character with id= " + ComicsId + " not found");
        if (!Objects.equals(ids, "") && ids != null)
            characterList = getCharacterListById(ids);

        ofNullable(comicsDTO.getTitle()).map(comics.get()::setTitle);
        ofNullable(comicsDTO.getDescription()).map(comics.get()::setDescription);
        ofNullable(imageUri).map(comics.get()::setCoverUri);
        ofNullable(characterList).map(comics.get()::setCharacters);
        ofNullable(comicsDTO.getCreator()).map(comics.get()::setCreator);
        try {
            return new ComicsDTO(comicsRepository.save(comics.get()));
        } catch (Exception e) {
            throw new SuchElementAlreadyExist(e.getCause().getCause().getLocalizedMessage());
        }
    }


    List<Character> getCharacterListById(String ids) {
        List<Integer> IntList = Arrays.stream(ids.split(",")).toList().stream().map(Integer::parseInt).toList();
        List<Character> characterList = new ArrayList<>();
        for (Integer id : IntList) {
            try {
                characterList.add(characterService.findById(id).orElseThrow(() -> new ElementNotFound("Character with id = " + id + " not exist")));
            } catch (Exception e) {
                throw new ElementNotFound(e.getMessage());
            }
        }
        return characterList;

    }

}
//    public Page<Comics> getAllComicsByCharacterId(String name, String description, Integer characters,Integer page){
//        Specification<Comics> spec = Specification.where(null);
//        if (name != null) {
//            spec = spec.and(ComicsSpecification.titleLike(name));
//        }
//        if (description != null) {
//            spec = spec.and(ComicsSpecification.descriptionLike(description));
//        }
//
//        spec = spec.and(ComicsSpecification.byCharacterId(characters));
//        return comicsRepository.findAll(spec, PageRequest.of(page - 1, 5));
//    }

