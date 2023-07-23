package com.stm.marvel.Controllers;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.DTO.CreateCharacterResponse;
import com.stm.marvel.Entities.Character;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Services.CharacterService;
import com.stm.marvel.Services.ComicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/public/characters")
public class CharacterController {
    CharacterService characterService;
    ComicsService comicsService;

    @Autowired
    public CharacterController(CharacterService characterService, ComicsService comicsService) {
        this.characterService = characterService;
        this.comicsService = comicsService;
    }

    @GetMapping
    public Page<CharacterDTO> getAllCharacters(@RequestParam(name = "p", defaultValue = "1") Integer page,
                                               @RequestParam(name = "name", required = false) String name,
                                               @RequestParam(name = "description", required = false) String description,
                                               @RequestParam(name = "comics", required = false) Integer comics) {
        if (page < 1) {
            page = 1;
        }
        return characterService.find(name, description, comics, page).map(CharacterDTO::new);

    }

    @GetMapping("/{characterId}")
    public CharacterDTO characterById(@PathVariable Integer characterId) {
        return characterService.findById(characterId).map(CharacterDTO::new)
                .orElseThrow(() -> new ElementNotFound("character not Found, id= " + characterId));

    }

    @GetMapping("/{characterID}/comics")
    public List<ComicsDTO> getComicsByCharacterId(Integer characterId) {
        return null;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CharacterDTO createCharacter(@RequestPart(name = "file", required = false) MultipartFile file,
                                     @RequestPart("character") CharacterDTO character,
                                     @RequestPart(name= "comicsIds",required = false)String ids) {
        return characterService.save(character, file,ids);

    }

    @PutMapping("/{characterID}")
    public CharacterDTO editCharacter(@RequestPart(name = "file", required = false) MultipartFile file,
                                      @RequestPart(name = "character") CharacterDTO character,
                                      @RequestPart(name= "comicsIds",required = false)String ids,
                                      @PathVariable Integer characterID ) {


        return characterService.edit(character, file, ids, characterID);
    }


}