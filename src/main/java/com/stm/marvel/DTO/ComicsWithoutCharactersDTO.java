package com.stm.marvel.DTO;

import com.stm.marvel.Entities.Comics;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@Schema(description = "Сущность комикса без списка персонажей")
public class ComicsWithoutCharactersDTO  {
    @Schema(description = "Идентификатор комикса в БД")
    private Integer id;
    @Schema(description = "Название комикса")
    private String title;
    @Schema(description = "Создатель комикса")
    private String creator;
    @Schema(description = "Путь к файлу с обложкой комикса")
    private String coverUri;

    public ComicsWithoutCharactersDTO(Comics comics) {
        this.id = comics.getId();
        this.title = comics.getTitle();
        this.creator = comics.getCreator();
        this.coverUri = comics.getCoverUri();
    }
}
