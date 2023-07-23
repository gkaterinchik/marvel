package com.stm.marvel.DTO;

import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CharacterDTO{

    private Integer id;
    private String name;
    private String description;
    private String thumbnail_uri;
    private List<ComicsWithoutCharactersDTO> comics;

    public CharacterDTO(Character character){
        this.id=character.getId();
        this.name=character.getName();
        this.description=character.getDescription();
        this.thumbnail_uri= character.getThumbnailUri();
        if (character.getComics()!=null)
            this.comics=character.getComics().stream().map(ComicsWithoutCharactersDTO::new).collect(Collectors.toList());

    }
//    public CharacterDTO(CharacterDTO character)  {
//        this.id = character.getId();
//        this.name = character.getName();
//        this.description = character.getDescription();
//        this.thumbnail_uri = character.getThumbnail_uri();
//        if (character.getComics() != null)
//            this.comics = character.getComics().stream().map(c->new ComicsWithoutCharactersDTO(c)).collect(Collectors.toList());


  //  }
    }
