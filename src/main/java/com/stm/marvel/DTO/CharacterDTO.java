package com.stm.marvel.DTO;

import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class CharacterDTO {

    private Integer id;
    private String name;
    private String description;
    private List<Comics> comics;

    public CharacterDTO(Character character){
        this.id=character.getId();
        this.name=character.getName();
        this.description=character.getDescription();
        this.comics=character.getComics();

    }


}
