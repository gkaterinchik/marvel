package com.stm.marvel.DTO;

import lombok.Data;
import lombok.experimental.Accessors;
import com.stm.marvel.Entities.Character;

@Data
@Accessors(chain = true)
public class CharacterWithoutComicsDTO {
    private Integer id;
    private String name;
    private String description;
    private String thumbnail_uri;

    public CharacterWithoutComicsDTO(Character character){
        this.id=character.getId();
        this.name= character.getName();
        this.description= character.getDescription();
        this.thumbnail_uri= character.getThumbnailUri();
    }
}
