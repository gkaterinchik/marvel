package com.stm.marvel.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import com.stm.marvel.Entities.Character;

@Data
@Accessors(chain = true)
@Schema(description = "Сущность персонажа без списка комиксов")
public class CharacterWithoutComicsDTO {
    @Schema(description = "Идентификатор персонажа в БД")
    private Integer id;
    @Schema(description = "Имя персонажа")
    private String name;
    @Schema(description = "Описание персонажа")
    private String description;
    @Schema(description = "Иконка персонажа")
    private String thumbnail_uri;

    public CharacterWithoutComicsDTO(Character character){
        this.id=character.getId();
        this.name= character.getName();
        this.description= character.getDescription();
        this.thumbnail_uri= character.getThumbnailUri();
    }
}
