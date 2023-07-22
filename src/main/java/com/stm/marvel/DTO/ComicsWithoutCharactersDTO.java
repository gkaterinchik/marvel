package com.stm.marvel.DTO;

import com.stm.marvel.Entities.Comics;
import lombok.Data;

@Data
public class ComicsWithoutCharactersDTO {
    private Integer id;
    private String title;
    private String creator;
    private String coverUri;

    public ComicsWithoutCharactersDTO(Comics comics) {
        this.id = comics.getId();
        this.title = comics.getTitle();
        this.creator = comics.getCreator();
        this.coverUri = comics.getCoverUri();
    }
}
