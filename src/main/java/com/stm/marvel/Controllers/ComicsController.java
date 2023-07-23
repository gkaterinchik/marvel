package com.stm.marvel.Controllers;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Services.CharacterService;
import com.stm.marvel.Services.ComicsService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/public/comics")
public class ComicsController {
    ComicsService comicsService;
    CharacterService characterService;
    public ComicsController(ComicsService comicsService, CharacterService characterService){
        this.comicsService=comicsService;
        this.characterService=characterService;
    }
    @GetMapping
    public Page<ComicsDTO> getComics(@RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "title", required = false) String name,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "characters", required = false) Integer characters) {
        if (page < 1) {
            page = 1;
        }
        return comicsService.find(name, description, characters, page).map(ComicsDTO::new);


    }
    @GetMapping("/{comicsId}")
    public ComicsDTO comicsById(@PathVariable Integer comicsId) {
        return comicsService.findById(comicsId).map(ComicsDTO::new)
                .orElseThrow(() -> new ElementNotFound("comics not Found, id= " +comicsId));

    }
    @GetMapping("/{ComicsID}/characters")
    public Page<CharacterDTO> getCharactersByComicsId(@RequestParam(name = "p", defaultValue = "1") Integer page,
                                                         @RequestParam(name = "name", required = false) String name,
                                                         @RequestParam(name = "description", required = false) String description,
                                                         @PathVariable Integer ComicsID) {
        if (page < 1) {
            page = 1;
        }
        return characterService.find(name, description, ComicsID, page).map(CharacterDTO::new);
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ComicsDTO createComics(@RequestPart(name = "file", required = false) MultipartFile file,
                                        @RequestPart("comics") ComicsDTO comics,
                                        @RequestPart(name= "charactersIds",required = false)String ids) {
        return comicsService.save(comics, file,ids);

    }

    @PutMapping("/{comicsID}")
    public ComicsDTO editCharacter(@RequestPart(name = "file", required = false) MultipartFile file,
                                      @RequestPart(name = "comics") ComicsDTO comics,
                                      @RequestPart(name= "charactersIds",required = false)String ids,
                                      @PathVariable Integer comicsID ) {


        return comicsService.edit(comics, file, ids, comicsID);
    }
}
