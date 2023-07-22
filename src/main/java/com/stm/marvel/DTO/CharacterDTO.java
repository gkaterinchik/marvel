package com.stm.marvel.DTO;

import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class CharacterDTO {

    private Integer id;
    private String name;
    private String description;
    private List<ComicsWithoutCharactersDTO> comics;

    public CharacterDTO(Character character){
        this.id=character.getId();
        this.name=character.getName();
        this.description=character.getDescription();
        this.comics=character.getComics().stream().map(ComicsWithoutCharactersDTO::new).collect(Collectors.toList());

    }


}
