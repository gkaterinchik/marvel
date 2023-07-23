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
public class ComicsDTO {
    private Integer id;
    private String title;
    private String creator;
    private String coverUri;
    private String description;
    private List<CharacterWithoutComicsDTO> characters;

    public ComicsDTO(Comics comics){
        this.id=comics.getId();
        this.title=comics.getTitle();
        this.description=comics.getDescription();
        this.coverUri= comics.getCoverUri();
        if (comics.getCharacters()!=null)
            this.characters=comics.getCharacters().stream().map(CharacterWithoutComicsDTO::new).collect(Collectors.toList());
    }
}
