package com.stm.marvel.Controllers;

import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Services.CharacterService;
import com.stm.marvel.Services.ComicsService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
}
