package com.stm.marvel.Controllers;

import com.stm.marvel.DTO.CharacterDTO;
import com.stm.marvel.DTO.ComicsDTO;
import com.stm.marvel.Exceptions.ElementNotFound;
import com.stm.marvel.Services.CharacterService;
import com.stm.marvel.Services.ComicsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/public/comics")
@Tag(name="Контроллер комиксов",description = "Запрос списка всех комиксов, добавление новых комиксов," +
        "запрос комикса по идентификатору, изменение комикса")
public class ComicsController {
    ComicsService comicsService;
    CharacterService characterService;
    public ComicsController(ComicsService comicsService, CharacterService characterService){
        this.comicsService=comicsService;
        this.characterService=characterService;
    }
    @GetMapping
    @Operation(
            summary = "Запрос всех комиксов",
            description = "Извлекает список комиксов с дополнительными фильтрами"
    )
    public Page<ComicsDTO> getComics(@RequestParam(name = "p", defaultValue = "1")@Parameter(description = "Номер страницы") Integer page,
            @RequestParam(name = "title", required = false)@Parameter(description = "дополнительный фильтр по названию") String name,
            @RequestParam(name = "description", required = false)@Parameter(description = "дополнительный фильтр по описанию") String description,
            @RequestParam(name = "characters", required = false)@Parameter(description = "Фильтр по персонажу") Integer characters) {
        if (page < 1) {
            page = 1;
        }
        return comicsService.find(name, description, characters, page).map(ComicsDTO::new);


    }
    @GetMapping("/{comicsId}")
    @Operation(
            summary = "Запрос комикса по идентификатору",
            description = "Извлекает комикс с указанным идентификатором"
    )
    public ComicsDTO comicsById(@PathVariable @Parameter(description = "Идентификатор комикса") Integer comicsId) {
        return comicsService.findById(comicsId).map(ComicsDTO::new)
                .orElseThrow(() -> new ElementNotFound("comics not Found, id= " +comicsId));

    }
    @GetMapping("/{ComicsID}/characters")
    @Operation(
            summary = "Запрос списка персонажей в комиксе с указанным идентификатором с дополнительными фильтрами",
            description = "Извлекает персонажей, которые есть в комиксе с указанным идентификаторм. " +
                    "Если таких персонажей нет, то возвращается пустой список "
    )
    public Page<CharacterDTO> getCharactersByComicsId(@RequestParam(name = "p", defaultValue = "1")@Parameter(description = "Номер страницы") Integer page,
                                                         @RequestParam(name = "name", required = false)@Parameter(description = "дополнительный фильтр по имени персонажа") String name,
                                                         @RequestParam(name = "description", required = false)@Parameter(description = "дополнительный фильтр по описанию") String description,
                                                         @PathVariable @Parameter(description = "Идентификатор комикса") Integer ComicsID) {
        if (page < 1) {
            page = 1;
        }
        return characterService.find(name, description, ComicsID, page).map(CharacterDTO::new);
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            summary = "Создание нового комикса",
            description = "Запрос на добавление в БД нового комикса"
    )
    public ComicsDTO createComics(@RequestPart(name = "file", required = false)@Parameter(description = "Файл с иконкой персонажа") MultipartFile file,
                                        @RequestPart("comics")@Parameter(description = "Объект с данными создаваемого комикса") ComicsDTO comicsDTO,
                                        @RequestPart(name= "charactersIds",required = false)@Parameter(description = "Список идентификаторов персонажей которые присутствуют в данном комиксе. Идентификаторы пишутся через запятую без пробелов")String ids) {
        return comicsService.save(comicsDTO, file,ids);

    }

    @PutMapping("/{comicsID}")
    public ComicsDTO editCharacter(@RequestPart(name = "file", required = false)@Parameter(description = "Файл с иконкой персонажа") MultipartFile file,
                                      @RequestPart(name = "comics") @Parameter(description = "Объект с данными создаваемого комикса")ComicsDTO comics,
                                      @RequestPart(name= "charactersIds",required = false)@Parameter(description = "Список идентификаторов персонажей которые присутствуют в данном комиксе. Идентификаторы пишутся через запятую без пробелов")String ids,
                                      @PathVariable Integer comicsID ) {


        return comicsService.edit(comics, file, ids, comicsID);
    }
}
