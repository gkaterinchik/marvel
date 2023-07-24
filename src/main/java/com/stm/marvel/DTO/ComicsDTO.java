package com.stm.marvel.DTO;

import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "Сущность комикса")
public class ComicsDTO {
    @Schema(description = "Идентификатор комикса в БД")
    private Integer id;
    @Schema(description = "Название комикса")
    private String title;
    @Schema(description = "Создатель комикса")
    private String creator;
    @Schema(description = "Путь к файлу с обложкой комикса")
    private String coverUri;
    @Schema(description = "Описание комикса")
    private String description;
    @Schema(description = "Список персонажей в комиксе")
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
