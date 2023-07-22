package com.stm.marvel.Controllers;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/public/characters")
public class CharacterController {
    CharacterService characterService;
@Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public Page<CharacterDTO> getAllCharacters( @RequestParam(name = "p", defaultValue = "1") Integer page,
                                                @RequestParam(name = "name", required = false) String name,
                                                @RequestParam(name = "nameStartsWith", required = false) String nameStartsWith,
                                                @RequestParam(name = "comics", required = false) Integer comics){
        if (page < 1) {
            page = 1;
        }
return characterService.find(name,nameStartsWith,comics,page).map(CharacterDTO::new);

    }
    @GetMapping("/{characterId}")
    public CharacterDTO characterById(@PathVariable Integer characterId){
    return characterService.findById(characterId).map(CharacterDTO::new)
            .orElseThrow(()->new ElementNotFound("character not Found, id= "+characterId));

    }
    @GetMapping("/{characterID}/comics")
    public List<ComicsDTO> getComicsByCharacterId(Integer characterId){
return null;
    }
}
