package com.stm.marvel.Controllers;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Services.CharacterService;
import com.stm.marvel.Services.ComicsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/public/characters")
@SecurityRequirement(name = "JWT")
@Tag(name="Контроллер Персонажей",description = "Запрос списка всех персонажей, добавление новых персонажей," +
        "запрос персонажа по идентификатору, изменение персонажа")
public class CharacterController {
    CharacterService characterService;
    ComicsService comicsService;

    @Autowired
    public CharacterController(CharacterService characterService, ComicsService comicsService) {
        this.characterService = characterService;
        this.comicsService = comicsService;
    }
    @Operation(
            summary = "Запрос всех персонажей",
            description = "Извлекает списки персонажей комиксов с дополнительными фильтрами"
    )
    @GetMapping
    public Page<CharacterDTO> getAllCharacters(@RequestParam(name = "p", defaultValue = "1")@Parameter(description = "Номер страницы") Integer page,
                                               @RequestParam(name = "name", required = false)@Parameter(description = "дополнительный фильтр по имени") String name,
                                               @RequestParam(name = "description", required = false) @Parameter(description = "дополнительный фильтр по описанию")String description,
                                               @RequestParam(name = "comics", required = false)@Parameter(description = "Фильтр по идентификатору комикса в котором есть данный персонаж") Integer comics,
                                               @RequestParam(name = "sortBy", defaultValue = "name")@Parameter(description = "Поле по которому сортировать") String sortBy
                                               ) {
        if (page < 1) {
            page = 1;
        }
        return characterService.find(name, description, comics, page,sortBy).map(CharacterDTO::new);

    }
    @Operation(
            summary = "Запрос персонажа по идентификатору",
            description = "Извлекает персонажа с указанным идентификатором"
    )
    @GetMapping("/{characterId}")
    public CharacterDTO characterById(@PathVariable @Parameter(description = "Идентификатор персонажа") Integer characterId) {
        return characterService.findById(characterId).map(CharacterDTO::new)
                .orElseThrow(() -> new ElementNotFound("character not Found, id= " + characterId));

    }
    @Operation(
            summary = "Запрос всех комиксов по персонажу с указанным идентификатором",
            description = "Извлекает список  комиксов с дополнительными фильтрами"
    )
    @GetMapping("/{characterID}/comics")
    public Page<ComicsDTO> getComicsByCharacterId(@RequestParam(name = "p", defaultValue = "1")@Parameter(description = "Номер страницы") Integer page,
                                                  @RequestParam(name = "title", required = false)@Parameter(description = "Дополнительный фильтр по названию комикса") String name,
                                                  @RequestParam(name = "description", required = false)@Parameter(description = "Дополнительный фильтр по описанию комикса") String description,
                                                  @PathVariable @Parameter(description = "Идентификатор персонажа") Integer characterID,
                                                  @RequestParam(name = "sortBy", defaultValue = "name")@Parameter(description = "Поле по которому сортировать") String sortBy) {
        if (page < 1) {
            page = 1;
        }
        return comicsService.find(name, description, characterID, page,sortBy).map(ComicsDTO::new);
    }
    @Operation(
            summary = "Создание нового персонажа",
            description = "Запрос на добавление в БД нового персонажа"
    )

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CharacterDTO createCharacter(@RequestPart(name = "file", required = false)@Parameter(description = "Файл с иконкой персонажа") MultipartFile file,
                                     @RequestPart("character")@Parameter(description = "Объект с данными создаваемого персонажа") CharacterDTO character,
                                     @RequestPart(name= "comicsIds",required = false)@Parameter(description = "Список идентификаторов комиксов в которых присутствует данный персонаж. Идентификаторы пишутся через запятую без пробелов")String ids) {
        return characterService.save(character, file,ids);

    }
    @Operation(
            summary = "Изменение данных о персонаже",
            description = "Изменение данных о персонаже по идентификатору персонажа"
    )


    @PutMapping("/{characterID}")
    public CharacterDTO editCharacter(@RequestPart(name = "file", required = false)@Parameter(description = "Файл с иконкой персонажа") MultipartFile file,
                                      @RequestPart(name = "character")@Parameter(description = "Объект с данными создаваемого персонажа") CharacterDTO character,
                                      @RequestPart(name= "comicsIds",required = false)@Parameter(description = "Список идентификаторов комиксов в которых присутствует данный персонаж. Идентификаторы пишутся через запятую без пробелов")String ids,
                                      @PathVariable @Parameter(description = "Идентификатор персонажа") Integer characterID ) {


        return characterService.edit(character, file, ids, characterID);
    }


}